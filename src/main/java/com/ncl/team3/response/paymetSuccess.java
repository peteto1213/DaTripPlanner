package com.ncl.team3.response;

/**
 * This class is used to respond the request from front end by calling the API horsepay given by university
 */
public class paymetSuccess {

    private Boolean Status;

    private String reason;

    public void setStatus(Boolean status) {
        Status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getStatus() {
        return Status;
    }

    public String getReason() {
        return reason;
    }
}
