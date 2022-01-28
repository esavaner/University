import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';

import { ErrorInterceptor } from './helpers/error.interceptor';
import { AuthGuard } from './helpers/auth.guard';

import { Routes, RouterModule } from '@angular/router';
import { fakeBackendProvider } from './helpers/fakedb';
import { ToolsComponent } from './tools/tools.component';
import { ProjectsComponent } from './projects/projects.component';

import { NgxPaginationModule } from 'ngx-pagination';
import { AdminUsersComponent } from './admin-users/admin-users.component';

import { NgxsModule } from '@ngxs/store';
import { NgxsStoragePluginModule } from '@ngxs/storage-plugin';
import { ScanState } from './states/scan.state';
import { UserState } from './states/user.state';
import { LoginState } from './states/login.state';
import { ProjectState } from './states/project.state';
import { ToolState } from './states/tool.state';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatExpansionModule, MatFormFieldModule, MatInputModule,
  MatGridListModule, MatDatepickerModule, MatNativeDateModule, MatCheckboxModule } from '@angular/material';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { SettingsComponent } from './settings/settings.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/scans',
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'scans',
    component: ToolsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'projects',
    component: ProjectsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'project/:id',
    component: ProjectDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'users',
    component: AdminUsersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'settings',
    component: SettingsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: '',
    canActivate: [AuthGuard]
  }
];

const matmodules = [
  MatExpansionModule,
  MatFormFieldModule,
  MatInputModule,
  MatGridListModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatCheckboxModule
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    ToolsComponent,
    ProjectsComponent,
    AdminUsersComponent,
    ProjectDetailsComponent,
    SettingsComponent
  ],
  imports: [
    ...matmodules,
    NgxPaginationModule,
    FormsModule,
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    NgxsModule.forRoot([
      ToolState,
      ProjectState,
      ScanState,
      UserState,
      LoginState
    ]),
    NgxsStoragePluginModule.forRoot({
      key: ['auth.token', 'auth.email']
    }),
    BrowserAnimationsModule
  ],
  exports: [
    ...matmodules,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
