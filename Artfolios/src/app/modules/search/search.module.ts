import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchListComponent } from './components/search-list/search-list.component';
import { SearchShowComponent } from './components/search-show/search-show.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '../../material.module';
import {SearchDisplayComponent} from './components/search-display/search-display.component';
import {AppRoutingModule} from '../../app-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {ProfilesModule} from '../profiles/profiles.module';


@NgModule({
  declarations: [SearchListComponent, SearchShowComponent, SearchDisplayComponent],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    ProfilesModule
  ],
  exports: [
    SearchListComponent
  ]
})
export class SearchModule { }
