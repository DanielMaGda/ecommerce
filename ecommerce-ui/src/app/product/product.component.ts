import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product-service";
import {Product} from "../models/product";
import {orderItem} from "../models/orderItem";
import {CartService} from "../cart/cart.service";
import {AuthService} from "../services/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {EditProductDialogComponent} from "./edit-product-dialog/edit-product-dialog.component";
import {Category} from "../models/category";
import {CategoryService} from "../services/category.service";
import {AddProductDialogComponent} from "./add-product-dialog/add-product-dialog.component";

export interface Links {
  id: number;
  name: string;
}

const Links: Links[] = [
  {
    id: 1,
    name: "cpu"
  },
  {
    id: 2,
    name: "gpu"
  },
  {
    id: 3,
    name: "hard_disc"
  },
  {
    id: 4,
    name: "mother_board"
  },
  {
    id: 5,
    name: "ram"
  }

]

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.sass']
})
export class ProductComponent implements OnInit {
  isUserAdmin: boolean;
  productOrders: orderItem[] = [];
  products: Product[] = [];
  product: Product;
  clickMessage: Product;
  term = '';
  category: Category[] = [];
  Links = Links;

  constructor(private productService: ProductService, private cartService: CartService,
              private authService: AuthService, private snackbar: MatSnackBar, private dialog: MatDialog
    , private categoryService: CategoryService
  ) {


  }

  ngOnInit() {
    this.isUserAdmin = this.authService.isUserAdmin();
    this.productOrders = [];
    this.loadProducts();
    this.loadCategory();
  }


  loadProducts() {
    this.productService.getAllProducts()
      .subscribe(
        (products: any[]) => {
          this.products = products;
          this.products.forEach(product => {
            this.productOrders.push(new orderItem(product, 1));
          })
        },
        (error) => console.log(error)
      );
  }

  loadCategory() {
    this.categoryService.getCategory()
      .subscribe(
        (category: any[]) => {
          this.category = category;
          console.log(this.category)
        },


        (error) => console.log(error)
      );
  }

  addToCart(product: Product) {
    this.snackbar.open("Product has been added to cart")._dismissAfter(5000);
    this.cartService.addToCart(product);

  }

  EditProduct(product: Product, id: number) {
    this.dialog.open(EditProductDialogComponent, {
      data: {
        name: product.name,
        manufacturer: product.manufacturer,
        price: product.price

      }
    });

  }

  DeleteProduct(product: number) {
    this.snackbar.open("Product has been deleted")._dismissAfter(3000);
    this.productService.deleteProduct(product).subscribe();
    window.setTimeout(function () {
      location.reload()
    }, 3000)

  }

  ChooseByCategory(category: any) {
    this.term = category;
  }

  ProductDialog() {
    this.dialog.open(AddProductDialogComponent);

  }
}


