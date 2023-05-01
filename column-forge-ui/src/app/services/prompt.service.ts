import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PromptService {

  prompt$ = new BehaviorSubject<string | undefined>(localStorage.getItem('column-forge.prompt') || undefined);

  placeholders: string[] = [];

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
      if (prompt) {
        localStorage.setItem('column-forge.prompt', prompt);
      } else {
        localStorage.removeItem('column-forge.prompt');
      }
      this.updatePlaceholders();
    }
  }

  get prompt(): string | undefined {
    return this.prompt$.value;
  }
}
