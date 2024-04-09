import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { RestaurantService } from '../../service/restaurant.service';
import { DataService } from '../../service/data.service';

@Component({
  selector: 'app-navbar3',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet],
  templateUrl: './navbar3.component.html',
  styleUrl: './navbar3.component.scss'
})
export class Navbar3Component implements OnInit{
  badgeVisible=false;
  sidenavOpen: boolean = false;
  editStatus: any=null;
  viewStatus: any=null;
  profileData:any;

  updatedUserData: any;


  constructor(
    private router: Router,
    private restaurantService:RestaurantService,
    private dataService: DataService){}
  ngOnInit(){

  }

  badgeVisibilty(){
    this.badgeVisible= true;
  }

  onEditClick(){
    console.log('edit clicked');
    this.editStatus=1;
    this.dataService.notifyOther(this.editStatus);

  }

  onViewClick(){
    console.log('view clicked');
    this.editStatus=2;
    this.dataService.notifyOther(this.editStatus);
  }


  onDeleteClick(){
    const confirmation = confirm("Are you sure you want to delete the account?");
    if(confirmation){
    this.restaurantService.deleteProfile().subscribe(
      response => {
        console.log('Delete successful', response)
      },
      error => {
        console.error(error);
      }
    );
    localStorage.clear();
    this.router.navigate(['Sign-up']);
    }
  }

  showNavigationItems(): boolean {
    return this.router.url !== '**';
  }

  logout(){
    const confirmation = confirm("Do you want to Logout?");
    if (confirmation){
      localStorage.clear();

      this.router.navigate(['Login']);
    }
  }



}

