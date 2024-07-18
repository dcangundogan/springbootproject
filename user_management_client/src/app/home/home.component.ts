import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service'; // Adjust the path as needed
import { User } from '../model/user.model';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common"; // Adjust the path as needed

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [
    DatePipe,
    CurrencyPipe,
    NgIf
  ],
  standalone: true
})
export class HomeComponent implements OnInit {
  user: User | null = null;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.fetchUserDetails().subscribe(user => {
      this.user = user;
    });
  }

  logout(): void {
    this.authService.logout();
  }

  navigateToUsers(): void {
    this.router.navigate(['/users']);
  }
}
