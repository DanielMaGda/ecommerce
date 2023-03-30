import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "../models/user";
import {Observable} from "rxjs";
import {Order} from "../models/order";

@Injectable({providedIn: "root"})
export class UserService {
  user: User []
  private usersUrl: string;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8082/api/user';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.usersUrl}/`, this.httpOptions).pipe();
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  public deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.usersUrl}/${id}`, this.httpOptions).pipe();
  }
  public getUserByUserName(userName: string): Observable<Order> {
    return this.http.get<Order>(`${this.usersUrl}/${userName}`, this.httpOptions).pipe();
  }
}
