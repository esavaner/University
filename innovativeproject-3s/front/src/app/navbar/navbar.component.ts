import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { Store, Select } from '@ngxs/store';
import { Logout } from '../actions/login.action';
import { Observable } from 'rxjs';
import { LoginState } from '../states/login.state';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  currentUser: any;

  constructor(private store: Store, private router: Router) {
    this.currentUser = this.store.selectSnapshot(LoginState.userDetails);
  }

  ngOnInit() {
  }

  logout() {
    this.store.dispatch(new Logout());
    this.router.navigate(['/login']);
  }
}
