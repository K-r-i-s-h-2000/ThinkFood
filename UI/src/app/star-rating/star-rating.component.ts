import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-star-rating',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './star-rating.component.html',
  styleUrl: './star-rating.component.scss'
})
// export class StarRatingComponent {
//   @Input() rating: number = 0;
//   @Input() readOnly: boolean = false;
//   @Output() ratingChange: EventEmitter<number> = new EventEmitter<number>();

//   onClick(rating: number): void {
//     if (!this.readOnly) {
//       this.rating = rating;
//       this.ratingChange.emit(this.rating);
//     }
//   }
// }
export class StarRatingComponent {
  @Input() rating: number = 0;
  @Input() readOnly: boolean = false;
  @Output() ratingChange: EventEmitter<number> = new EventEmitter<number>();

  // Array to represent the stars
  stars: number[] = [1, 2, 3, 4, 5];

  // Emit the selected rating when a star is clicked
  setRating(selectedRating: number): void {
    if (!this.readOnly) {
      this.rating = selectedRating;
      this.ratingChange.emit(this.rating);
      localStorage.setItem('starRating', this.rating.toString());
    }
  }
}
