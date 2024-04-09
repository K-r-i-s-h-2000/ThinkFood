import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { Navbar2Component } from '../components/navbar2/navbar2.component';
import { ReviewsAndRatingsService } from '../service/reviews-and-ratings.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-reviews',
  standalone: true,
  imports: [CommonModule, RouterLink, Navbar2Component, ReactiveFormsModule],
  templateUrl: './update-reviews.component.html',
  styleUrl: './update-reviews.component.scss'
})
// export class UpdateReviewsComponent implements OnInit {
//   updateForm:FormGroup;
//   id: number;
//   existingReview: any;


//   constructor(
//     private route: ActivatedRoute,
//     private router: Router,
//     private fb: FormBuilder,
//     private reviewsAndRatingsService: ReviewsAndRatingsService
//   ) {}

//   ngOnInit(): void {

//     this.route.params.subscribe(params => {
//       this.id = +params['id'];
//       console.log('Review ID ', this.id);
//     });

//     this.reviewsAndRatingsService.getReviewsAndRatingsById(this.id).subscribe(
//       (review: any) => {
//         this.existingReview = review;
//         this.updateForm.patchValue({
//           review: this.existingReview.review,
//           rating: this.existingReview.rating
//         });
//       },
//       error => {
//         console.error('Error fetching existing review data:', error);
//       }
//     );

//     this.updateForm = this.fb.group({
//       review: ['', Validators.required],
//       rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]]

//     });
//   }

//   updateReview(): void {
//     if (this.updateForm.valid) {
//       const updatedReview = {
//         id: this.id,
//         review: this.updateForm.value.review,
//         rating: this.updateForm.value.rating
//       };

//        this.reviewsAndRatingsService.updateReviewsAndRatings(this.id,updatedReview).subscribe(
//         response => {
//           console.log('Review updated successfully:', response);
//           alert('Review updated successfully.');
//           this.router.navigate(['/reviews']);
//         },
//         error => {
//           console.error('Error updating review:', error);

//         }
//       );
//     } else {
//       console.log('form is not valid');
//       alert('form is not valid');
//     }
//   }

// }
export class UpdateReviewsComponent implements OnInit {
  updateForm: FormGroup;

  constructor(
    private snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<UpdateReviewsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { reviewId: number },
    private fb: FormBuilder,
    private reviewsAndRatingsService: ReviewsAndRatingsService // Replace with your actual service
  ) {
    this.updateForm = this.fb.group({
      review: ['', [Validators.required]],
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
    });
  }

  ngOnInit(): void {
    this.reviewsAndRatingsService.getReviewsAndRatingsById(this.data.reviewId).subscribe(
      (existingReview) => {
        this.updateForm.patchValue({
          review: existingReview.review,
          rating: existingReview.rating,
        });
      },
      (error) => {
        console.error('Error fetching review details:', error);
      }
    );
  }

  updateReview(): void {
    if (this.updateForm.valid) {
    const updatedReview = this.updateForm.value;

    this.reviewsAndRatingsService.updateReviewsAndRatings(this.data.reviewId, updatedReview).subscribe(
      response => {
        console.log('Review updated successfully:', response);
        this.openSnackBar('Review updated successfully');
        // alert('Review updated successfully.');
        this.dialogRef.close();
      },
      (error) => {
        console.error('Error updating review:', error);
      }
    );
  } else {
    console.log('form is not valid');
    this.openSnackBar('form is not valid');
    // alert('form is not valid');
  }}

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, 
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}