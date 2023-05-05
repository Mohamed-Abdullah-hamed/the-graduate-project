package com.main.rest;

import java.security.Principal;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.AppUser;
import com.main.entity.Customer;
import com.main.entity.Seller;
import com.main.service.AppUserService;
import com.main.service.CustService;
import com.main.service.SellerService;

import lombok.extern.flogger.Flogger;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/rest/api/user")
@Log4j2
public class UserRest {

	@Autowired
	private CustService custService;
	@Autowired
	private AppUserService userService;

	@GetMapping("/checkEmail/{email}")
	public boolean checkIfEmailIsValidToUse(@PathVariable("email") String email, Principal principal) {
		AppUser user = userService.findByEmail(principal.getName());
		Customer cust = user.getCust();
		if (cust == null)
			return true;
		if (principal.getName().equals(cust.getUser().getEmail()))
			return true;
		return false;
	}
	
	@GetMapping("/isEmailUnique/{email}")
	public boolean isEmailUnique(@PathVariable("email") @Email String email) {
		log.error("email is => "+email);
		AppUser user = userService.findByEmail(email);
		if(user == null) return true;
		return false;
	}
	@GetMapping("/isNameUnique/{name}")
	public boolean isNameUnique(@PathVariable("name") String name) {
		log.error("name is => "+name);
		Customer cust = custService.findByName(name);
		if(cust == null) return true;
		return false;
	}
	
}
