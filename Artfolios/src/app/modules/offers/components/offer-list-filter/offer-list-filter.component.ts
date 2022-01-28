import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {OfferModel} from '../../models/offer-model';
import {OffersService} from '../../services/offers.service';
import actions from '@angular/fire/schematics/deploy/actions';
import {map} from 'rxjs/operators';

@Component({
  selector: 'offer-list-filter',
  templateUrl: './offer-list-filter.component.html',
  styleUrls: ['./offer-list-filter.component.scss']
})
export class OfferListFilterComponent implements OnInit {
  @Input() filterOwner;
  offerIds;
  offerIdsInactive;
  activeOffers: boolean;
  inactiveOffers: boolean;

  constructor(private offersService: OffersService) {
  }

  ngOnInit() {
    this.offersService.offerListRef.snapshotChanges().subscribe(snapshot => {
      const offers = snapshot.map(act => {
        return act.payload.doc.data();
      }) as OfferModel[];
      this.offerIds = snapshot.map(act => {
        return act.payload.doc.id;
      });
      offers.forEach((o, index) => {
        o['id'] = this.offerIds[index];
      });
      this.offerIds = offers.filter(value => value.isActive && value.authorId === this.filterOwner).map(value => value.id);
      this.offerIdsInactive = offers.filter(value => !value.isActive && value.authorId === this.filterOwner).map(value => value.id);
      this.activeOffers = this.offerIds.length > 0;
      this.inactiveOffers = this.offerIdsInactive.length > 0;
    });
  }
}
