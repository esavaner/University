import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Project } from '../models/project';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  projectUrl = environment.url + '/projects';
  addUrl = environment.url + '/projects_add_all';

  constructor(private http: HttpClient) { }

  fetchProjects(email: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Email: email
      })
    };
    return this.http.get<Project[]>(this.projectUrl, httpOptions).pipe(map(projects => projects as Project[]));
  }

  addProject(payload: Project) {
    return this.http.post<Project>(this.addUrl, payload);
  }

  deleteProject(id: number) {
    return this.http.delete(this.projectUrl + '/' + id);
  }

  updateProject(payload: Project, id: number) {
    return this.http.put<Project>(this.projectUrl + '/' + id, payload);
  }
}
