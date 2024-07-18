import {Component, Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {FormsModule} from "@angular/forms";
@Injectable()
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    FormsModule
  ],
  standalone: true
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}


  onLogin(): void {
    const loginData = { email: this.email, password: this.password };
    this.http.post<any>('http://localhost:8080/auth/login', loginData).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        // Assuming the response contains the token and redirect URL
        localStorage.setItem('token', response.token);
        this.router.navigate(['/home']); // or the route you need to go
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
}
