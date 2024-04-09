import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { FaqService } from '../service/faq.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Navbar4Component } from '../components/navbar4/navbar4.component';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-faq',
  standalone: true,
  imports: [CommonModule,RouterLink, RouterOutlet, FormsModule, ReactiveFormsModule, Navbar4Component],
  templateUrl: './faq.component.html',
  styleUrl: './faq.component.scss'
})


export class FaqComponent implements OnInit {
  form: FormGroup;
  queries: any[];
  selectedId: any;
  response: any;

  constructor(private faqService: FaqService, private route: Router, private formBuilder: FormBuilder, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      query: [null, Validators.required],
      response: [null, Validators.required]
    });

    this.getAllQueries();
  }

  getAllQueries(): void {
    const token = localStorage.getItem('token');

    this.faqService.getAllQueries(token).subscribe(
      (data: any) => {
        console.log(data);
        this.queries = data;

      },
      (error) => {
        console.error(error);
      }
    );
  }

  toggleAccordion(id: any): void {
    if (this.selectedId === id) {
      this.selectedId = null;
    } else {
      this.selectedId = id;
      this.getSupportById(id);
    }
  }

  getSupportById(id: number) {
    console.log('Query clicked:', id);
    const token = localStorage.getItem('token');

    this.faqService.getSupportById(id, token).subscribe(
      (data: any) => {
        console.log(data);
        this.response = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  createSupport(): void {
    const token = localStorage.getItem('token');
    const supportData = this.form.value;

    if(supportData.query && supportData.response) {
      this.faqService.createSupport(supportData, token).subscribe(
        (data: any) => {
          console.log('Support created:', data);
          this.openSnackBar('support created successfully');
          // alert('support created successfully');
          this.getAllQueries();
          this.form.reset();
        },
        (error) => {
          console.error(error);
        }
        );
      } else {
        // alert('Please enter both query and response before submitting.');
        this.openSnackBar('Form is not valid');
      }
  }

  softDeleteSupportById(id: any): void {
    const token = localStorage.getItem('token');

    if (confirm('Are you sure you want to delete?')) {
    this.faqService.softDeleteSupportById(id, token).subscribe(
      (data: any) => {
        console.log('Support soft-deleted:', data);
        // alert('Support deleted successfully');
        this.openSnackBar('Support deleted successfully');
        this.getAllQueries();
      },
      (error) => {
        console.error(error);
      }
    );
  }
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, 
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

}
