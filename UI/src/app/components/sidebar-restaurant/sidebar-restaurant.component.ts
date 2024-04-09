import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DataService } from '../../service/data.service';
import { RestaurantService } from '../../service/restaurant.service';
import { LoginService } from '../../login.service';
import { MenuService } from '../../service/menu.service';
import { MenudataService } from '../../service/menudata.service';

@Component({
  selector: 'app-sidebar-restaurant',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet,FormsModule],
  templateUrl: './sidebar-restaurant.component.html',
  styleUrl: './sidebar-restaurant.component.scss'
})
export class SidebarRestaurantComponent {

  menuStatus:any=null;
  constructor(private router: Router,private menuService:MenuService,private menuDataService:MenudataService){}



  onAddMenu(){
    console.log('display menu clicked');
    this.menuStatus=1;
    this.menuDataService.notifyOther(this.menuStatus);
  }

  onGetMenu(){
    console.log('display menu clicked');
    this.menuStatus=3;
    this.menuDataService.notifyOther(this.menuStatus);
  }



  onOrders(){
    this.menuStatus=7;
    this.menuDataService.notifyOther(this.menuStatus);
  }

}
