import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialogClose } from '@angular/material/dialog';
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { DeliveryDetailsDisplay } from '../delivery-history/delivery-details-display';
import { DeliveryDetailsService } from '../../service/delivery-details.service';

@Component({
  selector: 'app-deliveredconfirmation',
  standalone: true,
  imports: [CommonModule,MatDialogClose],
  templateUrl: './deliveredconfirmation.component.html',
  styleUrl: './deliveredconfirmation.component.scss'
})
export class DeliveredconfirmationComponent {
  constructor( private deliveryDetailsService:DeliveryDetailsService, private router:Router, private activatedRouter: ActivatedRoute,private deliveryHomeService: DeliveryHomeService){}
  deliveryId: number;
  orderId: number;
  deliveryDetails: DeliveryDetailsDisplay;
  loadDetails()
  { 
    const orderId= parseInt(localStorage.getItem('orderId'),10)
    this.deliveryDetailsService.getPreparationStatusDetails(orderId).subscribe(
    (response) => {
      console.log(response);
    },
    (error) => {
      console.error(error);
    }
  );
  const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
    this.deliveryHomeService.getDeliveryDetails(this.deliveryId).subscribe(
      (data) => {
        this.deliveryDetails = data;
      },
      (error) => {
        console.error('Error fetching delivery details:', error);
      }
    );
  }
  yes(){
    
    const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
    this.deliveryHomeService.updateDeliveryStatus(deliveryId).subscribe(
      (response) => {
        console.log(response); // Log the response from the server
        // Perform any additional actions if needed
        this.router.navigate(['/deliveryHistory'])
      },
      (error) => {
        console.error('Error updating delivery status:', error);
      }
    );
  
  }
}
