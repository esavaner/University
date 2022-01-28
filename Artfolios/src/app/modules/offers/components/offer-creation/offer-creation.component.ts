import {Observable} from 'rxjs';
import {Component, OnInit, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {ProfessionModel} from 'src/app/modules/profiles/models/profession-model';
import {AngularFirestore} from '@angular/fire/firestore';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {OfferModel} from '../../models/offer-model';
import {OffersService} from '../../services/offers.service';
import {AuthService} from '../../../../auth.service';
import Timestamp = firebase.firestore.Timestamp;
import * as firebase from 'firebase';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'offer-creation',
  templateUrl: './offer-creation.component.html',
  styleUrls: ['./offer-creation.component.scss']
})
export class OfferCreationComponent implements OnInit {
  form: FormGroup;
  public cityPipe: Observable<string[]>;
  public professionPipe: Observable<ProfessionModel[]>;
  saving = false;
  error = false;
  startNow = new Date();
  endInMonth = new Date();

  constructor(private fb: FormBuilder, public dialogRef: MatDialogRef<OfferCreationComponent>,
              @Inject(MAT_DIALOG_DATA) public data, private afs: AngularFirestore, private offersService: OffersService,
              private auth: AuthService) {
    this.professionPipe = this.afs.collection('/professions').valueChanges() as Observable<ProfessionModel[]>;
    this.endInMonth.setDate(this.startNow.getDate() + 30); // Set now + 30 days as the new date
  }

  get profession(): FormGroup {
    return this.form.get('profession') as FormGroup;
  }

  ngOnInit() {
    this.form = this.fb.group({
      title: this.fb.control('', [Validators.required, Validators.maxLength(50)]),
      city: this.fb.control('', Validators.required),
      startDate: this.fb.control(Timestamp.now(), Validators.required),
      endDate: this.fb.control('', Validators.required),
      description: this.fb.control('', [Validators.required, Validators.maxLength(1000)]),
      price: this.fb.control(0, Validators.required),
      profession: this.fb.group({
        name: this.fb.control('', Validators.required),
      }),
      authorId: this.fb.control(this.auth.currentUserId),
      isActive: this.fb.control(true)
    });
    if (this.data.editable) {
      this.offersService.getOfferById(this.data.offerId).subscribe(snapshot => {
        const offer = snapshot.payload.data();
        this.form.patchValue(offer);
        this.form.controls.city.disable();
        this.form.controls.price.disable();
        this.form.controls.profession.disable();
        this.form.controls.endDate.setValue(offer.endDate.toDate());
        this.form.controls.endDate.disable();
      });
    }

    this.dialogRef.backdropClick().subscribe(_ => {
      this.confirmCancel();
    });
  }


  private createOffer(offer: OfferModel) {
    this.offersService.createNewOffer(offer).then(_ => {
      console.log('Offer successfully created');
      this.saving = false;
      this.dialogRef.close();
    }).catch(reason => {
      console.log('error[offer-create]: ', reason);
      this.saving = false;
      this.error = true;
    });
  }

  confirmCancel(): boolean {
    if (!this.form.dirty) {
      this.dialogRef.close();
      return true;
    }
    if (confirm('Are you sure to about this?')) {
      this.dialogRef.close();
      return true;
    }
    return false;
  }

  onSubmit() {
    console.log(this.form.getRawValue());
    if (this.form.valid) {
      this.saving = true;
      if (this.data.editable) {
        this.offersService.updateOffer(this.data.offerId, this.form.getRawValue()).then(_ => {
          console.log('Offer successfully updated');
          this.saving = false;
          this.dialogRef.close();
        }).catch(reason => {
          console.log('error[offer-create]: ', reason);
          this.saving = false;
          this.error = true;
        });
        return;
      }
      this.createOffer(this.form.getRawValue());
    }
  }
}
