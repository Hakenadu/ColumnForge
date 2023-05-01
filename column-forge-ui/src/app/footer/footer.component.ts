import {Component} from '@angular/core';
import {Data, DataService} from '../services/data.service';
import {MatDialog} from '@angular/material/dialog';
import {SettingsComponent} from '../settings/settings.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {

  loading = false;
  currentRecordIndex?: number;

  constructor(public dataService: DataService, private matDialog: MatDialog) {
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
      this.updateDataAtIndex(index, data);
    }, error => {
      const header = this.dataService.data!.header;
      if (!header.includes('forgedColumn')) {
        header.push('forgedColumn');
      }
      this.updateDataAtIndex(index, {
        header: header,
        records: [[...this.dataService.data!.records[index], '']],
        type: 'complex'
      });
    });
  }

  private updateDataAtIndex(index: number, data: Data) {
    this.dataService.updateDataAtIndex(index, 0, data);
    if (this.currentRecordIndex !== undefined) {
      this.currentRecordIndex! += 1;
    }
    this.forgeResultForCurrentRecordIndex();
  }

  stopGenerating() {
    this.currentRecordIndex = undefined;
  }

  openSettings() {
    this.matDialog.open(SettingsComponent);
  }
}
