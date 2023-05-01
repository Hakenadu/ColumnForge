import {AfterViewChecked, Component, OnInit} from '@angular/core';
import {BreakpointObserver, BreakpointState} from '@angular/cdk/layout';
import {MatDialog} from '@angular/material/dialog';
import {WelcomeComponent} from './welcome/welcome.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent implements OnInit {

  splitDirection?: 'horizontal' | 'vertical';

  constructor(private breakpointObserver: BreakpointObserver, private matDialog: MatDialog) {
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
    this.matDialog.open(WelcomeComponent);
  }
}
