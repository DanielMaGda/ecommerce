import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {User} from "../models/user";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  registerForm: FormGroup;
  submitted = false;

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {

  }

  ngOnInit() {
    this.registerForm = this.fb.group({
      email: ['', Validators.required, Validators.email],
      password: ['', Validators.required, Validators.minLength(5)],
      userName: ['', Validators.required]
    })
  }

  getForm() {
    return this.registerForm.controls;
  }

  Register() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    alert('Good' + JSON.stringify(this.registerForm.value, null, 4));
    this.authService.registerUser(JSON.stringify(this.registerForm.value)).subscribe();
    this.router.navigate(["/login"]);
  }


}

