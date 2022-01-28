import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../../auth.service';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'menu-navbar',
  templateUrl: './menu-navbar.component.html',
  styleUrls: ['./menu-navbar.component.scss']
})
export class MenuNavbarComponent implements OnInit {
  userLoggedIn: boolean;

  constructor(private authService: AuthService) {
    this.userLoggedIn = this.isUserLoggedIn();
  }

  ngOnInit() {
  }

  // method that checks whether user is logged in or not
  isUserLoggedIn() {
    return this.authService.isAuthenticated;
  }
}
