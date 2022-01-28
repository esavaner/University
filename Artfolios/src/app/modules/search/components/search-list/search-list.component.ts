import { Component, OnInit } from '@angular/core';
import { PortfolioModel } from '../../../profiles/models/portfolio-model';
import {SearchService} from '../../search.service';
import {BehaviorSubject, Observable, ObservedValueOf} from 'rxjs';
import {ProfessionModel} from '../../../profiles/models/profession-model';
import {filter, map} from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {QueryDocumentSnapshot} from '@angular/fire/firestore';
import { Router, ActivatedRoute } from '@angular/router';
import {LocationModel} from '../../location-model';

@Component({
  selector: 'search-list',
  templateUrl: './search-list.component.html',
  styleUrls: ['./search-list.component.scss']
})

export class SearchListComponent implements OnInit {

  public allPortfolioList: Observable<QueryDocumentSnapshot<PortfolioModel>[]>;
  public displayPortfolioList: Observable<QueryDocumentSnapshot<PortfolioModel>[]> ;

  public proffList: Observable<ProfessionModel[]>;
  locationList: Observable<LocationModel[]>;


  searchForm: FormGroup;
  searching = false;
  click = false;


  constructor(public searchService: SearchService, private fb: FormBuilder, private router: Router, private route: ActivatedRoute) {
    this.searchForm = this.fb.group({
      text: [''],
      location: [''],
      tag: ['']
    });
    this.allPortfolioList = this.searchService.allPortfolios;
    this.displayPortfolioList = this.allPortfolioList;
    this.proffList = this.searchService.professions;
    this.locationList = this.searchService.locations;
  }

  ngOnInit() {
    // this.route.params.subscribe(p => {
    //   const str: string = p[2];
    //   const tags = str ? str.split(',') : [];
    //   this.searchForm.patchValue({text: p[0], location: p[1], tag: tags});
    //   this.onSubmit();
    // });
    let text = this.route.snapshot.paramMap.get('text');
    let loc = this.route.snapshot.paramMap.get('loc');
    let prof = this.route.snapshot.paramMap.get('prof');
    this.searchForm.patchValue({text: text, location: loc, tag: prof})

    this.onSubmit();
  }

  get f() { return this.searchForm.controls; }

  clickSearch() {
    this.click = true;
    this.onSubmit();
  }

  onSubmit() {
    const txt: string = this.f.text.value ? this.f.text.value : '';
    const loc: string = this.f.location.value ? this.f.location.value : '';
    const tg: string[] = this.f.tag.value ? this.f.tag.value : [];
    const params = [txt, loc, tg];
    this.router.navigate(['search', params]);

    this.displayPortfolioList = this.allPortfolioList.pipe(
       map((rr) => {
        return rr.filter((ss) => {
          return this.checkProffesions(ss.data().professions, tg) && this.checkCity(ss.data().location, loc)
          && this.checkInput(txt, ss.data());
        });
      })
    );


    if (txt === '' && loc === '' && tg.length === 0) {
      this.searching = false;
    } else {
      this.searching = true;
    }
  }

  checkInput(input: string, userData: PortfolioModel): boolean {
    if (input.trim() === '') {
      return true;
    }

    const val = input.trim().split(' ');
    const splitName = userData.name.trim().split(' ');

    for (let i = 0; i < val.length; i++) {

      if (this.equalsIgnoringCase(userData.location, val[i])) {
        return true;
      }

      for (let k = 0; k < userData.professions.length; k++) {
        if (this.equalsIgnoringCase(val[i], userData.professions[k])) {
          return true;
        }
      }

      for (let j = 0; j < splitName.length; j++) {
        if (this.equalsIgnoringCase(splitName[j], val[i])) {
          return true;
        }
      }
    }

    return false;
  }


  equalsIgnoringCase(text, other): boolean {
    if (text.localeCompare(other, 'en', {sensitivity: 'base'}) === 0) {
      return true;
    } else {
      return false;
    }


  }

  checkProffesions(profileProfessions: ProfessionModel[], selectedProfessions: string[]): boolean {
    if (selectedProfessions.length === 0) {
      return true;
    }

    for (let i = 0; i < selectedProfessions.length; i++) {
      for (let j = 0; j < profileProfessions.length; j++) {
        if (this.equalsIgnoringCase(profileProfessions[j], selectedProfessions[i])) {
          return true;
        }
      }
    }

    /*   for(let selectedProfession in selectedProfessions){
         for (let profileProfession in profileProfessions) {
           //if( selectedProfession == profileProfession.name) return true;
           console.log(selectedProfession + " " + prof)
         }
       }

      selectedProfessions.forEach(selectedProfession =>{
         profileProfessions.forEach(profileProfession =>{
           console.log(selectedProfession + " --- " +  profileProfession);
           //if (selectedProfession == profileProfession.name) return true;
         })
       });*/

    return false;
  }

  checkCity(city: string, selectedCity: string): boolean {
    if (selectedCity == null || selectedCity === '') {
      return true;
    }
    return city === selectedCity;
  }

}
