import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userUrl = environment.url + '/users';

  constructor(private http: HttpClient) { }

  fetchUsers(email: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Email: email
      })
    };
    return this.http.get<User[]>(this.userUrl, httpOptions).pipe(map(users => users as User[]));
  }

  addUser(payload: User) {
    return this.http.post<User>(this.userUrl, payload);
  }

  deleteUser(id: number) {
    return this.http.delete(this.userUrl + '/' + id);
  }

  updateUser(payload: User, id: number) {
    return this.http.put<User>(this.userUrl + '/' + id, payload);
  }
}
