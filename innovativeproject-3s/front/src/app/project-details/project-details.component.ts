import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ScanService } from '../services/scan.service';
import { Store, Select } from '@ngxs/store';
import { ProjectState } from '../states/project.state';
import { Observable } from 'rxjs';
import { Project } from '../models/project';
import { GetProjects, DeleteProject } from '../actions/project.action';
import { LoginState } from '../states/login.state';
import { map, concatMap, filter } from 'rxjs/operators';
import { error } from 'protractor';
import { Tool } from '../models/tool';
import { Scan } from '../models/scan';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit {

  @Select(ProjectState.getSelectedProject) selected: Observable<Project>;
  currentUser: any;
  loading = false;
  success = false;
  sending = false;
  failure = false;
  blurValue = 'blur(0px)';
  log: any;

  constructor(private store: Store, private router: Router, private scanService: ScanService) {
    if (!this.selected) {
      this.router.navigate(['/projects']);
    }
  }

  ngOnInit() {
    this.currentUser = this.store.selectSnapshot(LoginState.userDetails);
  }

  trigger(scan: Scan) {
    if (scan.status !== 'positive') {
      this.selected.subscribe(project => {
        const tool = project.tools.filter(t => t.name === scan.toolName);
        this.sending = true;
        this.scanService.triggerScan(tool[0], project.name, 'rurakf@gmail.com', this.currentUser.email).subscribe(result => {
          this.success = true;
          console.log(result);
        }, error => {
          this.failure = true;
          console.log(error);
        });
      });
    }
  }

  closeSuccess() {
    this.success = false;
  }

  closeSending() {
    this.sending = false;
  }

  closeFailure() {
    this.failure = false;
  }

  delProject(id: any) {
    this.selected.subscribe(project => {
      this.store.dispatch(new DeleteProject(project.id, this.currentUser.email));
      this.toggle();
    }, err => {
      console.log(err);
    });
  }

  toggle() {
    this.blurValue === 'blur(0px)' ? this.blurValue = 'blur(10px)' : this.blurValue = 'blur(0px)';
  }

  setLog(display: string) {
    this.log = JSON.parse(display)[0];
  }
}
