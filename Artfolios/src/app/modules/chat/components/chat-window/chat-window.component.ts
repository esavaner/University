import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Observable} from 'rxjs';
import {ChatService} from '../../services/chat.service';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../../../auth.service';
import {PortfolioDataService} from '../../../profiles/services/portfolio-data.service';
import {PortfolioModel} from '../../../profiles/models/portfolio-model';
import {ChatModel} from '../../models/chat-model';
import {log} from 'util';

@Component({
  selector: 'chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ChatWindowComponent implements OnInit, AfterViewChecked {


  chat$: Observable<any>;
  newMsg: string;
  photoUrls = new Map<string, string>();
  userNames = new Map<string, string>();
  otherUser = '';
  lastSize = 0;
  disableScrollDown = false;
  messages = [];
  currentChatId = '';
  private sound = new Audio('../../../../../assets/messageSound.mp3');
  @ViewChild('messages', null) private myScrollContainer: ElementRef;

  constructor(
    public cs: ChatService,
    private route: ActivatedRoute,
    public auth: AuthService,
    private portfolioService: PortfolioDataService
  ) {
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  onScroll(size) {
    if (this.disableScrollDown === false && size !== this.lastSize) {
      this.lastSize = size;
      return;
    }
    const element = this.myScrollContainer.nativeElement;
    const atBottom = element.scrollHeight - element.scrollTop === element.clientHeight;
    this.disableScrollDown = !(this.disableScrollDown && atBottom);
  }


  private scrollToBottom(): void {
    if (this.disableScrollDown) {
      return;
    }
    try {
      this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch (err) {
    }
  }


  ngOnInit() {
    this.sound.load();
    this.route.params.subscribe(params => {
      if (params.id) {
        this.currentChatId = params.id;
        this.initChat(params.id);
      } else {
        this.currentChatId = '';
      }
    });
  }

  initChat(chatId) {
    this.disableScrollDown = false;
    this.lastSize = 0;
    this.photoUrls.clear();
    this.userNames.clear();
    this.newMsg = '';

    this.cs.getChatOnce(chatId).get().toPromise().then(doc => {
      const chat = doc.data();
      const users = chat.users as string[];
      chat.unseen[this.auth.currentUserId] = 0;
      this.cs.getChatOnce(chatId).update({unseen: chat.unseen}).catch(err => log(err));
      users.forEach(user => {
        if (user !== this.auth.currentUserId) {
          this.otherUser = user;
        }
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
        }
      });
    });
    this.chat$ = this.cs.getChat(chatId);
    const subscriber = this.chat$.subscribe(next => {
      const chat = next as ChatModel;
      if (next.id !== this.currentChatId) {
        subscriber.unsubscribe();
      }
      if (this.messages.length > 0 &&
        this.messages.length !== chat.messages.length &&
        chat.messages[chat.messages.length - 1].uid === this.otherUser) {
        this.sound.play();
        chat.unseen[this.auth.currentUserId] = 0;
        console.log('update');
        this.cs.getChatOnce(chatId).update({unseen: chat.unseen}).catch(err => log(err));
      }
      this.messages = chat.messages;
    });
  }


  detRadiusClass(id: number, messages) {
    if (messages.length < 3) {
      return '';
    }
    const curr = messages[id].uid;
    const side = curr === this.otherUser ? 'left' : 'right';
    if (id === 0) {
      const next = messages[id + 1].uid;
      return next === curr ? `msg-${side}-top` : '';
    } else if (id === messages.length - 1) {
      const prev = messages[id - 1].uid;
      return prev === curr ? `msg-${side}-bot` : '';
    } else {
      const prev = messages[id - 1].uid;
      const next = messages[id + 1].uid;
      if (prev === curr && next === curr) {
        return `msg-${side}-mid`;
      } else if (prev === curr) {
        return `msg-${side}-bot`;
      } else if (next === curr) {
        return `msg-${side}-top`;
      } else {
        return '';
      }
    }
  }

  detDeltedClass(msg) {
    if (msg === '') {
      return 'deleted-message';
    }
    return '';
  }


  detPhoto(id: number, messages) {
    if (messages.length === 1) {
      return true;
    }
    const curr = messages[id].uid;
    if (id === messages.length - 1) {
      return true;
    } else {
      const next = messages[id + 1].uid;
      return next !== curr;
    }
  }

  submit(chatId) {
    if (this.newMsg) {
      this.cs.sendMessage(chatId, this.newMsg);
      this.newMsg = '';
    }
  }

  deleteMessage(chatId, msg) {
    this.cs.deleteMessage(chatId, msg);
  }

  trackByCreated(i, msg) {
    return msg.createdAt;
  }

  get deletedText() {
    return 'Message deleted';
  }

}
