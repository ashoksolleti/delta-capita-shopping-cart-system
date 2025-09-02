package com.shopping.cart.service;

import java.util.List;

public interface ShoppingCartService {
	
	public List<String> addItem(String cartId, String item);
	
	public List<String> viewCart(String cartId);

}
