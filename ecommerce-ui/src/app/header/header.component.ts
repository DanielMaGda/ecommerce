import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit {
  isUserAdmin: boolean;
  isLoggedIn: boolean;

  constructor(private authenticationService: AuthService) {
  }

  ngOnInit(): void {
    this.isUserAdmin = this.authenticationService.isUserAdmin();
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();

  }

  handleLogout() {
    this.authenticationService.logout();

  }

}
