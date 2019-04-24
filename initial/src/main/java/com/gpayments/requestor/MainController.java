package com.gpayments.requestor;

import com.gpayments.requestor.dto.Cart;
import com.gpayments.requestor.dto.Item;
import com.gpayments.requestor.dto.Shop;
import com.gpayments.requestor.transaction.MerchantTransaction;
import com.gpayments.requestor.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    public MainController(
            TransactionManager threeDSRequestorTransactionManager,
            Shop shop,
            Cart cart) {
        this.transMgr = threeDSRequestorTransactionManager;
        this.shop = shop;
        this.cart = cart;
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
    public String result(Model model) {

        //make a copy of cart just for displaying
        model.addAttribute("cart", new Cart(cart));
        // clear the actual cart content
        cart.clearItem("all");

        return "result";
    }


}
