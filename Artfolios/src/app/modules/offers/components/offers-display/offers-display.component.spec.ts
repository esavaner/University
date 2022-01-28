import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {OffersDisplayComponent} from './offers-display.component';

describe('OffersDisplayComponent', () => {
  let component: OffersDisplayComponent;
  let fixture: ComponentFixture<OffersDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OffersDisplayComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OffersDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
