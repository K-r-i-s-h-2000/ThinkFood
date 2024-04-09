import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, forkJoin, mergeMap, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  constUrl="http://localhost:8080/think-food"

  constructor(private http:HttpClient) { }


  getAllProducts(): Observable<any[]> {
    const custId = localStorage.getItem('custId');

    if (custId) {
      return this.http.get<any[]>(`${this.constUrl}/order/customer/${custId}`).pipe(
        mergeMap((customerData: any[]) => {
          const orderIds = customerData.map(order => order.id);

          if (orderIds.length > 0) {
            const requests = orderIds.map(orderId =>
              this.http.get<any>(`${this.constUrl}/order/${orderId}`)
            );

            return forkJoin(requests);
          } else {
            console.error('No order ids available in customer data');
            return of([]);
          }
        }),
        catchError((error) => {
          console.error('Error fetching order details:', error);
          return of([]);
        })
      );
    } else {
      console.error('custId not available in localStorage');
      return of([]);
    }
  }


  // getAllProducts(): Observable<any[]> {
  //   return this.http.get<any[]>(this.constUrl+"/order/"+localStorage.getItem("custId"));
  // }

  // createOrder(): Observable<any[]> {
  //   return this.http.post<any[]>(this.constUrl+"/order");
  // }


  getCartItems(): Observable<any[]> {
    const cartId=localStorage.getItem('storeCart');
    return this.http.get<any[]>(this.constUrl+`/cart?cartId=${cartId}`);
  }

  createOrder(orderData: { cartId: number; couponCode: string }): Observable<any> {
    return this.http.post<any>(`${this.constUrl}/order`, orderData);
  }

  getAllOrders(): Observable<any[]> {
    const custId = localStorage.getItem('custId');
    return this.http.get<any[]>(this.constUrl+`/order/customer/${custId}`);
  }

  getOrdersByRestaurant(): Observable<any> {
    const restId= localStorage.getItem('restaurantId');
    return this.http.get<any>(this.constUrl+`/order/restaurant/${restId}`);
  }

  getOrderById(id:any):Observable<any>{
    localStorage.setItem('orderId',id);

    const id1 = localStorage.getItem('orderId')
    return this.http.get<any>(this.constUrl+`/order/${id1}`)
  }

  deleteOrder(orderId: number): Observable<any> {
    return this.http.delete(this.constUrl+`order/hard-delete/${orderId}`);
  }


}
