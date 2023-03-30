import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {OrdersComponent} from './orders/orders.component';
import {CartComponent} from './cart/cart.component';
import {ProductComponent} from './product/product.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import {LoginComponent} from './login/login.component';
import {InterceptorService} from "./interceptors/InterceptorService";
import {SharedModule} from "./shared/shared.module";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatDialogModule} from "@angular/material/dialog";
import {EditProductDialogComponent} from './product/edit-product-dialog/edit-product-dialog.component';
import {RegisterComponent} from './register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { UserOrdersDialogComponent } from './user-orders-dialog/user-orders-dialog.component';
import { AddProductDialogComponent } from './product/add-product-dialog/add-product-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    OrdersComponent,
    CartComponent,
    ProductComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    EditProductDialogComponent,
    RegisterComponent,
    UserOrdersDialogComponent,
    AddProductDialogComponent,

  ],
  imports: [
    MatSnackBarModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    Ng2SearchPipeModule,
    SharedModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,


  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: InterceptorService,
    multi: true
  }],
  exports: [
    // MainMenuComponent,

  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
