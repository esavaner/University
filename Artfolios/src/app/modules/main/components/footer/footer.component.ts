import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../../auth.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {


  constructor(private auth: AuthService) {
  }

  get isLoggedIn(): boolean {
    return this.auth.isAuthenticated;
  }

  ngOnInit() {
  }

}
