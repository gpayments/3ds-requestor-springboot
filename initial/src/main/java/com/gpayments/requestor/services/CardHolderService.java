package com.gpayments.requestor.services;

import com.gpayments.requestor.dto.CardholderInfo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CardHolderService {

  private static final Map<String, CardholderInfo> cardHolderList = new HashMap<>();

  private static final String DEFAULT_CARD_NUMBER = "4100000000000001";

  static {

    // this.cardNumber = "4123450000002";
    // this.cardNumber = "4100000000000011";

    CardholderInfo ch = new CardholderInfo(DEFAULT_CARD_NUMBER);
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000002");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000003");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000004");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000005");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000006");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000007");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000008");
    cardHolderList.put(ch.getCardNumber(), ch);

    ch = new CardholderInfo("4100000000000009");
    cardHolderList.put(ch.getCardNumber(), ch);
  }

  public CardholderInfo getDefaultCardHolder() {
    return cardHolderList.get(DEFAULT_CARD_NUMBER);
  }
}
