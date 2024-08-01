import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import {jwtDecode, JwtDecodeOptions} from "jwt-decode";
import {RegisterUserDto} from "../model/register-user.dto";
import {User} from "../model/user.model";

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

  getToken(): string | null {
    if (!this.token) {
      this.token = localStorage.getItem('token');
    }
    return this.token;
  }

  getUserId(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.userId || null; // Assuming userId is stored in the token
    }
    return null;
  }

  logout(): void {
    this.token = null;
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

  register(registerUserDto: RegisterUserDto): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/signup`, registerUserDto);
  }
  fetchUserDetails(): Observable<User | null> {
    const token = this.getToken();
    if (!token) {
      return of(null);
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<User>(`${this.userApiUrl}/me`, { headers }).pipe(
      tap(user => {
        this.currentUser = user;
      })
    );
  }

  hasAuthority(authority: string): boolean {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.authorities && decodedToken.authorities.includes(authority);
    }
    return false;
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
