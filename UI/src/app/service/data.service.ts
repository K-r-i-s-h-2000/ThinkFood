import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor() { }

  public notify = new BehaviorSubject<any>("");
  notifyObservale$=this.notify.asObservable();
 
  public notifyOther(data:any){
    if(data){
      this.notify.next(data);
    }
  }
}
