package com.shopping.cart.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.dto.ShoppingCartRequest;
import com.shopping.cart.dto.ShoppingCartTotalResponse;
import com.shopping.cart.service.impl.PricingService;
import com.shopping.cart.service.impl.ShoppingCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

	@Autowired
	private PricingService pricingService;

	@Autowired
	private ShoppingCartService shoppingCartService;


	@PostMapping("/total")
	public ResponseEntity<ShoppingCartTotalResponse> total(@Valid @RequestBody ShoppingCartRequest req) {
		int price = pricingService.calculateTotalPrice(req.getItems());
		return ResponseEntity.ok(new ShoppingCartTotalResponse(price));
	}

	@PostMapping("/{cartId}/add")
	public Map<String, Object> addItem(@PathVariable String cartId, @RequestBody Map<String, String> body) {
		String item = body.get("item");
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
