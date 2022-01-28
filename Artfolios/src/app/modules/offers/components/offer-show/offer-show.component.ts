import { MatDialog } from '@angular/material';
import { OffersService } from '../../services/offers.service';
import { OfferModel } from '../../models/offer-model';
import { Component, OnInit, Input } from '@angular/core';
import Timestamp = firebase.firestore.Timestamp;
import * as firebase from 'firebase';
import { AuthService } from '../../../../auth.service';
import { OfferCreationComponent } from '../offer-creation/offer-creation.component';
import { MessageSendingDialogComponent } from '../message-sending-dialog/message-sending-dialog.component';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'offer-show',
  templateUrl: './offer-show.component.html',
  styleUrls: ['./offer-show.component.scss']
})
export class OfferShowComponent implements OnInit {
  @Input() offerId: string;
  @Input() fullView = false;

  offer: OfferModel;

  constructor(private offersService: OffersService, private auth: AuthService, public dialog: MatDialog) {
  }

  get remainingTime() {
    const date = new Date((this.offer.endDate as unknown as Timestamp).seconds * 1000);
    const milliseconds = date.getTime() - Date.now();
    const seconds = milliseconds / 1000;
    const days = Math.floor(seconds / 24 / 60 / 60);
    const hoursLeft = Math.floor((seconds) - (days * 86400));
    const hours = Math.floor(hoursLeft / 3600);
    return { days, hours };
  }

  ngOnInit() {
    this.offersService.getOfferById(this.offerId).subscribe(snapshot => {
      this.offer = snapshot.payload.data();
      if (this.offer && this.remainingTime.days <= 0 && this.remainingTime.hours <= 0) {
        this.deactivateOffer();
      }
    });
  }

  get isOwner() {
    if (this.auth.currentUser == null) {
      return false;
    } else {
      return this.auth.currentUser.uid === this.offer.authorId;
    }
  }

  get hasPortfolio() {
    if (this.auth.currentUser == null) {
      return false;
    } else {
      return this.auth.currentUserData.hasPortfolio;
    }
  }

  deleteOffer() {
    console.log('Deleting offer');
    if (confirm('Are you sure about this? This is something you CANNOT undo.')) {
      this.offersService.deleteOffer(this.offerId).then(value => {
        console.log('Offer successfully deleted');
      }).catch(reason => {
        console.log('error[offer-delete]: ', reason);
      });
    }
  }

  editOffer() {
    console.log('Editing offer');
    const dialogRef = this.dialog.open(OfferCreationComponent, {
      width: '80%',
      disableClose: true,
      data: {
        editable: true,
        offerId: this.offerId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) {
        return;
      }
      console.log('The dialog was closed', result);
    });
  }

  renewOffer() {
    console.log('Renewing offer');
    if (confirm('Are you sure you want your offer to be active for the next 7 days from now?')) {
      const start = Timestamp.now();
      const end = Timestamp.fromDate(new Date(start.seconds * 1000 + 7 * 24 * 60 * 60 * 1000));
      this.offersService.updateOffer(this.offerId, { isActive: true, startDate: start, endDate: end }, false).then(value => {
        console.log('Offer successfully renewed');
      }).catch(reason => {
        console.log('error[offer-renew]: ', reason);
      });
    }

  }

  deactivateOffer() {
    console.log('Deactivating offer');
    if (confirm('Are you sure you want to deactivate your offer? It won\'t be visible for anyone but you.')) {
      this.offersService.updateOffer(this.offerId, { isActive: false }, false).then(value => {
        console.log('Offer successfully deactivated');
      }).catch(reason => {
        console.log('error[offer-deactivate]: ', reason);
      });
    }
  }

  openMessageDialog(): void {
    const dialogRef = this.dialog.open(MessageSendingDialogComponent, {
      width: '70%',
      disableClose: true,
      data: {
        offer: this.offer,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) {
        return;
      }
    });
  }
}
