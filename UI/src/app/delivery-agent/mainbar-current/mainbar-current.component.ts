import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryDetailsDisplay } from '../delivery-history/delivery-details-display';
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { DeliveryDetailsService } from '../../service/delivery-details.service';
import { DeliveredconfirmationComponent } from '../deliveredconfirmation/deliveredconfirmation.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-mainbar-current',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mainbar-current.component.html',
  styleUrl: './mainbar-current.component.scss'
})
export class MainbarCurrentComponent implements OnInit, OnDestroy{
  deliveryId: number;
  deliveryDetails: DeliveryDetailsDisplay;
  intervalTimeCurrent:any
   orderId :number;
  constructor(private dialog:MatDialog ,private deliveryHomeService:DeliveryHomeService,private deliveryDetailsService: DeliveryDetailsService){}
  ngOnDestroy(): void {
    clearInterval(this.intervalTimeCurrent)
  }
  
  ngOnInit(): void {
   this.loadDetails();
   if(this.deliveryDetails!=null)
   {
    this.intervalTimeCurrent= setInterval(() => {
      this.updatePreparationStatus()
    }, 15000);
   }
  }

  loadDetails()
  {
      this.deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
      console.log(this.deliveryId)
    this.deliveryHomeService.getDeliveryDetails(this.deliveryId).subscribe(
      (data) => {
        if(data!=null){
        this.deliveryDetails = data;
        localStorage.setItem('orderId', data.orderId.toString())
        this.orderId = parseInt(localStorage.getItem('orderId'), 10);
        
      }
        else{
          console.log("There is no current order to display!")
        }
      },
      (error) => {
        console.error("Error fetching delivery details");
      }
    );
  }

  updatePreparationStatus(){
    this.intervalTimeCurrent= this.deliveryDetailsService.getPreparationStatusDetails(this.orderId).subscribe(
      (response) => {
        console.log(response);
        if(response)
        {
        this.deliveryHomeService.getDeliveryDetails(this.deliveryId).subscribe(
      (data) => {
        this.deliveryDetails = data;
        if(data.deliveryStatus!='PREPARING')
        {
          clearInterval(this.intervalTimeCurrent)
        }
      },
      (error) => {
        console.error('Error fetching delivery details:', error);
      }
    );
        }
      },
      (error) => {
        console.error(error);
      }
    );
   }
 
   
  loadDetailsAfterStatusChange(){
    this.deliveryHomeService.getDeliveryDetails(this.deliveryId).subscribe(
      (data) => {
        this.deliveryDetails = data;
      },
      (error) => {
        console.error('Error fetching delivery details:', error);
      }
    );
  }

  updateStatus() {
  
    this.deliveryHomeService.updateDeliveryStatus(this.deliveryId).subscribe(
      (response) => {
        console.log(response); 
        this.loadDetailsAfterStatusChange()
      },
      (error) => {
        console.error('Error updating delivery status:', error);
      }
    );
  }

  updateStatusDelivered() {
    clearInterval(this.intervalTimeCurrent)
    let dialogRef = this.dialog.open(DeliveredconfirmationComponent, {
  
    });
  }

  getStatusImage(): any {
    
    return 'assets/images/no_order.jpg';
  
  }

  getStatusText(status: string): string {
    // Implement logic to return the appropriate text based on the status
    switch (status) {
      case 'PREPARATION':
        return 'ORDER  PREPARING';
      case 'OUT_FOR_DELIVERY':
        return 'ORDER  OUT FOR DELIVERY';
      case 'ON_THE_WAY':
        return 'ORDER  ON THE WAY';
      case 'DELIVERED':
        return 'SUCCESSFULLY DELIVERED';
      default:
        return 'Please Try again after some time!';
    }
  }
}
