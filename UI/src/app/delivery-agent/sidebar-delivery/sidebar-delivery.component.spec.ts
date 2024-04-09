import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarDeliveryComponent } from './sidebar-delivery.component';

describe('SidebarDeliveryComponent', () => {
  let component: SidebarDeliveryComponent;
  let fixture: ComponentFixture<SidebarDeliveryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarDeliveryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SidebarDeliveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
