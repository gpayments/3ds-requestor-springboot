package com.gpayments.requestor.sample_requestor.transaction;


import com.gpayments.requestor.sample_requestor.dto.activeserver.InitAuthResponseBRW;

/**
 * @author GPayments ON 31/07/2018
 * MerchantTransaction class to carry information about the transaction
 */
public class MerchantTransaction {

    //transaction id
    private String id;

    private InitAuthResponseBRW initAuthResponseBRW;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InitAuthResponseBRW getInitAuthResponseBRW() {
        return initAuthResponseBRW;
    }

    public void setInitAuthResponseBRW(InitAuthResponseBRW initAuthResponseBRW) {
        this.initAuthResponseBRW = initAuthResponseBRW;
    }
    @Override
    public String toString() {
        return "MerchantTransaction{" +
                "id='" + id + '\'' +
                '}';
    }
}
