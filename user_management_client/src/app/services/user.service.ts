import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model'; // Adjust the path as needed

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users/all'; // Your backend API URL

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<User[]>(this.apiUrl, { headers });
  }
}
