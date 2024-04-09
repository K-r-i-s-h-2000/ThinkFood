import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { Navbar4Component } from '../components/navbar4/navbar4.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet, FormsModule, Navbar4Component,
    HttpClientModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})

export class AdminComponent {

  // constructor(private http: HttpClient, private router: Router) {}


  // navigateTo(route: string): void {
  //   this.router.navigate([route]);
  // }

}
