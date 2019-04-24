package com.gpayments.requestor.transaction;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * @author GPayments ON 31/07/2018
 * Transaction manager it allows to create new transaction and retrieve MerchantTransaction given transaction id.
 */
@Component
public class TransactionManager {

    private final Map<String, MerchantTransaction> transList = new ConcurrentHashMap<>();


    public MerchantTransaction findTransaction(String transId) {

        return transList.get(transId);

    }


    public MerchantTransaction newTransaction() {
        MerchantTransaction transactionInfo = new MerchantTransaction();
        transactionInfo.setId(UUID.randomUUID().toString());
        transList.put(transactionInfo.getId(), transactionInfo);
        return transactionInfo;
    }

    @Override
    public String toString() {
        return "TransactionManager{" +
                "transList=" + transList +
                '}';
    }
}
