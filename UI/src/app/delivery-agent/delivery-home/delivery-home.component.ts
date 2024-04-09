import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarDeliveryComponent } from "../navbar-delivery/navbar-delivery.component";
import { SidebarDeliveryComponent } from "../sidebar-delivery/sidebar-delivery.component";
import { MainbarComponent } from "../mainbar/mainbar.component";
import { Navbar2Component } from "../../components/navbar2/navbar2.component";

@Component({
    selector: 'app-delivery-home',
    standalone: true,
    templateUrl: './delivery-home.component.html',
    styleUrl: './delivery-home.component.scss',
    imports: [CommonModule, NavbarDeliveryComponent, SidebarDeliveryComponent, MainbarComponent, Navbar2Component]
})
export class DeliveryHomeComponent {

}
