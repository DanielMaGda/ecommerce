import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {map} from 'rxjs/operators';
import {Buffer} from "buffer";
import {Role} from "../enums/role";
import {User} from "../models/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

  public username: string;
  public password: string;
  public id: number;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {

  }

  loginUser(username: string, password: string, id: number) {
    return this.http.get(environment.apiUrl + `login`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}}).pipe(
      map((res) => {
        this.username = username;
        this.password = password;
        this.id = id;
        this.registerSuccessfulLogin(username);
        this.userRole(Role.USER);
        sessionStorage.setItem('basicauth', this.createBasicAuthToken(username, password))


      })
    );
  }

  loginAdmin(username: string, password: string) {
    return this.http.get(environment.apiUrl + `admin/login`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}}).pipe(
      map((res) => {
        this.username = username;
        this.password = password;
        this.userRole(Role.ADMIN);
        this.registerSuccessfulLogin(username);
        sessionStorage.setItem('basicauth', this.createBasicAuthToken(username, password))


      })
    );
  }

  registerUser(user: any): Observable<User> {
    return this.http.post<User>(environment.apiUrl + 'register', user, this.httpOptions).pipe();

  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + Buffer.from(username + ":" + password).toString('base64');
  }


  registerSuccessfulLogin(username: string) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username)
  }

  userRole(role: Role) {
    sessionStorage.setItem('role', role)
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem('basicauth')
    sessionStorage.removeItem('role')
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    return user !== null;

  }

  isUserAdmin() {
    return sessionStorage.getItem('role') == "Admin";

  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }
}
