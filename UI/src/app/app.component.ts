import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from "./components/navbar/navbar.component";





@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    imports: [CommonModule, RouterOutlet, FormsModule, RouterLink, HttpClientModule, NavbarComponent]
})
export class AppComponent {
  title = 'Training_Project'
  str=""
  count = 0
  check ="even"
  is_Hidden = true
  data = {
    tech1 : 'Angular',
    tech2 : 'Nodejs',
    programming : 'JavaScript'
  }
  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  shouldShowFooter(): boolean {
    return this.activatedRoute.snapshot.url[0]?.path !== 'Demo';
  }



}
