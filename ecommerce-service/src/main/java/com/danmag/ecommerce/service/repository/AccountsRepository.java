package com.danmag.ecommerce.service.repository;

import com.danmag.ecommerce.service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserName(String userName);

    Boolean existsByUserName(String username);
}
