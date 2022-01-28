import { Component, OnInit } from '@angular/core';
import { Store, Select } from '@ngxs/store';
import { LoginState } from '../states/login.state';
import { ProjectState } from '../states/project.state';
import { Observable } from 'rxjs';
import { Project } from '../models/project';
import { GetProjects, AddProject, SetSelectedProject } from '../actions/project.action';

import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../models/user';
import { Scan } from '../models/scan';
import { Tool } from '../models/tool';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  @Select(ProjectState.getProjectList) projects: Observable<Project[]>;

  projectForm: FormGroup;
  userForm: FormGroup;
  scanForm: FormGroup;
  toolForm: FormGroup;
  currentUser: any;

  userList = [];
  scanList = [];
  toolList = [];

  columns = 3;
  rows = '2:1.5';
  blurValue = 'blur(0px)';
  privateRepo = false;

  constructor(private store: Store, private fb: FormBuilder) {
    this.currentUser = this.store.selectSnapshot(LoginState.userDetails);
    this.projectForm = this.fb.group({
      name: ['', Validators.required]
    });

    this.userForm = this.fb.group({
      user: ['', Validators.required]
    });

    this.toolForm = this.fb.group({
      repoName: ['', Validators.required],
      repo: ['', Validators.required],
      private: ['', Validators.required],
      login: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.scanForm = this.fb.group({
      tool: ['', Validators.required],
      date: ['', Validators.required],
      repeat: ['', Validators.required]
    });
  }

  get fp() { return this.projectForm.controls; }
  get fu() { return this.userForm.controls; }
  get fs() { return this.scanForm.controls; }
  get ft() { return this.toolForm.controls; }

  ngOnInit() {
    this.store.dispatch(new GetProjects({email: this.currentUser.email}));
    const w = window.innerWidth;
    if (w <= 375) {
      this.columns = 1;
      this.rows = '2:2';
    } else if (w <= 480) {
      this.columns = 1;
      this.rows = '2:1.75';
    } else if (w <= 640) {
      this.columns = 1;
      this.rows = '2:1.3';
    } else if (w <= 800) {
      this.columns = 2;
      this.rows = '2:2';
    } else if (w <= 1000) {
      this.columns = 2;
      this.rows = '2:1.75';
    } else if (w <= 1300) {
      this.columns = 3;
      this.rows = '2:2';
    } else if (w <= 1700) {
      this.columns = 3;
      this.rows = '2:1.75';
    } else {
      this.columns = 3;
      this.rows = '2:1.3';
    }
  }

  onResize(event) {
    const w = window.innerWidth;
    if (w <= 375) {
      this.columns = 1;
      this.rows = '2:2';
    } else if (w <= 480) {
      this.columns = 1;
      this.rows = '2:1.75';
    } else if (w <= 640) {
      this.columns = 1;
      this.rows = '2:1.3';
    } else if (w <= 800) {
      this.columns = 2;
      this.rows = '2:2';
    } else if (w <= 1000) {
      this.columns = 2;
      this.rows = '2:1.75';
    } else if (w <= 1300) {
      this.columns = 3;
      this.rows = '2:2';
    } else if (w <= 1700) {
      this.columns = 3;
      this.rows = '2:1.75';
    } else {
      this.columns = 3;
      this.rows = '2:1.3';
    }
  }

  toggle() {
    this.blurValue === 'blur(0px)' ? this.blurValue = 'blur(10px)' : this.blurValue = 'blur(0px)';
    this.userList = [];
    this.scanList = [];
    this.toolList = [];
  }

  togglePrivate() {
    this.privateRepo === true ? this.privateRepo = false : this.privateRepo = true;
  }

  addUser() {
    this.userList.push({email: this.fu.user.value, permission: ['USER']});
  }

  delUser(user) {
    const index = this.userList.indexOf(user);
    if (index > -1) {
        this.userList.splice(index, 1);
    }
  }

  addScan() {
    this.scanList.push({
      toolName: this.fs.tool.value,
      fakeDate: String(this.fs.date.value.toLocaleDateString()),
      stringDate: String(this.fs.date.value.toLocaleDateString()).replace(/\b0/g, '') + '.' + this.fs.repeat.value,
      email: this.currentUser.email
    });
  }

  delScan(scan) {
    const index = this.scanList.indexOf(scan);
    if (index > -1) {
        this.scanList.splice(index, 1);
    }
  }

  addTool() {
    this.toolList.push({
      name: this.ft.repoName.value,
      repo: this.ft.repo.value, isPrivate: this.privateRepo,
      login: this.ft.login.value, password: this.ft.password.value
    });
  }

  delTool(tool) {
    const index = this.toolList.indexOf(tool);
    if (index > -1) {
        this.toolList.splice(index, 1);
    }
  }

  onSubmit() {
    console.log({
      name: this.fp.name.value, users: this.userList as User[], scans: this.scanList as Scan[], tools: this.toolList as Tool[]
    } as Project);
    this.store.dispatch(new AddProject({
      name: this.fp.name.value,
      users: this.userList as User[],
      scans: this.scanList as Scan[],
      tools: this.toolList as Tool[],
    } as Project, this.currentUser.email)).subscribe(result => {
      console.log('Project added');
      this.store.dispatch(new GetProjects({email: this.currentUser.email}));
      window.location.reload();
    }, error => {
      console.log('Adding project failed');
    });
  }

  set(project: Project) {
    this.store.dispatch(new SetSelectedProject(project));
  }

}
