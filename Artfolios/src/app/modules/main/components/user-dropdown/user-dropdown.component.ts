import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../../auth.service';
import {ChatService} from '../../../chat/services/chat.service';

@Component({
  selector: 'app-user-dropdown',
  templateUrl: './user-dropdown.component.html',
  styleUrls: ['./user-dropdown.component.scss']
})
export class UserDropdownComponent implements OnInit {

  constructor(private authService: AuthService, private chatService: ChatService) {
  }

  logout() {
    this.authService.logout();
  }

  get userName(): string {
    return this.authService.currentUserName;
  }

  get userHasPortfolio() {
    return this.authService.userHasPortfolio;
  }

  get userId() {
    return this.authService.currentUser.uid;
  }

  ngOnInit() {
  }

  get numberOfMessages() {
    const count = this.chatService.unseenChatsCount;
    return count ? count : '';
  }

}
