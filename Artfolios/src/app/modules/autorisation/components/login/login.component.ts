import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../../auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  showErrorMessage = false;
  errorMessage: string;

  ngOnInit() {}
  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) {this.createForm(); }

  createForm() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.authService.login(this.loginForm.get('email').value, this.loginForm.get('password').value).then(res => {
      // this.router.navigateByUrl('/');
    }, err => {
      console.log(err);
      this.submitted = true;
      this.showErrorMessage = true;

      const errorCode = err.code;
      const errorMessage = err.message;

      if (errorCode === 'auth/wrong-password' || errorCode === 'auth/user-not-found' ) {
        this.errorMessage = 'Invalid email or password.';
      }
    });

  }

  loginWithGoogle() {
    this.authService.continueWithGoogle();
  }

  get f() { return this.loginForm.controls; }

}
