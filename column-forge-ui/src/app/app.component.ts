import {Component} from '@angular/core';
import {BreakpointObserver, BreakpointState} from '@angular/cdk/layout';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {

  splitDirection?: 'horizontal' | 'vertical';

  constructor(private breakpointObserver: BreakpointObserver) {
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
}
