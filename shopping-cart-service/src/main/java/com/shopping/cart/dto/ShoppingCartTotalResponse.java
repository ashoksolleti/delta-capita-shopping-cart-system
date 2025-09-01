package com.shopping.cart.dto;

public class ShoppingCartTotalResponse {
    private int totalPrice;

    public ShoppingCartTotalResponse(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalPence() {
        return totalPrice;
    }
}
