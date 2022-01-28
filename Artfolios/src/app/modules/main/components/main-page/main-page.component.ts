import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {

  searchGroup: FormGroup;

  constructor(private router: Router) {
    this.searchGroup = new FormGroup({
      searchInput: new FormControl('')
    });
  }

  ngOnInit() {
  }

  redirectToSearch() {
    const v = this.searchGroup.get('searchInput').value;
    this.router.navigate(['/search', {text: v, loc: '', prof: ''}]);
  }
}
