import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user-service";
import {User} from "../../models/user";
import {OrdersService} from "../../services/orders.service";
import {UserOrdersDialogComponent} from "../../user-orders-dialog/user-orders-dialog.component";
import {MatDialog} from "@angular/material/dialog";


@Component({
    selector: 'app-user-management',
    templateUrl: './user-management.component.html',
    styleUrls: ['./user-management.component.sass']
})

export class UserManagementComponent implements OnInit {
    users: User [] = [];
    user: User;
    displayedColumns: string[] = ['id', 'name', 'email', 'actions'];
    userForm: boolean;
    private _id: any;

    constructor(private userService: UserService, private ordersService: OrdersService, private dialog: MatDialog) {
    }

    ngOnInit() {

        this.loadUsers();

    }

    loadUsers() {
        this.userService.findAll()
            .subscribe(
                (users: any[]) => {
                    this.users = users;

                },
                (error) => console.log(error)
            );
    }

    deleteUser(id: number) {
        this.userService.deleteUser(id).subscribe();
        location.reload();
    }

    userOrders(id: any) {
        this.dialog.open(UserOrdersDialogComponent, {
            data: id,
            width: '80%',
            height: '80%'

        })

    }
}

