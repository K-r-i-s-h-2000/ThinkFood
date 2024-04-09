import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarDeliveryComponent } from "../navbar-delivery/navbar-delivery.component";
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { DeliveryDetailsDisplay } from './delivery-details-display';
import { SidebarDeliveryComponent } from "../sidebar-delivery/sidebar-delivery.component";
import { MainbarHistoryComponent } from "../mainbar-history/mainbar-history.component";

@Component({
    selector: 'app-delivery-history',
    standalone: true,
    templateUrl: './delivery-history.component.html',
    styleUrl: './delivery-history.component.scss',
    imports: [CommonModule, NavbarDeliveryComponent, SidebarDeliveryComponent,MainbarHistoryComponent]
})
export class DeliveryHistoryComponent {
 

}
