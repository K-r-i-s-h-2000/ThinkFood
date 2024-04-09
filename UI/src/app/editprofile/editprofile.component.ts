import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../login.service';
import { MatDialog } from '@angular/material/dialog';
import { Router, RouterEvent, RouterLink, RouterOutlet } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-editprofile',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule,RouterLink,RouterOutlet],
  templateUrl: './editprofile.component.html',
  styleUrl: './editprofile.component.scss'
})
export class EditprofileComponent implements OnInit{
  url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgB730p0ChSl_CNr5N6n05AGzEtEAhFypOFg&usqp=CAU"
  onSelect(event) {
    let fileType = event.target.files[0].type;
    if (fileType.match(/image\/*/)) {
      let reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (event: any) => {
        this.url = event.target.result;
        const formData = new FormData();
      formData.append('profileImage', event.target.files[0]); // Append image file to FormData

      };
    } else {
      window.alert('Please select correct image format');
    }
  }
  customerProfilePicture: string = '';
onFileSelected($event: Event) {
throw new Error('Method not implemented.');
}
  title="editProfileForm";
  reactiveform:FormGroup;

  userId: string = '';
  userData: any = {};
  _dialog:MatDialog;
  constructor(private snackBar: MatSnackBar,public dialog: MatDialog,private loginService: LoginService, private formBuilder: FormBuilder,private router: Router){
    this._dialog=dialog;
  }

  ngOnInit() {
    // this.customerProfilePicture = this.loginService.getCustomerProfilePicture();
    // console.log(this.customerProfilePicture)
    // reactiveform:FormGroup;
    // this.reactiveform=new FormGroup({
    //   customerAddress: new FormControl(null),
    //   customerLongitude: new FormControl(null),
    //   customerLatitude: new FormControl(null ),
    //   customerDateOfBirth: new FormControl(null),
    //   customerGender: new FormControl(null),


    // });
    this.customerProfilePicture = this.loginService.getCustomerProfilePicture();

    this.reactiveform = this.formBuilder.group({
      customerAddress: ['', [Validators.required, Validators.minLength(5)]],
      customerLongitude: ['', [Validators.required, Validators.pattern(/^\d{1,3}\.\d{4,}$/)]],
      customerLatitude: ['', [Validators.required, Validators.pattern(/^\d{1,3}\.\d{4,}$/)]],
      customerDateOfBirth: ['', [Validators.required, Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      customerGender: ['', [Validators.required, Validators.pattern(/^(male|female|other)$/i)]],
      customerName: ['', [Validators.required, Validators.minLength(5)]],
    });

  }

  onSubmit2() {
    if (this.reactiveform.valid) {
      // Call the signUpUser method from the SignupService
      this.loginService.updateUserProfile(this.reactiveform.value).subscribe(
        (data) => {
          console.log(data);
          this._dialog.closeAll();
          this.openSuccessSnackbar("Success! Profile updated.");


        },
        (error) => {
          this.openSuccessSnackbar("Error! Failed to update profile.");

          console.log(error);
        }
      );
    } else {
      alert("Please fill out all required fields correctly.");
      // Or handle validation error as needed
    }
  }

  closeForm() {
    this._dialog.closeAll();
}
openSuccessSnackbar(message: string): void {
  this.snackBar.open(message, 'Close', {
    duration: 3000, // Adjust the duration as needed
    verticalPosition: 'top', // Position the snackbar at the top
    panelClass: ['custom-snackbar'], // Add a custom CSS class for styling
  });
}
}

