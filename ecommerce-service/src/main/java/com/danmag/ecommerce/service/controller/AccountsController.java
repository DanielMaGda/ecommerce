package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.response.AccountResponse;
import com.danmag.ecommerce.service.exceptions.AccountNotFoundException;
import com.danmag.ecommerce.service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/accounts")
@RestController
public class AccountsController {
    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        try {
            List<AccountResponse> response = accountService.getAllAccounts();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable("id") long id) {
        try {
            AccountResponse response = accountService.getAccountById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable("id") long id) {
        try {
            accountService.deleteAccount(id);

            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User was deleted", null));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }


}


