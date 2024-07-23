import { Component, Inject } from '@angular/core';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogContent,
  MatDialogTitle,
  MatDialogClose, MatDialogActions
} from '@angular/material/dialog';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-role-dialog',
  templateUrl: './role-dialog.component.html',
  standalone: true,
  imports: [
    MatFormField,
    MatDialogContent,
    MatDialogTitle,
    MatInput,
    FormsModule,
    MatButton,
    MatDialogClose,
    MatDialogActions,
    MatLabel
  ],
  styleUrls: ['./role-dialog.component.css']
})
export class RoleDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RoleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { roleName: string }
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
