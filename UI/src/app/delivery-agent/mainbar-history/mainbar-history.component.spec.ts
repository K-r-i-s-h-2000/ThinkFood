import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainbarHistoryComponent } from './mainbar-history.component';

describe('MainbarHistoryComponent', () => {
  let component: MainbarHistoryComponent;
  let fixture: ComponentFixture<MainbarHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainbarHistoryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MainbarHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
