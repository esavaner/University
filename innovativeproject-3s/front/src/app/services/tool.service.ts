import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Tool } from '../models/tool';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ToolService {
  toolsUrl = environment.url + '/tools';

  constructor(private http: HttpClient) { }

  fetchTools(email: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Email: email
      })
    };
    return this.http.get<Tool[]>(this.toolsUrl, httpOptions).pipe(map(tools => tools as Tool[]));
  }

  addTool(payload: Tool) {
    return this.http.post<Tool>(this.toolsUrl, payload);
  }

  deleteTool(id: number) {
    return this.http.delete(this.toolsUrl + '/' + id);
  }
}
