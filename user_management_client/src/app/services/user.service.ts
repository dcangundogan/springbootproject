import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model'; // Adjust the path as needed

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users/all'; // backend URL

  constructor(private http: HttpClient) {}

  getUsers(page: number, size: number, searchTerms: any): Observable<{ content: User[], totalElements: number }> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());

    // Add search parameters
    for (const key in searchTerms) {
      if (searchTerms[key]) {
        params = params.set(key, searchTerms[key]);
      }
    }

    return this.http.get<{ content: User[], totalElements: number }>(this.apiUrl, { headers, params });
  }
}
