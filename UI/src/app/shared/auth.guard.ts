import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginService } from '../login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private service: LoginService, private router: Router) {}

  canActivate(): boolean {

    const userRole = localStorage.getItem('userRole');

    if (this.service.isLoggedIn()) {
      // const userRole = this.service.getRole();
      if (userRole === 'CUSTOMER') {
        return true;
      } 
      else {
        this.router.navigate(['/Login']);
        return false;
      }
    } 
    else {
      this.router.navigate(['/Login']);
      return false;
    }
  }

}
