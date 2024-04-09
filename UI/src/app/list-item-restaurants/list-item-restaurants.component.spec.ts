import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListItemRestaurantsComponent } from './list-item-restaurants.component';

describe('ListItemRestaurantsComponent', () => {
  let component: ListItemRestaurantsComponent;
  let fixture: ComponentFixture<ListItemRestaurantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListItemRestaurantsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListItemRestaurantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
