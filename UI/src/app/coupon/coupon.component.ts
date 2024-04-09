import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar2Component } from "../components/navbar2/navbar2.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CouponService } from '../service/coupon.service';
import { RouterLink, RouterOutlet } from '@angular/router';
import { Navbar4Component } from "../components/navbar4/navbar4.component";

@Component({
    selector: 'app-coupon',
    standalone: true,
    templateUrl: './coupon.component.html',
    styleUrl: './coupon.component.scss',
    imports: [CommonModule, Navbar2Component, FormsModule, ReactiveFormsModule, RouterLink, RouterOutlet, Navbar4Component]
})
export class CouponComponent implements OnInit {
  
  title = 'ReactiveForms';
  reactiveForm: FormGroup;
  responseData:any;
  checkIfExceeded:any;
  

  constructor(private couponService:CouponService) {}
  
  
  ngOnInit(): void {
    this.reactiveForm = new FormGroup({
      couponCode:new FormControl(null,Validators.required),
      discountPercentage:new FormControl(null,Validators.required),
      expiryDate:new FormControl(null,Validators.required)
    })
  
  }

  onSubmit()
  {     

    this.couponService.createCoupon(this.reactiveForm.getRawValue()).subscribe(
      (data)=>{
        this.checkIfExceeded=data;
        if(this.checkIfExceeded == false)
        {
          alert("Discount exceeded 100");
          this.checkIfExceeded=data;

        }
        console.log(data);
        this.responseData=data;
      }
    )
  }
}

