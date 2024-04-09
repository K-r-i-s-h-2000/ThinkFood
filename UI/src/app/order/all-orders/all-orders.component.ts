import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../service/order.service';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Navbar2Component } from "../../components/navbar2/navbar2.component";
import { MatPaginatorModule } from '@angular/material/paginator';


@Component({
  selector: 'app-all-orders',
  standalone: true,
  templateUrl: './all-orders.component.html',
  styleUrls: ['./all-orders.component.scss'], // Corrected 'styleUrl' to 'styleUrls
  imports: [CommonModule, RouterOutlet, RouterLink, Navbar2Component,MatPaginatorModule]
})
export class AllOrdersComponent implements OnInit {
  order: any[] = [];
  pageSize = 5;
  currentPage = 0;
  pagedOrder: any[] = [];


  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.orderService.getAllOrders().subscribe(
      (data: any[]) => {
        // this.order = data.reverse(); // Reverse the order array
        this.order = data.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
        this.pagedOrder = this.paginateOrder();
      },
      (error) => {
        console.error(error);
      }
    );
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pagedOrder = this.paginateOrder();
  }

  navigateToOrderDetails(orderId: number): void {
    this.router.navigate(['/order-details', orderId]);
  }

  private paginateOrder(): any[] {
    const startIndex = this.currentPage * this.pageSize;
    return this.order.slice(startIndex, startIndex + this.pageSize);
  }

 

  // onSubmit() {
  //   this.orderService.getAllProducts().subscribe(
  //     (data: any[]) => {
  //       this.order = data;
  //       console.log(data);
  //     },
  //     (error) => {
  //       console.error(error);
  //     }
  //   );
  // }
}
