import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
//import {UserDetailsComponent} from "./userDetails/user-details/user-details.component";

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

];
