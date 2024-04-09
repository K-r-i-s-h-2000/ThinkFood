import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Route, Router, RouterLink, RouterOutlet } from '@angular/router';
import { SignupService } from '../signup.service';
import { HttpClient } from '@angular/common/http';
import { ItemService } from '../service/item.service';
import { Navbar2Component } from "../components/navbar2/navbar2.component";
import { ListItemRestaurantsComponent } from '../list-item-restaurants/list-item-restaurants.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-listitems',
    standalone: true,
    templateUrl: './listitems.component.html',
    styleUrl: './listitems.component.scss',
    imports: [CommonModule, RouterLink, RouterOutlet, Navbar2Component]
})
export class ListitemsComponent implements OnInit {
  categoryId: number;
  listrestaurants:any;
  _dialog:MatDialog;

  itemsList: any;
  constructor(private route: ActivatedRoute,private signupservice: SignupService,private http: HttpClient,public dialog: MatDialog, private itemService:ItemService,private router:Router) { this._dialog=dialog;}
  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.categoryId = +params['id']; // Fetch the restaurant ID from the route parameter

      this.signupservice.getItemById(this.categoryId).subscribe(
        (details) => {
          this.itemsList = details;
          console.log(details) // Assign the fetched details to your component property
        },
        (error) => {
          console.error(error);
          // Handle errors, e.g., show an error message to the user
        }
      );
    });
  }

  navigate(restaurantId: number) {
    console.log(restaurantId);
    this.router.navigate(['/ToCart', restaurantId]);
  }

  showRestaurants(id:any)
  {
    this.itemService.getAllRestaurantsByItems(id).subscribe(
        (data: any) => {
          this.listrestaurants = data;
        },
        (error) => {
          console.error(error);
        }
      );
  }
  openavailablerestaurantpopup(itemId: number) {
    let dialogRef = this._dialog.open(ListItemRestaurantsComponent, {
    //  height: '1000px',
      width: '1000px',
      data: { itemId: itemId } // Pass the item ID as data to the dialog
    });
  }
}
