import {Injectable} from '@angular/core';
import {PortfolioModel} from '../models/portfolio-model';
import {SectionModel, TypeOfField} from '../models/section-model';
import {AngularFirestore} from '@angular/fire/firestore';
import {AuthService} from 'src/app/auth.service';
import {Router} from '@angular/router';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {AngularFireStorage} from '@angular/fire/storage';


@Injectable({
  providedIn: 'root'
})
export class PortfolioDataService {

  tempQueue = new Map<FormGroup, File>();
  imagesUploadQueue = new Map<FormGroup, File>();
  imagesLeftToUpload = 0;
  portfolioToUpload: FormGroup = null;
  public photosToDelete = [];

  constructor(private afs: AngularFirestore, private storage: AngularFireStorage,
              private authService: AuthService, private router: Router, private fb: FormBuilder) {

  }

  pushPhotoToDelete(url) {
    this.photosToDelete.push(url);
    console.log(this.photosToDelete);
  }


  get userPhotoURL() {
    if (this.userPortfolio) {
      return this.userPortfolio.profileImage;
    }
  }

  get userPortfolio() {
    return this.authService.currentUserPortfolio;
  }

  get userPortfolioDoc() {
    return this.authService.currentUserPortfolioDoc;
  }

  getPortfolioById(id: string) {
    return this.afs.collection('portfolio').doc<PortfolioModel>(id);
  }

  // getUserById(id: string) {
  //   return this.afs.collection('users').doc<UserData>(id);
  // }


  addPortfolio(portfolio: FormGroup) {
    this.portfolioToUpload = portfolio;
    if (this.imagesUploadQueue.size === 0) {
      console.log('no pgotots uploadnig');
      this.uploadPortfolio();
    }
    console.log(this.imagesUploadQueue.size);
    this.imagesLeftToUpload = this.imagesUploadQueue.size;
    this.imagesUploadQueue.forEach((val, key) => {
      this.uploadImg(key, val);
    });

  }

  uploadPortfolio() {
    this.clearImgQueue();
    const portfolioRef = this.afs.collection('portfolio').doc(this.authService.currentUserId);
    console.log(this.portfolioToUpload.getRawValue());
    console.log('im here');
    const port: PortfolioModel = this.portfolioToUpload.getRawValue();
    portfolioRef.set(this.portfolioToUpload.getRawValue()).then(() => {
      this.authService.currentUserDoc.update({hasPortfolio: true, privateAccount: false});
      console.log('Document successfully written!');
      this.photosToDelete.forEach(url => this.deletePhoto(url));
      this.router.navigate([`/profile/portfolio/${this.authService.currentUserId}`]);
    })
      .catch(error => {
        console.log('Error writing document: ', error);
      });
  }

  deletePhoto(url) {
    this.storage.storage.refFromURL(url).delete().then(_ => {
      console.log('deleted photo');
    });
  }

  deletePortfolio(id: string) {
    const portfolioRef = this.afs.collection('portfolio').doc(id);
    portfolioRef.get().toPromise().then(val => {
      const portfolio = val.data() as PortfolioModel;
      if (portfolio.profileImage !== '') {
        this.deletePhoto(portfolio.profileImage);
      }
      portfolio.sections.forEach(section => {
        section.fields.forEach(field => {
          if (field.type === TypeOfField.IMAGE) {
            this.deletePhoto(field.value);
          }
        });
      });
    });
    portfolioRef.delete()
      .then(val => {
        this.authService.currentUserDoc.update({hasPortfolio: false});
        this.authService.currentUserDoc.update({privateAccount: true});
        this.router.navigate(['/profile']);
      })
      .catch(reason => {
        console.log('Something went wrong: delete portfolio of ' + id);
        console.log(reason);
      });
    this.authService.currentUser.updateProfile({photoURL: ''});
  }

  uploadImg(field: FormGroup, file: File) {
    console.log('uploading ', file.name);
    const path = `${this.authService.currentUserId}/portfolio/sectionImages/${new Date().getTime()}_${file.name}`;
    this.storage.ref(path).put(file).then(snapshot => {
      snapshot.ref.getDownloadURL().then(url => {
        this.imagesLeftToUpload--;
        console.log('uploaded with success ', file.name);
        field.patchValue({value: url});
        if (this.imagesLeftToUpload === 0) {
          console.log('moving on to uploadng portfolio');
          this.uploadPortfolio();
        }
      });
    });
  }

  addImg(field: FormGroup, file: File) {

    this.tempQueue.set(field, file);
    console.log('added photo to temporary queue');
    console.log(this.tempQueue.size);
  }

  clearImgQueue() {
    this.imagesUploadQueue.clear();
    this.imagesLeftToUpload = 0;
  }

  submitImages() {
    this.tempQueue.forEach((value, key) => {
      this.imagesUploadQueue.set(key, value);
    });
    this.clearTempQueue();
    console.log('submitted images');
    console.log('current size: ', this.imagesUploadQueue.size);
  }

  clearTempQueue() {
    this.tempQueue.clear();
  }

  createSectionForm(section: SectionModel): FormGroup {
    let formTemplate: FormGroup;
    const sectionGroup: FormGroup = this.createSectionGroup();
    section.fields.forEach(field => {
      (sectionGroup.get('fields') as FormArray).controls.push(this.createFieldGroup());
    });
    formTemplate = sectionGroup;
    formTemplate.patchValue(section);
    return formTemplate;
  }

  private createSectionGroup(): FormGroup {
    return this.fb.group({
      fields: this.fb.array([]),
      titleValue: this.fb.control(''),
      titleHide: this.fb.control(''),
      headingColor: this.fb.control('#000')
    });
  }

  private createFieldGroup(): FormGroup {
    return this.fb.group({
      type: this.fb.control(''),
      value: this.fb.control(''),
      order: this.fb.control(''),
      width: this.fb.control(''),
      offset: this.fb.control(''),
      style: this.fb.control(''),
      class: this.fb.control(''),
    });
  }

}
