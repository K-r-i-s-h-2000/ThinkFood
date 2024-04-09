import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../login.service';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-viewcustomerdetails',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet,ReactiveFormsModule],
  templateUrl: './viewcustomerdetails.component.html',
  styleUrl: './viewcustomerdetails.component.scss'
})
export class ViewcustomerdetailsComponent implements OnInit {
  title="viewProfileForm";
  reactiveform:FormGroup;

 
  _dialog:MatDialog;
  userDetails: any;
  constructor(public dialog: MatDialog,private loginService: LoginService, private formBuilder: FormBuilder,private router: Router){
    this._dialog=dialog;
    
     
    
    
  }
  ngOnInit(): void {
    this.reactiveform = this.formBuilder.group({
      // Define your form controls here based on your requirements
      customerAddress: [''],
      customerLongitude: [''],
      customerLatitude: [''],
      customerDateOfBirth: [''],
      customerGender: ['']
      // Add other form controls as needed
    });
    
      this.loginService.viewProfile(this.reactiveform.getRawValue()).subscribe(
        (data) => {
          console.log(data);
           
            this.userDetails = data;
        }, 
        (error) => {
          alert("error");
          console.log(error);
        }
      );
  }
  



closeForm() {
  this._dialog.closeAll();    
}



}

