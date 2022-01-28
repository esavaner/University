import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  loginUrl = environment.url + '/validateLogin';

  constructor(private http: HttpClient) { }

  login(email: string, password: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Email: email,
        Password: password
      })
    };
    return this.http.get<any>(this.loginUrl, httpOptions).pipe(map(user => user as User));
  }

  logout() {
    return of(null);
  }
}
