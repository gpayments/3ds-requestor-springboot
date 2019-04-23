package com.gpayments.requestor.sample_requestor;

import com.gpayments.requestor.sample_requestor.dto.activeserver.*;
import com.gpayments.requestor.sample_requestor.transaction.MerchantTransaction;
import com.gpayments.requestor.sample_requestor.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final RestTemplate restTemplate;
    private final TransactionManager transMgr;

    // ActiveServer entry points for authentication
    public static final String THREE_DS_SERVER_URL = "https://uat.testlab.activeserver.cloud/api/v1/auth";
    private final String THREE_DS_REQUESTOR_URL = "http://localhost:8082";

    @Autowired
    public AuthController(RestTemplate restTemplate, TransactionManager transMgr) {
        this.restTemplate = restTemplate;
        this.transMgr = transMgr;
    }

    /**
     * Handler method for /auth/init, it initialises authentication through /brw/init/{}
     *
     * @param request
     * @return
     */
    @PostMapping("/auth/init")
    public InitAuthResponseBRW initAuth(@RequestBody InitAuthRequestBRW request) {
        // find transaction with transaction id
        MerchantTransaction transactionInfo = transMgr.findTransaction(request.getThreeDSRequestorTransID());
        // fill the request with default data
        fillInitAuthRequestBRW(request, THREE_DS_REQUESTOR_URL + "/3ds-notify");

        logger.info("initAuthRequest {}", request);
        String initBrwUrl = THREE_DS_SERVER_URL + "/brw/init/pa";
        // Initialise authentication by making  POST request to /brw/init/{messageCategory} (Step. 3)
        InitAuthResponseBRW initAuthResponseBRW = restTemplate.postForObject(initBrwUrl,
                request, InitAuthResponseBRW.class);

        logger.info("initAuthResponseBRW {}", initAuthResponseBRW);

        // set InitAuthResponseBRW for future use
        transactionInfo.setInitAuthResponseBRW(initAuthResponseBRW);
        return initAuthResponseBRW;
    }

    @PostMapping("/auth")
    public AuthResponseBRW auth(@RequestParam("id") String transId) {

        MerchantTransaction transaction = transMgr.findTransaction(transId);

        //create authentication request.
        AuthRequestBRW authRequest = new AuthRequestBRW();
        authRequest.setThreeDSRequestorTransID(transaction.getId());
        authRequest.setThreeDSServerTransID(transaction.getInitAuthResponseBRW().getThreeDSServerTransID());

        String brwUrl = THREE_DS_SERVER_URL + "/brw";
        AuthResponseBRW response = restTemplate.postForObject(brwUrl, authRequest, AuthResponseBRW.class);

        logger.info("authResponseBRW {}", response);

        return response;
    }

    /**
     * This method is to fill in the InitAuthRequestBRW with demo data, you need to fill the information from your database
     * Commented out fields are filled in by 3ds-web-adapter.js from the input fields in checkout page
     * @param initAuthRequestBRW
     * @param eventCallBackUrl
     */
    private void fillInitAuthRequestBRW(InitAuthRequestBRW initAuthRequestBRW, String eventCallBackUrl) {

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
        initAuthRequestBRW.setAuthenticationInd("01");//01 = Payment transaction

        // fills ThreeDSRequestorAuthenticationInfo
        ThreeDSRequestorAuthenticationInfo threeDSRequestorAuthenticationInfo = new ThreeDSRequestorAuthenticationInfo();
        threeDSRequestorAuthenticationInfo.setThreeDSReqAuthData("login GP");
        threeDSRequestorAuthenticationInfo.setThreeDSReqAuthMethod("02");
        threeDSRequestorAuthenticationInfo.setThreeDSReqAuthTimestamp("201711071307");
        initAuthRequestBRW.setAuthenticationInfo(threeDSRequestorAuthenticationInfo);

        // fills MerchantRiskIndicator, optional but strongly recommended for the accuracy of risk based authentication
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
         * Options for threeDSRequestorChallengeInd - Indicates whether a challenge is requested for this transaction.
         * Values accepted:
         *  01 = No preference
         *  02 = No challenge requested
         *  03 = Challenge requested: 3DS Requestor Preference
         *  04 = Challenge requested: Mandate
         *  05–79 = Reserved for EMVCo future use (values invalid until defined by EMVCo)
         *  80-99 = Reserved for DS use
         */
        initAuthRequestBRW.setChallengeInd("01");
        initAuthRequestBRW.setEventCallbackUrl(eventCallBackUrl); //Set this to your url
        initAuthRequestBRW.setMerchantID("123456789012345");
        initAuthRequestBRW.setPurchaseDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        initAuthRequestBRW.setPurchaseInstalData("24");
        initAuthRequestBRW.setRecurringExpiry("20180131");
        initAuthRequestBRW.setRecurringFrequency("6");
        initAuthRequestBRW.setThreeDSRequestorID("123456789");
        initAuthRequestBRW.setTransType("03");

//        Following attributes are filled in by 3ds-web-adapter.js
//        initAuthRequestBRW.setThreeDSRequestorTransID("jaondoXJojaNOASaAsSAAS");
//        initAuthRequestBRW.setAcctNumber("4123450000002");
//        initAuthRequestBRW.setPurchaseAmount("101");
//        initAuthRequestBRW.setCardExpiryDate("2010");
//         fills CardholderInformation, some of them can be filled in at 3ds-web-adapter.js but for demo purposes just fill default data, to avoid confusion
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

