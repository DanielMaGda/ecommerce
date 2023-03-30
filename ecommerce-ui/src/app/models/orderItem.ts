import {Product} from "./product";

export class orderItem {
  id: number;
  product: Product;
  quantity: number;

  constructor(product: Product, quantity: number) {
    this.product = product;
    this.quantity = quantity;

  }
}
