import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PromptService {

  prompt$ = new BehaviorSubject<string | undefined>(undefined);

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
    if (prompt !== this.prompt$.value) {
      this.prompt$.next(prompt);
      this.updatePlaceholders();
    }
  }

  get prompt(): string | undefined {
    return this.prompt$.value;
  }
}
