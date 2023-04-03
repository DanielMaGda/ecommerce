package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.exceptions.ResourceNotFoundException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.dto.AccountLoginDTO;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

// TODO Customer Service interacting user order History /sending email confirmation when new acc
@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Account getByUserName(String userName) {
        Optional<Account> customers = accountsRepository.findByUserName(userName);
        if (customers.isPresent()) {
            return customers.get();
        }
        throw new NoSuchElementException("User with " + userName + " not present");

    }

    public Account getUser() throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }

        Optional<Account> account = accountsRepository.findByUserName(userName);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return account.get();
    }

    public List<Account> getAll() {
        return accountsRepository.findAll();
    }

    public void deleteUser(long id) {
        Account account = accountsRepository.findById(id).orElse(null);
        if (account != null) {
            accountsRepository.delete(account);
        } else {
            throw new NoSuchElementException("User with id" + id + " not present");

        }

    }

    public AccountLoginDTO fetchUser() throws AccessDeniedException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("invalid Acces ");
        }
        Optional<Account> accounts = accountsRepository.findByUserName(userName);
        if (Objects.isNull(accounts)) {
            throw new ResourceNotFoundException("Account not found.");

        }

        return accounts.map(account -> modelMapper.map(account, AccountLoginDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

    }

}

