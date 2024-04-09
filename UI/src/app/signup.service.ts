import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {
  private baseUrl='http://localhost:8080/think-food';

  constructor(private http: HttpClient) { }
  listItemsUnderRestaurant(restaurantId: number) {

    return this.http.get<any>(`${this.baseUrl}/search/specific-restaurant/${restaurantId}`);
  }

  submitSignupForm(formValue: any): Observable<any> {
    return this.http.post('http://localhost:8080/think-food/auth/register', formValue);
  }
  getRestaurantById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/restaurant/${id}`);
  }

  getItemById(categoryId: number):  Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/item/get-by-category/${categoryId}`);
  }

}
