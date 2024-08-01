import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {AddRoleDialogComponent} from "./add-role-dialog/add-role-dialog.component";
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import {Role} from "./model/roles.model";
import {RolesComponent} from "./roles/roles.component";
import {RoleDialogComponent} from "./roles/role-dialog.component";

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component'; // Adjust the path as needed
import { LoginComponent } from './login/login.component'; // Adjust the path as needed
import { RegistrationComponent } from './registration/registration.component'; // Adjust the path as needed
import { UsersComponent } from './users/users.component'; // Adjust the path as needed

import { AuthService } from './services/auth.service'; // Adjust the path as needed
import { UserService } from './services/user.service';
import {MatSortModule} from "@angular/material/sort";
import {PermissionsDialogComponent} from "./permissions-dialog/permissions-dialog.component";
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatSelectModule} from "@angular/material/select";

// Adjust the path as needed



@NgModule({
  declarations: [


  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegistrationComponent,
    UsersComponent,
    MatSortModule,
    RolesComponent,
    RoleDialogComponent,
    PermissionsDialogComponent,
    AddRoleDialogComponent,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatExpansionModule,
    MatSelectModule

  ],
  providers: [AuthService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
