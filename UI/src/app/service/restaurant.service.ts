import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  private apiUrl ='http://localhost:8080/think-food/restaurant'


  constructor(private http: HttpClient,private router:Router) { }

  deleteProfile():Observable<any>{
    console.log('dlt clicked');


    const id = localStorage.getItem('restaurantId');
    if (!id) {
      console.error('Restaurant id not found in localStorage');

    }

    const url = `${this.apiUrl}/${id}`;

    return this.http.delete<any>(url);
  }

  editProfile(updatedRestaurantData: any): Observable<any> {
    const id = localStorage.getItem('restaurantId');
    if (!id) {
      console.error('Restaurant id not found in localStorage');

    }
    return this.http.put(`${this.apiUrl}/update/${id}`, updatedRestaurantData);
  }

  viewProfile():Observable<any>{
    const id = localStorage.getItem('restaurantId');
    if (!id) {
      console.error('Restaurant id not found in localStorage');

    }


    return this.http.get(`${this.apiUrl}/${id}`);

  }


  updateOrderStatus(id:any):Observable<any>{
    console.log('update order status service');
    localStorage.setItem('orderId',id);

    const id1 = localStorage.getItem('orderId')


     if (!id) {
      console.error('Order id not found in localStorage');

    }
    return this.http.get<any>(`${this.apiUrl}/update-preparation-status/${id1}`);
  }

  listItems(id:any):Observable<any>{
    localStorage.setItem('itemId',id);
    console.log(id);
    
    return this.http.get(`http://localhost:8080/think-food/search/list-all-items`);
  }


  logout(){

    const confirmation = confirm("Do you want to Logout?");
    if (confirmation){
      localStorage.removeItem('token');
      localStorage.removeItem('restaurantId');

      this.router.navigate(['Login']);
    }
  }
}
