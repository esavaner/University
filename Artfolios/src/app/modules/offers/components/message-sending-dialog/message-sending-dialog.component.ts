import {Component, OnInit, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {ChatService} from '../../../chat/services/chat.service';
import {AuthService} from '../../../../auth.service';

@Component({
  selector: 'app-message-sending-dialog',
  templateUrl: './message-sending-dialog.component.html',
  styleUrls: ['./message-sending-dialog.component.scss']
})
export class MessageSendingDialogComponent implements OnInit {
  form: FormGroup;

  constructor(private fb: FormBuilder, public dialogRef: MatDialogRef<MessageSendingDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data, private chatService: ChatService, private auth: AuthService) { // data.offer
    this.form = this.fb.group({
      message: this.fb.control('', Validators.required),
      attachPortfolio: this.fb.control(true)
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    let content = this.form.controls.message.value;
    if (this.form.controls.attachPortfolio.value) {
      content += '\nSee my portfolio here: https://artfolios.pl/' + this.auth.currentUserId;
    }
    console.log(content);
    this.chatService.createChatWith(this.data.offer.authorId, content);
    this.dialogRef.close();
  }
}
