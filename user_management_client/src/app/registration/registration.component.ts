import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service'; // Adjust the path as needed
import { RegisterUserDto } from '../model/register-user.dto'; // Adjust the path as needed
import { formatDate } from '@angular/common';
import {FormsModule} from "@angular/forms"; // Import formatDate from Angular common module

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  imports: [
    FormsModule
  ],
  standalone: true
})
export class RegistrationComponent {
  user: RegisterUserDto = {
    name: '',
    surname: '',
    email: '',
    password: '',
    identity_number: '',
    birth_date: '',
    salary: 0
  };

  constructor(private authService: AuthService, private router: Router) {}

  onRegister(): void {
    const formattedUser = {
      ...this.user,
      birth_date: formatDate(this.user.birth_date, 'yyyy-MM-dd', 'en')
    };

    this.authService.register(formattedUser).subscribe(
      response => {
        console.log('Registration successful', response);
        this.router.navigate(['/home']);
      },
      error => {
        console.error('Registration failed', error);
        // Optionally, handle registration error (e.g., show a message to the user)
      }
    );
  }
}
