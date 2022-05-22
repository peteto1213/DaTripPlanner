package com.ncl.team3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * This class is to respond the request from front end
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainPlanDetail {

    private Integer trainTicketId;

    private String startPoint;

    private String endPoint;

    private BigDecimal trainTicketPrice;

    private Integer trainPlanQuantity;

    private String trainTicketType;

    private String trainTravelDate;

    private String trainTravelTime;

    private String trainOperator;

    private String trainName;
}
