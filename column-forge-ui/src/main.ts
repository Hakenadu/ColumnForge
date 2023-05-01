import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app/app.module';
import {ENVIRONMENT, environment, Environment} from './environments/environment';
import {enableProdMode} from '@angular/core';

async function main() {
  let env: Environment;
  try {
    const data = await fetch('/environment.json');
    env = await data.json();
  } catch (error) {
    console.warn('no environment.json read => falling back to default')
    env = environment;
  }

  if (env.production) {
    enableProdMode();
  }

  await platformBrowserDynamic([
    {provide: ENVIRONMENT, useValue: env}
  ]).bootstrapModule(AppModule)
    .then(() => console.log(env))
    .catch(err => console.error(err));
}

main();
