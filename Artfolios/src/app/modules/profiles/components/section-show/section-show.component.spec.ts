import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionShowComponent } from './section-show.component';

describe('SectionShowComponent', () => {
  let component: SectionShowComponent;
  let fixture: ComponentFixture<SectionShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
