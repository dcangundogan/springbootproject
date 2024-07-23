import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component'; // Adjust the path as needed
import { LoginComponent } from './login/login.component'; // Adjust the path as needed
import { RegistrationComponent } from './registration/registration.component'; // Adjust the path as needed
import { UsersComponent } from './users/users.component'; // Adjust the path as needed

import { AuthService } from './services/auth.service'; // Adjust the path as needed
import { UserService } from './services/user.service';
import {MatSortModule} from "@angular/material/sort"; // Adjust the path as needed



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
    MatSortModule
  ],
  providers: [AuthService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
