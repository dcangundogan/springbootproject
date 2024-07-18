import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {HomeComponent} from "./home/home.component";
import {RegistrationComponent} from "./registration/registration.component";
import {LandingComponent} from "./landing/landing.component";
//import {UserDetailsComponent} from "./userDetails/user-details/user-details.component";

export const routes: Routes = [
  { path: '', component:LandingComponent },
  { path: 'login', component: LoginComponent },
  {path : 'home',component :HomeComponent},
  {path:'register',component :RegistrationComponent}

];
