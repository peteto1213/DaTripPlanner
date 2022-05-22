package com.ncl.team3.request;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class is used to interact with front end by calling the method changePlanName() in TripPlanService
 */
public class ChangePlanNameRequest {

    private String planName;

    private String planId;

    @JsonCreator
    public ChangePlanNameRequest(String planName,String planId) {
        this.planName = planName;
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPlanId() {
        return planId;
    }
}
