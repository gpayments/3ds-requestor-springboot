package com.gpayments.requestor;

import com.gpayments.requestor.services.CardHolderService;
import com.gpayments.requestor.services.CartService;
import com.gpayments.requestor.services.ShopService;
import com.gpayments.requestor.transaction.MerchantTransaction;
import com.gpayments.requestor.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author GPayments ON 31/07/2018 MainConroller class with handler methods.
 */
@Controller
@RequestMapping("/")
public class MainController {

  private final TransactionManager transMgr;
  private final CartService cartService;
  private final ShopService shopService;
  private final CardHolderService cardHolderService;


  @Autowired
  public MainController(
      TransactionManager threeDSRequestorTransactionManager,
      ShopService shopService,
      CartService cartService,
      CardHolderService cardHolderService) {

    this.transMgr = threeDSRequestorTransactionManager;
    this.shopService = shopService;
    this.cartService = cartService;
    this.cardHolderService = cardHolderService;
  }

  @RequestMapping
  public String main(
      @RequestParam(value = "key", required = false) String key,
      @RequestParam(value = "quantity", required = false) Integer quantity,
      @RequestParam(value = "clear", required = false) String clearItem,
      Model model) {

    if (!StringUtils.isEmpty(key)) {

      if ("true".equals(clearItem)) {
        cartService.clear(key);
      } else {

        if (quantity != null && quantity > 0) {

          cartService.updateCart(key, quantity);
        }
      }
    }
    model.addAttribute("cart", cartService.getMyCart());
    model.addAttribute("items", shopService.getItems());

    return "index";
  }

  @GetMapping("/checkout")
  public String checkout(Model model) {

    MerchantTransaction transactionInfo = transMgr.newTransaction();

    model.addAttribute("cart", cartService.getMyCart());
    model.addAttribute("transId", transactionInfo.getId());
    model.addAttribute("cardholder", cardHolderService.getDefaultCardHolder());

    return "checkout";
  }

  @PostMapping("/3ds-notify")
  public String notifyResult(
      @RequestParam("requestorTransId") String transId,
      @RequestParam("event") String callbackType,
      @RequestParam(name = "param", required = false) String param,
      Model model) {

    String callbackName;
    if ("3DSMethodFinished".equals(callbackType)) {

      callbackName = "_on3DSMethodFinished";

    } else if ("3DSMethodSkipped".equals(callbackType)) {

      callbackName = "_on3DSMethodSkipped";

    } else if ("AuthResultReady".equals(callbackType)) {
      callbackName = "_onAuthResult";
    } else {
      throw new IllegalArgumentException("invalid callback type");
    }

    model.addAttribute("transId", transId);
    model.addAttribute("callbackName", callbackName);
    model.addAttribute("callbackParam", param);

    return "notify_3ds_events";
  }
}
