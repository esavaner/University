import { Component, OnInit } from '@angular/core';
import { OffersService } from '../../services/offers.service';
import { OfferModel } from '../../models/offer-model';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { ProfessionModel } from 'src/app/modules/profiles/models/profession-model';
import { AngularFirestore } from '@angular/fire/firestore';
import { MatOptionSelectionChange } from '@angular/material';
import {LocationModel} from '../../../search/location-model';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'offer-list',
  templateUrl: './offer-list.component.html',
  styleUrls: ['./offer-list.component.scss']
})
export class OfferListComponent implements OnInit {
  form: FormGroup;


  offerIds: string[];
  offerItems: OfferModel[];

  offerItemsFiltered: OfferModel[];

  public cityPipe: Observable<LocationModel[]>;
  public professionPipe: Observable<ProfessionModel[]>;

  constructor(private offersService: OffersService, private fb: FormBuilder, private afs: AngularFirestore) {
    this.form = this.createFilterForm();
    this.professionPipe = this.afs.collection('/professions').valueChanges() as Observable<ProfessionModel[]>;
    this.cityPipe = this.afs.collection('/cities').valueChanges() as Observable<LocationModel[]>;
  }

  ngOnInit() {
    this.offersService.offerList.subscribe(snapshot => {
      this.offerItems = snapshot.map(item => {
        return item.payload.doc.data();
      }) as OfferModel[];
      this.offerIds = snapshot.map(item => {
        return item.payload.doc.id;
      });
      this.offerItems.forEach((item, index) => {
        item.id = this.offerIds[index];
      });
      this.offerItems = this.offerItems.filter(item => item.isActive);
      this.offerItemsFiltered = this.offerItems;
    });
  }

  createFilterForm() {
    return this.fb.group({
      priceMin: this.fb.control(''),
      priceMax: this.fb.control(''),
      city: this.fb.control(''),
      profession: this.fb.group({
        name: this.fb.control(''),
      }),
      search: this.fb.control('')
    });
  }
  clearForm() {
    this.form = this.createFilterForm();
    this.offerItemsFiltered = this.offerItems;
    console.log(this.offerIds, this.offerItems, this.offerItemsFiltered);
  }

  onSubmit() {
    this.offerItemsFiltered = this.offerItems.filter(item => this.checkFilter(item));
    console.log(this.offerIds, this.offerItems, this.offerItemsFiltered);
  }

  checkFilter(item: OfferModel): boolean {
    if (this.priceMin.value) {
      if (item.price < this.priceMin.value) {
        return false;
      }
    }
    if (this.priceMax.value) {
      if (item.price > this.priceMax.value) {
        return false;
      }
    }
    if (this.city.value) {
      if (this.city.value !== item.city) {
        return false;
      }
    }

    let hasThatProfession
    if (this.profession.get('name').value) {
      hasThatProfession = false;
      (this.profession.get('name').value as string[]).forEach(element => {
        if(element == item.profession.name){
          hasThatProfession = true;
        }
      });
    }
    let containInTitle;
    if (this.search.value) {
      const words = (this.search.value as string).split(' ');
      const splittedTitle = item.title.split(' ');
      containInTitle = false
      console.log(words, splittedTitle);
      words.forEach(word => {
        splittedTitle.forEach(titleElement => {
          if (word.toLowerCase() === titleElement.toLowerCase()) {
            containInTitle = true;
          }
        });
      });
    }
    if(hasThatProfession != undefined && containInTitle != undefined){
      return hasThatProfession && containInTitle;
    } 
    if(hasThatProfession != undefined){
      return hasThatProfession;
    }
    if(containInTitle != undefined){
      return containInTitle;
    }
  
    return true;
  }

  sortBy(isDesc: boolean, fieldName: string,  event: MatOptionSelectionChange){
    if(!event.isUserInput){
      return;
    }
    if(isDesc) {
      if(fieldName == 'endDate'){
        this.offerItemsFiltered.sort((a,b) => {return a.endDate.toMillis()-b.endDate.toMillis()})
      }
      if(fieldName == 'price') {
        this.offerItemsFiltered.sort((a,b) => {return a.price-b.price})
      }
    } else {
      if(fieldName == 'endDate'){
        this.offerItemsFiltered.sort((a,b) => {return b.endDate.toMillis()-a.endDate.toMillis()})
      }
      if(fieldName == 'price') {
        this.offerItemsFiltered.sort((a,b) => {return b.price-a.price})
      }
    }
    
    


    console.log("sortowańsko:",event)
  }

  get minValue() {
    return this.form.get('priceMin').value > 0 ? this.priceMin.value : 0;
  }
  get maxValue() {
    return this.form.get('priceMax').value > 0 ? this.priceMax.value : 999999;
  }

  get profession(): FormGroup {
    return this.form.get('profession') as FormGroup;
  }

  get priceMin() {
    return this.form.get('priceMin') as FormControl;
  }
  get priceMax() {
    return this.form.get('priceMax') as FormControl;
  }
  get city() {
    return this.form.get('city') as FormControl;
  }
  get search() {
    return this.form.get('search') as FormControl;
  }
}
