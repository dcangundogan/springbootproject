import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../model/user.model'; // Adjust the path as needed
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUser: User | null = null;
  private token: string | null = null;
  private apiUrl = 'http://localhost:8080/auth'; // Your backend auth API URL
  private userApiUrl = 'http://localhost:8080/api/users'; // Your backend user API URL

  constructor(private http: HttpClient, private router: Router) {}

  login(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
    this.fetchUserDetails().subscribe(user => {
      this.currentUser = user;
      localStorage.setItem('user', JSON.stringify(user));
    });
  }

  fetchUserDetails(): Observable<User | null> {
    const token = localStorage.getItem('token');
    if (!token) {
      return of(null);
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<User>(`${this.userApiUrl}/me`, { headers });
  }

  getCurrentUser(): User | null {
    if (!this.currentUser) {
      this.currentUser = JSON.parse(localStorage.getItem('user')!);
    }
    return this.currentUser;
  }

  logout(): void {
    this.currentUser = null;
    this.token = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  authenticate(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(response => {
        this.login(response.token);
      })
    );
  }
}
