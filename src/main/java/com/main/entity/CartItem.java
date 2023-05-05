package com.main.entity;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class CartItem {

	@EmbeddedId
	private CartItemId cartItemId;

	@ManyToOne
	@MapsId(value = "shopCartId")
	@JsonIgnoreProperties(value = { "items" })
	private ShoppingCart cart;

	@ManyToOne
	@MapsId(value = "prodId")
	@JsonIgnoreProperties(value = { "items" })
	private Product product;
	private int itemCount;

	public CartItem() {
		this.cartItemId = new CartItemId();
	}

	public CartItemId getCartItemId() {

		if (product == null || cart == null)
			return null;
		return new CartItemId(product.getId(), cart.getId());
	}

	@Override
	public String toString() {
		return "CartItem [ product=" + getProduct() + ", itemCount=" + itemCount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart.getId(), product.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(cart.getId(), other.cart.getId())
				&& Objects.equals(product.getId(), other.product.getId());
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
		this.cartItemId.setShopCartId(cart.getId());
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.cartItemId.setProdId(product.getId());
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public void setCartItemId(CartItemId cartItemId) {
		this.cartItemId = cartItemId;
	}

}
