import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model'; // Adjust the path as needed

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users/all'; //backend url

  constructor(private http: HttpClient) {}

  getUsers(page: number, size: number): Observable<{ users: User[], total: number }> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<{ users: User[], total: number }>(`${this.apiUrl}?page=${page}&size=${size}`, { headers });
  }
}
