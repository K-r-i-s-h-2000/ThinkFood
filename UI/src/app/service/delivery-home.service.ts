import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Delivery } from '../delivery-agent/delivery/delivery';
import { DeliveryDetailsDisplay } from '../delivery-agent/delivery-history/delivery-details-display';

@Injectable({
  providedIn: 'root'
})
export class DeliveryHomeService {

  deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
  constructor(private http:HttpClient) { }

  getAgents(deliveryId:number):Observable<Delivery>{
    return this.http.get<Delivery>(`http://localhost:8080/think-food/delivery/get-delivery-agent/${deliveryId}`);
  }

  getOrderCount(deliveryId: number): Observable<number> {
    return this.http.get<number>(`http://localhost:8080/think-food/delivery-details/total-count/${deliveryId}`);
  }

  softDelete(deliveryId: number): Observable<string> {

    return this.http.delete<string>(`http://localhost:8080/think-food/delivery/soft-delete/${deliveryId}`);
  }
  updateDelivery(deliveryId: number, formValue: any): Observable<string> {
    return this.http.put<string>(`http://localhost:8080/think-food/delivery/update/${deliveryId}`, formValue);
  }

  getDeliveryDetails(deliveryId: number): Observable<DeliveryDetailsDisplay> {
    return this.http.get<DeliveryDetailsDisplay>(`http://localhost:8080/think-food/delivery-details/display-current/${deliveryId}`);
  }

  getDeliveryDetailsList(deliveryId: number): Observable<DeliveryDetailsDisplay[]> {
    return this.http.get<DeliveryDetailsDisplay[]>(`http://localhost:8080/think-food/delivery-details/display-history/${deliveryId}`);
  }

  updateDeliveryStatus(deliveryId: number): Observable<string> {
    return this.http.put<string>(`http://localhost:8080/think-food/delivery-details/update/${deliveryId}`, {}); // Sending an empty object as the request body
  }

  updateStatusAvailability(deliveryId: number): Observable<boolean> {
    return this.http.put<boolean>(`http://localhost:8080/think-food/delivery/update-status/${deliveryId}`, {});
  }

}
