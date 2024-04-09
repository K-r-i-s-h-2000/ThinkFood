import { Component, OnInit } from '@angular/core';
import { CommonModule, getLocaleMonthNames } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { RestaurantService } from '../service/restaurant.service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DataService } from '../service/data.service';
import { catchError } from 'rxjs';
import { Navbar3Component } from '../components/navbar3/navbar3.component';
import { MenuService } from '../service/menu.service';
import { SidebarRestaurantComponent } from '../components/sidebar-restaurant/sidebar-restaurant.component';
import { OrderService } from '../service/order.service';
import { ViewareaComponent } from '../components/viewarea/viewarea.component';



@Component({
  selector: 'app-restaurantpage',
  standalone: true,
  imports: [CommonModule,Navbar3Component,SidebarRestaurantComponent,ViewareaComponent,ReactiveFormsModule,RouterLink,RouterOutlet],
  templateUrl: './restaurantpage.component.html',
  styleUrl: './restaurantpage.component.scss'
})
export class RestaurantpageComponent{
}
