package com.main.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.dto.UpdateUserDto;
import com.main.entity.AppUser;
import com.main.entity.Customer;
import com.main.entity.Role;
import com.main.entity.Seller;
import com.main.service.AppUserService;
import com.main.service.CustService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
public class UserController {

	@Autowired
	private CustService service;

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AppUserService userService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/*
	 * @PostMapping("/validateUser") public String
	 * validateUser(@ModelAttribute("user") AppUser user, BindingResult rs) { return
	 * "redirect:/products/add"; }
	 */
	@GetMapping("/signUp")
	public String signUp(Model model) {
		Customer cust = new Customer();
		model.addAttribute("cust", cust);
		return "sign-up";
	}

	@PostMapping("/validate")
	public String processSignUp(@Valid @ModelAttribute("cust") Customer cust, BindingResult rs,Model model) {
		if(rs.hasErrors()) {
			return "sign-up";
		}
		cust.getUser().setPassword(encoder.encode(cust.getUser().getPassword()));
		service.insert(cust);
		return "redirect-to-login";
	}
	
	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		
		Customer cust =null;
		AppUser user = getUser(principal);
		log.error("user Roles ->"+principal);
		if(user.getRoles().contains(new Role("ROLE_SELLER"))) {
			log.error("After ----  user Roles ->"+user.getRoles());
			Seller seller = (Seller) user.getCust();
			model.addAttribute("cart", seller.getCart());
			model.addAttribute("cust", seller);
			model.addAttribute("items", seller.getProducts());
		}
		else {
			cust = user.getCust();
			model.addAttribute("cart", cust.getCart());
			model.addAttribute("cust", cust);
		}
		UpdateUserDto userDto = new UpdateUserDto();
		model.addAttribute("isValidInfo",true);
		model.addAttribute("userDto", userDto);
		return "profile";
	}

	private AppUser getUser(Principal principal) {
		AppUser user =null;
		if (principal != null) {
			user = userService.findByEmail(principal.getName());
			System.err.println(user);
		}
		return user;
	}

	@PostMapping("/update")
	public String updateUser(@Valid @ModelAttribute("cust") Customer cust, BindingResult rs, Model model,
			Principal principal) {
		model.addAttribute("isValidInfo",true);
		if (rs.hasErrors()) {
			log.error(cust);
			UpdateUserDto userDto = new UpdateUserDto();
			AppUser user = getUser(principal);
			Customer errordCust = user.getCust();
			model.addAttribute("cart", errordCust.getCart());
	//		user.setEmail(errordUser.getUser().getEmail());
			model.addAttribute("cust", cust);
			model.addAttribute("userDto", userDto);
			return "profile";
		}

		
		
		Customer newCust = service.update(cust);
		UpdateUserDto userDto = new UpdateUserDto();
		model.addAttribute("cart", newCust.getCart());

		model.addAttribute("cust", newCust);
		model.addAttribute("userDto", userDto);

		return "profile";
	}

	@PostMapping("/changeInfo")
	public String changeInfo(@Valid @ModelAttribute("userDto") UpdateUserDto userDto, BindingResult rs,
			 Model model, Principal principal) {
		 AppUser user = getUser(principal);
		Customer cust = user.getCust();
		model.addAttribute("cart", cust.getCart());
		model.addAttribute("cust", cust);
		model.addAttribute("userDto", userDto);
		model.addAttribute("isValidInfo",true);
		if(rs.hasErrors()) {
			return "profile";
		}
		boolean isValidInfo = isValidInfo(userDto,cust);
		if(!isValidInfo) {
			model.addAttribute("isValidInfo",false);
			return "profile";
		}
		user.setEmail(userDto.getNewEmail());
		user.setPassword(encoder.encode(userDto.getNewPassword()));
		service.updateSecInfo(cust);
		SecurityContextHolder.clearContext();
		
		return "/login";
	}

	private boolean isValidInfo(UpdateUserDto userDto,Customer cust) {
		if(userDto.getEmail().equals(cust.getUser().getEmail())
				&& encoder.matches(userDto.getPassword(),cust.getUser().getPassword())) {
			return true;
		}
		return false;
	}
	/*
	 * @PostMapping("process-signUp") public String
	 * processSignUp(@ModelAttribute("user") AppUser user) { service.insert(user);
	 * return "login"; }
	 */
}
