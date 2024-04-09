import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainbarCurrentComponent } from './mainbar-current.component';

describe('MainbarCurrentComponent', () => {
  let component: MainbarCurrentComponent;
  let fixture: ComponentFixture<MainbarCurrentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainbarCurrentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MainbarCurrentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
