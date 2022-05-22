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
public class BusPlanDetail {

    private Integer busTicketId;

    private String startPoint;

    private String endPoint;

    private BigDecimal busTicketPrice;

    private Integer busPlanQuantity;

    private String busTicketType;

    private String busTravelDate;

    private String busTravelTime;

    private String busOperator;

    private String busName;
}
