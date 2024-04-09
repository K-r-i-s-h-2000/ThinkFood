import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveredconfirmationComponent } from './deliveredconfirmation.component';

describe('DeliveredconfirmationComponent', () => {
  let component: DeliveredconfirmationComponent;
  let fixture: ComponentFixture<DeliveredconfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeliveredconfirmationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeliveredconfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
