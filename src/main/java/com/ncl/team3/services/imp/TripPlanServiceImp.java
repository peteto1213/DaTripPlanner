package com.ncl.team3.services.imp;

import com.ncl.team3.exception.TicketUnavailableException;
import com.ncl.team3.mappers.*;
import com.ncl.team3.models.*;
import com.ncl.team3.request.*;
import com.ncl.team3.response.BusPlanDetail;
import com.ncl.team3.response.TrainPlanDetail;
import com.ncl.team3.response.ViewPlanDetailResponse;
import com.ncl.team3.response.ViewPlanResponse;
import com.ncl.team3.services.TripPlanService;
import com.ncl.team3.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 *
 * This class provides some methods about trip plan.
 * For example, it can insert the plan, delete the plan, get information of plans.
 *
 *
 * @author Lei Chen & Weidong Zhang
 * @version 1.0
 * @StudentNumber: 200936497,210004612
 * @date 2022/04/07 17:49:14
 */
@Service
@Slf4j
public class TripPlanServiceImp implements TripPlanService {

    private PlanMapper planMapper;

    private PlanCastleTicketMapper planCastleTicketMapper;

    private PlanBusTicketMapper planBusTicketMapper;

    private PlanTrainTicketMapper planTrainTicketMapper;

    private BusTicketMapper busTicketMapper;

    private TrainTicketMapper trainTicketMapper;

    private CastleTicketMapper castleTicketMapper;

    private CastleMapper castleMapper;

    private BusMapper busMapper;

    private TrainMapper trainMapper;
    @Autowired
    public TripPlanServiceImp(PlanMapper planMapper, PlanCastleTicketMapper planCastleTicketMapper, PlanBusTicketMapper planBusTicketMapper, PlanTrainTicketMapper planTrainTicketMapper, BusTicketMapper busTicketMapper, TrainTicketMapper trainTicketMapper, CastleTicketMapper castleTicketMapper, CastleMapper castleMapper, BusMapper busMapper, TrainMapper trainMapper) {
        this.planMapper = planMapper;
        this.planCastleTicketMapper = planCastleTicketMapper;
        this.planBusTicketMapper = planBusTicketMapper;
        this.planTrainTicketMapper = planTrainTicketMapper;
        this.busTicketMapper = busTicketMapper;
        this.trainTicketMapper = trainTicketMapper;
        this.castleTicketMapper = castleTicketMapper;
        this.castleMapper = castleMapper;
        this.busMapper = busMapper;
        this.trainMapper = trainMapper;
    }

    //to verify login
    public User authoriseLogin(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * this method is to change the name of the plan
     * @param changePlanNameRequest:this request has two inner fields,one is the plan id and one is the new plan name
     * @return ResultData
     */
    @Override
    public ResultData changePlanName(ChangePlanNameRequest changePlanNameRequest) {
        log.debug("Execute: changePlanName");
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        Plan plan = planMapper.findByPlanId(changePlanNameRequest.getPlanId());
        plan.setName(changePlanNameRequest.getPlanName());
        planMapper.save(plan);
        return new ResultData(200,"successfully change the name of the plan");
    }

    /**
     * this method is to create a new plan
     * @param createPlanRequest:this class has four inner fields,represents the name of the plan,
     *                         the castle ticket id and booked ticket quantity,
     *                         the bus ticket id and booked ticket quantity,
     *                         the train ticket id and booked ticket quantity
     * @return ResultData
     * @throws TicketUnavailableException
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData createPlan(CreatePlanRequest createPlanRequest){
        log.debug("Execute: createPlan");
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        User user = authoriseLogin();
        //get all inner fields in createPlanRequest
        String planName = createPlanRequest.getPlanName();
        Map<Integer, Integer> castleTicketNum = createPlanRequest.getCastleTicketNum();
        Map<Integer, Integer> busTicketNum = createPlanRequest.getBusTicketNum();
        Map<Integer, Integer> trainTicketNum = createPlanRequest.getTrainTicketNum();
        Plan plan = new Plan();
        String planId = UUID.randomUUID().toString();
        //set the plan
        plan.setPlanId(planId).setName(planName).setStatus("normal").setUser(user).setCreatedDate(TimeUtil.getCurrentDate())
                        .setCreatedTime(TimeUtil.getCurrentTime());
        planMapper.save(plan);
        //fetch relevant plan
        Plan fetchPlan = planMapper.findByPlanId(planId);
        //create the plan castle ticket table in database
        if (castleTicketNum != null){
            for (Map.Entry<Integer,Integer> entry : castleTicketNum.entrySet()){
                CastleTicket castleTicket = castleTicketMapper.findById(entry.getKey()).get();
                PlanCastleTicket planCastleTicket = new PlanCastleTicket();
                planCastleTicket.setPlan(fetchPlan).setCastleTicket(castleTicket).setCastleTicketQuantity(entry.getValue());
                planCastleTicketMapper.save(planCastleTicket);
            }
        }
        //create the plan bus ticket table in database
        if (busTicketNum != null){
            for (Map.Entry<Integer,Integer> entry : busTicketNum.entrySet()){
                BusTicket busTicket = busTicketMapper.findById(entry.getKey()).get();
                PlanBusTicket planBusTicket = new PlanBusTicket();
                planBusTicket.setPlan(fetchPlan).setBusTicket(busTicket).setBusTicketQuantity(entry.getValue());
                planBusTicketMapper.save(planBusTicket);
            }
        }
        //create the plan train ticket table in database
        if (trainTicketNum != null){
            for (Map.Entry<Integer,Integer> entry : trainTicketNum.entrySet()){
                TrainTicket trainTicket = trainTicketMapper.findById(entry.getKey()).get();
                PlanTrainTicket planTrainTicket = new PlanTrainTicket();
                planTrainTicket.setPlan(fetchPlan).setTrainTicket(trainTicket).setTrainTicketQuantity(entry.getValue());
                planTrainTicketMapper.save(planTrainTicket);
            }
        }
        return new ResultData(200,"successfully create the plan");
    }

    /**
     * to delete the castle ticket from corresponding plan
     * @param planCastleTicketId:the planCastleTicketId
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData deleteCastleTicketToThePlan(Integer planCastleTicketId) {
        log.debug("Execute: deleteCastleTicketToThePlan");
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        planCastleTicketMapper.deleteCastlePlanById(planCastleTicketId);
        return new ResultData(200,"successfully delete the castle plan");
    }

    /**
     * to delete the bus ticket from corresponding plan
     * @param planBusTicketId:the planBusTicketId
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData deleteBusTicketToThePlan(Integer planBusTicketId) {
        log.debug("Execute: deleteBusTicketToThePlan");
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        planBusTicketMapper.deleteBusPlanById(planBusTicketId);
        return new ResultData(200,"successfully delete the bus plan");
    }

    /**
     * to delete the train ticket from corresponding plan
     * @param planTrainTicketId:the planTrainTicketId
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData deleteTrainTicketToThePlan(Integer planTrainTicketId) {
        log.debug("Execute: deleteTrainTicketToThePlan");
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        planTrainTicketMapper.deleteTrainPlanById(planTrainTicketId);
        return new ResultData(200,"successfully delete the train plan");
    }

    /**
     * this method is to delete the plan
     * @param planId:the plan's ID
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData deletePlan(String planId) {
        log.debug("Execute: deletePlan");
        Plan plan = planMapper.findByPlanId(planId);
        planMapper.save(plan.setStatus("deleted"));
        return new ResultData(200,"successfully delete the plan");
    }

    /**
     * this method is used to view the brief plan the user created
     * @param page:the start page
     * @param size:the page size
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData viewPlan(Integer page,Integer size) {
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        User user = authoriseLogin();
        PageRequest pageAble = PageRequest.of(page, size);
        Page<Plan> plans = planMapper.allPlans(user.getEmail(),pageAble);
        List<Plan> planContent = plans.getContent();
        List<ViewPlanResponse> list = new ArrayList<>();
        for (Plan plan : planContent){
            //get the castle tickets
            List<CastleTicket> allCastleTickets = planCastleTicketMapper.findAllCastleTicketIds(plan.getPlanId());
            //get the bus tickets
            List<BusTicket> allBusTickets = planBusTicketMapper.findAllBusTicketIds(plan.getPlanId());
            //get the train tickets
            List<TrainTicket> allTrainTickets = planTrainTicketMapper.findAllTrainTicketIds(plan.getPlanId());
            //if user do not book any transport tickets,the start point should be "No transport ticket"
            if (allBusTickets.size() == 0 && allTrainTickets.size() == 0){
                ViewPlanResponse response = new ViewPlanResponse();
                response.setPlanId(plan.getPlanId()).setPlanName(plan.getName()).setCastleName(allCastleTickets.get(0).getCastle().getCastleName()).setPlanStatus(plan.getStatus())
                        .setDate(allCastleTickets.get(0).getDate())
                        .setStartPoint("No transport ticket");
                list.add(response);
                continue;
            }
            //if the List<BusTicket> is not null and size not equals 0,to get the start point and the visit date
            if (allBusTickets != null && allBusTickets.size() != 0){
                BusTicket busTicket = allBusTickets.get(0);
                String startPoint = busTicket.getBusTimetable().getRouteDetail().getRoute().getDepartureGps().getName();
                ViewPlanResponse response = new ViewPlanResponse();
                response.setPlanId(plan.getPlanId()).setPlanName(plan.getName()).setCastleName(allCastleTickets.get(0).getCastle().getCastleName()).setPlanStatus(plan.getStatus())
                        .setDate(allCastleTickets.get(0).getDate())
                        .setStartPoint(startPoint);
                list.add(response);
                continue;
            }
            //if the List<TrainTicket> is not null and size not equals 0,to get the start point and the visit date
            if (allTrainTickets != null && allTrainTickets.size() != 0){
                TrainTicket trainTicket = allTrainTickets.get(0);
                String startPoint = trainTicket.getTrainTimetable().getRouteDetail().getRoute().getDepartureGps().getName();
                ViewPlanResponse response = new ViewPlanResponse();
                response.setPlanId(plan.getPlanId()).setPlanName(plan.getName()).setCastleName(allCastleTickets.get(0).getCastle().getCastleName()).setPlanStatus(plan.getStatus())
                        .setDate(allCastleTickets.get(0).getDate())
                        .setStartPoint(startPoint);
                list.add(response);
                continue;
            }
        }
        return new ResultData(list,200,"view the plan");
    }

    /**
     * this method is to view the detail of the plan
     * @param planId:the plan's ID
     * @return ResultData
     */
    @Override
    public ResultData viewPlanDetail(String planId) {
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        ViewPlanDetailResponse response = new ViewPlanDetailResponse();
        String planName;
        String castleName;
        String date;
        Integer guestNumber;
        String planStatus;
        Integer castleId;
        BigDecimal castleTicketPrice;
        String castleDescription;
        Integer castleTicketId;
        String castleTravelDate;
        String castleTravelTime;
        String castleTicketType;
        List<BusPlanDetail> busPlanDetails = new ArrayList<>();
        List<TrainPlanDetail> trainPlanDetails = new ArrayList<>();
        //get the castle tickets
        List<CastleTicket> allCastleTickets = planCastleTicketMapper.findAllCastleTicketIds(planId);
        //get the bus tickets
        List<BusTicket> allBusTickets = planBusTicketMapper.findAllBusTicketIds(planId);
        //get the train tickets
        List<TrainTicket> allTrainTickets = planTrainTicketMapper.findAllTrainTicketIds(planId);
        //query the plan name
        planName = planMapper.findByPlanId(planId).getName();
        //query the castle name
        castleName = allCastleTickets.get(0).getCastle().getCastleName();
        //query the castle travel date
        date = allCastleTickets.get(0).getDate();
        //query the guest number
        guestNumber = planCastleTicketMapper.findPlanCastleTicketQuantity(allCastleTickets.get(0),planId);
        //query the plan status
        planStatus = planMapper.findByPlanId(planId).getStatus();
        //query the castle ID
        castleId = allCastleTickets.get(0).getCastle().getCastleId();
        //query the castle ticket price
        castleTicketPrice = allCastleTickets.get(0).getPrice();
        //query the castle description
        castleDescription = allCastleTickets.get(0).getCastle().getCastleDescription();
        //query the castle ticket ID
        castleTicketId = allCastleTickets.get(0).getCastleTicketId();
        //query the castle travel date
        castleTravelDate = allCastleTickets.get(0).getDate();
        //query the castle travel time
        castleTravelTime = allCastleTickets.get(0).getTime();
        //query the castle ticket type
        castleTicketType = allCastleTickets.get(0).getType();
        //to get all the bus plan detail
        for (BusTicket busTicket : allBusTickets){
            BusPlanDetail busPlanDetail = new BusPlanDetail();
            busPlanDetail.setBusTicketId(busTicket.getBusTicketId()).setStartPoint(busTicket.getBusTimetable().getRouteDetail().getStartPointGps().getName()).setEndPoint(busTicket.getBusTimetable().getRouteDetail().getEndPointGps().getName()).setEndPoint(busTicket.getBusTimetable().getRouteDetail().getRoute().getDestinationGps().getName()).setBusTicketPrice(busTicket.getPrice()).setBusPlanQuantity(planBusTicketMapper.findPlanBusTicketQuantity(busTicket,planId)).setBusTravelDate(busTicket.getBusTimetable().getDate())
                    .setBusTravelTime(busTicket.getBusTimetable().getTime()).setBusTicketType(busTicket.getType())
                            .setBusOperator(busTicket.getBusTimetable().getBus().getBusOperator().getName())
                                    .setBusName(busTicket.getBusTimetable().getBus().getBusName());
            busPlanDetails.add(busPlanDetail);
        }
        //to get all the train plan detail
        for (TrainTicket trainTicket : allTrainTickets){
            TrainPlanDetail trainPlanDetail = new TrainPlanDetail();
            trainPlanDetail.setTrainTicketId(trainTicket.getTrainTicketId()).setStartPoint(trainTicket.getTrainTimetable().getRouteDetail().getStartPointGps().getName()).setEndPoint(trainTicket.getTrainTimetable().getRouteDetail().getEndPointGps().getName()).setTrainTicketPrice(trainTicket.getPrice()).setTrainPlanQuantity(planTrainTicketMapper.findPlanTrainTicketQuantity(trainTicket,planId)).setTrainTravelDate(trainTicket.getTrainTimetable().getDate())
                    .setTrainTravelTime(trainTicket.getTrainTimetable().getTime()).setTrainTicketType(trainTicket.getType())
                            .setTrainOperator(trainTicket.getTrainTimetable().getTrain().getTrainOperator().getName())
                                    .setTrainName(trainTicket.getTrainTimetable().getTrain().getTrainName());
            trainPlanDetails.add(trainPlanDetail);
        }
        //set these attributes to response
        response.setPlanName(planName).setCastleName(castleName).setDate(date).setGuestNumber(guestNumber)
                .setPlanStatus(planStatus).setCastleId(castleId).setCastleTicketPrice(castleTicketPrice).setCastleDescription(castleDescription)
                .setCastleTicketId(castleTicketId).setCastleTicketType(castleTicketType).setCastleTravelDate(castleTravelDate)
                .setCastleTravelTime(castleTravelTime).setBusPlanDetails(busPlanDetails).setTrainPlanDetails(trainPlanDetails);
        return new ResultData(response,200,"view the detail of the plan");
    }
}
