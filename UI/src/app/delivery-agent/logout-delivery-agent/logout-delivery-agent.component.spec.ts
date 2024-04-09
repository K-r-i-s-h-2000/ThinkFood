import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoutDeliveryAgentComponent } from './logout-delivery-agent.component';

describe('LogoutDeliveryAgentComponent', () => {
  let component: LogoutDeliveryAgentComponent;
  let fixture: ComponentFixture<LogoutDeliveryAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LogoutDeliveryAgentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LogoutDeliveryAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
