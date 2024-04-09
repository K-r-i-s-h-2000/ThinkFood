import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewsAndRatingsService } from '../service/reviews-and-ratings.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { Navbar2Component } from '../components/navbar2/navbar2.component';
import moment from 'moment';
import { SignupService } from '../signup.service';
import { MomentModule } from 'ngx-moment';
import { MatDialog } from '@angular/material/dialog';
import { UpdateReviewsComponent } from '../update-reviews/update-reviews.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StarRatingComponent } from '../star-rating/star-rating.component';

@Component({
  selector: 'app-reviews-and-ratings',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink, RouterOutlet, Navbar2Component, MomentModule, StarRatingComponent ],
  templateUrl: './reviews-and-ratings.component.html',
  styleUrl: './reviews-and-ratings.component.scss'
})
export class ReviewsAndRatingsComponent implements OnInit {
  review: string = '';
  rating: number = null;
  restaurantId: number;
  reviewForm: FormGroup;
  reviewsAndRatingsList: any[] = [];
  currentUser: any;
  restaurantName: string;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder ,
    private reviewsAndRatingsService: ReviewsAndRatingsService,
    private signupService: SignupService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.restaurantId = +params['restaurantId'];
      console.log('Restaurant ID in ReviewsAndRatingsComponent:', this.restaurantId);

    });

    this.currentUser = localStorage.getItem('custId');
    console.log('ID of the currentUser',this.currentUser);

    this.loadReviewsAndRatings();

    this.reviewForm = this.fb.group({
      review: ['', Validators.required],
      // rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
    });

    this.signupService.getRestaurantById(this.restaurantId).subscribe(
      (restaurantDetails: any) => {
        this.restaurantName = restaurantDetails.restaurantName;
      },
      error => {
        console.error('Error loading restaurant details in review component:', error);
      }
      );

  }


  submitReviewAndRating(): void {
    const customerId = localStorage.getItem('custId');

    if (!customerId || !this.restaurantId) {
      console.error('Customer ID or Restaurant ID not found.');
      return;
    }

    if (this.reviewForm.invalid) {
      console.error('Form is invalid.');
      // alert('Form is invalid.');
      this.openSnackBar('Form is invalid.');

      return;
    }

    const data = {
      customerId,
      restaurantId: this.restaurantId,
      review: this.reviewForm.value.review,
      rating: parseInt(localStorage.getItem('starRating'), 10),
    };

    this.reviewsAndRatingsService.createReviewsAndRatings(data)
      .subscribe(
        response => {
          console.log('Review and rating submitted successfully:', response);
          // alert('reviews and ratings are saved');
          this.openSnackBar('Reviews and ratings are saved');

          this.loadReviewsAndRatings();
          // Reset the form after submission
          this.reviewForm.reset();
        },
        error => {
          console.error('Error submitting review and rating:', error);
        }
      );
  }


  // loadReviewsAndRatings() {

  //   if (this.restaurantId) {
  //     this.reviewsAndRatingsService.getAllReviewsAndRatings(this.restaurantId).subscribe(
  //       (data: any[]) => {
  //         this.reviewsAndRatingsList = data;
  //       },
  //       error => {
  //         console.error('Error loading reviews and ratings:', error);
  //       }
  //     );
  //   }
  // }

  loadReviewsAndRatings() {
    if (this.restaurantId) {
      this.reviewsAndRatingsService.getAllReviewsAndRatings(this.restaurantId).subscribe(
        (data: any[]) => {
          // Format the dates before assigning to the property
          this.reviewsAndRatingsList = data.map(review => {
            return {
              ...review,
              formattedDate: moment(review.lastModifiedDateTime).fromNow()
            };
          });
        },
        error => {
          console.error('Error loading reviews and ratings:', error);
        }
      );
    }
  }

  // updateReview(reviewId: number): void {
  //   this.router.navigate(['/update-reviews', reviewId]);
  // }


  deleteReview(reviewId: number): void {

    if (confirm('Are you sure you want to delete this review?')) {
      this.reviewsAndRatingsService.softDeleteReviewsAndRatingsById(reviewId).subscribe(
        (response) => {
          console.log('Review and rating deleted successfully:', response);
          // alert('Review and rating deleted successfully.');
          this.openSnackBar('Review and rating deleted successfully.');
          this.loadReviewsAndRatings();
        },
        (error) => {
          console.error('Error deleting review and rating:', error);
        }
      );
    }
  }

  openUpdateDialog(reviewId: number): void {
    const dialogRef = this.dialog.open(UpdateReviewsComponent, {
      data: { reviewId: reviewId }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadReviewsAndRatings();
    });
  }


  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }


}

