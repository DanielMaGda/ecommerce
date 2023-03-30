import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {OrdersService} from "../services/orders.service";
import {Order} from "../models/order";
import {orderItem} from "../models/orderItem";


@Component({
  selector: 'app-user-orders-dialog',
  templateUrl: './user-orders-dialog.component.html',
  styleUrls: ['./user-orders-dialog.component.sass']
})
export class UserOrdersDialogComponent implements OnInit {
  orders: Order;
  orderItems: orderItem;
  dataSource: any;
  errorApi = false;
  displayedColumns: string[] = ['id', 'name', 'price', 'quantity'
  ];

  constructor(@Inject(MAT_DIALOG_DATA) public data: number, private ordersService: OrdersService) {
  }

  ngOnInit() {

    this.showUserOrders();
  }

  showUserOrders() {
    this.ordersService.getOrderByUserId(this.data)
      .subscribe(
        (order: any) => {
          this.orders = order;
          this.dataSource = order.orderItems;
        },

        (error) => {
          this.errorApi = true;
          console.log(error);

        })

  }
}
