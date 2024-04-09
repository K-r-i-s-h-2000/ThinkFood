import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../../login.service';
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { MatDialog } from '@angular/material/dialog';
import { LogoutDeliveryAgentComponent } from '../logout-delivery-agent/logout-delivery-agent.component';


@Component({
  selector: 'app-sidebar-delivery',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar-delivery.component.html',
  styleUrl: './sidebar-delivery.component.scss'
})
export class SidebarDeliveryComponent {
  constructor(public dialog: MatDialog,private router: Router, private activatedRouter: ActivatedRoute,private deliveryHome:DeliveryHomeService, private loginService:LoginService){}

  navigateToDeliveryHistory()
  {
    this.router.navigate(['/deliveryHistory']);
  }
  navigateToDeliveryCurrent()
  {
    this.router.navigate(['/deliveryCurrent']);
  }
  logout(){
    let dialogRef = this.dialog.open(LogoutDeliveryAgentComponent, {
  
    });
    
  }
navigateToDeliveryHome(){
  this.router.navigate(['deliveryhome']);
}
}
