import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';

import { EditprofileComponent } from '../../editprofile/editprofile.component';
import { LoginService } from '../../login.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ViewcustomerdetailsComponent } from '../../viewcustomerdetails/viewcustomerdetails.component';
import { ChangepasswordComponent } from '../../changepassword/changepassword.component';
import { CartComponent } from '../../cart/cart.component';
import { DataService } from '../../service/data.service';
import { CartService } from '../../service/cart.service';
import { SharedService } from '../../service/shared.service';
import { MatSnackBar } from '@angular/material/snack-bar';

// enum Role {
//   CUSTOMER = 'CUSTOMER',
//   RESTAURANT_OWNER = 'RESTAURANT_OWNER',
// }


@Component({
  selector: 'app-navbar2',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet],
  templateUrl: './navbar2.component.html',
  styleUrl: './navbar2.component.scss'
})
export class Navbar2Component implements OnInit{

  cartItems : number;
  test:any=1;


viewCustomerDetails() {
  let dialogRef = this._dialog.open(ViewcustomerdetailsComponent, {
    height: '300px',
    width: '300px',
  });
}
  _dialog:MatDialog;


  showEditProfileForm:boolean=false;
  showChangePasswordForm: boolean = false;
  toggleChangePasswordForm() {
    this.showChangePasswordForm = !this.showChangePasswordForm;
  }
  toggleEditProfileForm(){
    this.showChangePasswordForm = !this.showChangePasswordForm;
  }
  cart:any;
  // role: Role; // Add this line to define the 'role' property
  // CUSTOMER = Role.CUSTOMER; // Add this line to define the 'CUSTOMER' property
  // RESTAURANT_OWNER = Role.RESTAURANT_OWNER;
  constructor(private snackBar: MatSnackBar,private loginService: LoginService,private router: Router, private http: HttpClient,private activatedRoute: ActivatedRoute,public dialog: MatDialog,private dataService : DataService, private cartService : CartService, private sharedService:SharedService) {this._dialog=dialog;}
  ngOnInit(): void {
    const customerId = localStorage.getItem('custId');
    console.log("customerId:",customerId);

    this.gettingCart();
    // this.cartService.getCart().subscribe(
    //   (data: any[]) => {
    //     this.cart = data;
    //     this.cartItems=this.cart.cartItem.length;
    //   }
    // );

    this.sharedService.refresh$.subscribe(() => {
      this.refreshComponent();
    });
  }

  refreshComponent(): void {
    this.gettingCart();
    console.log('Component B refreshed!');
  }



  gettingCart(){
    this.cartService.getCart().subscribe(
      (data: any[]) => {
        this.cart = data;
        this.cartItems=this.cart.cartItem.length;
        localStorage.setItem('cartLength',this.cart.cartItem.length);
        localStorage.setItem('storeCart',this.cart.cart);
      }
    );
  }
  Logout() {
    localStorage.removeItem('token');
    this.router.navigate(['Login']);

  }

  showNavigationItems(): boolean {
    // Customize this logic based on your requirements
    return this.router.url !== '**';
  }


  shouldShowHome(): boolean {
    return this.activatedRoute.snapshot.url[0]?.path !== 'Home';}

  shouldShowLogin(): boolean {
    return this.activatedRoute.snapshot.url[0]?.path !== 'Login'&&this.activatedRoute.snapshot.url[0]?.path !== 'deliveryDetails';
  }

  shouldShowSignUp(): boolean {
    return this.activatedRoute.snapshot.url[0]?.path !== 'Sign-up'&&this.activatedRoute.snapshot.url[0]?.path !== 'deliveryDetails';
  }

  navigateToCart(){
    this.router.navigate(['/Cart']);
  }
closeForm() {
  this._dialog.closeAll();
}
onDeleteAccount() {
  const userId = this.loginService.getUserId();
  this.loginService.deleteRecordById(userId).subscribe(
    (data) => {
      this.router.navigate(['/Home']);
      console.log(data);
      this.openSuccessSnackbar('Successfully deleted!');
    },
    (error) => {
      console.log(error);

    }
  );
}
editProfile() {

  let dialogRef = this._dialog.open(EditprofileComponent, {
    height: '300px',
    width: '300px',
  });
  }
  openChangepasswordPopup()
{
  let dialogRef = this._dialog.open(ChangepasswordComponent, {
    height: '300px',
    width: '300px',
  });

}
openSuccessSnackbar(message: string): void {
  this.snackBar.open(message, 'Close', {
    duration: 3000, // Adjust the duration as needed
    verticalPosition: 'top', // Position the snackbar at the top
    panelClass: ['custom-snackbar'], // Add a custom CSS class for styling
  });
}
}
