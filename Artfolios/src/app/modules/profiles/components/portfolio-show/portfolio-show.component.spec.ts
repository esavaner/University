import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioShowComponent } from './portfolio-show.component';

describe('PortfolioShowComponent', () => {
  let component: PortfolioShowComponent;
  let fixture: ComponentFixture<PortfolioShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PortfolioShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortfolioShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
