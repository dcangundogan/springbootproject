import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { AuthLoginInfo } from '../auth/login-info';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class LoginComponent {
  form: any = {};
  isLoginFailed = false;
  errorMessage = '';
  private loginInfo: AuthLoginInfo = new AuthLoginInfo('', ''); // Initialize with default values

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password
    );

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        localStorage.setItem('token', data.accessToken);
        this.isLoginFailed = false;
        this.router.navigate(['home']);
      },
      error => {
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }
}
