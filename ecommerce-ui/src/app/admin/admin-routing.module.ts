import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminLoginComponent} from "./admin-login/admin-login.component";
import {AdminComponent} from "./admin.component";
import {UserManagementComponent} from "./user-management/user-management.component";
import {RoleGuard} from "../guards/role.guard";

const AdminRoutes: Routes = [
  {path: 'login', component: AdminLoginComponent},
  {
    path: 'users', component: UserManagementComponent, canActivate: [RoleGuard]
  },

  {path: '', component: AdminComponent, canActivate: [RoleGuard]},

];

@NgModule({
  imports: [RouterModule.forChild(AdminRoutes)],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule {
}

