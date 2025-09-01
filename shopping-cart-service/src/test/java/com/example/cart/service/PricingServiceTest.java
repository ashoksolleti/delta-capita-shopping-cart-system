package com.example.cart.service;

import org.junit.jupiter.api.Test;

import com.shopping.cart.service.PricingService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private final PricingService pricingService = new PricingService();
    
    @Test
    void testUnknownItem() {
        assertThrows(IllegalArgumentException.class, () ->
        pricingService.calculateTotalPrice(List.of("Grapes")));
    }

    @Test
    void testSimpleTotals() {
        assertEquals(35, pricingService.calculateTotalPrice(List.of("Apple")));
        assertEquals(20, pricingService.calculateTotalPrice(List.of("Banana")));
    }

    @Test
    void testDiscountForMelons() {
        assertEquals(50, pricingService.calculateTotalPrice(List.of("Melon","Melon")));
        assertEquals(100, pricingService.calculateTotalPrice(List.of("Melon","Melon","Melon","Melon")));
        assertEquals(100, pricingService.calculateTotalPrice(List.of("Melon","Melon","Melon")));
    }

    @Test
    void testDiscountforLimes() {
        assertEquals(30, pricingService.calculateTotalPrice(List.of("Lime","Lime")));
        assertEquals(30, pricingService.calculateTotalPrice(List.of("Lime","Lime","Lime")));
        assertEquals(60, pricingService.calculateTotalPrice(List.of("Lime","Lime","Lime","Lime","Lime","Lime")));
        assertEquals(45, pricingService.calculateTotalPrice(List.of("Lime","Lime","Lime","Lime")));
    }

    @Test
    void testMixedBasket() {
        int pence = pricingService.calculateTotalPrice(List.of("Apple","Banana","Melon","Melon","Lime","Lime","Lime"));
        assertEquals(35 + 20 + 50 + 30, pence);
    }


}
