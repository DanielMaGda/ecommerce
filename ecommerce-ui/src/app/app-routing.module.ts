import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductComponent} from "./product/product.component";
import {AuthGuard} from "./guards/auth.guard";
import {CartComponent} from "./cart/cart.component";
import {LoginComponent} from './login/login.component';
import {OrdersComponent} from "./orders/orders.component";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  {path: 'product', component: ProductComponent, canActivate: [AuthGuard]},
  {path: 'order', component: OrdersComponent, canActivate: [AuthGuard]},
  {path: 'cart', component: CartComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  },
  {path: '', component: LoginComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
