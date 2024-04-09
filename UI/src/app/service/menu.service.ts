import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  menuId:any;

  private apiUrl ='http://localhost:8080/think-food/menu'

  constructor(private http: HttpClient,private router:Router) { }

  getMenuByRestaurant():Observable<any>{
    console.log('In restauarnt service');
    const id = localStorage.getItem('restaurantId');


     if (!id) {
      console.error('Restaurant id not found in localStorage');

    }

    return this.http.get(`http://localhost:8080/think-food/search/specific-restaurant/${id}`);

  }


  viewMenu(menuId:any):Observable<any>{
    console.log(('view '));
    localStorage.setItem('menuId',menuId);
    const id = localStorage.getItem('menuId');
    if (!id) {
      console.error('Menu id not found in localStorage');

    }

    return this.http.get(`${this.apiUrl}/${id}`);

  }

  addMenu(formValue: any,id:any):Observable<any>{
    const restaurantId = localStorage.getItem('restaurantId');
    const itemId = localStorage.getItem('itemId');

    const body = {
      ...formValue,
      restaurantId: parseInt(restaurantId, 10), // Assuming restaurantId is a number
      itemId: parseInt(itemId, 10), // Assuming itemId is a number
    };

    console.log('body:', body);

    return this.http.post(`${this.apiUrl}`, body);
  }

  updateMenu(formValue: any):Observable<any>{
    const id = localStorage.getItem('menuId');
    return this.http.put(`${this.apiUrl}/${id}`,formValue);

  }



  deleteMenu(menuId:any):Observable<any>{
    const confirmation = confirm("Are you sure you want to delete the item?");
    const id = localStorage.getItem('menuId');
    if (!id) {
      console.error('Menu id not found in localStorage');

    }

    if (!confirmation) {
      return throwError('Deletion canceled by user');
    }

    return this.http.delete(`${this.apiUrl}/${id}`);


  }



}
