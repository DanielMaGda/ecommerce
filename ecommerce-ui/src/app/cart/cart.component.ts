import {Component, OnInit,} from '@angular/core';
import {CartService} from "./cart.service";
import {Product} from "../models/product";
import {OrdersComponent} from "../orders/orders.component";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {orderItem} from "../models/orderItem";


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.sass']
})
export class CartComponent implements OnInit {
  product: Product[];
  displayedColumns: string[] = ['id', 'name', 'category', 'price'
  ];
  cart: orderItem[] = [];
  dataSource: any;

  constructor(private cartService: CartService, private dialog: MatDialog, private snackBar: MatSnackBar) {
  }

  ngOnInit() {

    this.getCart();
  }

  getCart() {
    this.dataSource = this.cartService.getItems();
    this.cart = this.cartService.getItems();
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product);
  }


  clearCart() {
    this.cartService.clearCart();
    this.snackBar.open("Cart has been cleaned")._dismissAfter(3000);
    window.setTimeout(function () {
      location.reload()
    }, 3000)
  }

  makeOrder(cart: Product[]) {
    this.dialog.open(OrdersComponent, {
      data: cart
    })
  }
}
