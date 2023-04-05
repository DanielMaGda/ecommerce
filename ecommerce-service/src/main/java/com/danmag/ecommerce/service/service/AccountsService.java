package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.dto.response.AccountResponse;
import com.danmag.ecommerce.service.exceptions.InvalidArgumentException;
import com.danmag.ecommerce.service.exceptions.ResourceNotFoundException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
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
            throw new NoSuchElementException("User with " + id + " not present");

        }


    }

    public Optional<Account> getAccount() throws AccessDeniedException {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }

        Optional<Account> account = accountsRepository.findByUserName(userName);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return account;
    }


    public void deleteAccount(long id) {
        Optional<Account> account = accountsRepository.findById(id);
        if (account.isEmpty()) {
            throw new NoSuchElementException("User with id " + id + " not present");

        } else {
            accountsRepository.delete(account.get());

        }

    }

//    public Optional<AccountResponse> fetchUser(long id) {
//        Optional<Account> optionalAccount = accountsRepository.findById(id);
//        return optionalAccount.map(account -> modelMapper.map(account, AccountResponse.class));
//    }


    public Account saveAccount(Optional<Account> account) {
        if (Objects.isNull(account)) {
            throw new InvalidArgumentException("Null user");

        }
        if (account.isPresent()) {
            return accountsRepository.save(account.get());

        }
        throw new NoSuchElementException("account not present");
    }

}

