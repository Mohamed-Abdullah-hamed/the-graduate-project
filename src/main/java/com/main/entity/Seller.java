package com.main.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seller extends Customer {

	@OneToMany(mappedBy = "seller")
	@JsonIgnoreProperties(value = { "seller" })
	private Set<Product> products = new LinkedHashSet<>();
	@OneToMany(mappedBy = "seller")
	@JsonIgnoreProperties(value = { "seller" })
	private Set<SellerRating> ratings = new LinkedHashSet<>();
	@Transient
	private double avgRating;

	@PostLoad
	private void setAvgRating() {
		avgRating = ratings.stream().filter(x -> {
			return x.getRatingLevel() != null;
		}).mapToInt(x -> {
			return x.getRatingLevel();
		}).average().orElseGet(() -> 0);
		/*
		 * avgRating = 0; int x = 0; Iterator<SellerRating> iterator=
		 * ratings.iterator(); while(iterator.hasNext()) { SellerRating rt =
		 * iterator.next(); if(rt.getRatingLevel()!= null) { ++x;
		 * avgRating+=rt.getRatingLevel(); } } avgRating = avgRating/x;
		 */
	}

	public void addRating(SellerRating rating) {
		this.ratings.add(rating);
		rating.setSeller(this);
	}

	public void removeRating(SellerRating rating) {
		this.ratings.remove(rating);
		rating.setSeller(null);
	}

	public void addProduct(Product prod) {
		this.products.add(prod);
		prod.setSeller(this);
	}

	public void removeProduct(Product prod) {
		this.products.remove(prod);
		prod.setSeller(null);
		prod.setUnitNumbers(0);
	}
}
