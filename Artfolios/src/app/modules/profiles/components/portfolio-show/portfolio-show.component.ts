import {Component, OnInit} from '@angular/core';
import {PortfolioDataService} from '../../services/portfolio-data.service';
import {AuthService} from '../../../../auth.service';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {AngularFireStorage} from '@angular/fire/storage';
import {AngularFirestore} from '@angular/fire/firestore';
import {NavigationEnd, Router} from '@angular/router';
import {PortfolioModel} from '../../models/portfolio-model';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'portfolio-show',
  templateUrl: './portfolio-show.component.html',
  styleUrls: ['./portfolio-show.component.scss']
})
export class PortfolioShowComponent implements OnInit {
  form: FormGroup;
  portfolioDoc: any;
  shouldShow = false;
  ownerId: string;
  showError = false;

  constructor(private portfolioService: PortfolioDataService, private authService: AuthService,
              private fb: FormBuilder, private storage: AngularFireStorage, private afs: AngularFirestore,
              private router: Router) {
    this.router.events.subscribe(value => {
      if (value instanceof NavigationEnd) {
        this.downloadPortfolio();
      }
    });
  }

  ngOnInit() {
    this.form = this.fb.group({
      name: this.fb.control(''),
      biography: this.fb.control(''),
      profileImage: this.fb.control(''),
      sections: this.fb.array([]),
      professions: this.fb.control(''),
      backgroundColor: this.fb.control(''),
      fontColor: this.fb.control(''),
    });
    this.downloadPortfolio();
  }

  redirectToSearch(input: string) {
    this.router.navigate(['/search', {text: input, loc: '', prof: ''}]);
  }

  get name() {
    return this.form.get('name').value;
  }

  get isOwner() {
    if (this.authService.currentUser == null) {
      return false;
    } else {
      return this.authService.currentUser.uid === this.ownerId;
    }
  }

  get professions() {
    return this.form.get('professions').value;
  }

  get sections() {
    return this.form.get('sections') as FormArray;
  }

  get biography() {
    return this.form.get('biography').value;
  }

  get photo() {
    const url = this.form.get('profileImage').value;
    if (url) {
      return url;
    } else {
      return '../../../../../assets/default-profile-photo.png';
    }
  }

  get backgroundColor() {
    return this.form.value.backgroundColor;
  }

  get fontColor() {
    return this.form.value.fontColor;
  }

  deletePortfolio(event: Event) {
    event.preventDefault();
    if (confirm('Are you sure you want to delete your portfolio? This is something you CANNOT undo.')) {
      this.portfolioService.deletePortfolio(this.ownerId);
    }
  }

  private downloadPortfolio() {
    this.ownerId = this.router.url.split('/').pop();
    this.portfolioDoc = this.afs.collection('portfolio').doc<PortfolioModel>(this.ownerId);
    this.portfolioDoc.valueChanges().pipe().subscribe(v => {
      if (v) {
        this.sections.clear();
        v.sections.forEach(section => this.sections.controls.push(this.portfolioService.createSectionForm(section)));
        this.form.patchValue(v);
        this.shouldShow = true;
      } else {
        this.showError = true;
      }
    });
  }
}
