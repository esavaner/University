import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './components/login/login.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {SettingsComponent} from './components/settings/settings.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '../../material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {PasswordResetComponent} from './components/password-reset/password-reset.component';
import {AuthorisationDisplayComponent} from './components/authorisation-display/authorisation-display.component';
import { LoginPopupComponent } from './components/login-popup/login-popup.component';


@NgModule({
  declarations: [LoginComponent,
    RegistrationComponent, SettingsComponent, PasswordResetComponent, AuthorisationDisplayComponent, LoginPopupComponent],
  entryComponents: [LoginPopupComponent],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule,
  ]
})
export class AutorisationModule {
}
