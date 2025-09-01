package com.shopping.cart.model;

import java.util.Optional;

public enum Item {
	
	LIME("Lime", 15),
    BANANA("Banana", 20),
    APPLE("Apple", 35),
    MELON("Melon", 50);    

    private final String displayName;
    private final int price;

    Item(String displayName, int pricePence) {
        this.displayName = displayName;
        this.price = pricePence;
    }

    public String displayName() {
    	return displayName;
    }

    public int price() {
    	return price;
    }
    
    public static Optional<Item> fromName(String name) {
        if (name == null) return Optional.empty();
        String n = name.trim().toLowerCase();
        for (Item i : values()) {
            if (i.displayName.toLowerCase().equals(n)) return Optional.of(i);
        }
        return Optional.empty();
    }
}
