import {Injectable, NgZone, OnInit} from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';
import {auth, User} from 'firebase';
import {Router} from '@angular/router';
import {AngularFirestore, AngularFirestoreDocument} from '@angular/fire/firestore';
import {UserData} from './modules/profiles/models/user-data';
import {PortfolioDataService} from './modules/profiles/services/portfolio-data.service';
import {PortfolioModel} from './modules/profiles/models/portfolio-model';
import {log} from 'util';
import {ChatService} from './modules/chat/services/chat.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authState: any = null;
  userData: UserData = null;
  userPortfolio: PortfolioModel = null;
  userPortfolioDoc: AngularFirestoreDocument<PortfolioModel> = null;
  userDataDoc: AngularFirestoreDocument<UserData> = null;

  constructor(private afs: AngularFirestore, private ngZone: NgZone, private router: Router, public fireAuth: AngularFireAuth) {

    this.fireAuth.authState.subscribe(authState => {
        this.authState = authState;
        console.log('auth Changed');
        if (authState !== null) {
          this.userDataDoc = this.afs.collection('users').doc<UserData>(this.currentUser.uid);
          this.userPortfolioDoc = this.afs.collection('portfolio').doc<PortfolioModel>(this.currentUser.uid);
          this.userDataDoc.valueChanges().pipe().subscribe(v => {
            this.userData = v;
          });
          // if (this.userHasPortfolio) {
          this.linkPortfolioRefrence(this.currentUserId);
          // }
        } else {
          this.userPortfolioDoc = null;
          this.userData = null;
          this.userDataDoc = null;
          this.unlinkPortfolioReference();
        }
      });
  }

  authSubscribe(f) {
    return this.fireAuth.authState.subscribe(f);
  }

  linkPortfolioRefrence(uid: string) {
    this.userPortfolioDoc = this.afs.collection('portfolio').doc<PortfolioModel>(uid);
    this.userPortfolioDoc.valueChanges().pipe().subscribe(v => {
      console.log('portfolio linked');
      console.log(v);
      this.userPortfolio = v;
    });
  }

  unlinkPortfolioReference() {
    this.userPortfolio = null;
    this.userPortfolioDoc = null;
  }

  get currentUserPortfolio() {
    return this.userPortfolio;
  }

  get currentUserPortfolioDoc() {
    return this.userPortfolioDoc;
  }

  get isAuthenticated(): boolean {
    return this.authState !== null;
  }

  get currentUser(): User {
    return this.isAuthenticated ? this.authState : null;
  }

  get currentUserData() {
    return this.userData;
  }

  get currentUserDoc() {
    return this.userDataDoc;
  }

  get currentUserObservable(): any {
    return this.fireAuth.authState;
  }

  get currentUserId(): string {
    return this.isAuthenticated ? this.authState.uid : '';
  }

  get currentUserEmail(): string {
    return this.currentUser.email;
  }

  get currentUserName(): string {
    return this.currentUser.displayName;
  }

  get currentUserFullName(): string {
    return this.currentUserData.firstName + ' ' + this.currentUserData.lastName;
  }

  register(email: string, password: string) {
    return this.fireAuth.auth.createUserWithEmailAndPassword(email, password).then(cred => {
      console.log(cred);
      cred.user.updateProfile({displayName: email});
      this.router.navigate(['']);
      this.createUserDocument(cred.user);
    });
  }

  private createUserDocument(user: User) {
    const userDocRef = this.afs.collection('users').doc<UserData>(user.uid);
    const userdata: UserData = {
      address: {city: '', postalCode: '', street: ''}, dateOfBirth: '', firstName: '', lastName: '',
      gender: '', privateAccount: true, hasPortfolio: false
    };
    userDocRef.set(userdata);
  }

  logout() {
    return this.fireAuth.auth.signOut().then(() => console.log('User signed out')).then(() => {
      this.router.navigate(['']);
    });
  }

  login(email: string, password: string) {
    return this.fireAuth.auth.signInWithEmailAndPassword(email, password).then(cred => {
      console.log(cred);
      this.router.navigateByUrl('/');
    });
  }


  reauthenticateWithCredential(password: string) {
    const credential = auth.EmailAuthProvider.credential(this.currentUserEmail, password);
    return this.currentUser.reauthenticateWithCredential(credential);
  }

  continueWithGoogle() {
    return this.fireAuth.auth.signInWithPopup(new auth.GoogleAuthProvider()).then(cred => {
      if (cred.additionalUserInfo.isNewUser) {
        this.createUserDocument(cred.user);
      }
      this.ngZone.run(() => this.router.navigateByUrl('/'));
    });
  }

  reauthenticateWithGoogle() {
    return this.currentUser.reauthenticateWithPopup(new auth.GoogleAuthProvider());
  }

  sendPasswordReset(email: string) {
    return this.fireAuth.auth.sendPasswordResetEmail(email);
  }

  get userHasPortfolio(): boolean {
    if (this.userData !== null) {
      return this.userData.hasPortfolio;
    }
  }

  set userProfilePhoto(url: string) {
    console.log('trying photo save');
    this.fireAuth.auth.currentUser.updateProfile({photoURL: url}).then( value => {
      console.log('photo saved!');
      console.log(value);
    });
  }


}
