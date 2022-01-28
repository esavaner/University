import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatListComponent } from './components/chat-list/chat-list.component';
import { ChatWindowComponent } from './components/chat-window/chat-window.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '../../material.module';
import {AppRoutingModule} from '../../app-routing.module';
import {FormsModule} from '@angular/forms';
import {ProfilesModule} from '../profiles/profiles.module';
import {LinkifyPipe} from './pipes/linkyfy.pipe';



@NgModule({
  declarations: [ChatListComponent, ChatWindowComponent, LinkifyPipe],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MaterialModule,
    AppRoutingModule,
    FormsModule,
    ProfilesModule
  ],
  exports: [
    ChatListComponent,
    ChatWindowComponent
  ]
})
export class ChatModule { }
