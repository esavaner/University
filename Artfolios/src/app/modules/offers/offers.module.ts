import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OfferListComponent} from './components/offer-list/offer-list.component';
import {OfferListFilterComponent} from './components/offer-list-filter/offer-list-filter.component';
import {OfferAddButtonComponent} from './components/offer-add-button/offer-add-button.component';
import {OfferCreationComponent} from './components/offer-creation/offer-creation.component';
import {OfferShowComponent} from './components/offer-show/offer-show.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '../../material.module';
import {OffersService} from './services/offers.service';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthService} from '../../auth.service';
import {RouterModule} from '@angular/router';
import {OfferDisplayComponent} from './components/offer-display/offer-display.component';
import {OffersDisplayComponent} from './components/offers-display/offers-display.component';
import { MessageSendingDialogComponent } from './components/message-sending-dialog/message-sending-dialog.component';


@NgModule({
  declarations: [
    OfferListComponent,
    OfferListFilterComponent,
    OfferAddButtonComponent,
    OfferCreationComponent,
    OfferShowComponent,
    OfferDisplayComponent,
    OffersDisplayComponent,
    MessageSendingDialogComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
    OfferListComponent,
    OfferListFilterComponent,
    OfferAddButtonComponent,
    OfferCreationComponent,
  ],
  providers: [
    OffersService,
    OfferListFilterComponent,
    AuthService
  ],
  entryComponents: [
    OfferCreationComponent,
    MessageSendingDialogComponent
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class OffersModule {
}
