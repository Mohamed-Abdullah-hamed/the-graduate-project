package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.CartItem;
import com.main.entity.CartItemId;
import com.main.entity.Product;
import com.main.entity.ShoppingCart;
import com.main.repo.CartItemRepo;

@Service
public class CartItemService {

	@Autowired
	private CartItemRepo repo;
	@Autowired
	private ProductService proService;
	@Autowired
	private ShoppingCartService cartService;

	public CartItem findById(CartItemId id) {
		return repo.findById(id).orElse(null);
	}

	public List<CartItem> findAll() {
		return repo.findAll();
	}

	public void deleteById(CartItemId id) {
		repo.deleteById(id);
	}

	public CartItem findByCartIdAndProductId(long cartId,long productId){
		return repo.findByCartIdAndProductId(cartId, productId);
	}
	public CartItem insert(CartItem p) {
		return repo.save(p);
	}

	public CartItem update(CartItem p) {
		return repo.save(p);
	}

	public CartItem getByProdId(long prodId, long cartId) {
		Product pro = proService.findById(prodId);
		ShoppingCart cart = cartService.findById(cartId);

		CartItemId cartItemId = new CartItemId(pro.getId(), cart.getId());
		CartItem item = findById(cartItemId);
		return item;
	}

}
