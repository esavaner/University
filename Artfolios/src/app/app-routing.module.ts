import {SettingsComponent} from './modules/autorisation/components/settings/settings.component';
import {LoginComponent} from './modules/autorisation/components/login/login.component';
import {MainPageComponent} from './modules/main/components/main-page/main-page.component';
import {UserProfileComponent} from './modules/profiles/components/user-profile/user-profile.component';
import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RegistrationComponent} from './modules/autorisation/components/registration/registration.component';
import {PasswordResetComponent} from './modules/autorisation/components/password-reset/password-reset.component';
import {PageNotFoundComponent} from './modules/main/components/page-not-found/page-not-found.component';
import {SearchListComponent} from './modules/search/components/search-list/search-list.component';
import {OfferListComponent} from './modules/offers/components/offer-list/offer-list.component';
import {ChatListComponent} from './modules/chat/components/chat-list/chat-list.component';
import {ChatWindowComponent} from './modules/chat/components/chat-window/chat-window.component';
import {SearchDisplayComponent} from './modules/search/components/search-display/search-display.component';
import {ProfileDisplayComponent} from './modules/profiles/components/profile-display/profile-display.component';
import {PortfolioDisplayComponent} from './modules/profiles/components/portfolio-display/portfolio-display.component';
import {PortfolioCreationComponent} from './modules/profiles/components/portfolio-creation/portfolio-creation.component';
import {PortfolioShowComponent} from './modules/profiles/components/portfolio-show/portfolio-show.component';
import {AngularFireAuthGuard, redirectUnauthorizedTo, canActivate, redirectLoggedInTo} from '@angular/fire/auth-guard';
import {AuthorisationDisplayComponent} from './modules/autorisation/components/authorisation-display/authorisation-display.component';
import {OffersDisplayComponent} from './modules/offers/components/offers-display/offers-display.component';
import {OfferDisplayComponent} from './modules/offers/components/offer-display/offer-display.component';

const redirectUnauthorizedToLogin = () => redirectUnauthorizedTo(['/auth']);
const redirectLoggedInToHome = () => redirectLoggedInTo(['']);
const routes: Routes = [
  {
    path: 'profile', component: ProfileDisplayComponent,
    children: [
      {
        path: '', component: UserProfileComponent,
        canActivate: [AngularFireAuthGuard],
        data: {authGuardPipe: redirectUnauthorizedToLogin}
      },
      {
        path: 'settings', component: SettingsComponent,
        canActivate: [AngularFireAuthGuard],
        data: {authGuardPipe: redirectUnauthorizedToLogin}
      },
      {
        path: 'portfolio', component: PortfolioDisplayComponent,
        children: [
          {path: '', redirectTo: 'creation', pathMatch: 'full'},
          {
            path: 'creation', component: PortfolioCreationComponent,
            canActivate: [AngularFireAuthGuard],
            data: {authGuardPipe: redirectUnauthorizedToLogin}
          },
          {path: ':id', redirectTo: '/:id', pathMatch: 'full'}
        ]
      }
    ]
  },
  {path: 'login', redirectTo: 'auth', pathMatch: 'full'},
  {path: 'register', redirectTo: 'auth/register', pathMatch: 'full'},
  {path: 'passwordReset', redirectTo: 'auth/passwordReset', pathMatch: 'full'},
  {
    path: 'auth', component: AuthorisationDisplayComponent,
    children: [
      {path: '', redirectTo: 'login', pathMatch: 'full'},
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegistrationComponent},
      {path: 'passwordReset', component: PasswordResetComponent}
    ],
    canActivate: [AngularFireAuthGuard],
    data: {authGuardPipe: redirectLoggedInToHome}
  },
  {
    path: 'search', component: SearchDisplayComponent,
    children: [
      {path: '', component: SearchListComponent},
      {path: ':searchInput', component: SearchListComponent}
    ]
  },
  {
    path: 'offers', component: OffersDisplayComponent,
    children: [
      {path: '', component: OfferListComponent},
      {path: ':offerId', component: OfferDisplayComponent}
    ],
    canActivate: [AngularFireAuthGuard],
    data: {authGuardPipe: redirectUnauthorizedToLogin}
  },
  {
    path: 'inbox', component: ChatListComponent,
    children: [
      {path: 'messages/:id', component: ChatWindowComponent}
    ],
    canActivate: [AngularFireAuthGuard],
    data: {authGuardPipe: redirectUnauthorizedToLogin}
  },
  {path: ':id', component: PortfolioShowComponent},
  {path: '', component: MainPageComponent},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
