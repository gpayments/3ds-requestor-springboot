package com.gpayments.requestor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpayments.requestor.dto.activeserver.AcctInfo;
import com.gpayments.requestor.dto.activeserver.AuthRequestBRW;
import com.gpayments.requestor.dto.activeserver.AuthResponseBRW;
import com.gpayments.requestor.dto.activeserver.InitAuthRequestBRW;
import com.gpayments.requestor.dto.activeserver.InitAuthResponseBRW;
import com.gpayments.requestor.dto.activeserver.MerchantRiskIndicator;
import com.gpayments.requestor.dto.activeserver.ThreeDSRequestorAuthenticationInfo;
import com.gpayments.requestor.transaction.MerchantTransaction;
import com.gpayments.requestor.transaction.TransactionManager;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  private final RestTemplate restTemplate;
  private final TransactionManager transMgr;

  static final String THREE_DS_SERVER_URL = "https://api.as.testlab.3dsecure.cloud:7443";
  private final String THREE_DS_REQUESTOR_URL = "http://localhost:8082";
  private final ObjectMapper objectMapper;

  @Autowired
  public AuthController(
          RestTemplate restTemplate, TransactionManager transMgr, ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.transMgr = transMgr;
    this.objectMapper = objectMapper;
  }

  /**
   * Handler method for /auth/init, it initialises authentication through /brw/init/{}
   */
  @PostMapping("/auth/init")
  public InitAuthResponseBRW initAuth(@RequestBody InitAuthRequestBRW request) throws JsonProcessingException {
    // find transaction with transaction id
    MerchantTransaction transactionInfo =
        transMgr.findTransaction(request.getThreeDSRequestorTransID());

    if (transactionInfo == null) {

      throw new IllegalArgumentException(
          String.format("transaction (%s) doesn't exists", request.getThreeDSRequestorTransID()));
    }

    // fill the request with default data
    fillInitAuthRequestBRW(request, THREE_DS_REQUESTOR_URL + "/3ds-notify");

    String initBrwUrl = THREE_DS_SERVER_URL + "/api/v1/auth/brw/init/pa";

    logger.info("initAuthRequest on url: {}, body: {}", initBrwUrl, objectMapper.writeValueAsString(request));

    // Initialise authentication by making  POST request to /brw/init/{messageCategory} (Step. 3)
    RequestEntity<InitAuthRequestBRW> req =
        new RequestEntity<>(request, HttpMethod.POST, URI.create(initBrwUrl));

    try {
      ResponseEntity<InitAuthResponseBRW> resp =
          restTemplate.exchange(req, InitAuthResponseBRW.class);

      InitAuthResponseBRW initRespBody = resp.getBody();
      logger.info("initAuthResponseBRW {}", objectMapper.writeValueAsString(initRespBody));

      // set InitAuthResponseBRW for future use
      transactionInfo.setInitAuthResponseBRW(initRespBody);
      return initRespBody;

    } catch (HttpClientErrorException | HttpServerErrorException ex) {

      logger.error("initAuthReq failed, {}, {}", ex.getStatusCode(), ex.getResponseBodyAsString());

      throw ex;
    }
  }

  @PostMapping("/auth")
  public AuthResponseBRW auth(@RequestParam("id") String transId) throws JsonProcessingException {

    MerchantTransaction transaction = transMgr.findTransaction(transId);

    // create authentication request.
    AuthRequestBRW authRequest = new AuthRequestBRW();
    authRequest.setThreeDSRequestorTransID(transaction.getId());
    authRequest.setThreeDSServerTransID(
        transaction.getInitAuthResponseBRW().getThreeDSServerTransID());

    String brwUrl = THREE_DS_SERVER_URL + "/api/v1/auth/brw";


    logger.info("requesting BRW Auth API {}, body {}", brwUrl, objectMapper.writeValueAsString(authRequest));

    AuthResponseBRW response =
        restTemplate.postForObject(brwUrl, authRequest, AuthResponseBRW.class);

    logger.info("authResponseBRW {}", objectMapper.writeValueAsString(response));

    return response;
  }

  /**
   * This method is to fill in the InitAuthRequestBRW with demo data, you need to fill the
   * information from your database Commented out fields are filled in by 3ds-web-adapter.js from
   * the input fields in checkout page
   */
  private void fillInitAuthRequestBRW(
      InitAuthRequestBRW initAuthRequestBRW, String eventCallBackUrl) {

    initAuthRequestBRW.setAcctID("personal account");

    // Fill AcctInfo with default data.
    AcctInfo acctInfo = new AcctInfo();
    acctInfo.setChAccAgeInd("03");
    acctInfo.setChAccChange("20160712");
    acctInfo.setChAccChangeInd("04");
    acctInfo.setChAccDate("20140328");
    acctInfo.setChAccPwChange("20170328");
    acctInfo.setChAccPwChangeInd("02");
    acctInfo.setNbPurchaseAccount("11");
    acctInfo.setPaymentAccAge("20160917");
    acctInfo.setPaymentAccInd("02");
    acctInfo.setProvisionAttemptsDay("3");
    acctInfo.setShipAddressUsage("20160714");
    acctInfo.setShipAddressUsageInd("02");
    acctInfo.setShipNameIndicator("02");
    acctInfo.setSuspiciousAccActivity("02");
    acctInfo.setTxnActivityDay("1");
    acctInfo.setTxnActivityYear("21");
    initAuthRequestBRW.setAcctInfo(acctInfo);

    initAuthRequestBRW.setAcctType("03");
    initAuthRequestBRW.setAuthenticationInd("01"); // 01 = Payment transaction

    // fills ThreeDSRequestorAuthenticationInfo
    ThreeDSRequestorAuthenticationInfo threeDSRequestorAuthenticationInfo =
        new ThreeDSRequestorAuthenticationInfo();
    threeDSRequestorAuthenticationInfo.setThreeDSReqAuthData("login GP");
    threeDSRequestorAuthenticationInfo.setThreeDSReqAuthMethod("02");
    threeDSRequestorAuthenticationInfo.setThreeDSReqAuthTimestamp("201711071307");
    initAuthRequestBRW.setAuthenticationInfo(threeDSRequestorAuthenticationInfo);

    // fills MerchantRiskIndicator, optional but strongly recommended for the accuracy of risk based
    // authentication
    MerchantRiskIndicator merchantRiskIndicator = new MerchantRiskIndicator();
    merchantRiskIndicator.setDeliveryEmailAddress("test@123.com");
    merchantRiskIndicator.setDeliveryTimeframe("02");
    merchantRiskIndicator.setGiftCardAmount("123");
    merchantRiskIndicator.setGiftCardCount("02");
    merchantRiskIndicator.setGiftCardCurr("840");
    merchantRiskIndicator.setPreOrderDate("20170519");
    merchantRiskIndicator.setPreOrderPurchaseInd("02");
    merchantRiskIndicator.setReorderItemsInd("01");
    merchantRiskIndicator.setShipIndicator("01");

    initAuthRequestBRW.setMerchantRiskIndicator(merchantRiskIndicator);

    /**
     * Options for threeDSRequestorChallengeInd - Indicates whether a challenge is requested for
     * this transaction. Values accepted: 01 = No preference 02 = No challenge requested 03 =
     * Challenge requested: 3DS Requestor Preference 04 = Challenge requested: Mandate 05–79 =
     * Reserved for EMVCo future use (values invalid until defined by EMVCo) 80-99 = Reserved for DS
     * use
     */
    initAuthRequestBRW.setChallengeInd("01");
    initAuthRequestBRW.setEventCallbackUrl(eventCallBackUrl); // Set this to your url
    initAuthRequestBRW.setMerchantId(
        "123456789012345"); // from admin.as.testlab.3dsecure.cloud, temp for test only
    initAuthRequestBRW.setPurchaseDate(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    initAuthRequestBRW.setPurchaseInstalData("24");
    // initAuthRequestBRW.setRecurringExpiry("20180131");
    // initAuthRequestBRW.setRecurringFrequency("6");
    initAuthRequestBRW.setTransType("03");

    //        Following attributes are filled in by 3ds-web-adapter.js
    //        initAuthRequestBRW.setThreeDSRequestorTransID("jaondoXJojaNOASaAsSAAS");
    //        initAuthRequestBRW.setAcctNumber("4123450000002");
    //        initAuthRequestBRW.setPurchaseAmount("101");
    //        initAuthRequestBRW.setCardExpiryDate("2010");
    //         fills CardholderInformation, some of them can be filled in at 3ds-web-adapter.js but
    // for demo purposes just fill default data, to avoid confusion
    //        CardholderInformation cardholderInformation = new CardholderInformation();
    //        cardholderInformation.setBillAddrCity("Bill City Name");
    //        cardholderInformation.setBillAddrCountry("840");
    //        cardholderInformation.setBillAddrLine1("Bill Address Line 1");
    //        cardholderInformation.setBillAddrLine2("Bill Address Line 2");
    //        cardholderInformation.setBillAddrLine3("Bill Address Line 3");
    //        cardholderInformation.setBillAddrPostCode("Bill Post Code");
    //        cardholderInformation.setBillAddrState("CO");
    //        cardholderInformation.setCardholderName("Cardholder Name");
    //        cardholderInformation.setEmail("example@example.com");

    //        PhoneNumber mobilePhone = new PhoneNumber();
    //        mobilePhone.setCountryCode("212");
    //        mobilePhone.setSubscriber("123456789");
    //        cardholderInformation.setMobilePhone(mobilePhone);

    //        PhoneNumber homePhone = new PhoneNumber();
    //        homePhone.setCountryCode("212");
    //        homePhone.setSubscriber("123456789");
    //        cardholderInformation.setHomePhone(homePhone);

    //        PhoneNumber workPhone = new PhoneNumber();
    //        workPhone.setCountryCode("212");
    //        workPhone.setSubscriber("123456789");
    //        cardholderInformation.setWorkPhone(workPhone);

    //        cardholderInformation.setShipAddrCity("Ship City Name");
    //        cardholderInformation.setShipAddrCountry("840");
    //        cardholderInformation.setShipAddrLine1("Ship Address Line 1");
    //        cardholderInformation.setShipAddrLine2("Ship Address Line 2");
    //        cardholderInformation.setShipAddrLine3("Ship Address Line 3");
    //        cardholderInformation.setShipAddrPostCode("Ship Post Code");
    //        cardholderInformation.setShipAddrState("CO");
    //        Set priorTransID if this is not the first transaction
    //		  initAuthRequestBRW.setPriorTransID(generateUuid());
    //        initAuthRequestBRW.setPurchaseCurrency("870"); //USD, see ISO country code

  }
}
