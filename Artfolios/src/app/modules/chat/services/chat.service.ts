import {Injectable} from '@angular/core';
import {AngularFirestore} from '@angular/fire/firestore';
import {AuthService} from '../../../auth.service';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';
import {firestore, User} from 'firebase';
import {PortfolioDataService} from '../../profiles/services/portfolio-data.service';
import {UserData} from '../../profiles/models/user-data';
import {ChatModel} from '../models/chat-model';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  chats = [];
  seenMap = new Map<string, boolean>();

  constructor(private afs: AngularFirestore,
              private authService: AuthService) {
    const callback = (user: User) => {
      if (user) {
        this.afs.collection('users').doc(user.uid).valueChanges().subscribe((userData: UserData) => {
          this.chats = userData.chats;
          this.subscribeChats();
        });
      } else {
        this.chats = [];
      }
    };
    authService.authSubscribe(callback);
  }

  loadChats() {
    this.authService.currentUserDoc.valueChanges().subscribe(userData => {
      this.chats = userData.chats;
    });
  }

  getChat(chatId) {
    return this.afs
      .collection('chats')
      .doc(chatId)
      .snapshotChanges()
      .pipe(
        map(doc => {
          return {id: doc.payload.id, ...doc.payload.data()};
        })
      );
  }

  getChatOnce(chatId) {
    return this.afs.collection('chats').doc(chatId);
  }

  createChatWith(uid2: string, message: string) {
    const uid1 = this.authService.currentUserId;

    this.afs.collection('users').doc(uid1).get().toPromise().then(val => {
      const chats = (val.data() as UserData).chats;
      if (chats) {
        console.log('check');
        for (const chatItem of chats) {
          if (chatItem.user === uid2) {
            console.log('found');
            this.sendMessage(chatItem.id, message).then(value => {
              console.log('msg sent');
            });
            return;
          }
        }
      }
      const chat: ChatModel = {
        users: [uid1, uid2],
        count: 0,
        messages: [],
        unseen: {}
      };
      chat.unseen[uid1] = 0;
      chat.unseen[uid2] = 0;
      this.afs.collection('chats').add(chat).then(ref => {
        const u1Ref = this.afs.collection('users').doc(uid1);
        const u2Ref = this.afs.collection('users').doc(uid2);
        const chatId = ref.id;
        u1Ref.update({
          chats: firestore.FieldValue.arrayUnion({id: chatId, user: uid2})
        }).then(res => console.log(res));
        u2Ref.update({
          chats: firestore.FieldValue.arrayUnion({id: chatId, user: uid1})
        }).then(res => console.log(res));
        console.log(uid1);
        console.log(uid2);
        console.log(ref.id);
        this.sendMessage(ref.id, message).then(value => {
          console.log('msg sent');
        });
        return;
      });
    });
  }


  deleteMessage(chatId, message) {
    if (confirm('Are you sure?')) {
      const ref = this.afs.collection('chats').doc(chatId);
      ref.get().toPromise().then(snapshot => {
        const chat = snapshot.data() as ChatModel;
        const messages = chat.messages;
        let id = -1;
        messages.forEach((msg, index) => {
            if (msg.createdAt === message.createdAt) {
              id = index;
            }
        });
        messages[id].content = '';
        ref.update({messages});
      });
      // ref.update({messages: firestore.FieldValue.arrayRemove(message)});
    }
  }

  async sendMessage(chatId, content) {
    const uid = this.authService.currentUserId;

    const data = {
      uid,
      content,
      createdAt: Date.now()
    };

    if (uid) {
      const ref = this.afs.collection('chats').doc(chatId);
      // return ref.update({
      //   messages: firestore.FieldValue.arrayUnion(data)
      // }).then(() => {
      ref.get().toPromise().then(snapshot => {
        const chat = snapshot.data() as ChatModel;
        const uid2 = chat.users[0] === uid ? chat.users[1] : chat.users[0];
        chat.unseen[uid2]++;
        ref.update({messages: firestore.FieldValue.arrayUnion(data), unseen: chat.unseen});
      });
      // });
    }
  }

  subscribeChats() {
    this.chats.forEach(chat => {
        this.afs
        .collection('chats')
        .doc(chat.id)
        .valueChanges()
        .pipe(
          map((nextChat: ChatModel) => {
            return nextChat.unseen;
          })
        ).subscribe( unseen => {
          if (unseen[this.authService.currentUserId] === 0) {
            this.seenMap.set(chat.id, true);
          } else {
            this.seenMap.set(chat.id, false);
          }
        });
    });
  }

  get unseenChatsCount() {
    let count = 0;
    this.seenMap.forEach(val => {
      if (!val) {
        count++;
      }
    });
    return count;
  }

}
