import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiKeyService {

  private _apiKey? = localStorage.getItem('column-forge.api-key') || undefined;

  get apiKey(): string | undefined {
    return this._apiKey;
  }

  set apiKey(apiKey: string | undefined) {
    if (!apiKey) {
      this._apiKey = undefined;
      localStorage.removeItem('column-forge.api-key');
    } else {
      this._apiKey = apiKey;
      localStorage.setItem('column-forge.api-key', apiKey);
    }
  }
}
