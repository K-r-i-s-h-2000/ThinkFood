import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  showNavigationItems(): boolean {
    // Customize this logic based on your requirements
    return this.router.url !== '**';
  }

  
  shouldShowHome(): boolean {
    return this.activatedRoute.snapshot.url[0]?.path !== 'Home';
  }

  shouldShowLogin(): boolean {
    return (this.activatedRoute.snapshot.url[0]?.path !== 'Login'&&this.activatedRoute.snapshot.url[0]?.path !== 'deliveryDetails');
  }

  shouldShowSignUp(): boolean {
    return (this.activatedRoute.snapshot.url[0]?.path !== 'Sign-up'&&this.activatedRoute.snapshot.url[0]?.path !== 'deliveryDetails');
  }
}


