import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoleService } from '../services/role.service';
import { Role } from '../model/roles.model';
import {PermissionsDialogComponent} from "../permissions-dialog/permissions-dialog.component";
import {NgForOf} from "@angular/common";

declare var $: any; // Declare jQuery

@Component({
  selector: 'app-role-management',
  templateUrl: './roles.component.html',
  standalone: true,
  imports: [
    PermissionsDialogComponent,
    NgForOf
  ],
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {
  roles: Role[] = [];
  selectedPermissions: any[] = []; // To store the permissions of the selected role

  constructor(private roleService: RoleService, private router: Router) {}

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
    const newRole: Role = { id: '', rolename: 'New Role', permissions: [] };
    this.roleService.createRole(newRole).subscribe(
      data => {
        this.loadRoles();
      },
      error => {
        console.error('Error creating role', error);
      }
    );
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

  openPermissionsDialog(role: Role): void {
    console.log('Opening permissions dialog for role:', role);
    this.selectedPermissions = role.permissions;
    console.log('Selected permissions:', this.selectedPermissions);

    const modal = document.getElementById('permissionsModal');
    if (modal) {
      const modalBackdrop = document.createElement('div');
      modalBackdrop.className = 'modal-backdrop fade show';
      document.body.appendChild(modalBackdrop);
      modal.style.display = 'block';
      modal.classList.add('show');
      document.body.classList.add('modal-open');
    }
  }

  closeModal(): void {
    const modal = document.getElementById('permissionsModal');
    if (modal) {
      modal.style.display = 'none';
      document.body.classList.remove('modal-open');
      const modalBackdrop = document.getElementsByClassName('modal-backdrop')[0];
      if (modalBackdrop) {
        document.body.removeChild(modalBackdrop);
      }
    }
  }
}
