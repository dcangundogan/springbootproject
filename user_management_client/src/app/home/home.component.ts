import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service'; // Adjust the path as needed
import { User } from '../model/user.model';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common"; // Adjust the path as needed

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  imports: [
    DatePipe,
    CurrencyPipe,
    NgIf
  ],
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: User | null = null;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.fetchUserDetails().subscribe(user => {
      this.user = user;
      console.log(this.user);
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
