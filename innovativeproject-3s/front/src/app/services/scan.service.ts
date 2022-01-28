import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Scan } from '../models/scan';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Tool } from '../models/tool';

@Injectable({
  providedIn: 'root'
})
export class ScanService {
  scanUrl = environment.url + '/scans';

  constructor(private http: HttpClient) { }

  fetchScans(email: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Email: email
      })
    };
    return this.http.get<Scan[]>(this.scanUrl, httpOptions).pipe(map(scans => scans as Scan[]));
  }

  addScan(payload: Scan) {
    return this.http.post<Scan>(this.scanUrl, payload);
  }

  deleteScan(id: number) {
    return this.http.delete(this.scanUrl + '/' + id);
  }

  triggerScan(tool: Tool, project: string, emailR: string, loginEmail: string) {
      console.log(tool, project, emailR, loginEmail);
      return this.http.post<any>(environment.url + '/trivy', {
          toolName: tool.name,
          projectName: project,
          email: 'rurakf@gmail.com',
          login: 'admin@nokia.com',
          username: tool.login,
          password: tool.password,
          name: 'danieldrapala/tooltest:latest',
          isPrivate: 'true'
      });
  }
}
