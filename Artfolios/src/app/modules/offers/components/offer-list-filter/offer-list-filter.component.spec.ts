import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferListFilterComponent } from './offer-list-filter.component';

describe('OfferListFilterComponent', () => {
  let component: OfferListFilterComponent;
  let fixture: ComponentFixture<OfferListFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferListFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferListFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
