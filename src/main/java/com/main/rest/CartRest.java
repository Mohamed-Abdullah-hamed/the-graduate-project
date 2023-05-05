package com.main.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.AppUser;
import com.main.entity.CartItem;
import com.main.entity.Customer;
import com.main.entity.ShoppingCart;
import com.main.service.AppUserService;
import com.main.service.CustService;
import com.main.service.ShoppingCartService;

@RestController
@RequestMapping("/rest/api/cart")
public class CartRest {
	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private CustService custService;
	@Autowired
	private AppUserService userService;

	@GetMapping("/checkout")
	public ResponseEntity<?> checkOut(@RequestParam(value = "chekedItems", required = false) String[] items) {

		return ResponseEntity.ok(items);
	}

	@PostMapping("/update")
	public ShoppingCart updateCartItemOfCart(@RequestBody CartItem item, Principal principal) {
		System.out.println("Cart Item .... " + item.toString());

		Customer cust = null;
		if (principal != null) {
			AppUser user = userService.findByEmail(principal.getName());
			cust = user.getCust();
			ShoppingCart cart = cartService.updateCartItem(cust, item);
			return cart;
		}

		System.out.println("Cart Item .... " + item.toString());
		return null;

	}

	@DeleteMapping
	public ShoppingCart removeCartItemFromCart(@RequestBody CartItem item, Principal principal) {
		System.out.println("Cart Item .... " + item.toString());

		Customer cust = null;
		if (principal != null) {
			AppUser user = userService.findByEmail(principal.getName());
			cust = user.getCust();
			ShoppingCart cart = cartService.removeCartItem(cust, item);
			return cart;
		}

		System.out.println("Cart Item .... " + item.toString());
		return null;

	}

}
