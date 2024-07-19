import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { UserService } from '../services/user.service'; // Adjust the path as needed
import { User } from '../model/user.model';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable
} from "@angular/material/table"; // Adjust the path as needed

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  imports: [
    DatePipe,
    CurrencyPipe,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRowDef,
    MatRow,
    MatPaginator
  ],
  standalone: true
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  totalUsers: number = 0;
  pageSize: number = 10;
  currentPage: number = 0;

  displayedColumns: string[] = ['name', 'surname', 'email', 'identityNumber', 'birthDate', 'salary'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers(this.currentPage, this.pageSize).subscribe(
      data => {
        console.log('Data received from API:', data); // Logging to check data
        this.users = data.users;
        this.totalUsers = data.total;
        console.log('Users loaded: ', this.users); // Logging to check data
        console.log('Total users count: ', this.totalUsers); // Logging to check data
      },
      error => {
        console.error('Error fetching users', error);
      }
    );
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsers();
  }
}
