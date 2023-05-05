package com.main.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.service.SellerService;

@RestController
@RequestMapping("/rest/api/seller")
public class SellerRest {

	@Autowired
	private SellerService service;
	
	@DeleteMapping("/id/{id}")
	public void removeProd(@PathVariable("id") long id) {
		service.deleteById(id);
	}
}
