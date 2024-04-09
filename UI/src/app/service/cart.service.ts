import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  cart : any;
  status1 : boolean;
  constructor(private http : HttpClient) { }

  getCart(): Observable<any>{
    const customerId = localStorage.getItem('custId');
    console.log("customerId:",customerId);
    const apiUrl= `http://localhost:8080/think-food/carts?customerId=${customerId}`;
    const token = localStorage.getItem('token');

    const headers = token ? new HttpHeaders({'Authorization': `Bearer ${token}` }) : undefined;
    return this.http.get<any[]>(apiUrl,{headers});
  }
  deleteCartItem(cartItemId : number): Observable<any>{
    const cartId= localStorage.getItem('storeCart');
    const apiUrl = `http://localhost:8080/think-food/cartItem/${cartId}?id=${cartItemId}`;
    const token = localStorage.getItem('token');

    const headers = token ? new HttpHeaders({'Authorization' : `Bearer ${token}`}) : undefined;
    return this.http.delete<any[]>(apiUrl,{headers});
  }
}
