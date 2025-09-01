package com.shopping.cart.repository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ShoppingCartRepository {

    // in-memory store for now
    private final Map<String, List<String>> carts = new HashMap<>();

    public List<String> addItem(String cartId, String item) {
        carts.computeIfAbsent(cartId, k -> new ArrayList<>()).add(item);
        return carts.get(cartId);
    }

    public List<String> getCart(String cartId) {
        return carts.getOrDefault(cartId, List.of());
    }

    public void clearCart(String cartId) {
        carts.remove(cartId);
    }

    public Map<String, List<String>> getAllCarts() {
        return Collections.unmodifiableMap(carts);
    }
}
