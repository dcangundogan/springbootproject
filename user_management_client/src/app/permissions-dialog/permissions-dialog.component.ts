import { Component, Input, OnInit } from '@angular/core';
import {NgForOf} from "@angular/common";


@Component({
  selector: 'app-permissions-dialog',
  templateUrl: './permissions-dialog.component.html',
  styleUrls: ['./permissions-dialog.component.css'],
  imports: [
    NgForOf
  ],
  standalone: true
})
export class PermissionsDialogComponent implements OnInit {
  @Input() permissions: any[] = [];

  constructor() {}

  ngOnInit(): void {
    console.log('Permissions received in modal:', this.permissions);
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
