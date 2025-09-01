package com.shopping.cart.model;

import java.util.List;

public class ShoppingCart {

	private String cartId;
	private List<Item> items;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
