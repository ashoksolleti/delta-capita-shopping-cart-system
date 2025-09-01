package com.shopping.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.cart.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

	private final ShoppingCartRepository cartRepository;
	
	private final PricingService pricingService;

    public ShoppingCartService(ShoppingCartRepository cartRepository,PricingService pricingService) {
        this.cartRepository = cartRepository;
        this.pricingService = pricingService;
    }

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
