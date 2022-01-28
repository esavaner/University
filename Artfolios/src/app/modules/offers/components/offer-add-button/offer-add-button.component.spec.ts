import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferAddButtonComponent } from './offer-add-button.component';

describe('OfferAddButtonComponent', () => {
  let component: OfferAddButtonComponent;
  let fixture: ComponentFixture<OfferAddButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferAddButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferAddButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
