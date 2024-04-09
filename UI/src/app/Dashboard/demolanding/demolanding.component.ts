import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { Navbar2Component } from "../../components/navbar2/navbar2.component";
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../login.service';
import {MatCardModule} from '@angular/material/card';
import { ListcategoriesComponent } from "./categorylisting/listcategories/listcategories.component";
import { ListrestaurantsComponent } from "./restaurant listing/listrestaurants/listrestaurants.component";
@Component({
    selector: 'app-demolanding',
    standalone: true,
    templateUrl: './demolanding.component.html',
    styleUrl: './demolanding.component.scss',
    imports: [CommonModule, RouterLink, RouterOutlet, NavbarComponent, Navbar2Component, FormsModule, MatCardModule, ListcategoriesComponent, ListrestaurantsComponent]
})
export class DemolandingComponent implements OnInit {
  loggedInUserName: string = '';
  @ViewChild('widgetsContent') widgetsContent: ElementRef;
  selectedCategoryId: number;


  constructor(private loginService: LoginService,private http: HttpClient,private router: Router ) {

  }
  ngOnInit() {
    this.loggedInUserName =localStorage.getItem('loggedInUserName');
    console.log(this.loggedInUserName);
  }
  categories: any[] = [];
  restaurants:any[]=[];
  categoryItems: any[] = []; // Store items related to a specific category
  onSubmit() {

    const apiUrl = 'http://localhost:8080/think-food/category/get-all';
     // Get the token from localStorage
     const token = localStorage.getItem('token');

     // Create headers with Authorization if token is present
     const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

     // Make an authenticated request using HttpClient
     this.http.get<any[]>(apiUrl, { headers }).subscribe(
       (data) => {
        this.restaurants = [];
        this.categories = data;
         console.log(data);
         // Handle the response data as needed
       },
       (error) => {
         console.error(error);
         // Handle errors
       }
     );
   }
   onSubmit1() {

    const apiUrl = 'http://localhost:8080/think-food/search/list-all-restaurants';
     // Get the token from localStorage
     const token = localStorage.getItem('token');

     // Create headers with Authorization if token is present
     const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

     // Make an authenticated request using HttpClient
     this.http.get<any[]>(apiUrl, { headers }).subscribe(
       (data) => {
        this.categoryItems=[];
        this.categories=[];
        this.restaurants = data;
         console.log(data);
         // Handle the response data as needed
       },
       (error) => {
         console.error(error);
         // Handle errors
       }
     );
   }
   fetchItemsForCategory(categoryId: number) {
    const apiUrl = `http://localhost:8080/think-food/item/get-by-category/${categoryId}`;
    const token = localStorage.getItem('token');

     // Create headers with Authorization if token is present
     const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;
    this.http.get<any[]>(apiUrl, { headers }).subscribe(
      (data) => {
        this.categoryItems = data;
        console.log(data);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  showCategoryItems(categoryId: number) {
    this.selectedCategoryId = categoryId;
    this.fetchItemsForCategory(categoryId);
  }
  addToCart(_t54: any) {
    throw new Error('Method not implemented.');
    }
    scrollLeft(){
      this.widgetsContent.nativeElement.scrollLeft -= 150;
    }

    scrollRight(){
      this.widgetsContent.nativeElement.scrollLeft += 150;
    }
  // isHidden=true;
  // items1: any[]=[];
  // items: any[]=[];
  // categoryId: string = '';



  // constructor(private http: HttpClient) {}
  // onSubmit() {
  //   const apiUrl = 'http://localhost:8080/think-food/category/get-all';

  //   const token = localStorage.getItem('token');

  //   const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

  //   this.http.get(apiUrl, { headers }).subscribe(
  //     (data:any) => {
  //       this.items=data;
  //       console.log(data);
  //       this.isHidden=false;
  //     },
  //     (error) => {
  //       console.error(error);
  //     }
  //   );
  // }

  // onSubmit1() {

  //   const apiUrl = `http://localhost:8080/think-food/item/category-id/${this.categoryId}/get-items`;
  //   const token = localStorage.getItem('token');
  //   const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

  //   this.http.get(apiUrl, { headers }).subscribe(
  //     (data: any) => {
  //       this.items1 = data;
  //       console.log(data);
  //     },
  //     (error) => {
  //       console.error(error);
  //     }
  //   );
  // }


  // onSubmit2(itemId: any) {
  //   const apiUrl = `http://localhost:8080/think-food/item/category-id/${itemId}/get-items`;
  //   const token = localStorage.getItem('token');
  //   const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

  //   this.http.get(apiUrl, { headers }).subscribe(
  //     (data: any) => {
  //       this.items1 = data;
  //       console.log(data);
  //     },
  //     (error) => {
  //       console.error(error);
  //     }
  //   );
  // }
}
