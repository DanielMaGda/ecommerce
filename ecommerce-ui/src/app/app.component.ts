import {Component} from '@angular/core';
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'ecommerce';
  isLoggedIn = false;

  constructor(private authenticationService: AuthService) {

  }
  ngOnInit(){
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();

  }
}
