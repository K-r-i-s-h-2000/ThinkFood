import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { Navbar2Component } from "../components/navbar2/navbar2.component";
import { MatSnackBar } from '@angular/material/snack-bar';
import { DataService } from '../service/data.service';
import { CartService } from '../service/cart.service';
import { SharedService } from '../service/shared.service';



@Component({
    selector: 'app-cart',
    standalone: true,
    templateUrl: './cart.component.html',
    styleUrl: './cart.component.scss',
    imports: [CommonModule, Navbar2Component,RouterModule]
})
export class CartComponent implements OnInit {

  deleteOutput: any[];
  cartQuantity:number;
  quantityChanged : boolean = false;
  constructor(private cdr: ChangeDetectorRef,private http : HttpClient,private loginService : LoginService,private router:Router, private snackBar: MatSnackBar,private dataService : DataService,private cartService:CartService,private sharedService:SharedService){

  }
  cart : any;
  cartItems: any[]=[];
  status1 : boolean;
  ngOnInit() {
    this.cartService.getCart().subscribe(
      (data: any[]) => {
        this.cart = data;
        this.sortedCartItems();
        localStorage.setItem('storeCart',this.cart.cart);
        console.log(data);
        if(this.cart.cartItems!=null && this.cart.cartItems.length > 0) {
            this.status1=true;
            console.log('Status1:', this.status1);

        }
      },
      (error) => {
        console.error(error);
      }
    );
  }
  sortedCartItems() {
    if (this.cart && this.cart.cartItem) {
      // Sort the cartItem array by itemName
      return this.cart.cartItem.slice().sort((a, b) => a.itemName.localeCompare(b.itemName));
    }
    return [];
  }

  triggerRefresh(): void {
    this.sharedService.triggerRefresh();
  }


  deleteCartItem(cartItemId : number){
    this.cartService.deleteCartItem(cartItemId).subscribe(
      (data) => {
        console.log(data);
        this.deleteOutput = data;
        this.openSuccessSnackbar('FoodItem is removed from cart successfully!');
        this.triggerRefresh();
        this.ngOnInit();
        console.log(data);

      },
      (error) => {
        console.error(error);
      }
    );
  }
  saveCart(){
    const cartId= localStorage.getItem('storeCart');
    this.router.navigate(['/Order']);
  }
  updateQuantity(cartItemId : number, action: 'increase' | 'decrease'){
    const cartItem = this.cart.cartItem.find(item => item.id === cartItemId);
    if (cartItem) {
      if (action === 'increase') {
        cartItem.quantity += 1;
        cartItem.subtotal = cartItem.quantity * cartItem.itemPrice;
        this.cartQuantity = cartItem.quantity;
        this.updateCartItem(cartItemId);
      } else if (action === 'decrease' && cartItem.quantity > 1) {
        cartItem.quantity -= 1;
        cartItem.subtotal = cartItem.quantity * cartItem.itemPrice;
        this.cartQuantity = cartItem.quantity;
        this.updateCartItem(cartItemId);
      }
    }


}
updateCartItem(cartItemId: number) {
  const cartId = localStorage.getItem('storeCart');
  const customerId = localStorage.getItem('custId');

  const cartItem = this.cart.cartItem.find(item => item.id === cartItemId);

  const q=cartItem.quantity;

  if (!cartItem) {
    console.error('CartItem not found.');
    return;
  }

  const requestBody = {
    cart: cartId,
    customer: customerId,
    cartItem: [{
      itemId: cartItem.itemId,
      quantity:q,
    }],
  };

  const apiUrl = `http://localhost:8080/think-food/cart`;
  const token = localStorage.getItem('token');

  const headers = token ? new HttpHeaders({'Authorization': `Bearer ${token}`}) : undefined;
  this.http.put(apiUrl, requestBody, { headers }).subscribe(
    (data) => {
      console.log(data);
      this.openSuccessSnackbar('Cart is updated successfully!');
      this.ngOnInit();
    },
    (error) => {
      console.error(error);
      if (error instanceof HttpErrorResponse) {
        console.error('Status:', error.status);
        console.error('Status Text:', error.statusText);
      }
    }
  );
}
openSuccessSnackbar(message: string): void {
  this.snackBar.open(message, 'Close', {
    duration: 3000, // Adjust the duration as needed
    verticalPosition: 'top', // Position the snackbar at the top
    panelClass: ['custom-snackbar'], // Add a custom CSS class for styling
  });
}

}
