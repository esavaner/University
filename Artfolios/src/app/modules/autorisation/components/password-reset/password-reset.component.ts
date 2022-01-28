import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  showErrorMessage = false;
  errorMessage: string;

  ngOnInit() {
  }

  constructor(private auth: AuthService, private fb: FormBuilder, private router: Router) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  onSubmit() {
    this.auth.sendPasswordReset(this.loginForm.get('email').value).then(res => {
      this.router.navigateByUrl('/login');
    }, err => {
      console.log(err);
      this.submitted = true;
      this.showErrorMessage = true;

      const errorCode = err.code;
      this.errorMessage = err.message;

    });
  }

  get f() {
    return this.loginForm.controls;
  }

}
