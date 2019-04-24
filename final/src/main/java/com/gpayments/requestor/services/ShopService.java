package com.gpayments.requestor.services;

import com.gpayments.requestor.dto.Item;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

  private final Map<String, Item> items = new ConcurrentHashMap<>();

  @Autowired
  public ShopService() {
    items.put(
        "Apple",
        new Item(
            "Apple",
            "Freshly picked Royal Gala apple. Delivery next day.",
            1,
            2.00f,
            "/images/apple.jpeg"));
    items.put(
        "Pineapple",
        new Item(
            "Pineapple",
            "Freshly picked Golden pineapple. Delivery next day.",
            1,
            5.00f,
            "/images/pineapple.jpeg"));
    items.put(
        "Banana",
        new Item(
            "Banana",
            "Freshly picked Cavendish banana. Delivery next day.",
            1,
            2.50f,
            "/images/banana.jpg"));
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
}
