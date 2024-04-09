import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAllCouponComponent } from './list-all-coupon.component';

describe('ListAllCouponComponent', () => {
  let component: ListAllCouponComponent;
  let fixture: ComponentFixture<ListAllCouponComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAllCouponComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListAllCouponComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
