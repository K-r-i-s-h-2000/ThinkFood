import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginService } from '../../../../login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listcategories',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listcategories.component.html',
  styleUrl: './listcategories.component.scss'
})
export class ListcategoriesComponent implements OnInit{
  navigateToItemsByCategory(categoryId: number) {
    // Assuming '/items' is the route to display items based on category ID
    this.router.navigate(['/ListItems', categoryId]); // Navigate to the 'items' component with the category ID
    
  }
  constructor(private loginService: LoginService,private http: HttpClient,private router: Router ) {

  }
  categories: any[] = [];
  restaurants: any[] = [];
  ngOnInit() {
   

      const apiUrl = 'http://localhost:8080/think-food/category/get-all';
       // Get the token from localStorage
       const token = localStorage.getItem('token');
  
       // Create headers with Authorization if token is present
       const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;
  
       // Make an authenticated request using HttpClient
       this.http.get<any[]>(apiUrl, { headers }).subscribe(
         (data) => {
          console.log(data);
          this.categories = data;
           // Handle the response data as needed
         },
         (error) => {
           console.error(error);
           // Handle errors
         }
       );
     }
  }
  

