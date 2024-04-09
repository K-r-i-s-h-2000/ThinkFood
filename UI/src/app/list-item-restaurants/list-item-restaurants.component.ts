
import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ItemService } from '../service/item.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-list-item-restaurants',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './list-item-restaurants.component.html',
  styleUrl: './list-item-restaurants.component.scss'
})
export class ListItemRestaurantsComponent implements OnInit {
  _dialog:MatDialog;
  restaurants: any[] = [];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private itemService:ItemService,public dialog: MatDialog,private router:Router,private snackBar:MatSnackBar ) { this._dialog=dialog;}
  ngOnInit(): void {
    this.fetchRestaurantsByItemId(this.data.itemId);
  }

  fetchRestaurantsByItemId(itemId: number) {
    this.itemService.getAllRestaurantsByItems(itemId).subscribe(
      (data: any) => {
        console.log(data);
        this.restaurants = data; // Update the restaurants array with fetched data
        console.log(this.restaurants.length);
      },
      (error) => {
        console.error(error);
      }
    );
  }
  closeForm() {
    this._dialog.closeAll();
}

navigate(restaurantId: number) {
  this._dialog.closeAll();

  console.log(restaurantId);
  this.router.navigate(['/ToCart', restaurantId]);
}
openSuccessSnackbar(message: string): void {
  this.snackBar.open(message, 'Close', {
    duration: 3000, // Adjust the duration as needed
    verticalPosition: 'top', // Position the snackbar at the top
    panelClass: ['custom-snackbar'], // Add a custom CSS class for styling
  });
}

}
