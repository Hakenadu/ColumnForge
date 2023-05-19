import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PromptService} from './prompt.service';
import {saveAs} from 'file-saver';
import {Papa} from 'ngx-papaparse';
import {ApiKeyService} from './api-key.service';
import {Environment, ENVIRONMENT} from '../../environments/environment';

interface TransformationRequest {
  model: string;
  query: string;
  context: string;
  apiKey?: string;
  data: Data;
}

export interface Data {
  type: 'complex';
  header: string[];
  records: string[][];
}

@Injectable({
  providedIn: 'root'
})
export class DataService {

  data?: Data;

  constructor(private httpClient: HttpClient,
              private promptService: PromptService,
              private apiKeyService: ApiKeyService,
              private papa: Papa,
              @Inject(ENVIRONMENT) private env: Environment) {
  }

  get invalidMessages(): string[] {
    const result: string[] = [];
    if (!this.data) {
      result.push('no dataset opened');
      return result;
    }
    if (this.promptService.queryPlaceholders.length === 0) {
      result.push('no placeholders in user message');
    }
    for (const placeholder of [
      ...this.promptService.queryPlaceholders,
      ...this.promptService.contextPlaceholders
    ]) {
      if (placeholder === 'forgedColumn') {
        this.pushMessageIfNotExists(result, 'forgedColumn can\'t be used as a placeholder');
      } else if (!this.data?.header.includes(placeholder)) {
        this.pushMessageIfNotExists(result, `no dataset column with name ${placeholder}`);
      }
    }
    return result;
  }

  private pushMessageIfNotExists(messages: string[], message: string) {
    if (!messages.includes(message)) {
      messages.push(message);
    }
  }

  resetDataset() {
    if (this.data) {
      if (this.data.header.includes('forgedColumn')) {
        const forgedColumnIndex = this.data.header.findIndex(h => h === 'forgedColumn');
        if (forgedColumnIndex >= 0) {
          this.data.header.splice(forgedColumnIndex, 1);
          for (const record of this.data.records) {
            record.splice(forgedColumnIndex, 1);
          }
        }
      }
    }
  }

  runCompletionOnRecord(index: number): Observable<Data> {
    if (!this.data) {
      throw new Error('no data');
    }
    const request: TransformationRequest = {
      model: 'gpt-3.5-turbo',
      query: this.promptService.query!,
      context: this.promptService.context!,
      apiKey: this.apiKeyService.apiKey,
      data: {
        type: 'complex',
        header: this.data.header.filter(h => h !== 'forgedColumn'),
        records: [this.data.records[index]]
      }
    };
    return this.httpClient.post<Data>(`${this.env.apiUrl}/v1/transformations`, request, {});
  }

  updateDataAtIndex(targetIndex: number, sourceIndex: number, sourceData: Data) {
    if (this.data) {
      this.data.header = sourceData.header;
      this.data.records[targetIndex] = sourceData.records[sourceIndex];
      this.data = Object.assign({}, this.data);
    }
  }

  private convertToCSV(): string {
    if (!this.data) {
      throw new Error('no data');
    }
    return this.papa.unparse(
      {
        data: this.data.records,
        fields: this.data.header
      },
      {
        header: true
      });
  }

  public downloadData(filename: string) {
    const csv = this.convertToCSV();
    const blob = new Blob([csv], {type: 'text/csv;charset=utf-8;'});
    saveAs(blob, filename);
  }
}
