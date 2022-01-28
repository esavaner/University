import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../../auth.service';


@Component({
  selector: 'registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  registerForm: FormGroup;
  submitted = false;
  showErrorMessage = false;
  emailError = false;
  errorMessage = '';
  passNotMatch = false;

  ngOnInit() {
  }

  constructor(private authService: AuthService, private fb: FormBuilder, private ngZone: NgZone) {
    this.createForm();
  }

  createForm() {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['']
    });
  }

  check() {
    console.log('sadf');
    if(this.checkPasswords()) {
      this.passNotMatch = false;
    } else {
      this.passNotMatch = true;
    }
  }

  onSubmit() {
    this.submitted = true;
    this.showErrorMessage = false;
    this.emailError = false;
    if (this.registerForm.valid && !this.passNotMatch) {
      console.log('registering');
      this.authService.register(this.registerForm.get('email').value, this.registerForm.get('password').value).catch(err => {
        console.log(err);
        const errorCode = err.code;

        if (errorCode === 'auth/invalid-email') {
          this.emailError = true;
          this.registerForm.get('email').setErrors(Validators.email);
        }
        if (errorCode === 'auth/email-already-in-use') {
          this.showErrorMessage = true;
          this.errorMessage = 'The email address is already in use by another account.';
        }
      });
    }
  }

  checkPasswords() {
    const pass = this.registerForm.get('password').value;
    const confirmPass = this.registerForm.get('confirmPassword').value;
    return pass === confirmPass;
  }

  registerWithGoogle() {
    this.authService.continueWithGoogle();
  }

  get f() {
    return this.registerForm.controls;
  }
}
