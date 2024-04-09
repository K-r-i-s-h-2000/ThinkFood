import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeliveryDetailsComponent } from '../delivery-details/delivery-details.component';
import { DeliveryDetailsDisplay } from '../delivery-agent/delivery-history/delivery-details-display';

@Injectable({
  providedIn: 'root'
})
export class DeliveryDetailsService {

  constructor(private http:HttpClient) { }

  
  getDetails(orderId:number):Observable<DeliveryDetailsDisplay>{
    return this.http.get<DeliveryDetailsDisplay>(`http://localhost:8080/think-food/delivery-details/display/${orderId}`)
  }
  assignDeliveryByOrder(orderId: number): Observable<string> {
    return this.http.post<string>(`http://localhost:8080/think-food/delivery-details/add/${orderId}`, {});
  }

  getPreparationStatusDetails(orderId: number): Observable<any> {
    return this.http.put<any>(`http://localhost:8080/think-food/delivery-details/update-restaurant-status/${orderId}`,{});
  }

}
