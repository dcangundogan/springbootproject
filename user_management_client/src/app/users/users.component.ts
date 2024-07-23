import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {MatSort, MatSortModule, Sort} from '@angular/material/sort';
import { UserService } from '../services/user.service'; // Adjust the path as needed
import { User } from '../model/user.model';
import { CurrencyPipe, DatePipe } from '@angular/common';
import {
  MatTable,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatCell,
  MatCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
} from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input'; // Adjust the path as needed

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
    MatPaginator,
    MatSort,
    FormsModule,
    MatCardContent,
    MatCard,
    MatFormField,
    MatInput,
    MatLabel,
    MatSortModule
  ],
  standalone: true
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  totalUsers: number = 0;
  pageSize: number = 10;
  currentPage: number = 0;
  sortBy: string = '';
  sortDirection: string = 'asc';

  displayedColumns: string[] = ['name', 'surname', 'email', 'identityNumber', 'birthDate', 'salary'];

  searchTerms: any = {
    name: '',
    surname: '',
    email: '',
    identityNumber: '',
    birthDate: '',
    salary: ''
  };

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers(this.currentPage, this.pageSize, this.searchTerms, this.sortBy, this.sortDirection).subscribe(
      data => {
        console.log('Data received from API:', data); // Logging to check data
        this.users = data.content;
        this.totalUsers = data.totalElements;
        console.log('Users loaded: ', this.users); // Logging to check data
        console.log('Total users count: ', this.totalUsers); // Logging to check data
      },
      error => {
        console.error('Error fetching users', error);
      }
    );
  }

  onSearchChange(): void {
    this.currentPage = 0; // Reset to first page on search
    this.loadUsers();
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsers();
  }

  onSortChange(sort: Sort): void {
    this.sortBy = sort.active;
    this.sortDirection = sort.direction;
    this.loadUsers();
  }
}
