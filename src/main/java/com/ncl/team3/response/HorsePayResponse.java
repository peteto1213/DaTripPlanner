package com.ncl.team3.response;

/**
 * This class is used to respond the request from front end by calling the API horsepay given by university
 */
public class HorsePayResponse {

    private String storeID;

    private String customerID;

    private String date;

    private String time;

    private String timeZone;

    private Float transactionAmount;

    private String currencyCode;

    private paymetSuccess paymetSuccess;

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setPaymetSuccess(com.ncl.team3.response.paymetSuccess paymetSuccess) {
        this.paymetSuccess = paymetSuccess;
    }

    public String getStoreID() {
        return storeID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public com.ncl.team3.response.paymetSuccess getPaymetSuccess() {
        return paymetSuccess;
    }
}
