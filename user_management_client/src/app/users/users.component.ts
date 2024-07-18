import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service'; // Adjust the path as needed
import { User } from '../model/user.model';
import {CurrencyPipe, DatePipe, NgForOf} from "@angular/common"; // Adjust the path as needed

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  imports: [
    DatePipe,
    CurrencyPipe,
    NgForOf
  ],
  standalone: true
})
export class UsersComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(
      data => {
        this.users = data;
      },
      error => {
        console.error('Error fetching users', error);
      }
    );
  }
}
