import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { AbstractControl, FormControl, FormGroup, FormsModule, NgForm, ValidationErrors, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { SignupService } from '../signup.service';
import { NavbarComponent } from "../components/navbar/navbar.component";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-signup',
    standalone: true,
    templateUrl: './signup.component.html',
    styleUrl: './signup.component.scss',
    imports: [CommonModule, RouterLink, RouterOutlet, FormsModule, ReactiveFormsModule, NavbarComponent]
})
export class SignupComponent implements OnInit {
  errorMessage: any;
  showSpinner:boolean =false;

  constructor( private snackBar: MatSnackBar,private signupService: SignupService,private router :Router) {}

  showCustomerForm = false;
  showRestaurantForm = false;
  showDeliveryForm = false;
  title='ReactiveForms';
  reactiveForm: FormGroup;
  ngOnInit(): void {
    this.reactiveForm = new FormGroup({
      firstname: new FormControl(null, Validators.compose([
        Validators.required,
        Validators.pattern(/^[A-Z][a-zA-Z]{0,8}$/) // First letter capital, 1 to 9 characters
      ])),

      lastname: new FormControl(null, Validators.compose([
        Validators.required,
        Validators.pattern(/^[A-Za-z]{1,9}$/) // Allows only alphabetic characters and limits length to 1-9
      ])),
      email: new FormControl(null, [Validators.email, Validators.required]),
      mobileNumber: new FormControl(null, [
        Validators.pattern(/^[0-9]{10}$/), // Accepts only numbers and validates length to 10
        Validators.required
      ]),
      acceptTerms: new FormControl(null, Validators.required),

      username: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.compose([
        Validators.required,
        Validators.minLength(8), // Minimum length requirement
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/) // Strong password pattern
      ])),
      retypepassword: new FormControl(null, Validators.required),
      role: new FormControl(null, Validators.required),
      customer: new FormGroup({
        customerName: new FormControl(null, Validators.compose([
          Validators.required,
          Validators.pattern(/^[A-Z][a-zA-Z\s]*$/) // Ensures customer name starts with a capital letter and contains only letters
        ])),
        customerAddress: new FormControl(null, Validators.pattern(/^[a-zA-Z0-9, ]*$/)),
        customerEmail: new FormControl(null, Validators.email), // Validates email format
        customerLongitude: new FormControl(null, Validators.pattern(/^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?)/)), // Longitude validation
        customerLatitude: new FormControl(null, Validators.pattern(/^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?)/)), // Latitude validation
        customerDateOfBirth: new FormControl(null, [Validators.required, this.validateDOB]),
        customerGender: new FormControl(null),
      }),
      restaurant: new FormGroup({
        restaurantName:new FormControl(null, Validators.compose([
          Validators.required,
          Validators.pattern(/^[A-Z][a-zA-Z]*$/) // Ensures customer name starts with a capital letter and contains only letters
        ])),
        restaurantDescription: new FormControl(null, Validators.pattern(/^[a-zA-Z0-9, ]*$/)),
        email: new FormControl(null, Validators.email), // Validates email format
        latitude: new FormControl(null, Validators.pattern(/^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?)/)), // Latitude validation
        longitude: new FormControl(null, Validators.pattern(/^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?)/)), // Longitude validation
        phoneNumber: new FormControl(null, [
          Validators.pattern(/^[0-9]{10}$/), // Accepts only numbers and validates length to 10
          Validators.required
        ]),
        restaurantAvailability: new FormControl(null),
      }),

     delivery: new FormGroup({
  deliveryName: new FormControl(null, Validators.compose([
    Validators.required,
    Validators.pattern(/^[A-Z][a-zA-Z]*$/)
  ])),
  deliveryContactNumber: new FormControl(null, Validators.compose([
    Validators.required,
    Validators.pattern(/^[0-9]{10}$/)
  ])),
  deliveryEmail: new FormControl(null, Validators.email),
  vehicleNumber: new FormControl(null, Validators.pattern(/^[\w\d]{4,10}$/))

}),

    }, { validators: this.passwordMatchValidator });
  }




  passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password');
    const retypepassword = control.get('retypepassword');

    if (password && retypepassword && password.value !== retypepassword.value) {
      return { 'passwordMismatch': true };
    }

    return null;
  }
  getDetails(event: Event) {
    const value = (event.target as HTMLSelectElement).value;
    this.showCustomerForm = value === '1';
    this.showRestaurantForm = value === '2';
    this.showDeliveryForm = value === '3';
    // Disable validations for fields not currently shown
  if (!this.showCustomerForm) {
    this.reactiveForm.get('customer').clearValidators();
    this.reactiveForm.get('customer').updateValueAndValidity();
  }

  if (!this.showRestaurantForm) {
    this.reactiveForm.get('restaurant').clearValidators();
    this.reactiveForm.get('restaurant').updateValueAndValidity();
  }

  if (!this.showDeliveryForm) {
    this.reactiveForm.get('delivery').clearValidators();
    this.reactiveForm.get('delivery').updateValueAndValidity();
  }
  }


  //public passwordMatchError: string = '';

  // onSubmit(){
  //   this.signupService.submitSignupForm(this.reactiveForm.getRawValue()).subscribe(
  //     (data) => {
  //       this.openSuccessSnackbar("Registration Completed Successfully");
  //       this.router.navigate(['/Login']);
  //       console.log(data);
  //       console.log(this.reactiveForm)
  //     },
  //     (error) => {
  //       this.openSuccessSnackbar("Registration Failed");

  //       console.error('Error:', error);
  //       console.log(this.reactiveForm)
  //     }
  //   );
  // }
  onSubmit() {
   
  
    this.showSpinner = true;
    this.signupService.submitSignupForm(this.reactiveForm.getRawValue()).subscribe(
      (data) => {
        this.showSpinner = false; // Toggle off the spinner upon successful submission
        this.router.navigate(['/Login']);
        const message = data.message; // Assuming the message is stored in the 'message' field
        this.openSuccessSnackbar(message);
      },
      (error) => {
        this.showSpinner = false; // Toggle off the spinner upon error
        if (error.error.message === 'User with the provided email already exists or is active.') {
          this.openSuccessSnackbar(error.error.message);
        } else {
          this.openSuccessSnackbar('An error occurred.');
        }
        console.error('Error:', error);
      }
    );
  }
  
  validateDOB(control: AbstractControl): { [key: string]: boolean } | null {
    const selectedDate = new Date(control.value);
    const today = new Date();

    if (selectedDate > today) {
      return { 'futureDate': true };
    }

    return null;
  }
  openSuccessSnackbar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // Adjust the duration as needed
      verticalPosition: 'top', // Position the snackbar at the top
      panelClass: ['custom-snackbar'], // Add a custom CSS class for styling
    });
  }
}





