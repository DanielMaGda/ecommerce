package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.service.AccountsService;
import com.danmag.ecommerce.service.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class HomeController {

    private final AccountsService accountsService;

    public HomeController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/login")
    public void login() {

    }

    @GetMapping("/admin/login")
    public void adminLogin() {
    }

    @PostMapping("/register")
    //TODO make dto for register login ?
    public ResponseEntity<ApiResponse> addUser(@RequestBody Account user) {

        try {
            accountsService.addUser(user);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
        }

    }


}
