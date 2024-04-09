import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryCurrentComponent } from './delivery-current.component';

describe('DeliveryCurrentComponent', () => {
  let component: DeliveryCurrentComponent;
  let fixture: ComponentFixture<DeliveryCurrentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeliveryCurrentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeliveryCurrentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
