package com.main.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Min(value = 1, message = "{price.violation}")
	private double price;

	@NotEmpty(message = "{name.violation}")
	private String name;
	private String imgPath;
	private Boolean isActive;
	private long unitNumbers;
	private String description;
	private String category;
	@ManyToOne
	@JsonIgnoreProperties(value = {"products"})
	private Seller seller;
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<CartItem> items = new ArrayList<>();
	@OneToMany(mappedBy = "prod")
	private Set<ProductRating> ratings = new LinkedHashSet<>();

	@Transient
	private double avgRating;
	
	@PostLoad
	private void setAvgRating() {
		avgRating = ratings.stream().filter(x-> {return x.getRatingLevel()!=null;})
				.mapToInt(x-> {return x.getRatingLevel();}).average().orElseGet(()->0);
		/*
		avgRating = 0;
		int x = 0;
		Iterator<SellerRating> iterator= ratings.iterator();
		while(iterator.hasNext()) {
			SellerRating rt = iterator.next();
			if(rt.getRatingLevel()!= null) {
				++x;
				avgRating+=rt.getRatingLevel();
			}
		}
		avgRating = avgRating/x;
		*/
	}
	public void addRating(ProductRating rating) {
		this.ratings.add(rating);
		rating.setProd(this);
	}
	public void removeRating(ProductRating rating) {
		this.ratings.remove(rating);
		rating.setProd(null);
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", price=" + price + ", name=" + name + ", imgPath=" + imgPath + ", isActive="
				+ isActive + ", unitNumbers=" + unitNumbers + ", description=" + description + ", category=" + category
				+ "]";
	}

}
