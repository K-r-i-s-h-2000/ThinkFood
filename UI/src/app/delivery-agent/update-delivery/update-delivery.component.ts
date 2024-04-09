import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DeliveryHomeService } from '../../service/delivery-home.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NavbarDeliveryComponent } from "../navbar-delivery/navbar-delivery.component";
import { MatDialogClose } from '@angular/material/dialog';

@Component({
    selector: 'app-update-delivery',
    standalone: true,
    templateUrl: './update-delivery.component.html',
    styleUrl: './update-delivery.component.scss',
    imports: [CommonModule, FormsModule, ReactiveFormsModule, NavbarDeliveryComponent,MatDialogClose]
})
export class UpdateDeliveryComponent implements OnInit {


  showUpdateForm:boolean=true
  updateForm:FormGroup
  constructor(private formBuilder: FormBuilder, private deliveryHomeService: DeliveryHomeService,private route:ActivatedRoute, private router:Router){}
  ngOnInit(): void {
    this.updateForm = this.formBuilder.group({
      vehicleNumber: new FormControl(null),
      deliveryContactNumber: new FormControl(null),
    });

    console.log(this.showUpdateForm)

  }

  updateDelivery()
  {
    const deliveryId= parseInt(localStorage.getItem('deliveryId'),10)
    if (this.updateForm.valid) {

      this.deliveryHomeService.updateDelivery(deliveryId,this.updateForm.getRawValue())
        .subscribe(response => {
          console.log(response); // The response from the server (e.g., "Delivery is Updated")
          // You can perform any additional actions here if needed
          this.showUpdateForm = false; // Hide the form after successful update
        });
      }
      this.router.navigate(['/deliveryhome'])
  }



  }
