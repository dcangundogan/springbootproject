import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    RouterOutlet  // Ensure RouterOutlet is imported to handle routing in standalone mode
  ]
})
export class AppComponent {
  title = 'Login Page';
}
