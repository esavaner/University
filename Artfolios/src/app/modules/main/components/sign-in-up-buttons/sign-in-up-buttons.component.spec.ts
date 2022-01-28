import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SignInUpButtonsComponent} from './sign-in-up-buttons.component';

describe('SignInUpButtonsComponent', () => {
  let component: SignInUpButtonsComponent;
  let fixture: ComponentFixture<SignInUpButtonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SignInUpButtonsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignInUpButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
