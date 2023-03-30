import {NgModule} from '@angular/core';
import {AdminRoutingModule} from "./admin-routing.module";
import {AdminLoginComponent} from "./admin-login/admin-login.component";
import {SharedModule} from "../shared/shared.module";
import {AdminComponent} from "./admin.component";
import {UserManagementComponent} from './user-management/user-management.component';
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    AdminLoginComponent,
    AdminComponent,
    UserManagementComponent,
  ],
  imports: [
    AdminRoutingModule,
    SharedModule,
    CommonModule


  ],
  bootstrap: [AdminComponent]
})
export class AdminModule {
}
