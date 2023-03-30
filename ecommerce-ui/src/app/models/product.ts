import {Category} from "./category";

export class Product {
  id: number;
  name: string;
  category: Category;
  manufacturer: string;
  price: number;

  constructor(id: number, name: string, price: number,category :Category) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.category = category;

  }
}
