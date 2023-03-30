import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Product} from "../../models/product";
import {ProductService} from "../../services/product-service";

@Component({
  selector: 'app-edit-product-dialog',
  templateUrl: './edit-product-dialog.component.html',
  styleUrls: ['./edit-product-dialog.component.sass']
})
export class EditProductDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: Product, private productService: ProductService) {
  }

  ngOnInit() {
  }

  EditProduct(data: Product) {
    this.productService.updateProduct(data,1).subscribe();
    location.reload();

  }
}
