import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryDetailsDisplay } from '../delivery-history/delivery-details-display';
import { DeliveryHomeService } from '../../service/delivery-home.service';

@Component({
  selector: 'app-mainbar-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mainbar-history.component.html',
  styleUrl: './mainbar-history.component.scss'
})
export class MainbarHistoryComponent implements OnInit {
  deliveryDetailsList: DeliveryDetailsDisplay[];
  activeAccordionId: number | null = null;

  constructor(private deliveryHomeService:DeliveryHomeService){}
  ngOnInit(): void {
    this.activeAccordionId=null;
    const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
    this.deliveryHomeService.getDeliveryDetailsList(deliveryId).subscribe(
      (data) => {
        this.deliveryDetailsList = data;
      },
      (error) => {
        console.error('Error fetching delivery details list:', error);
      }
    );
  }

 


  toggleAccordion(id: number): void {
    if (this.activeAccordionId === id) {
      this.activeAccordionId = null;
    } else {
      this.activeAccordionId = id;
    }
  }
}
