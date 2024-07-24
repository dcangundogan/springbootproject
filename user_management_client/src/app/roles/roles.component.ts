import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { RoleService } from '../services/role.service';
import { Role } from '../model/roles.model';
import { RoleDialogComponent } from "./role-dialog.component";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-role-management',
  templateUrl: './roles.component.html',
  standalone: true,
  imports: [
    CommonModule, // Import CommonModule for *ngFor
    MatButton,
    MatButtonModule,
    MatDialogModule,
    FormsModule,
  ],
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {
  roles: Role[] = [];
  displayedColumns: string[] = ['roleName', 'actions'];

  constructor(private roleService: RoleService, public dialog: MatDialog, private router: Router) {}

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.roleService.getRoles().subscribe(
      data => {
        this.roles = data;
        console.log(this.roles);
      },
      error => {
        console.error('Error fetching roles', error);
      }
    );
  }

  addRole(): void {
    const dialogRef = this.dialog.open(RoleDialogComponent, {
      width: '250px',
      data: { rolename: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const newRole: Role = { id: '', rolename: result.rolename, permissions: [] };
        this.roleService.createRole(newRole).subscribe(
          data => {
            this.loadRoles();
          },
          error => {
            console.error('Error creating role', error);
          }
        );
      }
    });
  }

  deleteRole(id: string): void {
    this.roleService.deleteRole(id).subscribe(
      () => {
        this.loadRoles();
      },
      error => {
        console.error('Error deleting role', error);
      }
    );
  }

  navigateToPermissions(roleId: string): void {
    this.router.navigate(['/permissions', roleId]);
  }
}
