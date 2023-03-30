package com.danmag.ecommerce.service.service;


import com.danmag.ecommerce.service.exceptions.ResourceNotFoundException;
import com.danmag.ecommerce.service.exceptions.UserExistException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.dto.AccountLoginDTO;
import com.danmag.ecommerce.service.model.UserRole;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

// TODO Customer Service interacting user order History /sending email confirmation when new acc
@Service
public class AccountsService {
    private final PasswordEncoder passwordEncoder;
    private final AccountsRepository accountsRepository;
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AccountsService(PasswordEncoder passwordEncoder, AccountsRepository accountsRepository, UserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountsRepository = accountsRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public void addUser(Account user) throws UserExistException {
        //TODO Change this with Optional or smth
        if (accountsRepository.existsByUserName(user.getUserName())) {
            throw new UserExistException("Something went wrong");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserRole roles = userRoleRepository.findByName("USER").orElse(null);
        if (roles != null) {
            user.setRoles(Collections.singleton(roles));
            accountsRepository.save(user);
            return;
        }
        throw new NoSuchElementException("Role not present");


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

        Optional<Account> user = accountsRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return user.get();
    }

    public List<Account> getAll() {
        return accountsRepository.findAll();
    }

    public void deleteUser(long id) {
        Account account = accountsRepository.findById(id).orElse(null);
        if (account != null) {
            accountsRepository.delete(account);
        } else {
            throw new NoSuchElementException("User with " + id + " not present");

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

