import {Component} from '@angular/core';
import {ApiKeyService} from '../services/api-key.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent {

  constructor(public apiKeyService: ApiKeyService) {
  }
}
