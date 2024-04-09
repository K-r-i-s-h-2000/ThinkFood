import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewsAndRatingsService {

  constructor(private http: HttpClient) {}

  createReviewsAndRatings(reviewData: any, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/reviews-ratings/create`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.post(apiUrl, reviewData, { headers });
  }

  getAllReviewsAndRatings(restaurantId: number, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/reviews-ratings/get-all/${restaurantId}`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get(apiUrl, { headers });
  }

  updateReviewsAndRatings(reviewId: number, updatedData: any, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/reviews-ratings/update/${reviewId}`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.put(apiUrl, updatedData, { headers });
  }

  softDeleteReviewsAndRatingsById(reviewId: number, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/reviews-ratings/soft-delete/${reviewId}`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.delete(apiUrl, { headers });
  }

  getReviewsAndRatingsById(reviewId: number, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/reviews-ratings/get/${reviewId}`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get(apiUrl, { headers });
  }
}
