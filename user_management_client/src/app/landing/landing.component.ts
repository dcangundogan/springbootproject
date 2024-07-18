import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css'],
  standalone:true
})
export class LandingComponent {

  constructor(private router: Router) {}

  navigateToRegister(): void {
    this.router.navigate(['/register']);
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }
}
