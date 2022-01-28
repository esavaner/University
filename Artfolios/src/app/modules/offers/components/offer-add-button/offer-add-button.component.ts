import { OfferCreationComponent } from './../offer-creation/offer-creation.component';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'add-button-offer',
  templateUrl: './offer-add-button.component.html',
  styleUrls: ['./offer-add-button.component.scss']
})
export class OfferAddButtonComponent {

  constructor(public dialog: MatDialog) { }

  openOfferCreator(): void {
    const dialogRef = this.dialog.open(OfferCreationComponent, {
      width: '80%',
      disableClose: true,
      data: {
        editable: false,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(!result){
        return
      }
      console.log('The dialog was closed', result);
    });
  }
}
