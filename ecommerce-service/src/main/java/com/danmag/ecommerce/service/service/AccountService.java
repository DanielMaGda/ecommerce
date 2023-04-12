package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.dto.response.AccountResponse;
import com.danmag.ecommerce.service.exceptions.AccountNotFoundException;
import com.danmag.ecommerce.service.exceptions.InvalidArgumentException;
import com.danmag.ecommerce.service.exceptions.UserAccountNotFoundException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountsRepository accountsRepository;
    private final ModelMapper modelMapper;


    public AccountService(AccountsRepository accountsRepository, ModelMapper modelMapper) {
        this.accountsRepository = accountsRepository;
        this.modelMapper = modelMapper;
    }

    public List<AccountResponse> getAllAccounts() {
        List<Account> accountList = accountsRepository.findAll();
        return accountList.stream().map(account -> modelMapper.map(account, AccountResponse.class)).toList();


    }

    public AccountResponse getAccountById(long id) {
        Optional<Account> account = accountsRepository.findById(id);
        if (account.isPresent()) {
            return modelMapper.map(account.get(), AccountResponse.class);
        } else {
            throw new AccountNotFoundException("User with " + id + " not present");

        }


    }

    public Account getCurrentUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserAccountNotFoundException("User not authenticated");
        }

        String username = authentication.getName();
        if (Objects.isNull(username)) {
            throw new UserAccountNotFoundException("Username not found in authentication object");
        }

        return accountsRepository.findByUserName(username).orElseThrow(() -> new UserAccountNotFoundException("User account not found"));
    }


    public void deleteAccount(long id) {
        Optional<Account> account = accountsRepository.findById(id);
        if (account.isPresent()) {
            accountsRepository.delete(account.get());
        } else {
            throw new AccountNotFoundException("User with id " + id + " not present");
        }

    }


    public Account saveAccount(Account account) {
        if (Objects.isNull(account)) {
            throw new InvalidArgumentException("Null user");

        }

        return accountsRepository.save(account);

    }
}


