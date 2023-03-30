import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {OrdersService} from "../services/orders.service";
import {Order} from "../models/order";
import {orderItem} from "../models/orderItem";
import {AuthService} from "../services/auth.service";
import {User} from "../models/user";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.sass']
})
export class OrdersComponent implements OnInit {
  order: Order;
  user: User;

  constructor(@Inject(MAT_DIALOG_DATA) public data: orderItem[], private  ordersService: OrdersService, private authService: AuthService) {
  }

  ngOnInit() {

  }

  MakeOrder(data: orderItem[]) {
    this.order = new Order("pending", this.user, data)
    this.ordersService.makeOrder(this.order).subscribe()
    location.reload();
  }
}
