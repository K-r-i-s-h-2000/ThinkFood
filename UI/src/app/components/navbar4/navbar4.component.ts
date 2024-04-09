import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-navbar4',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterOutlet],
  templateUrl: './navbar4.component.html',
  styleUrl: './navbar4.component.scss'
})

export class Navbar4Component implements OnInit {


  constructor(private router: Router) { }

  ngOnInit(): void {
    
  }

  Logout() {
    localStorage.removeItem('token');
    this.router.navigate(['Login']);
  }

}
