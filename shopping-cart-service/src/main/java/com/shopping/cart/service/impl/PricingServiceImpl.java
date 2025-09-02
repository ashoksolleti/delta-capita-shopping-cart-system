package com.shopping.cart.service.impl;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shopping.cart.model.Item;
import com.shopping.cart.service.PricingService;

@Service
public class PricingServiceImpl implements PricingService{

	public int calculateTotalPrice(List<String> itemNames) {
		Map<Item, Integer> itemCounts = new EnumMap<>(Item.class);
		for (String name : itemNames) {
		    Item item = Item.fromName(name)
		            .orElseThrow(() -> new IllegalArgumentException("Item not found: " + name));

		    if (itemCounts.containsKey(item)) {
		        itemCounts.put(item, itemCounts.get(item) + 1);
		    } else {
		        itemCounts.put(item, 1);
		    }
		}
		int total = 0;
		for (Map.Entry<Item, Integer> entry : itemCounts.entrySet()) {
			total += calculateItemTotal(entry.getKey(), entry.getValue());
		}
		return total;
	}

	//Adding some discounts for some of the items in the cart as per the business
	//Melons are 50p each, but are available as buy one get one free;
	//Limes are 15p each, but are available in a three for the price of two.
	private int calculateItemTotal(Item item, int count) {
		int chargeableCount;
		switch (item) {
		case MELON:
			chargeableCount = count - (count / 2); 
			break;
		case LIME:
			chargeableCount = count - (count / 3);
			break;
		default:
			chargeableCount = count;
			break;
		}
		return chargeableCount * item.price();
	}

}
