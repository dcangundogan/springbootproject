import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router: Router) {}

  logout() {
    // Clear session data
    localStorage.removeItem('userToken');
    // Navigate to login page
    this.router.navigate(['/login']);

  }
}
