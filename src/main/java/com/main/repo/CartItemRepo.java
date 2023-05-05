package com.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.CartItem;
import com.main.entity.CartItemId;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,CartItemId>{

	CartItem findByCartIdAndProductId(long cartId,long productId);
}
