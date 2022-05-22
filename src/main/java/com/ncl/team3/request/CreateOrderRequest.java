package com.ncl.team3.request;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class is used to interact with the front end by calling the method createOrder() in OrderService
 */
public class CreateOrderRequest {

    private String planId;

    private Float transactionAmount;

    private String cardNumber;

    private String securityCode;

    private String expireDate;

    private String ownerName;

    @JsonCreator
    public CreateOrderRequest(String planId, Float transactionAmount, String cardNumber, String securityCode, String expireDate, String ownerName) {
        this.planId = planId;
        this.transactionAmount = transactionAmount;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expireDate = expireDate;
        this.ownerName = ownerName;
    }

    public String getPlanId() {
        return planId;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
