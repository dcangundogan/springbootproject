import { Component, ViewChild } from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { UserService } from '../services/user.service';
import { User } from '../model/user.model';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {CurrencyPipe, DatePipe} from "@angular/common";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  imports: [
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    DatePipe,
    MatHeaderRow,
    MatRowDef,
    MatHeaderRowDef,
    CurrencyPipe,
    MatPaginator,
    MatRow,
    MatLabel
  ],
  standalone: true
})
export class UsersComponent {
  displayedColumns: string[] = ['name', 'surname', 'email', 'identityNumber', 'birthDate', 'salary'];
  dataSource = new MatTableDataSource<User>();
  searchControl = new FormControl('');
  totalUsers = 0;
  pageSize = 10;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userService: UserService) {
    this.searchControl.valueChanges.subscribe(value => {
      // @ts-ignore
      this.applyFilter(value);
    });
  }

  loadUsers() {
    this.userService.getUsers(0, this.pageSize).subscribe(response => {
      this.dataSource.data = response.users;
      this.totalUsers = response.total;
      this.dataSource.paginator = this.paginator;
    });
  }

  applyFilter(value: string) {
    this.dataSource.filter = value.trim().toLowerCase();
  }

  onPageChange(event: any) {
    this.userService.getUsers(event.pageIndex, event.pageSize).subscribe(response => {
      this.dataSource.data = response.users;
      this.totalUsers = response.total;
    });
  }
}
