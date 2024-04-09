import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router,RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LogoutDeliveryAgentComponent } from '../logout-delivery-agent/logout-delivery-agent.component';

@Component({
  selector: 'app-navbar-delivery',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './navbar-delivery.component.html',
  styleUrl: './navbar-delivery.component.scss'
})
export class NavbarDeliveryComponent {
  isHomeLink : boolean;
  isLoginLink: boolean;
  isSignupLink : boolean;
  
  constructor( private dialog: MatDialog,private router:Router,private activatedRoute: ActivatedRoute){}




  logout(){
    let dialogRef = this.dialog.open(LogoutDeliveryAgentComponent, {
  
    });
    
  }
}
