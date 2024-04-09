import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { LoginService } from '../login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-forgotpassword',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterLink,ReactiveFormsModule],
  templateUrl: './forgotpassword.component.html',
  styleUrl: './forgotpassword.component.scss'
})
export class ForgotpasswordComponent implements OnInit {
  formStatus: string;


  _dialog:MatDialog;
  responseData: any;
  constructor(private snackBar: MatSnackBar,public dialog: MatDialog,private loginService: LoginService, private formBuilder: FormBuilder,private router: Router){
    this._dialog=dialog;
  }

onSubmit() {
  if(this.forgotPasswordForm.valid){
  console.log(this.forgotPasswordForm.getRawValue())
  this.loginService.forgotPassword(this.forgotPasswordForm.getRawValue()).subscribe(
   (data) => {
    this._dialog.closeAll();
      console.log(data);
      this.openSuccessSnackbar("Please check your mail..");

       this.router.navigate(['/Login']);
    },
    (error) => {
      this.openSuccessSnackbar("invalid credentials");
      console.error('Error:', error);
    }
  );
}else{
  alert("Entries are required");

}}
 forgotPasswordForm:FormGroup;
  ngOnInit() {
    this.forgotPasswordForm=new FormGroup({
      email:new FormControl(null,[Validators.required,Validators.email]) ,

    });
    this.forgotPasswordForm.statusChanges.subscribe((value) => {
      console.log(value);
      this.formStatus = value;
    });
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
