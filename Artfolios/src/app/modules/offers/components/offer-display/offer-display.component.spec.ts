import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {OfferDisplayComponent} from './offer-display.component';

describe('OfferDisplayComponent', () => {
  let component: OfferDisplayComponent;
  let fixture: ComponentFixture<OfferDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OfferDisplayComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
