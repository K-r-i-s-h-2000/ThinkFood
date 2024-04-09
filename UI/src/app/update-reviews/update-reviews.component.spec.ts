import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateReviewsComponent } from './update-reviews.component';

describe('UpdateReviewsComponent', () => {
  let component: UpdateReviewsComponent;
  let fixture: ComponentFixture<UpdateReviewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateReviewsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
