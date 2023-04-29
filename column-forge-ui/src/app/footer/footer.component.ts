import {Component} from '@angular/core';
import {DataService} from '../services/data.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {

  loading = false;
  currentRecordIndex?: number;

  constructor(private dataService: DataService) {
  }

  forge() {
    this.currentRecordIndex = 0;
    this.loading = true;
    this.forgeResultForCurrentRecordIndex();
  }

  private forgeResultForCurrentRecordIndex() {
    if (this.currentRecordIndex === undefined || this.currentRecordIndex >= this.dataService.data!.records.length) {
      this.loading = false;
      return;
    }
    this.dataService.runCompletionOnRecord(this.currentRecordIndex).subscribe(data => {
      if (this.currentRecordIndex === undefined) {
        throw new Error('wtf');
      }
      this.dataService.updateDataAtIndex(this.currentRecordIndex, 0, data);
      this.currentRecordIndex! += 1;
      this.forgeResultForCurrentRecordIndex();
    });
  }
}
