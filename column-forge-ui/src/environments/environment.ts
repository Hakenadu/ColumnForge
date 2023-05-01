import {InjectionToken} from '@angular/core';

export interface Environment {
  production: boolean;
  apiUrl: string;
}

export const ENVIRONMENT = new InjectionToken('the ui configuration used for the current runtime environment');

export const environment: Environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
