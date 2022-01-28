import {Component, ElementRef, NgZone, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {AuthService} from '../../../../auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {auth} from 'firebase';
import {DateAdapter, MatDialog, MatSlideToggleChange} from '@angular/material';
import {FormControl, Validators} from '@angular/forms';
import {LoginPopupComponent} from '../login-popup/login-popup.component';
import {UserData} from '../../../profiles/models/user-data';
import { Observable } from 'rxjs';
import {SearchService} from 'src/app/modules/search/search.service';
import {LocationModel} from '../../../search/location-model';


@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  name = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required, Validators.minLength(6)]);
  phone = new FormControl('', [Validators.pattern('[0-9]*'), Validators.minLength(9),
    Validators.maxLength(9)]);
  passwordWeak = false;
  postCode = new FormControl('', [Validators.pattern('[0-9][0-9]-[0-9][0-9][0-9]')]);
  selected: string;
  locationList: Observable<LocationModel[]>;


  constructor(private adapter: DateAdapter<any>, private ngZone: NgZone, public dialog: MatDialog,
              private authService: AuthService, private snackBar: MatSnackBar, private searchService: SearchService) {
    adapter.setLocale('pl');
    this.locationList = this.searchService.locations;
  }

  ngOnInit() {
  }

// functions used for changes
  changeDisplayName(name: string) {
    if (this.name.valid) {
      this.authService.currentUser.updateProfile({displayName: name}).then(() => {
        this.openSnackBar(`Name succesfully changed to ${name}!`, 'Close');
      });
    }
  }

  changeEmail(email: string) {
    this.authService.currentUser.updateEmail(email).then(() => {
      this.openSnackBar(`Email succesfully changed to ${email}!`, 'Close');
    }, err1 => {
      const err = err1 as auth.Error;
      if (err.code === 'auth/invalid-email') {
        this.email.setErrors(Validators.email);
      }
      if (err.code === 'auth/requires-recent-login') {
        this.openLoginPopup('email', email);
      }
      console.log(err.code);
    });
  }

  verifyForEmail(email: string) {
    if (this.email.valid) {
      this.openLoginPopup('email', email);
    }
  }

  changePassword(password: string) {
    this.authService.currentUser.updatePassword(password).then(() => {
      this.openSnackBar(`Password successfully changed!`, 'Close');
    }, err1 => {
      const err = err1 as auth.Error;

      if (err.code === 'auth/requires-recent-login') {
        this.openLoginPopup('password', password);
      }
      console.log(err.code);
    });
  }

  verifyForPassword(password: string) {
    if (this.password.valid) {
      this.openLoginPopup('password', password);
    }
  }

  changeRealName(firstname: string, lastname: string) {
    console.log(firstname, lastname);
    this.authService.currentUserDoc.update({firstName: firstname, lastName: lastname}).then(res => {
      this.openSnackBar('Name successfully changed!', 'Close');
    }).catch(() => {
      console.log('errorr');
    });
  }

  changeGender(gender: string) {
    console.log(gender);
    if (gender === undefined) {
      gender = '';
    }
    this.authService.currentUserDoc.update({gender}).then(res => {
      this.openSnackBar('Gender successfully changed!', 'Close');
    }).catch(() => {
      console.log('errorr');
    });
  }

  changeAddress(street: string, postCode: string, city: string) {
    console.log(street, postCode, city);
    if (this.postCode.valid) {
      this.authService.userPortfolioDoc.update({location: city});
      this.authService.currentUserDoc.update({address: {street, postalCode: postCode, city}}).then(res => {
        this.openSnackBar('Address successfully changed!', 'Close');
      }).catch(() => {
        console.log('errorr');
      });
    }
  }


  changePhone(phone: string) {
    if (this.phone.valid) {
      phone = `+48${phone}`;
      console.log(phone);
      const applicationVerifier = new auth.RecaptchaVerifier(
        'recaptcha-container');
      const provider = new auth.PhoneAuthProvider();
      provider.verifyPhoneNumber(phone, applicationVerifier)
        .then(verificationId => {
          const verificationCode = window.prompt('Please enter the verification ' +
            'code that was sent to your mobile device.');
          return auth.PhoneAuthProvider.credential(verificationId,
            verificationCode);
        })
        .then(phoneCredential => {
          this.authService.currentUser.updatePhoneNumber(phoneCredential).then(() => {
            this.ngZone.run(() => this.openSnackBar('Phone number successfully changed!', 'close'));
          });
        });

    }
  }

  changeDateOfBirth(date: string) {
    console.log(date);
    this.authService.currentUserDoc.update({dateOfBirth: date}).then(res => {
      this.openSnackBar('Date of birth successfully changed!', 'Close');
    }).catch(() => {
      console.log('error');
    });
  }

  changePrivacy(change: MatSlideToggleChange) {
    console.log('Prviacy changed to ', change.checked);
    this.authService.userPortfolioDoc.update({isPrivate: change.checked});
    this.authService.currentUserDoc.update({privateAccount: change.checked}).then(res => {
      this.openSnackBar('Privacy successfully changed!', 'Close');
    }).catch(() => {
      console.log('error');
    });


  }

  getErrorMessage(form: FormControl) {
    if (form === this.postCode) {
      return 'Post code should follow XX-XXX pattern';
    }
    if (form === this.phone) {
      return 'Badly formated phone number';
    }
    if (form.hasError('required')) {
      return 'You must enter a value';
    }
    if (form.hasError('email')) {
      return 'Not a valid email';
    }
    if (form.hasError('minlength')) {
      return 'Password too weak';
    }
    return '';
  }


  openLoginPopup(operation: string, value: string): void {
    const dialogRef = this.dialog.open(LoginPopupComponent, {
      panelClass: ['re-auth-popup']
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        if (operation === 'email') {
          this.changeEmail(value);
        } else if (operation === 'password') {
          this.changePassword(value);
        }
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      panelClass: ['settings-snack'],
      verticalPosition: 'top'
    });
  }

  get userData(): UserData {
    const data = this.authService.currentUserData;
    if (data !== null) {
      return data;
    } else {
      return null;
    }
  }

  get realName(): string {
    if (this.userData != null) {
      return `${this.userData.firstName} ${this.userData.lastName}`;
    }
  }

  get address(): string {
    if (this.userData != null) {
      const address = this.userData.address;
      return `${address.street}, ${address.postalCode} ${address.city}`;
    }
  }

  get gender(): string {
    if (this.userData != null) {
      return this.userData.gender;
    }
  }

  get dateOfBirth(): string {
    if (this.userData != null) {
      return this.userData.dateOfBirth;
    }
  }

  get currentName(): string {
    return this.authService.currentUserName;
  }

  get currentEmail(): string {
    return this.authService.currentUserEmail;
  }

  get currentPhone(): string {
    if (this.authService.currentUser.phoneNumber != null ) {
      return this.authService.currentUser.phoneNumber.substr(3);
    }
  }

  get privacyStatus(): boolean {
    if (this.userData != null) {
      return this.userData.privateAccount;
    }
  }

  get privacyStatusText(): string {
    if (this.userData != null) {
      return this.userData.privateAccount ? 'Private' : 'Public';
    }
  }

  get userHasPortfolio() {
    return this.authService.userHasPortfolio;
  }

}
