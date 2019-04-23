package com.gpayments.requestor.sample_requestor.dto;

/**
 * @author GPayments ON 31/07/2018
 */
public class CardholderInfo {

    private String cardNumber;
    private String cardExpiry;

    private String billAddrCity;
    private String billAddrCountry;
    private String billAddrLine1;
    private String billAddrLine2;
    private String billAddrPostCode;
    private String billAddrState;
    private String email;
    private String phoneNumber;
    private String cardholderName;
    private String shipAddrCity;
    private String shipAddrCountry;
    private String shipAddrLine1;
    private String shipAddrLine2;
    private String shipAddrPostCode;
    private String shipAddrState;

    public CardholderInfo() {
        // dummy data
        this.cardNumber = "4123450000002";
        this.cardExpiry = "2101";
        this.billAddrCity = "Sydney";
        this.billAddrCountry = "Australia";
        this.billAddrLine1 = "Unit 1";
        this.billAddrLine2 = "123 Street Suburb";
        this.billAddrPostCode = "2000";
        this.billAddrState = "NSW";
        this.email = "abc@123.com";
        this.phoneNumber = "0421522102";
        this.cardholderName = "Suda Ichiro";
        this.shipAddrCity = this.billAddrCity;
        this.shipAddrCountry = this.billAddrCountry;
        this.shipAddrLine1 = this.billAddrLine1;
        this.shipAddrLine2 = this.billAddrLine2;
        this.shipAddrPostCode = this.billAddrPostCode;
        this.shipAddrState = this.billAddrState;
    }

    /**
     * Getter and Setters
     */
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getBillAddrCity() {
        return billAddrCity;
    }

    public void setBillAddrCity(String billAddrCity) {
        this.billAddrCity = billAddrCity;
    }

    public String getBillAddrCountry() {
        return billAddrCountry;
    }

    public void setBillAddrCountry(String billAddrCountry) {
        this.billAddrCountry = billAddrCountry;
    }

    public String getBillAddrLine1() {
        return billAddrLine1;
    }

    public void setBillAddrLine1(String billAddrLine1) {
        this.billAddrLine1 = billAddrLine1;
    }

    public String getBillAddrLine2() {
        return billAddrLine2;
    }

    public void setBillAddrLine2(String billAddrLine2) {
        this.billAddrLine2 = billAddrLine2;
    }

    public String getBillAddrPostCode() {
        return billAddrPostCode;
    }

    public void setBillAddrPostCode(String billAddrPostCode) {
        this.billAddrPostCode = billAddrPostCode;
    }

    public String getBillAddrState() {
        return billAddrState;
    }

    public void setBillAddrState(String billAddrState) {
        this.billAddrState = billAddrState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getShipAddrCity() {
        return shipAddrCity;
    }

    public void setShipAddrCity(String shipAddrCity) {
        this.shipAddrCity = shipAddrCity;
    }

    public String getShipAddrCountry() {
        return shipAddrCountry;
    }

    public void setShipAddrCountry(String shipAddrCountry) {
        this.shipAddrCountry = shipAddrCountry;
    }

    public String getShipAddrLine1() {
        return shipAddrLine1;
    }

    public void setShipAddrLine1(String shipAddrLine1) {
        this.shipAddrLine1 = shipAddrLine1;
    }

    public String getShipAddrLine2() {
        return shipAddrLine2;
    }

    public void setShipAddrLine2(String shipAddrLine2) {
        this.shipAddrLine2 = shipAddrLine2;
    }

    public String getShipAddrPostCode() {
        return shipAddrPostCode;
    }

    public void setShipAddrPostCode(String shipAddrPostCode) {
        this.shipAddrPostCode = shipAddrPostCode;
    }

    public String getShipAddrState() {
        return shipAddrState;
    }

    public void setShipAddrState(String shipAddrState) {
        this.shipAddrState = shipAddrState;
    }

    @Override
    public String toString() {
        return "CardholderInfo{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardExpiry='" + cardExpiry + '\'' +
                ", billAddrCity='" + billAddrCity + '\'' +
                ", billAddrCountry='" + billAddrCountry + '\'' +
                ", billAddrLine1='" + billAddrLine1 + '\'' +
                ", billAddrLine2='" + billAddrLine2 + '\'' +
                ", billAddrPostCode='" + billAddrPostCode + '\'' +
                ", billAddrState='" + billAddrState + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cardholderName='" + cardholderName + '\'' +
                ", shipAddrCity='" + shipAddrCity + '\'' +
                ", shipAddrCountry='" + shipAddrCountry + '\'' +
                ", shipAddrLine1='" + shipAddrLine1 + '\'' +
                ", shipAddrLine2='" + shipAddrLine2 + '\'' +
                ", shipAddrPostCode='" + shipAddrPostCode + '\'' +
                ", shipAddrState='" + shipAddrState + '\'' +
                '}';
    }
}
