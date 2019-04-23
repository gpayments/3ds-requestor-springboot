package com.gpayments.requestor.sample_requestor.transaction;

/**
 * @author GPayments ON 31/07/2018
 * MerchantTransaction class to carry information about the transaction
 */
public class MerchantTransaction {

    //transaction id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MerchantTransaction{" +
                "id='" + id + '\'' +
                '}';
    }
}
