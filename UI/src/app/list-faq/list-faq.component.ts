import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { FaqService } from '../service/faq.service';
import { Navbar2Component } from '../components/navbar2/navbar2.component';

@Component({
  selector: 'app-list-faq',
  standalone: true,
  imports: [CommonModule,RouterLink, RouterOutlet, Navbar2Component],
  templateUrl: './list-faq.component.html',
  styleUrl: './list-faq.component.scss'
})
export class ListFaqComponent implements OnInit {
  queries: any[];
  selectedId: any;
  response: any;

  constructor(private faqService: FaqService, private route: Router) {}

  ngOnInit(): void {
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
}