import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Role } from '../model/roles.model';
import {Permission} from "../model/permission.model";

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private apiUrl = 'http://localhost:8080/api/roles/all';
  private baseUrl ='http://localhost:8080/api/roles'
  private permUrl='http://localhost:8080/api/perms';// backend URL

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiUrl}  `, { headers: this.getHeaders() });
  }

  getRoleById(id: string): Observable<Role> {
    return this.http.get<Role>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createRole(role: Role): Observable<Role> {
    return this.http.post<Role>(this.baseUrl, role, { headers: this.getHeaders() });
  }

  deleteRole(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }


  addPermissionToRole(roleId: string, permissionId: string): Observable<Role> {
    return this.http.put<Role>(`${this.permUrl}/${roleId}/permissions/${permissionId}`, {}, { headers: this.getHeaders() });
  }
  getPermissionsForRole(roleId: string): Observable<Permission[]> {
    return this.http.get<Permission[]>(`${this.permUrl}/role/${roleId}`, { headers: this.getHeaders() });
  }
  removePermissionFromRole(roleId: string, permissionId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${roleId}/permissions/${permissionId}`, { headers: this.getHeaders() });
  }
  getAllPermissions(): Observable<Permission[]> {
    return this.http.get<Permission[]>(this.permUrl, { headers: this.getHeaders() });
  }
}
