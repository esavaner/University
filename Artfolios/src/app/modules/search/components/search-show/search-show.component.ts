import { Component, OnInit, Input } from '@angular/core';
import {QueryDocumentSnapshot} from '@angular/fire/firestore';
import {PortfolioModel} from '../../../profiles/models/portfolio-model';
import { Router } from '@angular/router';

@Component({
  selector: 'search-show',
  templateUrl: './search-show.component.html',
  styleUrls: ['./search-show.component.scss']
})
export class SearchShowComponent implements OnInit {

  @Input() portfolio: QueryDocumentSnapshot<PortfolioModel>;
  portfolioData: PortfolioModel;
  portfolioId: string;

  constructor(private router: Router) {

  }

  ngOnInit() {
    this.portfolioData = this.portfolio.data();
    this.portfolioId = this.portfolio.id;
  }

  showPortfolio() {
    this.router.navigate([]).then(result => {
      window.open(('/profile/portfolio/' + this.portfolioId), '_blank');
    });
  }

}

