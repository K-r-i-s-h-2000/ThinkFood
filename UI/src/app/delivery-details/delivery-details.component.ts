import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryDetailsDisplay } from '../delivery-agent/delivery-history/delivery-details-display';
import { Subscription,interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { DeliveryDetailsService } from '../service/delivery-details.service';
import { DeliveryDetailsNavbarComponent } from "../components/delivery-details-navbar/delivery-details-navbar.component";

@Component({
    selector: 'app-delivery-details',
    standalone: true,
    templateUrl: './delivery-details.component.html',
    styleUrl: './delivery-details.component.scss',
    imports: [CommonModule, DeliveryDetailsNavbarComponent]
})
export class DeliveryDetailsComponent implements OnInit,OnDestroy{
  deliveryDetails:DeliveryDetailsDisplay;
  status:string;
  remainingTime:any;
   TimeTaken :number;
   intervalTime:any;
   count:number;
  constructor(  private route:ActivatedRoute,private deliveryDetailsService:DeliveryDetailsService,private router:Router){}
  ngOnDestroy(): void {
    console.log("Clearing the interval in displaying delivery details")
    clearInterval(this.intervalTime)
  }
  ngOnInit(): void {
    this.count=0;
    const orderId = parseInt(localStorage.getItem('orderId'), 10);
    this.getDeliveryDetails(orderId)
    this.intervalTime=setInterval(() => {
      this.updatePreparationStatus()
    }, 15000);
  }

 getDeliveryDetails(orderId){
  console.log("Going to display the whole delivery details!")
  console.log(orderId)
  this.deliveryDetailsService.getDetails(orderId).subscribe(
    (details) => {
    console.log("Displayed Data")
    console.log(details);
    console.log("Displayed delivery details in console")
   this.deliveryDetails=details;
   this.status=details.deliveryStatus
   console.log(details.timeTaken)
   this.TimeTaken=parseInt(details.timeTaken,10)
   console.log(this.TimeTaken)
   this.calculateRemainingTime();
   if(details.deliveryStatus=='DELIVERED')
      {
        console.log("Clearing the interval in displaying delivery details")
        clearInterval(this.intervalTime)
      }
  //  this.updatePreparationStatus();  
},
  (error) => {
    console.error(error);
  }
  );
 }
 updatePreparationStatus(){
  this.calculateRemainingTime()
  const orderId = parseInt(localStorage.getItem('orderId'),10);
  this.deliveryDetailsService.getPreparationStatusDetails(orderId).subscribe(
    (response) => {
      console.log(response);
      if(response)
      {
        this.getDeliveryDetails(orderId);
      }
    },
    (error) => {
      console.error(error);
    }
  );
 }



 getStatusImage(status: string): string {
  // Implement logic to return the appropriate image path based on the status
  switch (status) {
    case 'PREPARATION':
      return 'assets/images/preparing.jpg';
    case 'OUT_FOR_DELIVERY':
      return '/assets/images/out_for_delivery.jpg';
    case 'ON_THE_WAY':
      return 'assets/images/on_the_way.png'
    case 'DELIVERED':
      return 'assets/images/delivered.jpg';
    default:
      return 'assets/images/default.jpg';
  }
}

getStatusText(status: string): string {
  // Implement logic to return the appropriate text based on the status
  switch (status) {
    case 'PREPARATION':
      return 'The order is being Prepared';
    case 'OUT_FOR_DELIVERY':
      return 'Order is out for delivery';
    case 'ON_THE_WAY':
      return 'Order is on the way';
    case 'DELIVERED':
      return 'Food is delivered';
    default:
      return 'Please Try again after some time!';
  }
}

calculateRemainingTime() {
    console.log(this.TimeTaken)
    const deliveryTime = new Date(this.deliveryDetails.createdDateTime).getTime() +
      (this.TimeTaken * 60000); // Convert minutes to milliseconds
    const currentTime = new Date().getTime();
    this.remainingTime =Math.max(0, Math.floor((deliveryTime - currentTime) / 60000));; // Convert milliseconds to minutes
    console.log(this.remainingTime);
  }
}


