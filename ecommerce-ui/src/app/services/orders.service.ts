import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../models/order";

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  private serviceUrl = 'http://localhost:8082/api/order/';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  public getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.serviceUrl, this.httpOptions).pipe();
  }

  public makeOrder(data: any): Observable<Order> {
    return this.http.post<Order>(this.serviceUrl,data,this.httpOptions).pipe();
  }

  public getOrderByUserId(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.serviceUrl}${id}`, this.httpOptions).pipe();
  }
}
