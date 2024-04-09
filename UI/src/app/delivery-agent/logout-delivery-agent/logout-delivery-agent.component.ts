import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogClose } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-logout-delivery-agent',
  standalone: true,
  imports: [CommonModule,MatDialogClose],
  templateUrl: './logout-delivery-agent.component.html',
  styleUrl: './logout-delivery-agent.component.scss'
})
export class LogoutDeliveryAgentComponent {
constructor(private router:Router, private activatedRouter: ActivatedRoute){}

yes(){
  
  localStorage.clear();
  this.router.navigate(['Login']);

}

}
