import {Component, OnInit} from '@angular/core';
import {ChatService} from '../../services/chat.service';
import {PortfolioModel} from '../../../profiles/models/portfolio-model';
import {PortfolioDataService} from '../../../profiles/services/portfolio-data.service';
import {ChatModel} from '../../models/chat-model';
import {AuthService} from '../../../../auth.service';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {filter, map} from 'rxjs/operators';

@Component({
  selector: 'chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.scss']
})
export class ChatListComponent implements OnInit {
  photoUrls = new Map<string, string>();
  userNames = new Map<string, string>();
  lastMessages = new Map<string, string>();
  timestamps = new Map<string, number>();
  formats = new Map<string, string>();
  unseenMessages = new Map<string, number>();
  chats = [];
  activeId = '';
  chatOpened: boolean;

  constructor(private chatService: ChatService,
              private portfolioService: PortfolioDataService,
              private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    if (this.route.firstChild) {
      this.activeId = this.route.firstChild.snapshot.paramMap.get('id');
      this.chatOpened = this.activeId !== '';
    }
    this.router.events.subscribe(value => {
      if (value instanceof NavigationEnd) {
        this.chatOpened = value.urlAfterRedirects !== '/inbox';
      }
    });
  }

  ifActive(chatId) {
    if (this.activeId === chatId) {
      return 'activeChat';
    } else {
      return '';
    }
  }

  isChatOpened() {
    return this.chatOpened;
  }

  openChat(chatId: string) {
    this.router.navigate(['inbox', 'messages', chatId]).then(_ => {
      this.activeId = chatId;
    });
  }

  get chatList() {
    const chatlist = this.chatService.chats;
    if (chatlist !== this.chats) {
      chatlist.forEach(chat => {
        const user = chat.user;
        if (!this.photoUrls.has(user)) {
          this.photoUrls.set(user, '../../../../../assets/default-profile-photo.png');
          this.userNames.set(user, 'loading...');

          this.portfolioService.getPortfolioById(user).valueChanges().subscribe(value => {
            const portfolio = value as PortfolioModel;
            if (portfolio.profileImage) {
              this.photoUrls.set(user, portfolio.profileImage);
            }
            if (portfolio.name !== ' ') {
              this.userNames.set(user, portfolio.name);
            } else {
              this.userNames.set(user, 'Anonymous');
            }
          });
          this.chatService.getChatOnce(chat.id).valueChanges().subscribe((nextChat: ChatModel) => {
            if (nextChat.messages.length > 0) {
              const lastMessage = nextChat.messages.pop();
              let lastContent = lastMessage.content;
              if (lastMessage.uid !== user) {
                lastContent = 'You: ' + lastContent;
              }
              this.lastMessages.set(user, lastContent);
              this.timestamps.set(user, lastMessage.createdAt);
              this.formats.set(user, this.getFormat(user));
              this.unseenMessages.set(user, nextChat.unseen[this.authService.currentUserId]);

            }
          });
        }
      });
      this.chats = chatlist;
    }
    chatlist.sort((a, b) => {
      const timeA = this.timestamps.get(a.user);
      const timeB = this.timestamps.get(b.user);
      return timeA > timeB ? -1 : timeA < timeB ? 1 : 0;
    });
    return chatlist;
  }

  detClass(user) {
    if (this.unseenMessages.get(user)) {
      return 'unseen';
    } else {
      return '';
    }
  }

  getUnseenCount(user) {
    const count = this.unseenMessages.get(user);
    if (count) {
      return '(' + count + ')';
    }
  }

  get myPhoto() {
    return this.authService.currentUser.photoURL;
  }

  trackById(i, chat) {
    return chat.id;
  }

  getFormat(user) {
    const day = 1000 * 60 * 60 * 24;
    const timestamp = this.timestamps.get(user);
    const diff = (Date.now() - timestamp) / day;
    if (diff < 1.0) {
      return 'HH:mm';
    } else if (diff < 7.0) {
      return 'EEEE';
    } else {
      const date = new Date(timestamp);
      const now = new Date();
      if (date.getFullYear() === now.getFullYear()) {
        return 'MMM d';
      } else {
        return 'dd.MM.yyyy';
      }
    }
  }


}
