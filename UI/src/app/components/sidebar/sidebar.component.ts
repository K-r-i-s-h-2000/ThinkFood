import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DataService } from '../../service/data.service';
import { CouponService } from '../../service/coupon.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterOutlet,FormsModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {

  couponCode :any;
  popUp:any;

  advertisementCoupons: any[]=[] ;

  // advertisementCoupons: any[] = [
  //   { code: 'FLAT50' },
  //   { code: 'FLAT22' },
  //   { code: 'FLAT30' }
  // ];


  constructor(private dataService: DataService, private couponService: CouponService) { }
  ngOnInit(): void {

    this.gettingCoupon();
  }

  applyCoupon() {
    this.couponService.getCouponDetails(this.couponCode).subscribe(
      (couponDetails: any) => {
        this.popUp=null;
        this.dataService.notifyOther(couponDetails);
        console.log('Applying coupon:', this.couponCode);
      },
      (error) => {
        this.popUp=1;

        console.error('Error applying coupon:', error);
      }
    );
  }


  applyAdvertisementCoupon(coupon: any): void {
    this.couponCode = coupon.couponCode;
  }

  gettingCoupon(){
    this.couponService.getAllCouponWithExpiry().subscribe(
      (data)=>{
        this.advertisementCoupons = data.slice(0, 3);
        console.log('available coupons',this.advertisementCoupons);
      }
    )
  }

 
}

