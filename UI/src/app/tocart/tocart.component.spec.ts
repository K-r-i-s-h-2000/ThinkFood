import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TocartComponent } from './tocart.component';

describe('TocartComponent', () => {
  let component: TocartComponent;
  let fixture: ComponentFixture<TocartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TocartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TocartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
