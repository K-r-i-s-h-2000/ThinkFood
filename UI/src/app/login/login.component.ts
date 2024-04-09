import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../login.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { NavbarComponent } from "../components/navbar/navbar.component";
import { MatDialog } from '@angular/material/dialog';
import { ForgotpasswordComponent } from '../forgotpassword/forgotpassword.component';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
    selector: 'app-login',
    standalone: true,
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
    imports: [CommonModule, FormsModule, ReactiveFormsModule,RouterLink,RouterOutlet, NavbarComponent]
})
export class LoginComponent implements OnInit {

  title = 'ReactiveForms';
  reactiveForm: FormGroup;
  formStatus;
  responseData: any;
  _dialog:MatDialog;
  constructor( private snackBar: MatSnackBar,private loginService: LoginService,private router: Router,public dialog: MatDialog) {this._dialog=dialog;}

  ngOnInit() {
    localStorage.clear();
    this.reactiveForm = new FormGroup({
      email: new FormControl(null, [Validators.email, Validators.required]),
      password: new FormControl(null, Validators.required),
      acceptTerms: new FormControl(false, Validators.requiredTrue) // Required to be checked
    });

    this.reactiveForm.statusChanges.subscribe((value) => {
      console.log(value);
      this.formStatus = value;
    });
  }

  onSubmit() {
    
    this.loginService.submitLoginForm(this.reactiveForm.getRawValue()).subscribe(
      (data) => {
        console.log(data);
        this.responseData=data;
        localStorage.setItem("token",this.responseData.token);




        localStorage.setItem("user_details",JSON.stringify(data));

        const custId = data.custId;
        localStorage.setItem("custId", custId);


        const restaurantId = data.restaurantId;
        localStorage.setItem('restaurantId',restaurantId);

        const restaurantName = data.restaurantName;
        localStorage.setItem('restaurantName',restaurantName);

        const restaurantDescription = data.restaurantDescription;
        localStorage.setItem('restaurantDescription',restaurantDescription);

        const restaurantAvailability = data.restaurantAvailability;
        localStorage.setItem('restaurantAvailability',restaurantAvailability);
        this.handleLoginSuccess(data);

      },
      (error) => {
        this.openSuccessSnackbar('invalid credentials!');

        console.error('Error:', error);
      }
    );
  }
  handleLoginSuccess(data: any) {
    const role = data.role;

    if (role === 'CUSTOMER') {
      this.router.navigate(['/Demo']);
    } else if (role === 'RESTAURANT_OWNER') {
      this.router.navigate(['/Restaurant']);
    } else if (role === 'ADMIN'){
      this.router.navigate(['/category']);
    }
    else if(role==='DELIVERY_AGENT'){
      localStorage.setItem("deliveryId",data.deliveryId);
      this.router.navigate(['/deliveryCurrent'])
    }
  }
  forgotPassword() {

    let dialogRef = this._dialog.open(ForgotpasswordComponent, {
      height: '200px',
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
