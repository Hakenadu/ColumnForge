import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {PromptComponent} from './prompt/prompt.component';
import {DataComponent} from './data/data.component';
import {NgxResizeObserverModule} from 'ngx-resize-observer';
import {MatButtonModule} from '@angular/material/button';
import {AngularSplitModule} from 'angular-split';
import {FooterComponent} from './footer/footer.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatChipsModule} from '@angular/material/chips';
import {NgParticlesModule} from 'ng-particles';
import {HttpClientModule} from '@angular/common/http';
import { SpinnerComponent } from './spinner/spinner.component';

@NgModule({
  declarations: [
    AppComponent,
    PromptComponent,
    DataComponent,
    FooterComponent,
    SpinnerComponent
  ],
  imports: [
    AngularSplitModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatCardModule,
    MatButtonModule,
    NgxResizeObserverModule,
    MatToolbarModule,
    MatIconModule,
    MatChipsModule,
    NgParticlesModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
