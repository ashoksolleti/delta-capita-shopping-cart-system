package com.shopping.cart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.cart.repository.ShoppingCartRepository;
import com.shopping.cart.service.PricingService;
import com.shopping.cart.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartRepository cartRepository;
	
	@Autowired
	private PricingService pricingService;


    public List<String> addItem(String cartId, String item) {
        return cartRepository.addItem(cartId, item);
    }

    public List<String> viewCart(String cartId) {
        return cartRepository.getCart(cartId);
    }

    public int calculateTotal(String cartId) {
        List<String> items = cartRepository.getCart(cartId);
        return pricingService.calculateTotalPrice(items);
    }

    public void clearCart(String cartId) {
        cartRepository.clearCart(cartId);
    }

}
