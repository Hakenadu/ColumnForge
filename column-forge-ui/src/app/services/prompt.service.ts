import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PromptService {

  query$ = new BehaviorSubject<string | undefined>(localStorage.getItem('column-forge.query') || undefined);
  context$ = new BehaviorSubject<string | undefined>(localStorage.getItem('column-forge.context') || undefined);

  queryPlaceholders: string[] = [];

  contextPlaceholders: string[] = [];


  constructor() {
    this.query$.subscribe(query => {
      if (query) {
        localStorage.setItem('column-forge.query', query);
      } else {
        localStorage.removeItem('column-forge.query');
      }
      this.queryPlaceholders = this.createPlaceholders(query);
    });
    this.context$.subscribe(context => {
      if (context) {
        localStorage.setItem('column-forge.context', context);
      } else {
        localStorage.removeItem('column-forge.context');
      }
      this.contextPlaceholders = this.createPlaceholders(context);
    });
  }

  private createPlaceholders(base: string | undefined): string[] {
    if (base) {
      const matches = base.match(/\${(.*?)}/g);
      if (matches) {
        return [...new Set(matches.map((match) => match.slice(2, -1)))];
      }
    }
    return [];
  }

  set query(query: string | undefined) {
    this.query$.next(query);
  }

  get query(): string | undefined {
    return this.query$.value;
  }

  set context(context: string | undefined) {
    this.context$.next(context);
  }

  get context(): string | undefined {
    return this.context$.value;
  }
}
