import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionAddButtonComponent } from './section-add-button.component';

describe('SectionAddButtonComponent', () => {
  let component: SectionAddButtonComponent;
  let fixture: ComponentFixture<SectionAddButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionAddButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionAddButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
