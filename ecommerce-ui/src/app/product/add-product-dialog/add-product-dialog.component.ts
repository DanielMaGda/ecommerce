import {Component, OnInit} from '@angular/core';
import {Product} from "../../models/product";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../models/category";
import {ProductService} from "../../services/product-service";


@Component({
  selector: 'app-add-product-dialog',
  templateUrl: './add-product-dialog.component.html',
  styleUrls: ['./add-product-dialog.component.sass']
})
export class AddProductDialogComponent implements OnInit {

  product: Product;
  category: Category;
  SelectedCat: string;

  constructor(private categoryService: CategoryService, private productService: ProductService) {

  }

  ngOnInit() {


  }

  // AddProduct(product: Product) {
  //   this.category = new Category();
  //   this.product = new Product(product.id, product.name, product.price, this.category)
  //   console.log(this.product)
  //   this.productService.addProduct(this.product).subscribe();
  // }

}
