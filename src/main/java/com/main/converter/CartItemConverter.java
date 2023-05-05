package com.main.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.main.entity.CartItem;
import com.main.service.CartItemService;

import lombok.extern.log4j.Log4j2;
@Component
@Log4j2
public class CartItemConverter implements Converter<String, CartItem>{

	@Autowired
	private CartItemService itemService;
	
	@Override
	public CartItem convert(String cartItem) {
		String[]  cartItemIds= cartItem.split(" ");
		Long prodId = Long.parseLong(cartItemIds[0]);
		Long cartId =  Long.parseLong(cartItemIds[1]);
		log.info(prodId+"   ......................    "+cartId);
		return itemService.getByProdId(prodId, cartId);
	}

}
