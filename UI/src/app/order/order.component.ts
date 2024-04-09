import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { Navbar2Component } from "../components/navbar2/navbar2.component";
import { NavbarComponent } from '../components/navbar/navbar.component';
import { SidebarComponent } from "../components/sidebar/sidebar.component";
import { MainbarComponent } from "./mainbar/mainbar.component";

@Component({
    selector: 'app-order',
    standalone: true,
    templateUrl: './order.component.html',
    styleUrl: './order.component.scss',
    imports: [CommonModule, RouterLink, RouterOutlet, Navbar2Component, NavbarComponent, SidebarComponent, MainbarComponent]
})
export class OrderComponent {

}
