import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { OrderService } from '../../service/order.service';
import { DataService } from '../../service/data.service';

@Component({
  selector: 'app-mainbar',
  standalone: true,
  imports: [CommonModule,
    RouterLink, RouterOutlet,
  ],
  templateUrl: './mainbar.component.html',
  styleUrls: ['./mainbar.component.scss']
})
export class MainbarComponent implements OnInit {

  order: any | undefined;
  showUp:boolean = true;
  cartItems: any[];
  status1=false;
  cartId:any;
  cart: any;
  totalAmount:any;
  coupon: any;
  isHidden: boolean=false;
  isHiddenButton = true;
  obj: any={
    "cartId" : 115,
    "couponCode" : "FLAT50"

  }
  userDetails:any;
  obj2:any;

  constructor(private orderService: OrderService, private http: HttpClient, private dataService: DataService,private router:Router) { }

  ngOnInit(): void {

    this.getUserDetails();
    
    this.orderService.getCartItems().subscribe(
      (data: any) => {
        this.cart = data;
        if(this.cart)
        {
          this.status1=true;
          console.log('Status1:', this.status1);

        }
        this.cartItems = data.cartItem;
        this.totalAmount=data.totalAmount;
        console.log(data);
      },
      (error) => {
        console.error(error);
      }
    );


    this.dataService.notifyObservale$.subscribe((data) => {
      this.coupon = data;
      console.log('Received coupon details:', this.coupon);
    });

    if(this.cart)
    {
      this.status1=true;
      console.log('Status1:', this.status1);

    }

  }

  onSubmit() {
  }

  calculateDiscountedAmount(): number {
    if (this.coupon && this.coupon.discountPercentage) {


      const discountPercentage = this.coupon.discountPercentage / 100;
      return this.cart.totalAmount - (this.cart.totalAmount * discountPercentage);
    }
    return this.totalAmount;
  }

  calculateTotalAmount(): number {
    return this.totalAmount;
  }

  removeCoupon()
  {
    this.coupon=null;
  }

  calculateGst1()
  {
    if(this.coupon)
    {
      const discountedAmount = this.calculateDiscountedAmount();
      const gst = discountedAmount*(.10);
      return gst;

    }
    const gst1= this.totalAmount*(.10);
    return gst1;

  }

    calculateGst()
  {
    if(this.coupon)
    {
      const discountedAmount = this.calculateDiscountedAmount();
      const gst = discountedAmount*(.10);
      return gst+discountedAmount;

    }
    const gst1= this.totalAmount*(.10);
    return this.totalAmount + gst1;

  }


  placeOrder():void{
     this.isHidden = true;
     this.isHiddenButton=false;
     console.log(this.obj2);

  }

  confirmOrder(): void {
    // this.showUp = false;
    // this.cartId = localStorage.getItem('storeCart');

    // const orderData = {
    //   cartId: this.cartId,
    //   couponCode: this.coupon ? this.coupon.couponCode : null
    // };

    // this.orderService.createOrder(orderData).subscribe((data: any) => {
    //   this.obj2 = data;
    //   localStorage.setItem('orderId', this.obj2.id);
    //   this.deleteCart();

    //   // Navigate to the same route to refresh the component
    //   this.router.navigateByUrl('/Order', { skipLocationChange: true }).then(() => {
    //     this.router.navigate(['/Order']);

    //     // Once the order page is refreshed, navigate to the Payment page
        this.router.navigate(['/Payment']);
  }

  navigateToHomepage(){
    this.router.navigate(['/Demo'])
  }

  // confirmOrder(): void {
  //   this.showUp=false;
  //   this.cartId=localStorage.getItem('storeCart');

  //   const orderData = {
  //      cartId : this.cartId,
  //      couponCode: this.coupon ? this.coupon.couponCode : null
  //   };

  //   this.orderService.createOrder(orderData).subscribe((data: any) => {
  //     this.obj2 = data;
  //     console.log(this.obj);
  //     console.log(this.obj2);
  //     localStorage.setItem('orderId',this.obj2.id);
  //     this.deleteCart();



  //     this.router.navigate(['/Payment']);


  //   });
  // }

    deleteCart()
    {
      const cartId= localStorage.getItem('storeCart');
      const apiUrl = `http://localhost:8080/think-food/cart/delete-cartById?id=${cartId}`;
      const token = localStorage.getItem('token');

      const headers = token ? new HttpHeaders({'Authorization' : `Bearer ${token}`}) : undefined;
      this.http.delete<any[]>(apiUrl,{headers}).subscribe(
        (data) => {
          // alert("FoodItem is removed from cart successfully!");this.router.navigate(['/Cart']);
          console.log(data);

        },
        (error) => {
          console.error(error);
        }
      );
    }

    getUserDetails()
    {
      this.userDetails=localStorage.getItem('user_details')
      console.log('user details',this.userDetails);
    }


  // openSpinner()
  // {
  //   this.spinner.show();
  //   setTimeout(() => {
  //     this.spinner.hide();
  //   }, 5000);
  // }

  //   const orderData = {
  //     cartId:115,
  //           // cartId: this.cart.cartId,

  //     couponCode: this.coupon ? this.coupon.couponCode : null
  //   };
  //   console.log(orderData);


  //   this.orderService.createOrder(orderData).subscribe((data: any) => {
  //     this.obj2 = data;
  //     console.log(this.obj);
  //     console.log(this.obj2);

  //   });
  // }


}

