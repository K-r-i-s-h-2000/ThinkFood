import { TestBed } from '@angular/core/testing';

import { ReviewsAndRatingsService } from './reviews-and-ratings.service';

describe('ReviewsAndRatingsService', () => {
  let service: ReviewsAndRatingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReviewsAndRatingsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
