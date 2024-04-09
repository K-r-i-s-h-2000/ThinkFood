import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterOutlet,RouterModule } from '@angular/router';

@Component({
  selector: 'app-delivery-details-navbar',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterOutlet,RouterModule],
  templateUrl: './delivery-details-navbar.component.html',
  styleUrl: './delivery-details-navbar.component.scss'
})
export class DeliveryDetailsNavbarComponent {

}
