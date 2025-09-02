package com.shopping.cart.service.impl;

import java.util.List;

public interface PricingService {
	
	public int calculateTotalPrice(List<String> itemNames);

}
