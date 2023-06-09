package com.danmag.ecommerce.service.repository;

import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    Optional<Cart> findByAccountId(long id);

    Optional<Cart> findByAccount(Account account);

}
