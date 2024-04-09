import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { Navbar2Component } from "../components/navbar2/navbar2.component";
import { ListcategoriesComponent } from "../Dashboard/demolanding/categorylisting/listcategories/listcategories.component";
import { LoginService } from '../login.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Restaurant } from '../models/restaurant.model';
import { SignupService } from '../signup.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SharedService } from '../service/shared.service';

@Component({
    selector: 'app-tocart',
    standalone: true,
    templateUrl: './tocart.component.html',
    styleUrl: './tocart.component.scss',
    imports: [CommonModule, RouterOutlet, RouterLink, ListcategoriesComponent, Navbar2Component]
})
export class TocartComponent {
  private baseUrl='http://localhost:8080/think-food';
restaurantDescription: any;
restaurantName: any;
  restaurantId: number;
  restaurantDetails: any;
  itemsList: any;
  cart : any;
  cartId : any;
cartItem : {
  itemId : number,
  quantity : number
} = { itemId : 0, quantity : 0}
constructor(private route: ActivatedRoute,private signupservice: SignupService,private http: HttpClient,private router:Router, private snackBar : MatSnackBar,private sharedService:SharedService) { }

ngOnInit(): void{
  this.route.params.subscribe(params => {
    this.restaurantId = +params['id']; // Fetch the restaurant ID from the route parameter

    this.signupservice.getRestaurantById(this.restaurantId).subscribe(
      (details) => {
        this.restaurantDetails = details;
        console.log(details)
        this.signupservice.listItemsUnderRestaurant(this.restaurantId).subscribe(
          (items) => {
            this.itemsList = items;
            if(items.length=='0'){
              this.openSuccessSnackbar('Restaurant new to listing , Items not yet added!');
            }
            console.log(items);
          },
          (error) => {
            console.error(error);
            // Handle errors, e.g., show an error message to the user
          }
        );
      },// Assign the fetched details to your component property

      (error) => {
        console.error(error);
        // Handle errors, e.g., show an error message to the user
      }
    );
  });
}

triggerRefresh(): void {
  this.sharedService.triggerRefresh();
}

// handleQuantity(itemId : number, action: 'increase' | 'decrease'){
//   this.cartItem.itemId = itemId;
//     if (action === 'increase') {
//       this.cartItem.quantity += 1;
//     } else if (action === 'decrease' && this.cartItem.quantity > 1) {
//       this.cartItem.quantity -= 1;
//   }

createCart(foodItemId : number){
  const customerId = localStorage.getItem('custId');
  const requestBody = {
    customer: customerId,
    cartItem: [{
      itemId:  foodItemId,
      quantity: 1,
    }],
  };
  const apiUrl = `http://localhost:8080/think-food/carts`;
  const token = localStorage.getItem('token');

  const headers = token ? new HttpHeaders({'Authorization' : `Bearer ${token}`}) : undefined;
  this.http.post(apiUrl,requestBody,{headers}).subscribe(
    (data) => {
      this.openSuccessSnackbar('Item is added to the cart successfully!');

      console.log(data);
      this.cart=data,
      localStorage.setItem('newCartId',this.cart.cart);
    },
    (error) => {
      console.error(error);
    }
  )
}
// handleQuantity(item : number, action: 'increase' | 'decrease'){
//   this.item.itemId=item;
//   if(this.item.itemId){
//     if (action === 'increase') {
//         this.item.quantity += 1;
//       } else if (action === 'decrease' && this.item.quantity >= 0) {
//         this.item.quantity -= 1;
//     }
//   }

// }
createOrUpdateCart(foodItemId: number) {
  if(localStorage.getItem('cartLength')!= '0'){
    this.cartId = localStorage.getItem('storeCart');
  } else {
    this.cartId = null;
  }
  const customerId = localStorage.getItem('custId');
   // Retrieve the cartId from localStorage

  const requestBody = {
    cart: this.cartId,
    customer: customerId,
    cartItem: [{
      itemId: foodItemId,
      quantity: 1,
    }],
  };

  const apiUrl = 'http://localhost:8080/think-food/cart';
  const token = localStorage.getItem('token');
  const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

  this.http.post(apiUrl, requestBody, { headers }).subscribe(
    (data) => {
      this.openSuccessSnackbar('Item is added to the cart successfully!');
      this.triggerRefresh();
      console.log(data);
      const serverResponse = data as any;
      localStorage.setItem('cartId', serverResponse.cart);
    },
    (error) => {
      this.openSuccessSnackbar('Item is not from the same Restaurant!');
      console.error(error);
    }
  );
}


openReviewsAndRatings(): void {
  console.log('Current restaurantId:', this.restaurantId);

  // Pass the restaurantId as a parameter when navigating to the reviews component
  this.router.navigate(['/reviews', { restaurantId: this.restaurantId }])
    .then(success => {
      console.log('Navigation successful:', success);
    })
    .catch(error => {
      console.error('Navigation error:', error);
    });
}

openSuccessSnackbar(message: string): void {
  this.snackBar.open(message, 'Close', {
    duration: 3000,
    verticalPosition: 'top',
    panelClass: ['custom-snackbar'],
  });
}

}
