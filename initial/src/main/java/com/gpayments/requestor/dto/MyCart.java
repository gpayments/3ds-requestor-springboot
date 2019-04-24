package com.gpayments.requestor.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GPayments ON 31/07/2018
 */
public class MyCart {

  private final Map<String, Item> items = new HashMap<>();

  private double totalPrice;

  private int count;

  /**
   * This method will add the item to the cart with specific quantity
   */
  public void addItem(Item item, int quantity) {

    Item existing = items.get(item.getName());

    // if it contains the key just add the quantity and price
    if (existing != null) {
      existing.inclQuantity(quantity);

    } else {
      item.setQuantity(quantity);
      items.put(item.getName(), item);
    }

    updateTotal();
  }

  /**
   * Helper method to update the count and totalPrice
   */
  private void updateTotal() {

    double total = 0;
    int count = 0;
    for (Item item : items.values()) {
      total += item.getTotalPrice();
      count += item.getQuantity();
    }
    this.totalPrice = total;
    this.count = count;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public int getCount() {
    return count;
  }

  public Map<String, Item> getItems() {
    return items;
  }

  /**
   * This method will clear the item with certain key, if key is null it will clear all items
   */
  public void clear(String key) {

    if (key == null) {
      items.clear();
    } else {
      items.remove(key);
    }
    updateTotal();
  }
}
