import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionChoiceComponent } from './section-choice.component';

describe('SectionChoiceComponent', () => {
  let component: SectionChoiceComponent;
  let fixture: ComponentFixture<SectionChoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionChoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
