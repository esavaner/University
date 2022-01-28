import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { HttpErrorResponse } from '@angular/common/http';
import { Store } from '@ngxs/store';
import { Login } from '../actions/login.action';

@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    loading = false;
    returnUrl: string;
    loginError = false;
    errorMsg: string;

    constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private store: Store) {
    }

    ngOnInit() {
        this.loginForm = this.fb.group({
            email: ['', Validators.required],
            password: ['', Validators.required]
        });
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    get f() { return this.loginForm.controls; }

    get email() { return this.loginForm.get('email'); }
    get password() { return this.loginForm.get('password'); }

    onSubmit() {
        if (this.loginForm.valid) {
            this.loading = true;
            this.store.dispatch(new Login({email: this.f.email.value, password: this.f.password.value}))
            .subscribe(data => {
                this.loginError = false;
                this.router.navigate(['/']);
            }, error => {
                this.loading = false;
                this.loginError = true;
                if (error.status === 404) {
                    this.errorMsg = 'Incorrect login or password';
                } else {
                    this.errorMsg = 'Cannot connect to the service';
                }
            });
        }
    }
}
