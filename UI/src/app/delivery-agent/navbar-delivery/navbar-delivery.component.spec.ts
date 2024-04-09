import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarDeliveryComponent } from './navbar-delivery.component';

describe('NavbarDeliveryComponent', () => {
  let component: NavbarDeliveryComponent;
  let fixture: ComponentFixture<NavbarDeliveryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarDeliveryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NavbarDeliveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
