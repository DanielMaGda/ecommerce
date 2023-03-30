import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from '@angular/router';
import {Role} from "../enums/role";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  id: number;
  role: Role;
  errorMessage = 'Invalid Credentials';
  successMessage: string;
  invalidLogin = false;
  loginSuccess = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  handleLogin() {
    this.authService.loginUser(this.username, this.password,this.id).subscribe((result) => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
      this.router.navigate(['/product']);


    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });
  }

  RegisterPage() {
    this.router.navigate(['/register'])
  }
}
