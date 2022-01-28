import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../../auth.service';
import {MatDialogRef} from '@angular/material';


@Component({
  selector: 'app-login-popup',
  templateUrl: './login-popup.component.html',
  styleUrls: ['./login-popup.component.scss']
})
export class LoginPopupComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  showErrorMessage = false;
  errorMessage: string;

  ngOnInit() {
  }

  constructor(private ngZone: NgZone, public dialogRef: MatDialogRef<LoginPopupComponent>,
              public authService: AuthService, private fb: FormBuilder) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.authService.reauthenticateWithCredential(this.loginForm.get('password').value).then(() => {
      this.dialogRef.close(true);
    }, err => {
      console.log(err);
      this.submitted = true;
      this.showErrorMessage = true;

      const errorCode = err.code;
      this.errorMessage = err.message;

      if (errorCode === 'auth/wrong-password' || errorCode === 'auth/user-not-found') {
        this.errorMessage = 'Wrong password';
      }
    });

  }

  dismiss() {
    this.dialogRef.close();
  }

  loginWithGoogle() {
    this.authService.reauthenticateWithGoogle().then(() => {
      this.dialogRef.close(true);
    }, err => {
      console.log(err);
      this.ngZone.run(() => {
        this.showErrorMessage = true;
        this.errorMessage = err.message;
      });
    });
  }

  get f() {
    return this.loginForm.controls;
  }


}
