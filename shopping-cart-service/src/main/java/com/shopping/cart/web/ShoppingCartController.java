package com.shopping.cart.web;

import com.shopping.cart.dto.ShoppingCartRequest;
import com.shopping.cart.dto.ShoppingCartTotalResponse;
import com.shopping.cart.model.ShoppingCart;
import com.shopping.cart.service.PricingService;
import com.shopping.cart.service.ShoppingCartService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

	private final PricingService pricingService;

	private final ShoppingCartService shoppingCartService;

	public ShoppingCartController(PricingService pricingService, ShoppingCartService shoppingCartService) {
		this.pricingService = pricingService;
		this.shoppingCartService = shoppingCartService;
	}

	@PostMapping("/total")
	public ResponseEntity<ShoppingCartTotalResponse> total(@Valid @RequestBody ShoppingCartRequest req) {
		int price = pricingService.calculateTotalPrice(req.getItems());
		return ResponseEntity.ok(new ShoppingCartTotalResponse(price));
	}

	@PostMapping("/{cartId}/add")
	public Map<String, Object> addItem(@PathVariable String cartId, @RequestBody Map<String, String> body) {
		String item = body.get("item");
		if (item == null || item.isBlank()) {
			return Map.of("error", "Missing 'item' field in request");
		}
		List<String> updatedCart = shoppingCartService.addItem(cartId, item);
		return Map.of("cartId", cartId, "message", "Added " + item, "cart", updatedCart);
	}

	@GetMapping("/{cartId}/view")
	public Map<String, Object> viewCart(@PathVariable String cartId) {
		return Map.of("cartId", cartId, "items", shoppingCartService.viewCart(cartId));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
