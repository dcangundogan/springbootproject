import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {HomeComponent} from "./home/home.component";
import {RegistrationComponent} from "./registration/registration.component";
import {LandingComponent} from "./landing/landing.component";
import {UsersComponent} from "./users/users.component";
import {AuthGuard} from "./auth.guard";
import {RolesComponent} from "./roles/roles.component";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MessageComponent} from "./message/message.component";


export const routes: Routes = [
  { path: '', component:LandingComponent },
  { path: 'login', component: LoginComponent },
  {path : 'home',component :HomeComponent},
  {path:'register',component :RegistrationComponent},
  {path:'users',component:UsersComponent,canActivate:[AuthGuard]},
  {path:'roles',component:RolesComponent},
  { path: 'messages/:id', component: MessageComponent },

];
