package com.main.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartItemId implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Column(name = "product_id")
	private Long prodId;
	@Column(name = "shopCart_id")
	private long shopCartId;

}
