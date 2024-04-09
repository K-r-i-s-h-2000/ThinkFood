import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

import { AbstractControl, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { HttpHeaders } from '@angular/common/http';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-changepassword',
    standalone: true,
    templateUrl: './changepassword.component.html',
    styleUrl: './changepassword.component.scss',
    imports: [CommonModule, RouterLink, RouterOutlet, FormsModule,ReactiveFormsModule,]
})
export class ChangepasswordComponent implements OnInit {
  _dialog:MatDialog;



  constructor(private snackBar: MatSnackBar,private fb: FormBuilder,private loginService: LoginService,private router: Router,public dialog: MatDialog) {
    this._dialog=dialog;
  }
  title="ChangePasswordForm";
  reactiveform:FormGroup;
//   ngOnInit(){

//    this.reactiveform=new FormGroup({
//     currentPassword:new FormControl(null,Validators.required) ,
//     newPassword:new FormControl(null,Validators.required),
//     confirmationPassword:new FormControl(null,Validators.required)
//   }
//  );

//   }
ngOnInit() {
  this.reactiveform = this.fb.group({
    currentPassword: ['', Validators.required],
    newPassword: ['', [Validators.required,Validators.minLength(5)]],
    confirmationPassword: ['', [Validators.required, Validators.minLength(5)]],
  }, { validator: this.passwordMatchValidator });
}



  closeForm() {
      this._dialog.closeAll();
  }


  onSubmit() {

      // Call the signUpUser method from the SignupService
      this.loginService.changePassword(this.reactiveform.getRawValue()).subscribe(
        (data) => {
            this._dialog.closeAll();
            this.openSuccessSnackbar('Success!');
          this.router.navigate(['/Login']);
        },
        (error) => {
          this.openSuccessSnackbar('Error!');
          console.log('Error:');
        }
      );

  }
  // passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
  //   const newPassword = control.get('newPassword').value;
  //   const confirmationPassword = control.get('confirmationPassword').value;
  //   if (newPassword !== confirmationPassword) {
  //     return { passwordMismatch: true };
  //   }
  //   return null;
  // }
  passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const newPassword = control.get('newPassword').value;
    const confirmationPassword = control.get('confirmationPassword').value;

    if (newPassword !== confirmationPassword) {
      return { 'passwordMismatch': true };
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
