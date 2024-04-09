import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginService } from '../../../../login.service';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-listrestaurants',
  standalone: true,
  imports: [CommonModule,MatCardModule,RouterLink],
  templateUrl: './listrestaurants.component.html',
  styleUrl: './listrestaurants.component.scss'
})
export class ListrestaurantsComponent implements OnInit {
  navigate(restaurantId: number) {
    this.router.navigate(['/ToCart', restaurantId]); 
  }
description: any;
  chunkedRestaurants: any[];
  constructor(private loginService: LoginService,private http: HttpClient,private router: Router ) {

  }
  categories: any[] = [];
  restaurants:any[]=[];
  categoryItems: any[] = []; 
  ngOnInit() {
    const apiUrl = 'http://localhost:8080/think-food/search/list-all-restaurants';
    const token = localStorage.getItem('token');
    const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

    this.http.get<any[]>(apiUrl, { headers }).subscribe(
      (data) => {
        this.restaurants = data;
        this.chunkedRestaurants = this.chunkArray(this.restaurants, 5); // Chunk array into groups of 3
      },
      (error) => {
        console.error(error);
      }
    );
  }
  // Function to chunk the array into smaller arrays
  chunkArray(arr: any[], chunkSize: number): any[] {
    const chunkedArray = [];
    for (let i = 0; i < arr.length; i += chunkSize) {
      chunkedArray.push(arr.slice(i, i + chunkSize));
    }
    return chunkedArray;
  }

}
