package com.gpayments.requestor.services;

import com.gpayments.requestor.dto.Item;
import com.gpayments.requestor.dto.MyCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

  private static final Logger logger = LoggerFactory.getLogger(CartService.class.getName());

  private final ShopService shopService;

  // contains list of items added to the cart
  private final MyCart myCart = new MyCart();

  @Autowired
  public CartService(ShopService shopService) {
    this.shopService = shopService;
  }

  public void updateCart(String key, int quantity) {

    // add to cart otherwise
    Item item = shopService.findItem(key);

    if (item != null) {

      myCart.addItem(item, quantity);
    } else {
      throw new IllegalArgumentException("invalid item");
    }
  }

  /**
   * This method will clear the item with certain key, if key is null it will clear all items
   */
  public void clear(String key) {

    myCart.clear(key);
  }

  /**
   * Return current cart
   */
  public MyCart getMyCart() {
    return myCart;
  }
}
