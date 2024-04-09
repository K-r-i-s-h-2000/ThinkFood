import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListrestaurantsComponent } from './listrestaurants.component';

describe('ListrestaurantsComponent', () => {
  let component: ListrestaurantsComponent;
  let fixture: ComponentFixture<ListrestaurantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListrestaurantsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListrestaurantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
