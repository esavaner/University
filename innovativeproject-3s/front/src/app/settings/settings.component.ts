import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { LoginState } from '../states/login.state';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  currentUser: any;
  Password = "****";
  ButtonName = "Show";

  constructor(private store: Store) {
    this.currentUser = this.store.selectSnapshot(LoginState.userDetails);
  }

  ngOnInit() {
  }

  public showPassword() {
    this.Password = 'haselko';
}
  public changeName() {
    if (this.ButtonName === 'Show') {
        this.ButtonName = 'Hide';
    } else {
      this.Password = '****';
      this.ButtonName = 'Show';
    }
  }

}
