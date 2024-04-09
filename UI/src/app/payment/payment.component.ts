import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationExtras, Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { DeliveryDetailsService } from '../service/delivery-details.service';
import { OrderService } from '../service/order.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DataService } from '../service/data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WarningNoAgentComponent } from '../warning-no-agent/warning-no-agent.component';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterOutlet,ReactiveFormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent implements OnInit {


  title="PaymentForm";
  reactiveform:FormGroup;
  cartId: any;
  coupon: any;
  obj2: any;
  showSpinner: boolean = false;
  constructor(private dialog:MatDialog,private fb: FormBuilder,private loginService: LoginService, private http: HttpClient,private router: Router,private deliveryDetailsService:DeliveryDetailsService, private orderService:OrderService, private dataService:DataService, private snackBar : MatSnackBar) {

  }
  ngOnInit(){
  //   reactiveform:FormGroup;
  //   this.reactiveform=new FormGroup({
  //     cardNumber:new FormControl(null) ,
  //     expirationDate:new FormControl(null),
  //     cvv:new FormControl(null)
  //  });

  this.dataService.notifyObservale$.subscribe((data) => {
    this.coupon = data;
    console.log('Received coupon details:', this.coupon);

    // this.preventBackNavigation();
  });

  this.reactiveform = this.fb.group({
    cardNumber: [null, [Validators.required, Validators.pattern(/^\d{16}$/)]],
    expirationDate: [null, [Validators.required, Validators.pattern(/^(0[1-9]|1[0-2])\/\d{2}$/)]],
    cvv: [null, [Validators.required, Validators.pattern(/^\d{3,4}$/)]]
  });
  }

  // preventBackNavigation() {
  //   const navigationExtras: NavigationExtras = {
  //     replaceUrl: true
  //   };

  //   // Navigate to the new URL with replaceUrl set to true
  //   this.router.navigate(['/Payment'], navigationExtras);
  // }


  closeForm() {
    this.dialog.closeAll();
}
paymentSubmit() {
  if (this.reactiveform.valid) {
    this.showSpinner = true;
    this.cartId = localStorage.getItem('storeCart');

    const orderData = {
      cartId: this.cartId,
      couponCode: this.coupon ? this.coupon.couponCode : null
    };

    this.orderService.createOrder(orderData).subscribe((data: any) => {
      this.obj2 = data;
      localStorage.setItem('orderId', this.obj2.id);
      const orderId = parseInt(localStorage.getItem('orderId'), 10);
      this.deliveryDetailsService.assignDeliveryByOrder(orderId).subscribe(
        (response) => {
          if (response) {
            this.openSuccessSnackbar('Success');
            this.router.navigate(['/deliveryDetails']);
            this.deleteCart();
          } else {
            let dialogRef = this.dialog.open(WarningNoAgentComponent, {});
            this.orderService.deleteOrder(orderId).subscribe(
              (response) => {
                console.log(response)
              console.log("Order deleted successfully")},
              (error) => { console.log("Order is not deleted successfully")}
            );
            this.router.navigate(['/Demo']);
          }
        },
        (error) => {
          console.error(error);
        }
      );

      // Navigate to the same route to refresh the component
      this.router.navigateByUrl('/Demo', { skipLocationChange: true }).then(() => {
        // Use replaceState to replace the current state in the browser's history
        this.router.navigate(['/Demo'], { replaceUrl: true });

        this.loginService.payment(this.reactiveform.getRawValue()).subscribe(
          (data) => {
            this.router.navigate(['/deliveryDetails'])

          },
          () => {
            this.openSuccessSnackbar('Error');
          }
        );
      });
    });
  } else {
    this.openSuccessSnackbar('Please enter valid values.');
  }
}

openSuccessSnackbar(message: string): void {
  this.snackBar.open(message, 'Close', {
    duration: 3000,
    verticalPosition: 'top',
    panelClass: ['custom-snackbar'],
  });
}

deleteCart()
{
  const cartId= localStorage.getItem('storeCart');
  const apiUrl = `http://localhost:8080/think-food/cart?id=${cartId}`;
  const token = localStorage.getItem('token');

  const headers = token ? new HttpHeaders({'Authorization' : `Bearer ${token}`}) : undefined;
  this.http.delete<any[]>(apiUrl,{headers}).subscribe(
    (data) => {
      // alert("FoodItem is removed from cart successfully!");this.router.navigate(['/Cart']);
      console.log(data);
      alert("success");
      console.log("Payment is success")
      this.router.navigate(['/deliveryDetails']);
      console.log("Delivery details page is reloaded")
    },
    (error) => {
      console.error(error);
    }
  );
}
}



