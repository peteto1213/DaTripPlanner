package com.ncl.team3.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * This class is to respond the request from front end
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleOrderInfoResponse {

    private String planName;

    private List<Integer> orderCastleIds;

    private CastleOrderInfoResponse castleOrderInfoResponse;

    private List<Integer> orderBusIds;

    private List<BusOrderDetail> busPlanDetails;

    private List<Integer> orderTrainIds;

    private List<TrainOrderDetail> trainPlanDetails;
}
