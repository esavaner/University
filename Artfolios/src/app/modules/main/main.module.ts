import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MenuNavbarComponent} from './components/menu-navbar/menu-navbar.component';
import {UserDropdownComponent} from './components/user-dropdown/user-dropdown.component';
import {MainPageComponent} from './components/main-page/main-page.component';
import {FooterComponent} from './components/footer/footer.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '../../material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SignInUpButtonsComponent} from './components/sign-in-up-buttons/sign-in-up-buttons.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {AppRoutingModule} from '../../app-routing.module';


@NgModule({
  declarations: [MenuNavbarComponent, UserDropdownComponent, MainPageComponent, FooterComponent, SignInUpButtonsComponent, PageNotFoundComponent],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  exports: [
    MenuNavbarComponent,
    MainPageComponent,
    FooterComponent
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class MainModule {
}
