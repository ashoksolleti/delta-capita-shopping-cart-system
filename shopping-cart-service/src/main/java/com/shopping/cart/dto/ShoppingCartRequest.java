package com.shopping.cart.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ShoppingCartRequest {
    @NotNull
    private List<String> items;
    
    public ShoppingCartRequest(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
