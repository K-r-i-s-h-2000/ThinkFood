import { TestBed } from '@angular/core/testing';

import { DeliveryHomeService } from './delivery-home.service';

describe('DeliveryHomeService', () => {
  let service: DeliveryHomeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeliveryHomeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
