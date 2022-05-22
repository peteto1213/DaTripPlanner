package com.ncl.team3.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;
import java.util.Map;

/**
 * This class is used to interact with front end by calling the method createPlan() in TripPlanService
 */
public class CreatePlanRequest implements Serializable {

    private String planName;

    private Map<Integer,Integer> castleTicketNum;

    private Map<Integer,Integer> busTicketNum;

    private Map<Integer,Integer> trainTicketNum;

    @JsonCreator
    public CreatePlanRequest(String planName,Map<Integer, Integer> castleTicketNum,Map<Integer, Integer> busTicketNum,Map<Integer, Integer> trainTicketNum) {
        this.planName = planName;
        this.castleTicketNum = castleTicketNum;
        this.busTicketNum = busTicketNum;
        this.trainTicketNum = trainTicketNum;
    }

    public String getPlanName() {
        return planName;
    }

    public Map<Integer, Integer> getCastleTicketNum() {
        return castleTicketNum;
    }

    public Map<Integer, Integer> getBusTicketNum() {
        return busTicketNum;
    }

    public Map<Integer, Integer> getTrainTicketNum() {
        return trainTicketNum;
    }
}
