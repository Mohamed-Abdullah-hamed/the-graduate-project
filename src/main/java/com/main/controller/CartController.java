package com.main.controller;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.entity.AppUser;
import com.main.entity.CartItem;
import com.main.entity.Customer;
import com.main.entity.Product;
import com.main.entity.ShoppingCart;
import com.main.service.AppUserService;
import com.main.service.CustService;
import com.main.service.ShoppingCartService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/cart")
@Log4j2
public class CartController {

	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private CustService custService;
	@Autowired
	private AppUserService userService;

	@GetMapping
	public String cartInfo(Principal principal, Model model) {
		if (principal == null) {
			return "cart-info";
		}
		model.addAttribute("user", getUserName(principal));
		ShoppingCart cart = getCartInfo(principal);
		ShoppingCart checkedCart = new ShoppingCart();
		model.addAttribute("cart", cart);
		Set<CartItem> items = cart.getItems();

		model.addAttribute("items", items);
		model.addAttribute("checkedCart", checkedCart);
		return "cart-info";
	}

	@PostMapping("/checkout")
	public String checkOut(@RequestParam(value = "chekedItems", required = false) CartItem[] items) {

		print(items);
		return "cart-info";
	}

	@PostMapping("/addItem")
	public String addCartItemToCart(HttpServletRequest request, @Valid @ModelAttribute("item") CartItem item,
			BindingResult rs, Principal principal, Model model) {

		Customer cust = null;
		AppUser user = null;
		if (principal != null) {
			Long prodId = Long.parseLong(request.getParameter("prodId").trim());

			user = userService.findByEmail(principal.getName());
			cust = user.getCust();
			ShoppingCart cart = cartService.addCartItemToCart(cust, item, prodId);
			Product product = item.getProduct();
			model.addAttribute("product", product);
			log.info("product => " + product);
			model.addAttribute("item", item);
			log.info("item => " + item);
			model.addAttribute("cart", cart);
			log.info("cart => " + cart);
			model.addAttribute("user", getUserName(principal));
		}
		return "product-spec";
	}

	private String getUserName(Principal principal) {
		String user = "UN";
		System.out.println(principal);
		if (principal != null) {
			user = principal.getName().substring(0, 2);
		}
		return user;
	}

	private ShoppingCart getCartInfo(Principal principal) {
		ShoppingCart cart = null;
		if (principal != null) {
			AppUser user = userService.findByEmail(principal.getName());
			Customer cust = user.getCust();
			cart = cust.getCart();

			if (cart == null) {
				cart = cartService.addCartToUser(cust);
			}
		}
		return cart;

	}

	private void print(CartItem[] items) {
		for (CartItem item : items) {
			log.info(item);
		}
	}

}
