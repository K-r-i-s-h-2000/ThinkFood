import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WarningNoAgentComponent } from './warning-no-agent.component';

describe('WarningNoAgentComponent', () => {
  let component: WarningNoAgentComponent;
  let fixture: ComponentFixture<WarningNoAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WarningNoAgentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WarningNoAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
