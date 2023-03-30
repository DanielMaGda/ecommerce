import {User} from "./user";
import {orderItem} from "./orderItem";

export class Order {
  id:number;
  status: string;
  customers : User;
  orderItems : orderItem[];

  constructor(status : string, customers: User,orderItems: orderItem[] ) {
    this.status = status;
    this.customers = customers;
    this.orderItems = orderItems;
  }
}
