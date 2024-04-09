import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private apiUrl = 'http://localhost:8080/think-food/item';

  constructor(private http: HttpClient) {}

  createItem(itemData: any, categoryId: number, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.post<any>(
      `${this.apiUrl}/category-id/${categoryId}/add-item`,
      itemData,
      { headers }
    );
  }

  updateItem(itemData: any, id: number, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.put<any>(
      `${this.apiUrl}/update-item/${id}`,
      itemData,
      { headers }
    );
  }

  softDeleteItem(id: any, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.delete<any>(
      `${this.apiUrl}/soft-delete/${id}`,
      { headers }
    );
  }

  getAllItemsByCategory(categoryId: number, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get<any>(
      `${this.apiUrl}/get-by-category/${categoryId}`,
      { headers }
    );
  }

  getAllRestaurantsByItems(id:any):Observable<any>{
    return this.http.get<any>(`http://localhost:8080/think-food/search/specific-item/${id}`)
  }
}
