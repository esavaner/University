import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AuthorisationDisplayComponent} from './authorisation-display.component';

describe('AuthorisationDisplayComponent', () => {
  let component: AuthorisationDisplayComponent;
  let fixture: ComponentFixture<AuthorisationDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AuthorisationDisplayComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorisationDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
