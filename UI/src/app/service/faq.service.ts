import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FaqService {


  constructor(private http: HttpClient) {}

  getAllQueries(token?: string): Observable<any[]> {
    const apiUrl = 'http://localhost:8080/think-food/support/queries';
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get<any[]>(apiUrl, { headers });
  }

  getSupportById(id: number, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/support/query/${id}`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.get<any>(apiUrl, { headers });
  }

  createSupport(supportData: any, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/support/create`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.post(apiUrl, supportData, { headers });
  }

  softDeleteSupportById(id: number, token?: string): Observable<any> {
    const apiUrl = `http://localhost:8080/think-food/support/soft-delete/${id}`;
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    return this.http.delete(apiUrl, { headers });
  }
}
