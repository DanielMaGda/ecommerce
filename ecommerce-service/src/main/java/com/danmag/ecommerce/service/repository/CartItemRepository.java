package com.danmag.ecommerce.service.repository;

import com.danmag.ecommerce.service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
