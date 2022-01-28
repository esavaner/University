import { Component, OnInit } from '@angular/core';
import { ScanState } from '../states/scan.state';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import { Scan } from '../models/scan';
import { GetScans, DeleteScan } from '../actions/scan.action';
import { LoginState } from '../states/login.state';

@Component({
  selector: 'app-tools',
  templateUrl: './tools.component.html',
  styleUrls: ['./tools.component.css']
})
export class ToolsComponent implements OnInit {

  @Select(ScanState.getScanList) scanList: Observable<Scan[]>;
  positiveList = new Array();
  negativeList = new Array();
  waitingList = new Array();

  currentUser: any;
  log: any;

  constructor(private store: Store) {
    this.currentUser = this.store.selectSnapshot(LoginState.userDetails);
  }

  ngOnInit() {
    this.store.dispatch(new GetScans({email: this.currentUser.email}));
    this.scanList.subscribe(scans => {
      this.positiveList = [];
      this.negativeList = [];
      this.waitingList = [];
      for (const scan of scans) {
        if (scan.status === 'positive') {
          this.positiveList.push(scan);
        } else if (scan.status === 'waiting') {
          this.waitingList.push(scan);
        } else {
          this.negativeList.push(scan);
        }
      }
    });
  }

  setLog(display: string) {
    this.log = JSON.parse(display)[0];
  }

}
