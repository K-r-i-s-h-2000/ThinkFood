import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private http:HttpClient) { }

  submitSignupForm(formValue:any): Observable<any>{
    return this.http.post( 'http://localhost:8080/think-food/auth/register',formValue)
  }
}
