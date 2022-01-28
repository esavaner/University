import {RatingModel} from './../../models/rating-model';
import {AngularFirestore} from '@angular/fire/firestore';
import {Component, OnInit, Input, Inject} from '@angular/core';
import {FormControl, FormGroup, FormBuilder} from '@angular/forms';
import {Observable} from 'rxjs';
import {AuthService} from '../../../../auth.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';


@Component({
  selector: 'app-ratebar',
  templateUrl: './ratebar.component.html',
  styleUrls: ['./ratebar.component.scss']
})
export class RatebarComponent implements OnInit {
  @Input('idUser') idUser: string;
  @Input('disabled') disabled = false;
  ratingDoc: Observable<RatingModel>;
  ratingDocRef;
  currentUserId: string;
  ratingModel: RatingModel = {rating: 0, votes: []};

  constructor(private fb: FormBuilder, private afs: AngularFirestore, private auth: AuthService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.currentUserId = this.auth.currentUserId;
    this.ratingDocRef = this.afs.collection('/ratings').doc(this.idUser);
    this.ratingDoc = (this.ratingDocRef.valueChanges() as Observable<RatingModel>);
    this.ratingDoc.subscribe(response => {
      if (response) {
        this.ratingModel = response;
      }
    });
  }

  openRatingDialog(): void {
    let currentUserVote = 0;
    let hasVoted = false;
    this.ratingModel.votes.forEach(value => {
      if (value.voterId === this.currentUserId) {
        currentUserVote = value.vote;
        hasVoted = true;
      }
    });
    const dialogRef = this.dialog.open(RatebarDialogComponent, {
      width: '80vw',
      maxWidth: '400px',
      data: {rating: currentUserVote}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (hasVoted) {
          this.ratingModel.votes.forEach(value => {
            if (value.voterId === this.currentUserId) {
              value.vote = result;
            }
          });
        } else {
          this.ratingModel.votes.push({voterId: this.currentUserId, vote: result});
        }
        this.saveRating();
      }
    });
  }

  canVote() {
    return !this.disabled && this.idUser !== this.currentUserId;
  }

  private saveRating() {
    let userRate: RatingModel;
    let rateSum = 0;
    this.ratingModel.votes.forEach(value => {
      rateSum += value.vote;
    });
    userRate = {rating: (rateSum / this.ratingModel.votes.length), votes: this.ratingModel.votes};
    this.ratingDocRef.set(userRate).then(_ => {
      console.log('vote saved!');
    });
  }
}


@Component({
  selector: 'app-ratebar-dialog',
  templateUrl: 'ratebar-dialog.html',
  styleUrls: ['ratebar.component.scss']
})
export class RatebarDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RatebarDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { rating: number }) {
  }
}
