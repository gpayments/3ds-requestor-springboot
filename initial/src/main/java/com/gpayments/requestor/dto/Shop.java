package com.gpayments.requestor.sample_requestor.dto;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GPayments ON 31/07/2018
 */
@Component
public class Shop {
    private final Map<String, Item> items =  new ConcurrentHashMap<>();

    public Shop() {
        items.put("Apple", new Item("Apple", "Freshly picked Royal Gala apple. Delivery next day.", 1, 2.00f, "/images/apple.jpeg"));
        items.put("Pineapple", new Item("Pineapple", "Freshly picked Golden pineapple. Delivery next day.", 1, 3.00f, "/images/pineapple.jpeg"));
        items.put("Banana", new Item("Banana", "Freshly picked Cavendish banana. Delivery next day.", 1, 1.50f, "/images/banana.jpg"));
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Item findItem(String key) {

        if (key == null) {
            return null;
        }

        return items.get(key);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "items=" + items +
                '}';
    }
}
