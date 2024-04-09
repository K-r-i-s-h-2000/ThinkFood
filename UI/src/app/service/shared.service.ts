import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private refreshSubject = new BehaviorSubject<boolean>(false);

  // Observable to notify components to refresh
  refresh$ = this.refreshSubject.asObservable();

  // Method to trigger refresh
  triggerRefresh(): void {
    this.refreshSubject.next(true);
  }
}
