import { HttpInterceptorFn } from '@angular/common/http';

export const loggerInterceptor: HttpInterceptorFn = (req, next) => {
  console.log(`Request is on the way to ${req.url}`)
  const token = localStorage.getItem('token');

 if (token) {

    const authReq = req.clone({
      headers:req.headers.set('Authorization',`Bearer ${token}`)
  
    })

    return next(authReq);
  }

  return next(req);
};
