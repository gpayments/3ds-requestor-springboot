package com.gpayments.requestor.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author GPayments ON 31/07/2018
 */
@Component
public class Cart {
    //contains list of items added to the cart
    private final Map<String, Item> items = new HashMap<>();
    private float totalCost;
    private int totalNumItems; // total quantity

    public Cart() {
        this.totalCost = 0f;
        this.totalNumItems = 0;
    }


    // need it for copying
    public Cart(Cart cart) {
        // make deep copy of items
        for (Map.Entry<String, Item> entry : cart.items.entrySet()) {
            this.items.put(entry.getKey(), entry.getValue());
        }
        this.totalCost = cart.totalCost;
        this.totalNumItems = cart.totalNumItems;
    }

    /**
     * This method will add the item to the cart with specific quantity
     * @param item
     * @param quantity
     */
    public void addToCart(Item item, int quantity) {

        Item currItem = items.get(item.getName());

        // if it contains the key just add the quantity and price
        if(currItem != null) {
            currItem.setQuantity(currItem.getQuantity() + quantity);

        } else {
            // else set the currItem to be the item to be added
            currItem = new Item(item);
            currItem.setQuantity(quantity);
        }
        currItem.setPrice(currItem.getQuantity() * item.getPrice());
        items.put(currItem.getName(), currItem);

        updateTotal();
    }

    /**
     * This method will clear the item with certain key, if key is "all" it will clear all items
     * @param key
     */
    public void clearItem(String key) {

        if(key.equals("all")) {
            items.clear();
        } else {
            items.remove(key);
        }
        updateTotal();
    }

    /**
     * Helper method to update the totalNumItems and totalCost
     */
    private void updateTotal() {
        float totalCost = 0;
        int totalNumItems = 0;
        for(Item item : items.values()) {
            totalCost += item.getPrice();
            totalNumItems += item.getQuantity();
        }
        this.totalCost = totalCost;
        this.totalNumItems = totalNumItems;
    }

    //Getter and Setter
    public Map<String, Item> getItems() {
        return items;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalNumItems() {
        return totalNumItems;
    }

    public void setTotalNumItems(int totalNumItems) {
        this.totalNumItems = totalNumItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", totalCost=" + totalCost +
                ", totalNumItems=" + totalNumItems +
                '}';
    }
}
