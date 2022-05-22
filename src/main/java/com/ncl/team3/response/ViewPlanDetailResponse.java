package com.ncl.team3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class is to respond the request from front end
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewPlanDetailResponse {

    private String planName;

    private String castleName;

    private String date;

    private Integer guestNumber;

    private String planStatus;

    private Integer castleId;

    private BigDecimal castleTicketPrice;

    private String castleDescription;

    private Integer castleTicketId;

    private String castleTravelDate;

    private String castleTravelTime;

    private String castleTicketType;

    private List<BusPlanDetail> busPlanDetails;

    private List<TrainPlanDetail> trainPlanDetails;
}
