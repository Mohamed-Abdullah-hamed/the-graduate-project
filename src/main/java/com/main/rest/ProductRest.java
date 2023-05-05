package com.main.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.Product;
import com.main.service.ProductService;

@RestController
@RequestMapping("/rest/api/products")
public class ProductRest {
	@Autowired
	private ProductService service;

	@CrossOrigin
	@GetMapping("/product/{name}")
	public List<Product> productsByName(@PathVariable("name") String name) {

		List<Product> products = service.findByName(name);

		return products;
	}
	@CrossOrigin
	@GetMapping("/name/{name}")
	public List<String> findProductNames(@PathVariable("name") String name) {
		
		List<String> products = service.findProductNames(name);

		return products;
	}
	@CrossOrigin
	@GetMapping("/id/{id}")
	public Product findProductById(@PathVariable("id") long id) {
		
		Product product = service.findById(id);

		return product;
	}

	@DeleteMapping("/id/{id}")
	public void removeProd(@PathVariable("id") long id) {
		service.deleteById(id);
	}
}
