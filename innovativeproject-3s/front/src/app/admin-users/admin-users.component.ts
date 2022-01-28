import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Select, Store } from '@ngxs/store';
import {UserState} from '../states/user.state';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {AddUser, DeleteUser, UpdateUser, GetUsers} from '../actions/user.action';
import { LoginState } from '../states/login.state';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {

  @Select(UserState.getUserList) userList: Observable<User[]>;

  currentUser: any;

  userForm: FormGroup;
  loading = false;

  constructor(private fb: FormBuilder, private store: Store) {
    this.currentUser = this.store.selectSnapshot(LoginState.userDetails);
  }

  ngOnInit() {
    this.store.dispatch(new GetUsers({email: this.currentUser.email}));
    this.userForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
      type: ['', Validators.required]
    });
  }

  get f() { return this.userForm.controls; }

  get email() { return this.userForm.get('email'); }
  get password() { return this.userForm.get('password'); }

  onSubmit() {
    if (this.userForm.valid) {
      this.loading = true;
      const em: string = this.f.email.value;
      const pass: string = this.f.password.value;
      const t: string = this.f.type.value;

      this.store.dispatch(new AddUser({id: null, token: null, email: em, created: null, permission: [t]}))
      .subscribe(() => this.userForm.reset());

      this.loading = false;
    }
  }

}
