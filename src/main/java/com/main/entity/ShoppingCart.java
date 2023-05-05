package com.main.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "cart")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "cart" })
	private Set<CartItem> items = new HashSet<>();
	@Transient
	private int count;
	@Transient
	private double totalPrice;

	public void addItem(CartItem item) {
		items.add(item);
	}

	public void removeItem(CartItem item) {
		items.remove(item);
	}

	public int getCount() {
		count = 0;
		for (CartItem item : items) {
			count += item.getItemCount();
		}
		return count;
	}

	public double getTotalPrice() {
		totalPrice = 0;
		for (CartItem item : items) {
			totalPrice += item.getItemCount() * item.getProduct().getPrice();
		}
		return totalPrice;
	}

}
