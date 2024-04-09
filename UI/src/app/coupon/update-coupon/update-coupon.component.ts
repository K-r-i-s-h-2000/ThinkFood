import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CouponService } from '../../service/coupon.service';
import { Observable } from 'rxjs'; // Import Observable
import { Router, RouterLink } from '@angular/router';
import { Navbar4Component } from "../../components/navbar4/navbar4.component";

@Component({
    selector: 'app-update-coupon',
    standalone: true,
    templateUrl: './update-coupon.component.html',
    styleUrls: ['./update-coupon.component.scss'] // Fix typo: styleUrl to styleUrls
    ,
    imports: [CommonModule, ReactiveFormsModule, RouterLink, Navbar4Component]
})
export class UpdateCouponComponent {
  title = 'ReactiveForms';
  reactiveForm: FormGroup;
  responseData: any;

  constructor(private couponService: CouponService, private router:Router) {}

  ngOnInit(): void {
    this.reactiveForm = new FormGroup({
      couponCode: new FormControl(null, Validators.required),
      discountPercentage: new FormControl(null, Validators.required),
      expiryDate:new FormControl(null,Validators.required)

    });
  }

  onSubmit() {
    this.couponService.updateCoupon(this.reactiveForm.getRawValue()).subscribe(
      (data) => {
        console.log(data);
        alert("Coupon Updated")
        this.router.navigate(['list-all-coupon']);
        this.responseData = data;
      },
      (error) => {
        alert("check if all entries are correct");
        console.error(error);
      }


    );
  }
}
