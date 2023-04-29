import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PromptService {

  private _prompt?: string;

  placeholders: string[] = [];

  constructor() {
  }

  private updatePlaceholders() {
    if (this.prompt) {
      const matches = this.prompt.match(/\${(.*?)}/g);
      if (matches) {
        this.placeholders = matches.map((match) => match.slice(2, -1));
        return;
      }
    }
    this.placeholders = [];
  }

  set prompt(prompt: string | undefined) {
    if (prompt !== this._prompt) {
      this._prompt = prompt;
      this.updatePlaceholders();
    }
  }

  get prompt(): string | undefined {
    return this._prompt;
  }
}
