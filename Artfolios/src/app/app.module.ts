import { AutorisationModule } from './modules/autorisation/autorisation.module';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from './material.module';
import { ProfilesModule } from './modules/profiles/profiles.module';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainModule} from './modules/main/main.module';
import {FormsModule} from '@angular/forms';
import {SearchModule} from './modules/search/search.module';
import {OffersModule} from './modules/offers/offers.module';
import {ChatModule} from './modules/chat/chat.module';
import {AngularFireAuthGuard} from '@angular/fire/auth-guard';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {AngularFireAuthModule} from '@angular/fire/auth';
import { OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFirestoreModule,
    AngularFireAuthModule,
    AppRoutingModule,
    MainModule,
    FlexLayoutModule,
    MaterialModule,
    ProfilesModule,
    AutorisationModule,
    FormsModule,
    SearchModule,
    OffersModule,
    ChatModule
  ],
  providers: [
    AngularFireAuthGuard,
  ],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class AppModule {
}
