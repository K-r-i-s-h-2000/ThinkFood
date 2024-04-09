import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialogClose } from '@angular/material/dialog';


@Component({
  selector: 'app-delete-delivery',
  standalone: true,
  imports: [CommonModule,MatDialogClose],
  templateUrl: './delete-delivery.component.html',
  styleUrl: './delete-delivery.component.scss'
})
export class DeleteDeliveryComponent {

  constructor(private router:Router, private activatedRouter: ActivatedRoute,private deliveryHomeService:DeliveryHomeService){}
  softDelete() {
    const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
    this.deliveryHomeService.softDelete(deliveryId)
      .subscribe(response => {
        console.log(response); // The response from the server (e.g., "Deletion is successful")
        // You can perform any additional actions here if needed
      });
      const confirmation = confirm("Do you want to delete your account?");
        if (confirmation){
          localStorage.clear();
        this.router.navigate(['Login']);
        }
}
}
