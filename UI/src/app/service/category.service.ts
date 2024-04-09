import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiUrl = 'http://localhost:8080/think-food/category';

  constructor(private http: HttpClient) {}

  getAllCategory(token?: string): Observable<any[]> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get<any[]>(`${this.apiUrl}/get-all`, { headers });
  }

  createCategory(categoryData: any, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.post(`${this.apiUrl}/create`, categoryData, { headers });
  }

  updateCategory(categoryId: number, categoryData: any, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.put(`${this.apiUrl}/update/${categoryId}`, categoryData, { headers });
  }

  getCategoryById(categoryId: number, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get(`${this.apiUrl}/get/${categoryId}`, { headers });
  }

  deleteCategory(categoryId: number, token?: string): Observable<any> {
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.delete(`${this.apiUrl}/soft-delete/${categoryId}`, { headers });
  }
}
