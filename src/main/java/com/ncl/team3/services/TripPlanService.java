package com.ncl.team3.services;

import com.ncl.team3.exception.InvalidDeletionException;
import com.ncl.team3.exception.TicketUnavailableException;
import com.ncl.team3.models.*;
import com.ncl.team3.request.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Lei Chen & Weidong Zhang
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/07 17:42:28
 */
@Service
public interface TripPlanService {

    /**
     * this method is to change the name of the plan
     * @param changePlanNameRequest:this request has two inner fields,one is the plan id and one is the new plan name
     * @return ResultData
     */
    ResultData changePlanName(ChangePlanNameRequest changePlanNameRequest);

    /**
     * this method is to create a new plan
     * @param createPlanRequest:this class has four inner fields,represents the name of the plan,
     *                         the castle ticket id and booked ticket quantity,
     *                         the bus ticket id and booked ticket quantity,
     *                         the train ticket id and booked ticket quantity
     * @return ResultData
     * @throws TicketUnavailableException
     */
    ResultData createPlan(CreatePlanRequest createPlanRequest);

    /**
     * to delete the castle ticket from corresponding plan
     * @param id:the planCastleTicketId
     * @return ResultData
     */
    ResultData deleteCastleTicketToThePlan(Integer id) throws InvalidDeletionException;

    /**
     * to delete the bus ticket from corresponding plan
     * @param id:the planBusTicketId
     * @return ResultData
     */
    ResultData deleteBusTicketToThePlan(Integer id);

    /**
     * to delete the train ticket from corresponding plan
     * @param id:the planTrainTicketId
     * @return ResultData
     */
    ResultData deleteTrainTicketToThePlan(Integer id);

    /**
     * this method is to delete the plan
     * @param planId:the plan's ID
     * @return ResultData
     */
    ResultData deletePlan(String planId);

    /**
     * this method is used to view the brief plan the user created
     * @param page:the start page
     * @param size:the page size
     * @return ResultData
     */
    ResultData viewPlan(Integer page,Integer size);

    /**
     * this method is to view the detail of the plan
     * @param planId:the plan's ID
     * @return ResultData
     */
    ResultData viewPlanDetail(String planId);
}
