import {Injectable} from '@angular/core';
import {orderItem} from "../models/orderItem";
import {ProductOrders} from "../models/productOrders";
import {Observable} from "rxjs";
import {Product} from "../models/product";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Category} from "../models/category";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  productOrder: orderItem;
  orders: ProductOrders = new ProductOrders();


  private serviceUrl = 'http://localhost:8082/api/category';
  product: Product[]
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };


  constructor(private http: HttpClient) {
  }

  public getCategory(): Observable<Category[]> {
    return this.http.get<Category[]>(this.serviceUrl, this.httpOptions).pipe()
  }

}
