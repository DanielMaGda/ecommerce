import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Product} from "../models/product";
import {Observable} from "rxjs";
import {orderItem} from "../models/orderItem";
import {ProductOrders} from "../models/productOrders";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  productOrder: orderItem;
  orders: ProductOrders = new ProductOrders();

  private serviceUrl = 'http://localhost:8082/api/product';
  product: Product[]
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };


  constructor(private http: HttpClient) {
  }

  public getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.serviceUrl, this.httpOptions).pipe();
  }

  public updateProduct(Product: any, id: number): Observable<any> {
    console.log("xd");
    return this.http.patch(`${this.serviceUrl}/update/${id}`, Product).pipe();
  }

  public deleteProduct(id: number): Observable<any> {
    console.log("xd");
    return this.http.delete(`${this.serviceUrl}/${id}`);

  }


  public addProduct(Product: any): Observable<Product> {
    return this.http.post<Product>(`${this.serviceUrl}`, Product, this.httpOptions).pipe();

  }
}
