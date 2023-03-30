import {Injectable} from '@angular/core';
import {Product} from "../models/product";
import {orderItem} from "../models/orderItem";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  items: Product[] = [];
  cartItem: orderItem[] = [];
  constructor() {
  }

  addToCart(product: Product) {
    this.cartItem.push(new orderItem(product, 1)) ;
    localStorage.setItem('products', JSON.stringify(this.cartItem));

  }

  getItems() {
    let obj = localStorage.getItem('products');
    if (obj != null) {
      return JSON.parse(obj);
    }


  }

  clearCart() {
    localStorage.removeItem("products")

  }

}
