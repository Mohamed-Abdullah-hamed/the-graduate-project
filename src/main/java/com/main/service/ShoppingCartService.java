package com.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Customer;
import com.main.entity.CartItem;
import com.main.entity.Product;
import com.main.entity.ShoppingCart;
import com.main.repo.ShoppingCartRepo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ShoppingCartService{

	@Autowired
	private ShoppingCartRepo repo;
	@Autowired
	private CustService custService;
	@Autowired
	private CartItemService itemService;
	@Autowired
	private ProductService prodService;
	
	
	public ShoppingCart findById(long id) {
		return repo.findById(id).get();
	}

	public List<ShoppingCart> findAll() {
		return repo.findAll();
	}
	@Transactional
	public ShoppingCart updateCartItem(Customer cust,CartItem item) {
		ShoppingCart cart = cust.getCart();
		item.setCart(cart);
		int count = item.getItemCount();
		item = itemService.findById(item.getCartItemId());
		item.setItemCount(count);	
		return cart;
	}
	
	@Transactional
	public ShoppingCart removeCartItem(Customer cust,CartItem item) {
		ShoppingCart cart = cust.getCart();
		item.setCart(cart);
		item = itemService.findByCartIdAndProductId(cart.getId(),item.getProduct().getId());
		itemService.deleteById(item.getCartItemId());
		cart.removeItem(item);
		return cart;
	}
	@Transactional
	public ShoppingCart addCartItemToCart(Customer cust,CartItem item,long prodId) {
		ShoppingCart cart = cust.getCart();
		Product prod = prodService.findById(prodId);
		if(cart == null) {
			cart = new ShoppingCart();
			cart = repo.save(cart);
			cust.setCart(cart);
		}
		item.setCart(cart);
		item.setProduct(prod);
		log.info("product Id ->"+prod.getId());
		log.info("cart Id ->"+cart.getId());
		if(itemService.findByCartIdAndProductId(cart.getId(),prod.getId())!= null) {
			return updateCartItem(cust, item);
		}
		item = itemService.insert(item);
		cart.addItem(item);
		cart = insert(cart);
		custService.update(cust);	
		return cart;
	}
	@Transactional
	public ShoppingCart addCartToUser(Customer cust) {
		ShoppingCart cart = new ShoppingCart();
		cart =insert(cart);
		cust.setCart(cart);
		custService.update(cust);
		return cart;
	}
	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public Map<Product,Integer> getCartProducts(ShoppingCart cart) {
		Map<Product,Integer> prods = new HashMap<>();
		Set<CartItem> items = cart.getItems();
		items.forEach((pro)->{
			prods.put(pro.getProduct(),pro.getItemCount());
		});
		
		return prods;
		
	}
	public ShoppingCart insert(ShoppingCart p) {
		return repo.save(p);
	}

	public ShoppingCart update(ShoppingCart p) {
		return repo.save(p);
	}

	
	

}
