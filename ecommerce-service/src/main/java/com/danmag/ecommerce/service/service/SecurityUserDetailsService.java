package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.security.CustomerDetails;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findByUserName(username).
                orElseThrow(() -> new UsernameNotFoundException("User not present"));

        return new CustomerDetails(account);
    }

    public void createUser(UserDetails user) {
        accountsRepository.save((Account) user);
    }
}
