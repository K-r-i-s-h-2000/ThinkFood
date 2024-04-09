import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map, throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {



  private apiUrl = 'http://localhost:8080/think-food/users';
  private baseUrl='http://localhost:8080/think-food';
  private apiUrl3 ='http://localhost:8080/think-food/customer/update/';
  private userId: string = '';
  private customerName='';
  private customerLongitude: string = '';
  private customerLatitude: string = '';
  private custId: string = '';
  private customerAddress:string = '';
  private customerImage:  string='';
  private role:  string='';


  constructor(private http: HttpClient) {}

  submitLoginForm(formValue: any): Observable<any> {
    return this.http.post('http://localhost:8080/think-food/auth/authenticate', formValue).pipe(
      map((response: any) => {
        // Assuming the user ID is returned in the response upon successful authentication
        this.userId = response.id;
        this.custId=response.custId;
        this.role=response.role;

        localStorage.setItem('userRole',this.role);
        this.customerName=response.customerName;
        this.customerAddress=response.customerAddress;
        this.customerLatitude=response.customerLatitude;
        this.customerImage=response.customerImage;
        this.customerLongitude=response.customerLongitude;
        console.log(response);
        console.log(this.userId); // Store the user ID
        localStorage.setItem('token', response.token); // Store the token in localStorage
        return response;
      })
    );
  }

  changePassword(formValue: any): Observable<any> {
    // // Get the token from localStorage
    // const token = localStorage.getItem('token');

    // // Create headers with Authorization if token is present
    // const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

    // Make the HTTP request with the he  aders
    return this.http.post(this.apiUrl, formValue);
  }
  isLoggedIn()
  {
    return localStorage.getItem('token')!=null;
  }


  // updateUserProfile(updatedUserData: any): Observable<any> {
  //   const processUrl = `${this.baseUrl}/customer/update/${this.custId}`;
  // console.log(updatedUserData);
  // return this.http.put(processUrl, updatedUserData);
  // }
  updateUserProfile(updatedUserData: any): Observable<any> {
    console.log(updatedUserData);
    return this.http.put(`${this.apiUrl3}${this.custId}`, updatedUserData);
  }
  getUserId(): any {
    return this.userId;
  }
  getCustomerName():any{
    return this.customerName;
  }
  getCustomerProfilePicture(): any {
    return this.customerImage;
  }
  getRole():any{
    return this.role;
  }

  deleteRecordById(id: number) {
    const confirmation = confirm("Are you sure you want to delete the account?");
    if (!confirmation) {
      return throwError('Deletion canceled by user');
    }
    const deleteUrl = `${this.baseUrl}/users/soft-delete/${id}`;

    
    return this.http.delete(deleteUrl);
  }
   getUserDetails(custId: string): Observable<any> {
    const processUrl = `${this.baseUrl}/customer/${custId}`;
    return this.http.get(processUrl);
  }
  forgotPassword(formValue: any): Observable<any>{
    const processUrl=`${this.baseUrl}/auth/forgot-password`
    return this.http.post(processUrl,formValue)

  }viewProfile
  (updatedUserData: any): Observable<any> {
    console.log(updatedUserData);
    return this.http.get(`${this.baseUrl}/customer/${this.custId}`, updatedUserData);
  }
  payment(formValue: any): Observable<any> {
    const orderId=localStorage.getItem('orderId');
    const processUrl = `http://localhost:8080/think-food/payments/process/${orderId}`;
    const token = localStorage.getItem('token');
    const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;
    return this.http.post(processUrl, formValue);

  }

}
