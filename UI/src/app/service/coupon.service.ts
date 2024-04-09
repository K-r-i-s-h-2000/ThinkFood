import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CouponService {

  private apiUrl = 'http://localhost:8080/think-food/coupons';

  constructor(private http: HttpClient) { }

  getCouponDetails(couponCode: string): Observable<any> {
    const url = `${this.apiUrl}/${couponCode}`;
    return this.http.get<any>(url);
  }

  createCoupon(formValue:any) : Observable<any>{
    return this.http.post(`${this.apiUrl}`, formValue);
  }

  getAllCoupon() : Observable<any>{
    return this.http.get(`${this.apiUrl}/get-all-coupon`);
  }

  getAllCouponWithExpiry() : Observable<any>{
    return this.http.get(`${this.apiUrl}/get-all-coupon-with-expiry`);
  }

  deleteCoupon(couponId: number): Observable<string> {

    return this.http.delete<any>(`${this.apiUrl}/delete/${couponId}`);
  }

  updateCoupon(formValue: any): Observable<any> {
    const couponCode = formValue.couponCode; 
    return this.http.put(`${this.apiUrl}/update/${couponCode}`, formValue);
  }
}

