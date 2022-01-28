import { Component, OnInit } from '@angular/core';
import { PortfolioDataService } from 'src/app/modules/profiles/services/portfolio-data.service';
import { PortfolioModel } from 'src/app/modules/profiles/models/portfolio-model';
import { Observable } from 'rxjs';
import { SectionModel, TypeOfField } from '../../models/section-model';
import {AuthService} from '../../../../auth.service';

@Component({
  selector: 'user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  constructor(private authService: AuthService) {

  }

  get fullName() {
    if (this.userData !== null) {
      let name = this.authService.currentUserData.firstName + ' ' + this.authService.currentUserData.lastName;
      if (name === ' ') {
        name = this.authService.currentUser.displayName;
      }
      if (name == null || name === ' ') {
        name = this.authService.currentUser.email;
      }
      return name;
    }
   }

   get dateOfBirth() {
     if (this.userData !== null) {
       return this.authService.currentUserData.dateOfBirth;
     }
   }

  get address() {
    if (this.userData !== null) {
      const ad = this.authService.currentUserData.address;
      return `${ad.street} ${ad.postalCode} ${ad.city}`;
    }
  }

  get gender() {
    if (this.userData !== null) {
      const gender = this.authService.currentUserData.gender;
      if (gender !== '') {
        if (gender === 'Woman') {
          return 'female';
        }
        if (gender === 'Man') {
          return 'male';
        }
      }
    }
    return '';
  }

  get phone() {
    if (this.authService.currentUser !== null) {
      return this.authService.currentUser.phoneNumber;
    }
  }

  get privateAccount() {
      if (this.userData !== null) {
        return this.authService.currentUserData.privateAccount;
      }
  }
  get photoUrl() {
    const url = this.authService.currentUser.photoURL;
    if (url) {
      return url;
    } else {
      return '../../../../../assets/default-profile-photo.png';
    }
  }

  get userData() {
    return this.authService.currentUserData;
  }

  get userId() {
    return this.authService.currentUserId;
  }

  get userHasPortfolio() {
    return this.authService.userHasPortfolio;
  }

  addNewOffer() {
    // TODO: implement adding new offer
  }


  ngOnInit() {
  }

}
