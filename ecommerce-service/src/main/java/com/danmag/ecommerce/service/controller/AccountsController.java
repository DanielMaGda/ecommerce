package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.response.AccountResponse;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.UserRoleRepository;
import com.danmag.ecommerce.service.service.AccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("api/v1/user")
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

    // TODO add Some Else if user not found etc etc for all controllers
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        try {
            List<AccountResponse> response = accountsService.getAllAccounts();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable("id") long id) {
        try {
            AccountResponse response = accountsService.getAccountById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable("id") long id) {
        try {
            accountsService.deleteAccount(id);

            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User was deleted", null));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));

        }
    }


}


