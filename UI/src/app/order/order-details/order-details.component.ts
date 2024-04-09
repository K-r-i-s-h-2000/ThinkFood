import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { OrderService } from '../../service/order.service';
import { Navbar2Component } from "../../components/navbar2/navbar2.component";
import { SidebarComponent } from "../../components/sidebar/sidebar.component";

@Component({
    selector: 'app-order-details',
    standalone: true,
    templateUrl: './order-details.component.html',
    styleUrl: './order-details.component.scss',
    imports: [CommonModule, RouterLink, RouterOutlet, Navbar2Component, SidebarComponent]
})
export class OrderDetailsComponent implements OnInit {
  orderId: number;
  order:any;

  constructor(private router: Router, private route: ActivatedRoute, private orderService:OrderService) { }

  ngOnInit(): void {
    // Retrieve the orderId from the route parameters
    this.route.params.subscribe(params => {
      this.orderId = +params['id']; // Convert the parameter to a number
      this.orderService.getOrderById(this.orderId).subscribe(
        (data : any[]) => {
          this.order = data;
          console.log(data);
        },
        (error) => {
          console.error(error);
        }
      );
    }
    );
  }
  showDeliveryDetails()
  {
    localStorage.setItem('orderId',this.orderId.toString())
    this.router.navigate(['deliveryDetails'])
  }
  getOrderDetails(){
    
  }
}
