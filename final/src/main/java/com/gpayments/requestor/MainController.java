package com.gpayments.requestor.sample_requestor;

import com.gpayments.requestor.sample_requestor.dto.Cart;
import com.gpayments.requestor.sample_requestor.dto.Item;
import com.gpayments.requestor.sample_requestor.dto.Shop;
import com.gpayments.requestor.sample_requestor.dto.activeserver.AuthResponseBRW;
import com.gpayments.requestor.sample_requestor.transaction.MerchantTransaction;
import com.gpayments.requestor.sample_requestor.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * @author GPayments ON 31/07/2018
 * MainConroller class with handler methods.
 **/
@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final TransactionManager transMgr;
    private final Shop shop;
    private final Cart cart;
    private final RestTemplate restTemplate;

    @Autowired
    public MainController(
            TransactionManager threeDSRequestorTransactionManager,
            Shop shop,
            Cart cart,
            RestTemplate restTemplate) {
        this.transMgr = threeDSRequestorTransactionManager;
        this.shop = shop;
        this.cart = cart;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String main(Model model) {

        model.addAttribute("cart", cart);
        model.addAttribute("shop", shop);

        return "index";
    }

    @PostMapping("/update-cart")
    public String updateCart(@RequestParam(value = "key") String key,
                             @RequestParam(value = "quantity") int quantity,
                             Model model) {

        // if the quantity is zero just remove it
        if (quantity == 0) {
            cart.clearItem(key);
            return "redirect:/"; //redirect to index
        }

        // add to cart otherwise
        Item item = shop.findItem(key);
        cart.addToCart(item, quantity);
        return "redirect:/?success";
    }

    @GetMapping("/checkout")
    public String checkout(
            Model model) {

        MerchantTransaction transactionInfo = transMgr.newTransaction();

        model.addAttribute("cart", cart);
        model.addAttribute("transId", transactionInfo.getId());

        return "checkout";
    }

    @GetMapping("/auth/result")
    public String result(
            @RequestParam("txid") String transId,
            Model model) {

        MerchantTransaction transaction = transMgr.findTransaction(transId);

        String resultUrl = AuthController.THREE_DS_SERVER_URL + "/brw/result?threeDSServerTransID=" +
                transaction.getInitAuthResponseBRW().getThreeDSServerTransID();
        AuthResponseBRW response = restTemplate.getForObject(resultUrl, AuthResponseBRW.class);

        //convey result to the page.
        model.addAttribute("result", response);
        //make a copy of cart just for displaying
        model.addAttribute("cart", new Cart(cart));
        // clear the actual cart content
        cart.clearItem("all");

        return "result";
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
