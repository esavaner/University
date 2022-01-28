import {Injectable} from '@angular/core';
import {OfferModel} from '../models/offer-model';
import {AngularFirestore, AngularFirestoreDocument} from '@angular/fire/firestore';
import Timestamp = firebase.firestore.Timestamp;
import * as firebase from 'firebase';

@Injectable({
  providedIn: 'root'
})
export class OffersService {
  private collectionName = 'offers';

  constructor(private afs: AngularFirestore) {
  }

  offerMock(): OfferModel {
    return {
      title: 'Cool test offer',
      city: 'Wrocław',
      startDate: Timestamp.now(),
      endDate: Timestamp.now(),
      description: '',
      price: 130,
      profession: {name: 'Architect'},
      isActive: true,
      authorId: '',
      id: '1'
    };
  }

  get offerListRef() {
    return this.afs.collection(this.collectionName);
  }

  get offerList() {
    return this.offerListRef.snapshotChanges();
  }

  getOfferDocById(offerId: string): AngularFirestoreDocument<OfferModel> {
    return this.offerListRef.doc<OfferModel>(offerId);
  }

  getOfferById(offerId: string) {
    return this.getOfferDocById(offerId).snapshotChanges();
  }


  createNewOffer(offer: OfferModel) {
    return this.offerListRef.add(offer);
  }

  updateOffer(offerId, offerChanges, overwrite = true) {
    if (overwrite) {
      console.log('Overwriting object');
      return this.getOfferDocById(offerId).set(offerChanges);
    } else {
      console.log('Updating object');
      return this.getOfferDocById(offerId).update(offerChanges);
    }
  }

  deleteOffer(offerId: string) {
    return this.getOfferDocById(offerId).delete();
  }
}
