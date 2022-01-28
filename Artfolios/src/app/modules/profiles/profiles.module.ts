import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SectionAddButtonComponent} from './components/section-add-button/section-add-button.component';
import {SectionCreationComponent} from './components/section-creation/section-creation.component';
import {SectionChoiceComponent} from './components/section-choice/section-choice.component';
import {SectionShowComponent} from './components/section-show/section-show.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '../../material.module';
import {PortfolioShowComponent} from './components/portfolio-show/portfolio-show.component';
import {PortfolioCreationComponent} from './components/portfolio-creation/portfolio-creation.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';
import {AngularFireDatabaseModule} from '@angular/fire/database';
import {AngularFireModule} from '@angular/fire';
import {environment} from 'src/environments/environment';
import {AngularFireStorageModule} from '@angular/fire/storage';
import {ProfileDisplayComponent} from './components/profile-display/profile-display.component';
import {AppRoutingModule} from '../../app-routing.module';
import {OffersModule} from '../offers/offers.module';
import {AutorisationModule} from '../autorisation/autorisation.module';
import {PortfolioDisplayComponent} from './components/portfolio-display/portfolio-display.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {NgxPageScrollModule} from 'ngx-page-scroll';
import {ColorPickerModule} from 'ngx-color-picker';
import {RatebarComponent, RatebarDialogComponent} from './components/ratebar/ratebar.component';
import {BarRatingModule} from 'ngx-bar-rating';

@NgModule({
  declarations: [
    PortfolioShowComponent,
    SectionAddButtonComponent,
    SectionCreationComponent,
    PortfolioCreationComponent,
    SectionChoiceComponent,
    SectionShowComponent,
    UserProfileComponent,
    ProfileDisplayComponent,
    PortfolioDisplayComponent,
    RatebarComponent,
    RatebarDialogComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    AngularFireDatabaseModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireStorageModule,
    AppRoutingModule,
    OffersModule,
    AutorisationModule,
    DragDropModule,
    NgxPageScrollModule,
    ColorPickerModule,
    BarRatingModule
  ],
  exports: [
    UserProfileComponent,
    RatebarComponent
  ],
  entryComponents: [
    SectionChoiceComponent,
    SectionCreationComponent,
    RatebarDialogComponent
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class ProfilesModule {
}
