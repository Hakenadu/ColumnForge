import {AfterViewChecked, Component, Inject, OnInit} from '@angular/core';
import {BreakpointObserver, BreakpointState} from '@angular/cdk/layout';
import {MatDialog} from '@angular/material/dialog';
import {WelcomeComponent} from './welcome/welcome.component';
import {PromptService} from './services/prompt.service';
import {Environment, ENVIRONMENT} from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent implements OnInit {

  splitDirection?: 'horizontal' | 'vertical';

  constructor(public promptService: PromptService,
              private breakpointObserver: BreakpointObserver,
              private matDialog: MatDialog,
              @Inject(ENVIRONMENT) private env: Environment) {
    this.breakpointObserver
      .observe(['(min-width: 576px)'])
      .subscribe((state: BreakpointState) => {
        if (state.matches) {
          this.splitDirection = 'horizontal';
        } else {
          this.splitDirection = 'vertical';
        }
      });
  }

  ngOnInit() {

    // TEMP WIP MESSAGE FOR DEMO PAGE
    if ('column-forge.mseiche.de' === window.location.hostname) {
      this.matDialog.open(WelcomeComponent);
    }

    if (!this.promptService.context) {
      this.promptService.context = `you are a translation system.
translate the following messages to \${destinationLanguage}.
only answer the translation and do not add explanations.
don't be too formal, the translations are meant to be used for a playful website.
translate the text as if you're talking to a friend of yours.
do not translate terms that are commonly used for websites in \${destinationLanguage} language.`;
    }
    if (!this.promptService.query) {
      this.promptService.query = '${text}';
    }
  }
}
