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

  constructor(public dataService: DataService) {
  }

  forge() {
    this.dataService.resetDataset();
    this.currentRecordIndex = 0;
    this.loading = true;
    this.forgeResultForCurrentRecordIndex();
  }

  private forgeResultForCurrentRecordIndex() {
    if (this.currentRecordIndex === undefined || this.currentRecordIndex >= this.dataService.data!.records.length) {
      this.loading = false;
      return;
    }
    const index = this.currentRecordIndex;
    this.dataService.runCompletionOnRecord(this.currentRecordIndex).subscribe(data => {
      this.dataService.updateDataAtIndex(index, 0, data);
      if (this.currentRecordIndex !== undefined) {
        this.currentRecordIndex! += 1;
      }
      this.forgeResultForCurrentRecordIndex();
    });
  }

  stopGenerating() {
    this.currentRecordIndex = undefined;
  }
}
