import { Component, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { Delivery } from '../delivery/delivery';
import { LoginService } from '../../login.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { DeleteDeliveryComponent } from '../delete-delivery/delete-delivery.component';
import { MatDialog } from '@angular/material/dialog';
import { UpdateDeliveryComponent } from '../update-delivery/update-delivery.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';


@Component({
  selector: 'app-mainbar',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule,MatSlideToggleModule],
  templateUrl: './mainbar.component.html',
  styleUrl: './mainbar.component.scss'
})
export class MainbarComponent {

  delivery:Delivery
  isDeliveryOpen:string
  orderCount: number;


  constructor(public dialog: MatDialog, private deliveryHomeService:DeliveryHomeService,private loginService: LoginService, private route:ActivatedRoute, private router:Router, private formBuilder: FormBuilder){}
  ngOnInit(): void {

    const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)

    console.log(deliveryId);
    this.deliveryHomeService.getAgents(deliveryId).subscribe(details=>{
      this.delivery=details;
      this.isDeliveryOpen=details.deliveryAvailability;

    })


    console.log(deliveryId);
    this.deliveryHomeService.getOrderCount(deliveryId)
      .subscribe(count => {
        this.orderCount = count;
        console.log(this.orderCount);
      });
  }


  updateStatus(): void {
    this.deliveryHomeService.updateStatusAvailability(this.delivery.id).subscribe(
      (result) => {
        if (result) {
          console.log('Status updated successfully.');
          // Assuming you want to refresh delivery details after updating status
          // localStorage.setItem('isDeliveryOpen', this.isDeliveryOpen ? 'OPEN' : 'CLOSE');
  
          const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
          this.deliveryHomeService.getAgents(deliveryId).subscribe(details=>{
            this.delivery=details;
            console.log(details.deliveryAvailability)
            this.isDeliveryOpen=details.deliveryAvailability;
            console.log(this.isDeliveryOpen)
      
          })
        } else {
          console.log('Error updating status.');
        }
      },
      (error) => {
        console.error('Error updating status:', error);
      }
    );
  }

  update()
  {
    let dialogRef = this.dialog.open(UpdateDeliveryComponent, {
  
    });
  }

  softDelete() {
    let dialogRef = this.dialog.open(DeleteDeliveryComponent, {
  
    });
}

}
