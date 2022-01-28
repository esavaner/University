import {MatDialog} from '@angular/material';
import {Component, OnInit} from '@angular/core';
import {SectionModel, TypeOfField} from '../../models/section-model';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {PortfolioDataService} from '../../services/portfolio-data.service';
import {AuthService} from '../../../../auth.service';
import {AngularFireStorage} from '@angular/fire/storage';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {PortfolioModel} from '../../models/portfolio-model';
import {AngularFirestore, AngularFirestoreDocument} from '@angular/fire/firestore';
import {Router} from '@angular/router';
import {SectionCreationComponent} from '../section-creation/section-creation.component';
import {ProfessionModel} from '../../models/profession-model';


@Component({
  // tslint:disable-next-line:component-selector
  selector: 'portfolio-creation',
  templateUrl: './portfolio-creation.component.html',
  styleUrls: ['./portfolio-creation.component.scss']
})


export class PortfolioCreationComponent implements OnInit {
  form: FormGroup;

  previewUrl: any = null;
  private fileData: File;
  photosToDelete = [];

  userPortfolio: PortfolioModel;
  userPortfolioDoc: AngularFirestoreDocument<PortfolioModel>;

  public professionPipe: Observable<ProfessionModel[]>;

  constructor(private db: PortfolioDataService, private auth: AuthService,
              private fb: FormBuilder, private storage: AngularFireStorage, private afs: AngularFirestore,
              private router: Router, public dialog: MatDialog) {
    this.professionPipe = this.afs.collection('/professions').valueChanges() as Observable<ProfessionModel[]>;
  }

  get backgroundColor(): string {
    const color = this.form.get('backgroundColor') as FormGroup;
    if (color) {
      return color.value;
    } else {
      return '#000';
    }
  }

  set backgroundColor(value) {
    this.form.get('backgroundColor').setValue(value);
  }

  drop(event: CdkDragDrop<any[]>) {
    moveItemInArray(this.sections.controls, event.previousIndex, event.currentIndex);
  }

  uploadPhoto(fileInput: any) {
    this.fileData = fileInput.target.files[0] as File;
    const mimeType = this.fileData.type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    const reader = new FileReader();
    reader.readAsDataURL(this.fileData);
    reader.onload = () => {
      this.previewUrl = reader.result;
    };
  }

  deleteProfilePhoto(event: Event) {
    event.preventDefault();
    if (confirm('Are you sure you want to delete your profile photo?')) {
      this.db.deletePhoto(this.userPortfolio.profileImage);
      this.userPortfolioDoc.update({profileImage: ''});
      this.previewUrl = '';
      this.form.patchValue({profileImage: ''});
      this.auth.userProfilePhoto = '';
    }
  }

  deleteSection(index: number) {
    if (confirm('Are you sure you want to delete this section?')) {
      const section = this.sections.controls[index].value as SectionModel;
      section.fields.forEach(field => {
        if (field.type === TypeOfField.IMAGE) {
          this.db.photosToDelete.push(field.value);
          console.log(this.db.photosToDelete);
        }
      });
      this.sections.controls.splice(index, 1);
    }
  }

  openSectionEdit(sect: SectionModel, index: number): void {
    const dialogRef = this.dialog.open(SectionCreationComponent, {
      width: '80%',
      height: '80%',
      disableClose: true,
      data: {
        editable: true,
        section: sect
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) {
        return;
      }
      this.sections.controls[index] = result;
    });
  }

  ngOnInit() {
    this.db.clearImgQueue();
    this.db.photosToDelete = [];
    this.form = this.fb.group({
      name: this.fb.control(''),
      biography: this.fb.control('', [Validators.required, Validators.maxLength(150)]),
      profileImage: this.fb.control(''),
      sections: this.fb.array([]),
      professions: this.fb.control('', Validators.maxLength(5)),
      backgroundColor: this.fb.control('#fff', Validators.required),
      fontColor: this.fb.control('#000', Validators.required),
      location: this.fb.control(''),
      isPrivate: this.fb.control('')
    });
    console.log(this.auth.currentUserId);
    this.userPortfolioDoc = this.afs.collection('portfolio').doc<PortfolioModel>(this.auth.currentUserId);
    this.userPortfolioDoc.valueChanges().pipe().subscribe(v => {
      if (v) {
        this.userPortfolio = v;
        this.sections.clear();
        v.sections.forEach(section => {
          this.sections.controls.push(this.db.createSectionForm(section));
        });
        this.form.patchValue(this.userPortfolio);
        this.previewUrl = v.profileImage;
      }
    });
  }

  editSection(section: SectionModel, index: number) {
    this.openSectionEdit(section, index);
  }

  get sections() {
    return this.form.get('sections') as FormArray;
  }

  savePortfolio() {
    if (this.form.valid) {
      this.form.patchValue({name: this.auth.currentUserFullName});
      this.form.patchValue({location: this.auth.currentUserData.address.city});
      this.form.patchValue({isPrivate: false});
      if (this.fileData) {
        this.storage.storage
          .ref()
          .child(this.auth.fireAuth.auth.currentUser.uid + '/profile.jpg')
          .put(this.fileData)
          .then(snapshot => {
            snapshot.ref.getDownloadURL().then(url => {
              this.auth.userProfilePhoto = url;
              this.form.patchValue({profileImage: url});
              this.db.addPortfolio(this.form);
            });
          });
      } else {
        this.db.addPortfolio(this.form);
      }
    }
  }

  cancelCreation() {
    if (!this.form.dirty || confirm('Are you sure you want to abort changes?')) {
      this.router.navigate(['profile']);
    }
  }

  get fontColor(): string {
    const color = this.form.get('fontColor') as FormGroup;
    if (color) {
      return color.value;
    } else {
      return '#000';
    }
  }

  set fontColor(value) {
    this.form.get('fontColor').setValue(value);
  }

  get headingsColor(): string {
    const color = (this.sections.controls[0] as FormGroup);
    if (color) {
      return (color.getRawValue() as SectionModel).headingColor;
    } else {
      return '#000';
    }
  }

  set headingsColor(value) {
    this.sections.controls.forEach(section => {
      section.get('headingColor').setValue(value);
    });
  }
}
