import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {AngularFirestore} from '@angular/fire/firestore';
import {PortfolioModel} from '../profiles/models/portfolio-model';
import {ProfessionModel} from '../profiles/models/profession-model';
import {LocationModel} from './location-model';


@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private afs: AngularFirestore) {
  }

  get professions(): Observable<ProfessionModel[]> {
    return this.afs.collection<ProfessionModel>('/professions').valueChanges();
  }

  get locations(): Observable<LocationModel[]> {
    return this.afs.collection<LocationModel>('/cities').valueChanges().pipe(
      map(cities => {
        cities.sort((a, b) => (a.name > b.name) ? 1 : -1);
        return cities;
      })
    );
  }

  get allPortfolios() {
    const usersCollection = this.afs.collection<PortfolioModel>('portfolio', ref => ref.where('isPrivate', '==', false));

    return usersCollection.snapshotChanges().pipe(
      map(portfolios => portfolios.map(portfolio => {
          return portfolio.payload.doc;
        }
      ))
    );
  }

}




