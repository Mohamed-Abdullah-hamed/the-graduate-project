package com.main.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.main.dto.UpdateUserDto;
import com.main.entity.AppUser;
import com.main.entity.CartItem;
import com.main.entity.CartItemId;
import com.main.entity.Customer;
import com.main.entity.Product;
import com.main.entity.Role;
import com.main.entity.Seller;
import com.main.entity.ShoppingCart;
import com.main.service.AppUserService;
import com.main.service.ProductService;
import com.main.service.ShoppingCartService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private AppUserService userService;
	@Autowired
	private ShoppingCartService cartService;
	private static Map<String, String> categories = new LinkedHashMap<>();
	static {

		categories.put("book", "Books");
		categories.put("mobile", "Mobiles");
		categories.put("laptop", "Laptops");
		categories.put("other", "Others");
	}

	@GetMapping
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/all")
	public String allProducts(Model model, Principal principal) {
		List<Product> products = service.allProducts();
		model.addAttribute("categories", categories.entrySet());
		model.addAttribute("products", products);
		model.addAttribute("user", getUserName(principal));
		model.addAttribute("cart", getCartInfo(principal));
		return "home";
	}

	@GetMapping("/id/{id}")
	public String findById(@PathVariable("id") long id, Model model, Principal principal) {

		model.addAttribute("user", getUserName(principal));
		Product pro = service.findById(id);
		model.addAttribute("product", pro);
		ShoppingCart cart = getCartInfo(principal);
		CartItem item = new CartItem();
		if (principal != null) {
			model.addAttribute("cart", cart);
			CartItemId cartItemId = new CartItemId(id, cart.getId());
			item.setCartItemId(cartItemId);
		}
		item.setProduct(pro);
		model.addAttribute("item", item);
		return "product-spec";
	}

	@GetMapping("/category/{categ}")
	public String productsByCategory(@PathVariable("categ") String categ, Model model, Principal principal) {
		model.addAttribute("user", getUserName(principal));
		List<Product> products = service.findByCategory(categ);
		model.addAttribute("categories", categories.entrySet());
		model.addAttribute("products", products);
		model.addAttribute("cart", getCartInfo(principal));
		return "home";
	}

	@GetMapping("/name/{name}")
	public String productsByName(@PathVariable("name") String name, Model model, Principal principal) {

		model.addAttribute("user", getUserName(principal));
		List<Product> products = service.findByName(name);
		model.addAttribute("categories", categories.entrySet());
		model.addAttribute("products", products);
		model.addAttribute("cart", getCartInfo(principal));
		return "home";
	}

	@GetMapping("/add")
	public String addProduct(Model model, Principal principal) {
		Product product = new Product();
		AppUser user = getUser(principal);
		if (!user.getRoles().contains(new Role("ROLE_ADMIN"))) {
			Seller seller = (Seller) user.getCust();
			product.setSeller(seller);
		}
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		return "add-product";
	}

	@GetMapping("/update/{id}")
	public String updateProduct(@PathVariable("id") long id, Model model, Principal pricipal) {
		Product product = service.findById(id);
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);

		return "update-product";
	}

	@PostMapping("/update/validate")
	public ModelAndView updateProductValidation(@Valid @ModelAttribute("product") Product product, BindingResult rs,
			HttpServletRequest r, ModelMap model, Principal pricipal) {
		ModelAndView mAv = null;
		if (rs.hasErrors()) {
			mAv = new ModelAndView("update-product", rs.getModel());
			return mAv;
		}

		Part file = null;
		try {
			file = r.getPart("file");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		service.update(product, file);
		AppUser user = getUser(pricipal);
		if (!user.getRoles().contains(new Role("ROLE_ADMIN"))) {
			UpdateUserDto userDto = new UpdateUserDto();
			Seller seller = (Seller) user.getCust();
			model.addAttribute("cart", seller.getCart());
			model.addAttribute("cust", seller);
			model.addAttribute("items", seller.getProducts());
			model.addAttribute("isValidInfo", true);
			model.addAttribute("userDto", userDto);
			return mAv = new ModelAndView("redirect:/profile#", model);
		}
		mAv = new ModelAndView("redirect:/products/all", model);
		return mAv;
	}

	@PostMapping("/validate")
	public ModelAndView validateProduct(@Valid @ModelAttribute("product") Product product, BindingResult rs,
			HttpServletRequest r, ModelMap model, Principal principal) {
		ModelAndView mAv = null;
		if (rs.hasErrors()) {
			mAv = new ModelAndView("add-product", rs.getModel());
			mAv.addObject("categories", categories);
			return mAv;
		}

		Part file = null;
		try {
			file = r.getPart("file");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		service.insert(product, file);
		AppUser user = getUser(principal);
		if (!user.getRoles().contains(new Role("ROLE_ADMIN"))) {
			UpdateUserDto userDto = new UpdateUserDto();
			Seller seller = (Seller) user.getCust();
			model.addAttribute("cart", seller.getCart());
			model.addAttribute("cust", seller);
			model.addAttribute("items", seller.getProducts());
			model.addAttribute("isValidInfo", true);
			model.addAttribute("userDto", userDto);
			model.addAttribute("prods", "#prods");
			mAv = new ModelAndView("redirect:/profile#", model);
			return mAv;
		}
		mAv = new ModelAndView("redirect:/products/all", model);
		return mAv;
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") long id) {
		service.deleteById(id);
		return "redirect:/products/all";
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

	private AppUser getUser(Principal principal) {
		AppUser user = null;
		if (principal != null) {
			user = userService.findByEmail(principal.getName());
			System.err.println(user);
		}
		return user;
	}
}
