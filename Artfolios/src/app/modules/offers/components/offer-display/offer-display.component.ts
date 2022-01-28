import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-offer-display',
  templateUrl: './offer-display.component.html',
  styleUrls: ['./offer-display.component.scss']
})
export class OfferDisplayComponent implements OnInit {
  urlId: string;

  constructor(private route: ActivatedRoute, private location: Location) {
    this.urlId = this.route.snapshot.paramMap.get('offerId');
    console.log(this.urlId);
  }

  ngOnInit() {
  }

  goBack() {
    this.location.back();
  }

}
