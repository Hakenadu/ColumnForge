import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {PromptService} from './prompt.service';

interface TransformationRequest {
  model: string;
  query: string;
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

  constructor(private httpClient: HttpClient, private promptService: PromptService) {
  }

  runCompletionOnRecord(index: number): Observable<Data> {
    if (!this.data) {
      throw new Error('no data');
    }
    const request: TransformationRequest = {
      model: 'gpt-3.5-turbo',
      query: this.promptService.prompt!,
      data: {
        type: 'complex',
        header: this.data.header.filter(h => h !== 'result'),
        records: [this.data.records[index]]
      }
    };
    return this.httpClient.post<Data>(`${environment.apiUrl}/v1/transformations`, request, {});
  }

  updateDataAtIndex(targetIndex: number, sourceIndex: number, sourceData: Data) {
    if (this.data) {
      console.log(this.data);
      this.data.header = sourceData.header;
      this.data.records[targetIndex] = sourceData.records[sourceIndex];
      this.data = Object.assign({}, this.data);
      console.log(this.data);
    }
  }
}
