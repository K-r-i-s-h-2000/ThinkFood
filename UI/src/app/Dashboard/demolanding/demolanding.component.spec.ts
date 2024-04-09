import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemolandingComponent } from './demolanding.component';

describe('DemolandingComponent', () => {
  let component: DemolandingComponent;
  let fixture: ComponentFixture<DemolandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemolandingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DemolandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
