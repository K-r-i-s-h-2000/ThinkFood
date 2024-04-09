import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CouponService } from '../../service/coupon.service';
import { Navbar2Component } from "../../components/navbar2/navbar2.component";
import { Navbar3Component } from "../../components/navbar3/navbar3.component";
import { routes } from '../../app.routes';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Navbar4Component } from "../../components/navbar4/navbar4.component";

@Component({
    selector: 'app-list-all-coupon',
    standalone: true,
    templateUrl: './list-all-coupon.component.html',
    styleUrl: './list-all-coupon.component.scss',
    imports: [CommonModule, Navbar2Component, Navbar3Component, RouterLink, RouterOutlet, Navbar4Component]
})
export class ListAllCouponComponent implements OnInit {


  responseData:any;
  coupons: any[] = [];

  constructor(private couponService: CouponService, private router:Router) {}

  ngOnInit(): void {
    this.couponService.getAllCoupon().subscribe((data: any[]) => {
      this.coupons = data;

      },
      (error) => {
        console.error(error);
      }
    );


  }

  isExpired(expiryDate: string): boolean {
    const currentDate = new Date();
    const couponExpiryDate = new Date(expiryDate);
    return currentDate > couponExpiryDate;
  }


  navigateToCreateCoupon()
  {
    this.router.navigate(['Coupon']);
  }

  updateCoupon(couponCode:string)
  {
    this.router.navigate(['Update-coupon']);

  }

  deleteCoupon(couponId: number): void {
  const confirmation = confirm("Are you sure you want to delete the coupon?");
  if (!confirmation) {
    return throwError('Deletion canceled by user');
  }
    this.couponService.deleteCoupon(couponId).subscribe((response) => {
      console.log(response);
      this.ngOnInit();
    });
  }
}


function throwError(arg0: string): void {
  throw new Error('Function not implemented.');
}
// deleteRecordById(id: number) {
//   const confirmation = confirm("Are you sure you want to delete the item?");
//   if (!confirmation) {
//     return throwError('Deletion canceled by user');
//   }
//   const deleteUrl = `${this.baseUrl}/users/soft-delete/${id}`;

//   // // Get the token from localStorage if needed
//   // const token = localStorage.getItem('token');

//   // // Create headers with Authorization if token is present
//   // const headers = token ? new HttpHeaders({ 'Authorization': `Bearer ${token}` }) : undefined;

//   // Make the HTTP DELETE request
//   return this.http.delete(deleteUrl);
// }

