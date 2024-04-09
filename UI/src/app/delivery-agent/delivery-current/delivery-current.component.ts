import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarDeliveryComponent } from "../navbar-delivery/navbar-delivery.component";
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { DeliveryDetailsDisplay } from '../delivery-history/delivery-details-display';
import { Subscription,interval } from 'rxjs';
import { SidebarDeliveryComponent } from "../sidebar-delivery/sidebar-delivery.component";
import { MainbarCurrentComponent } from "../mainbar-current/mainbar-current.component";

@Component({
    selector: 'app-delivery-current',
    standalone: true,
    templateUrl: './delivery-current.component.html',
    styleUrl: './delivery-current.component.scss',
    imports: [CommonModule, NavbarDeliveryComponent, SidebarDeliveryComponent, MainbarCurrentComponent]
})
export class DeliveryCurrentComponent {
 
}
