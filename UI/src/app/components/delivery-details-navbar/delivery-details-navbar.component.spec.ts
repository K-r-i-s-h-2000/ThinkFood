import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryDetailsNavbarComponent } from './delivery-details-navbar.component';

describe('DeliveryDetailsNavbarComponent', () => {
  let component: DeliveryDetailsNavbarComponent;
  let fixture: ComponentFixture<DeliveryDetailsNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeliveryDetailsNavbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeliveryDetailsNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
