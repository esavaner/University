import { FormArray } from '@angular/forms';
import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { SectionChoiceComponent } from '../section-choice/section-choice.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'section-add-button',
  templateUrl: './section-add-button.component.html',
  styleUrls: ['./section-add-button.component.scss']
})
export class SectionAddButtonComponent {
  @Input('parentArrayForm') parentArrayForm: FormArray;
  constructor(public dialog: MatDialog) { }

  openDialog(): void {
    const dialogRef = this.dialog.open(SectionChoiceComponent, {
      width: '80%',
      height: '80%',
      disableClose: true,
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(!result){
        return
      }
      this.parentArrayForm.controls.push(result)
      console.log('The dialog was closed', result);
    });
  }

}
