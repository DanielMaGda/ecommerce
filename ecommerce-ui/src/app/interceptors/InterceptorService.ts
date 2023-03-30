import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";
import {Buffer} from "buffer"
@Injectable()
export class InterceptorService implements HttpInterceptor {


  constructor(private authenticationService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authenticationService.isUserLoggedIn() && sessionStorage.getItem('basicauth') != null) {
      let auth = sessionStorage.getItem('basicauth') || '';
      let buffer = Buffer.from(auth).toString();
      console.log(buffer);
      const authReq = req.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': ` ${buffer}`
        })
      });
      return next.handle(authReq);
    } else {
      return next.handle(req);
    }
  }
}
