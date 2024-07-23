import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { RoleService } from '../services/role.service';
import { Role } from '../model/roles.model';
import {RoleDialogComponent} from "./role-dialog.component";
import {MatButton} from "@angular/material/button";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow,
  MatHeaderRowDef, MatRow, MatRowDef,
  MatTable
} from "@angular/material/table";

@Component({
  selector: 'app-role-management',
  templateUrl: './roles.component.html',
  standalone: true,
  imports: [
    MatButton,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCellDef,
    MatCell,
    MatHeaderRowDef,
    MatHeaderRow,
    MatRow,
    MatRowDef
  ],
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {
  roles: Role[] = [];

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
      data: { roleName: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const newRole: Role = { id: '', roleName: result.roleName, permissions: [] };
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
