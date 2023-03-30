package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.repository.UserRoleRepository;
import com.danmag.ecommerce.service.service.AccountsService;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/user")
@RestController
public class AccountsController {
    private final AccountsService accountsService;
    AccountsRepository accountsRepository;
    UserRoleRepository userRoleRepository;

    public AccountsController(AccountsService accountsService, AccountsRepository accountsRepository, UserRoleRepository userRoleRepository) {
        this.accountsService = accountsService;
        this.accountsRepository = accountsRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @GetMapping("/")
    public List<Account> getUsers() {
        return accountsService.getAll();
    }


    @GetMapping(value = "/{userName}")
    public ResponseEntity<Object> getById(@PathVariable String userName) {
        try {
            var customer = accountsService.fetchUser();
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") long id) {
        try {
            accountsService.deleteUser(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
        }


    }
}


