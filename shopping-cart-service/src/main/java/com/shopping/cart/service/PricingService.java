package com.shopping.cart.service;

import java.util.List;

public interface PricingService {
	
	public int calculateTotalPrice(List<String> itemNames);

}
