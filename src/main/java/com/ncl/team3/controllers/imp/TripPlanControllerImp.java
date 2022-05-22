package com.ncl.team3.controllers.imp;

import com.ncl.team3.controllers.TripPlanController;
import com.ncl.team3.exception.TicketUnavailableException;
import com.ncl.team3.mappers.*;
import com.ncl.team3.models.*;
import com.ncl.team3.request.*;
import com.ncl.team3.services.TripPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class is the implementation class of TripPlanController,
 * which mainly implements the response to front-end requests and user order-related information
 * as well as the data validation of the data sent from the front-end to the back-end.
 * @author Lei Chen & Weidong Zhang
 * @version 1.0
 * @StudentNumber: 200936497,210004612
 * @date 2022/03/27 16:33:10
 */
@RestController
@RequestMapping("/tripPlan")
@Slf4j
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
public class TripPlanControllerImp implements TripPlanController {

    private TripPlanService tripPlanService;

    private CastleMapper castleMapper;

    private BusMapper busMapper;

    private TrainMapper trainMapper;

    @Autowired
    public TripPlanControllerImp(TripPlanService tripPlanService, CastleMapper castleMapper, BusMapper busMapper, TrainMapper trainMapper) {
        this.tripPlanService = tripPlanService;
        this.castleMapper = castleMapper;
        this.busMapper = busMapper;
        this.trainMapper = trainMapper;
    }

    /**
     * this method is to change the name of the plan
     * @param changePlanNameRequest:this class has two inner method,one is the plan id and one is the new plan name
     * @return ResultData
     */
    @PutMapping("/changePlanName")
    public ResultData changePlanName(@RequestBody ChangePlanNameRequest changePlanNameRequest){
        log.debug("Execute changePlanName");
        if (changePlanNameRequest.getPlanId() == null) {
            return new ResultData(404,"planId is null.");
        }
        if (changePlanNameRequest.getPlanName() == null){
            return new ResultData(404,"newPlanName is null.");
        }
        return tripPlanService.changePlanName(changePlanNameRequest);
    }

    /**
     * this method is to create a new plan
     * @param createPlanRequest:this class has four inner fields,represents the name of the plan,
     *      *                         the castle ticket id and booked ticket quantity,
     *      *                         the bus ticket id and booked ticket quantity,
     *      *                         the train ticket id and booked ticket quantity
     * @return ResultData
     * @throws TicketUnavailableException
     */
    @PostMapping("/createPlan")
    public ResultData createPlan(@RequestBody CreatePlanRequest createPlanRequest) throws TicketUnavailableException{
            log.debug("Execute createPlan");
            if (createPlanRequest.getPlanName() == null){
                return new ResultData(404,"plan name is null");
            }
            return tripPlanService.createPlan(createPlanRequest);
    }

    /**
     * to delete the castle ticket from corresponding plan
     * @param planCastleTicketId:the planCastleTicketId
     * @return ResultData
     */
    @DeleteMapping("/deleteCastleTicketToThePlan")
    public ResultData deleteCastleTicketToThePlan(Integer planCastleTicketId){
        if (planCastleTicketId == null){
            return new ResultData(404,"planCastleTicketId  is null");
        }
        return tripPlanService.deleteCastleTicketToThePlan(planCastleTicketId);
    }

    /**
     * to delete the bus ticket from corresponding plan
     * @param planBusTicketId:the planBusTicketId
     * @return ResultData
     */
    @DeleteMapping("/deleteBusTicketToThePlan")
    public ResultData deleteBusTicketToThePlan(Integer planBusTicketId){
        if (planBusTicketId == null){
            return new ResultData(404,"planCastleTicketId  is null.");
        }
        return tripPlanService.deleteBusTicketToThePlan(planBusTicketId);
    }

    /**
     * to delete the train ticket from corresponding plan
     * @param planTrainTicketId:the planTrainTicketId
     * @return ResultData
     */
    @DeleteMapping("/deleteTrainTicketToThePlan")
    public ResultData deleteTrainTicketToThePlan(Integer planTrainTicketId){
        if (planTrainTicketId == null){
            return new ResultData(404,"planTrainTicketId  is null.");
        }
        return tripPlanService.deleteTrainTicketToThePlan(planTrainTicketId);
    }

    /**
     * this method is to delete the plan
     * @param planId:the plan's ID
     * @return ResultData
     */
    @Override
    @DeleteMapping("/deletePlan")
    public ResultData deletePlan(String planId) {
        if (planId == null){
            return new ResultData(404,"deletePlan  is null.");
        }
        return tripPlanService.deletePlan(planId);
    }

    /**
     * this method is used to view the brief plan the user created
     * @param page:the start page
     * @param size:the page size
     * @return ResultData
     */
    @GetMapping("/viewPlan")
    public ResultData viewPlan(Integer page,Integer size) {
        if (page == null){
           log.warn("Execute the viewPlan, but the page is null , setting the page=0...");
           page = 0;
        }
        if (size == null){
            log.warn("Execute the viewPlan, but the size is null , setting the size=4...");
            size = 4;
        }
        return tripPlanService.viewPlan(page,size);
    }

    /**
     * this method is to view the detail of the plan
     * @param planId:the plan's ID
     * @return ResultData
     */
    @GetMapping("/viewPlanDetail")
    public ResultData viewPlanDetail(String planId) {
        if (planId == null){
            log.warn("Execute the viewPlanDetail, but the planId is null ");
            return  new ResultData(404,"Plan id is null . please check the parameter");
        }
        return tripPlanService.viewPlanDetail(planId);
    }
}
