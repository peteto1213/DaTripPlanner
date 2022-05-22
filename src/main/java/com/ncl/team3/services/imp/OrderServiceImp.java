package com.ncl.team3.services.imp;

import com.ncl.team3.exception.TicketUnavailableException;
import com.ncl.team3.exception.WrongCardDetailException;
import com.ncl.team3.mappers.*;
import com.ncl.team3.models.*;
import com.ncl.team3.request.CreateOrderRequest;
import com.ncl.team3.request.HorsePayRequest;
import com.ncl.team3.response.*;
import com.ncl.team3.services.OrderService;
import com.ncl.team3.util.PaymentFormatUtil;
import com.ncl.team3.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Weidong Zhang
 * @version 2.0
 * @StudentNumber: 210004612
 * @date 2022/05/01 15:48:37
 */

@Service
public class OrderServiceImp implements OrderService {

    private RedisTemplate<String,String> redisTemplate;

    private RestTemplate restTemplate;

    private PaymentFormatUtil paymentFormatUtil;

    private OrderMapper orderMapper;

    private PlanMapper planMapper;

    private CastleTicketMapper castleTicketMapper;

    private BusTicketMapper busTicketMapper;

    private TrainTicketMapper trainTicketMapper;

    private PlanCastleTicketMapper planCastleTicketMapper;

    private PlanBusTicketMapper planBusTicketMapper;

    private PlanTrainTicketMapper planTrainTicketMapper;

    private OrderCastleTicketMapper orderCastleTicketMapper;

    private OrderBusTicketMapper orderBusTicketMapper;

    private OrderTrainTicketMapper orderTrainTicketMapper;

    @Autowired
    public OrderServiceImp(RedisTemplate<String, String> redisTemplate, RestTemplate restTemplate, PaymentFormatUtil paymentFormatUtil, OrderMapper orderMapper, PlanMapper planMapper, CastleTicketMapper castleTicketMapper, BusTicketMapper busTicketMapper, TrainTicketMapper trainTicketMapper, PlanCastleTicketMapper planCastleTicketMapper, PlanBusTicketMapper planBusTicketMapper, PlanTrainTicketMapper planTrainTicketMapper, OrderCastleTicketMapper orderCastleTicketMapper, OrderBusTicketMapper orderBusTicketMapper, OrderTrainTicketMapper orderTrainTicketMapper) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.paymentFormatUtil = paymentFormatUtil;
        this.orderMapper = orderMapper;
        this.planMapper = planMapper;
        this.castleTicketMapper = castleTicketMapper;
        this.busTicketMapper = busTicketMapper;
        this.trainTicketMapper = trainTicketMapper;
        this.planCastleTicketMapper = planCastleTicketMapper;
        this.planBusTicketMapper = planBusTicketMapper;
        this.planTrainTicketMapper = planTrainTicketMapper;
        this.orderCastleTicketMapper = orderCastleTicketMapper;
        this.orderBusTicketMapper = orderBusTicketMapper;
        this.orderTrainTicketMapper = orderTrainTicketMapper;
    }

    public User authoriseLogin(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * this method is to create the order based on the plan
     * @param createOrderRequest:this request contains all the parameters that need to verify
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData createOrder(CreateOrderRequest createOrderRequest) {
        if (authoriseLogin() == null){
            return new ResultData(404,"user not login");
        }
        User user = authoriseLogin();
        Order order = new Order();
        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId).setUser(user).setDate(TimeUtil.getCurrentDate()).setTime(TimeUtil.getCurrentTime());
        //find all the plan castle ticket
        List<CastleTicket> allCastleTickets = planCastleTicketMapper.findAllCastleTicketIds(createOrderRequest.getPlanId());
        if (allCastleTickets != null){
            for (CastleTicket castleTicket : allCastleTickets){
                //find the plan castle ticket quantity
                Integer ticketQuantity = planCastleTicketMapper.findPlanCastleTicketQuantity(castleTicket, createOrderRequest.getPlanId());
                if (ticketQuantity > castleTicket.getQuantity()){
                    throw new TicketUnavailableException("ticket unavailable");
                }else {
                    castleTicket.setQuantity(castleTicket.getQuantity() - ticketQuantity);
                    castleTicketMapper.save(castleTicket);
                }
            }
        }
        //find all the plan bus ticket
        List<BusTicket> allBusTickets = planBusTicketMapper.findAllBusTicketIds(createOrderRequest.getPlanId());
        if (allBusTickets != null){
            for (BusTicket busTicket : allBusTickets){
                //find the plan bus ticket quantity
                Integer ticketQuantity = planBusTicketMapper.findPlanBusTicketQuantity(busTicket, createOrderRequest.getPlanId());
                if (ticketQuantity > busTicket.getQuantity().getQuantity()){
                    throw new TicketUnavailableException("ticket unavailable");
                }else {
                    busTicket.getQuantity().setQuantity(busTicket.getQuantity().getQuantity() - ticketQuantity);
                    busTicketMapper.save(busTicket);
                }
            }
        }
        //find all the plan train ticket
        List<TrainTicket> allTrainTickets = planTrainTicketMapper.findAllTrainTicketIds(createOrderRequest.getPlanId());
        if (allTrainTickets != null){
            for (TrainTicket trainTicket : allTrainTickets){
                //find the plan train ticket quantity
                Integer ticketQuantity = planTrainTicketMapper.findPlanTrainTicketQuantity(trainTicket,createOrderRequest.getPlanId());
                if (ticketQuantity > trainTicket.getQuantity().getQuantity()){
                    throw new TicketUnavailableException("ticket unavailable");
                }else {
                    trainTicket.getQuantity().setQuantity(trainTicket.getQuantity().getQuantity() - ticketQuantity);
                    trainTicketMapper.save(trainTicket);
                }
            }
        }
        // I simplify line 146-160 code -lei
        if (!paymentFormatUtil.verifyCardNumber(createOrderRequest.getCardNumber())){
            throw new WrongCardDetailException("wrong card number");
        }
        if (!paymentFormatUtil.verifyExpireDate(createOrderRequest.getExpireDate())){
            throw new WrongCardDetailException("wrong expire date");
        }
        if (!paymentFormatUtil.verifySecurityCode(createOrderRequest.getSecurityCode())){
            throw new WrongCardDetailException("wrong security code");
        }
        if (!paymentFormatUtil.verifyOwnerName(createOrderRequest.getOwnerName())){
            throw new WrongCardDetailException("wrong owner name");
        }
        HorsePayRequest horsePayRequest = new HorsePayRequest();
        horsePayRequest.setStoreID("Team03").setCustomerID("c1000461").setDate(TimeUtil.getCurrentDateForHorsePay())
                .setTime(TimeUtil.getCurrentTimeForHorsePay()).setTimeZone("GMT").setTransactionAmount(15.99F)
                .setCurrencyCode("GBP");
        HorsePayResponse horsePayResponse = restTemplate.postForObject("http://homepages.cs.ncl.ac.uk/daniel.nesbitt/CSC8019/HorsePay/HorsePay.php", horsePayRequest, HorsePayResponse.class);
        if (horsePayResponse.getPaymetSuccess().getReason().equals("payment successful")){
            return createSuccessfulOrder(createOrderRequest.getPlanId(),order, allCastleTickets, allBusTickets, allTrainTickets);
        }else {
            return createFailedOrder(order,createOrderRequest.getPlanId());
        }
    }

    /**
     * This method rebuild by lei, main write by weidong.
     * @param order: order info
     * @param planId: plan Id
     * @return ResultData
     */
    private ResultData createFailedOrder(Order order,String planId) {
        order.setStatus("failed");
        List<PlanCastleTicket> allPlanCastleTickets = planCastleTicketMapper.findAllPlanCastleTickets(planId);
        if (allPlanCastleTickets != null && allPlanCastleTickets.size() != 0){
            for (PlanCastleTicket planCastleTicket : allPlanCastleTickets){
                Integer planTicketQuantity = planCastleTicket.getCastleTicketQuantity();
                Integer ticketQuantity = planCastleTicket.getCastleTicket().getQuantity();
                planCastleTicket.getCastleTicket().setQuantity(ticketQuantity + planTicketQuantity);
                castleTicketMapper.save(planCastleTicket.getCastleTicket());
            }
        }
        List<PlanBusTicket> allPlanBusTickets = planBusTicketMapper.findAllPlanBusTickets(planId);
        if (allPlanBusTickets != null && allPlanBusTickets.size() != 0){
            for (PlanBusTicket planBusTicket : allPlanBusTickets){
                Integer planTicketQuantity = planBusTicket.getBusTicketQuantity();
                Integer ticketQuantity = planBusTicket.getBusTicket().getQuantity().getQuantity();
                planBusTicket.getBusTicket().getQuantity().setQuantity(ticketQuantity + planTicketQuantity);
                busTicketMapper.save(planBusTicket.getBusTicket());
            }
        }
        List<PlanTrainTicket> allPlanTrainTickets = planTrainTicketMapper.findAllPlanTrainTickets(planId);
        if (allPlanTrainTickets != null && allPlanTrainTickets.size() != 0){
            for (PlanTrainTicket planTrainTicket : allPlanTrainTickets){
                Integer planTicketQuantity = planTrainTicket.getTrainTicketQuantity();
                Integer ticketQuantity = planTrainTicket.getTrainTicket().getQuantity().getQuantity();
                planTrainTicket.getTrainTicket().getQuantity().setQuantity(ticketQuantity + planTicketQuantity);
                trainTicketMapper.save(planTrainTicket.getTrainTicket());
            }
        }
        orderMapper.save(order);
        return new ResultData(404,"payment unsuccessful,fail to create order");
    }

    /**
     * This method rebuild by lei, main written by weidong
     * @param order order info
     * @param allCastleTickets all castle tickets info
     * @param allBusTickets bus tickets list
     * @param allTrainTickets train tickets list
     * @return ResultData
     */
    private ResultData createSuccessfulOrder(String planId,Order order, List<CastleTicket> allCastleTickets, List<BusTicket> allBusTickets, List<TrainTicket> allTrainTickets) {
        for (CastleTicket castleTicket : allCastleTickets){
            Integer ticketQuantity = planCastleTicketMapper.findPlanCastleTicketQuantity(castleTicket,planId);
            OrderCastleTicket orderCastleTicket = new OrderCastleTicket();
            orderCastleTicket.setOrder(order).setCastleTicket(castleTicket).setCastleTicketQuantity(ticketQuantity);
            orderCastleTicketMapper.save(orderCastleTicket);
        }
        if (allBusTickets != null && allBusTickets.size() != 0){
            for (BusTicket busTicket : allBusTickets){
                Integer ticketQuantity = planBusTicketMapper.findPlanBusTicketQuantity(busTicket,planId);
                OrderBusTicket orderBusTicket = new OrderBusTicket();
                orderBusTicket.setOrder(order).setBusTicket(busTicket).setBusTicketQuantity(ticketQuantity);
                orderBusTicketMapper.save(orderBusTicket);
            }
        }
        if (allTrainTickets != null && allTrainTickets.size() != 0){
            for (TrainTicket trainTicket : allTrainTickets){
                Integer ticketQuantity = planTrainTicketMapper.findPlanTrainTicketQuantity(trainTicket,planId);
                OrderTrainTicket orderTrainTicket = new OrderTrainTicket();
                orderTrainTicket.setOrder(order).setTrainTicket(trainTicket).setTrainTicketQuantity(ticketQuantity);
                orderTrainTicketMapper.save(orderTrainTicket);
            }
        }
        order.setStatus("paid");
        orderMapper.save(order);
        return new ResultData(200,"successfully create the order");
    }

    /**
     * this method is used to view the brief order the user created
     * @param page:the start page
     * @param size:the page size
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData getOrderInfo(Integer page,Integer size) {
        if (authoriseLogin() == null) {
            return new ResultData(404, "user not login");
        }
        User user = authoriseLogin();
        PageRequest pageAble = PageRequest.of(page, size);
        Page<Order> orders = orderMapper.allOrders(user.getEmail(), "paid", pageAble);
        List<Order> orderContent = orders.getContent();
        //add by lei
        if (orderContent.isEmpty()) {
            return new ResultData(404,"empty order list");
        }
        List<ViewOrderResponse> list = new ArrayList<>();
        for (Order order : orderContent) {
            //get the castle tickets
            List<CastleTicket> allCastleTickets = orderCastleTicketMapper.findAllCastleTicketIds(order.getOrderId());
            //get the bus tickets
            List<BusTicket> allBusTickets = orderBusTicketMapper.findAllBusTicketIds(order.getOrderId());
            //get the train tickets
            List<TrainTicket> allTrainTickets = orderTrainTicketMapper.findAllTrainTicketIds(order.getOrderId());
            //if user do not book any transport tickets,the start point should be "No transport ticket"
            if (allBusTickets.size() == 0 && allTrainTickets.size() == 0){
                ViewOrderResponse response = new ViewOrderResponse();
                response.setOrderId(order.getOrderId()).setOrderStatus(order.getStatus()).setDate(allCastleTickets.get(0).getDate())
                        .setStartPoint("No transport ticket").setCastleName(allCastleTickets.get(0).getCastle().getCastleName())
                                .setPlanName(planMapper.findPlanNameByOrderCastleTicketId(allCastleTickets.get(0)).get(0));
                list.add(response);
                continue;
            }
            //if the List<BusTicket> is not null and size not equals 0,to get the start point and the visit date
            if (allBusTickets != null && allBusTickets.size() != 0) {
                BusTicket busTicket = allBusTickets.get(0);
                ViewOrderResponse response = new ViewOrderResponse();
                String startPoint = busTicket.getBusTimetable().getRouteDetail().getRoute().getDepartureGps().getName();
                response.setOrderId(order.getOrderId()).setOrderStatus(order.getStatus()).setDate(allCastleTickets.get(0).getDate())
                        .setStartPoint(startPoint).setCastleName(allCastleTickets.get(0).getCastle().getCastleName())
                                .setPlanName(planMapper.findPlanNameByOrderCastleTicketId(allCastleTickets.get(0)).get(0));
                list.add(response);
                continue;
            }else if (allTrainTickets != null && allTrainTickets.size() != 0){
            //if the List<TrainTicket> is not null and size not equals 0,to get the start point and the visit dateif (allTrainTickets != null && allTrainTickets.size() != 0) {
                TrainTicket trainTicket = allTrainTickets.get(0);
                String startPoint = trainTicket.getTrainTimetable().getRouteDetail().getRoute().getDepartureGps().getName();
                ViewOrderResponse response = new ViewOrderResponse();
                response.setOrderId(order.getOrderId()).setOrderStatus(order.getStatus()).setDate(allCastleTickets.get(0).getDate())
                        .setStartPoint(startPoint).setCastleName(allCastleTickets.get(0).getCastle().getCastleName())
                                .setPlanName(planMapper.findPlanNameByOrderCastleTicketId(allCastleTickets.get(0)).get(0));
                list.add(response);
                continue;
            }
        }
        return new ResultData(list,200, "view all orders");
    }

    /**
     * this method is used to get the castle order info
     * @param castleOrderId:the castle order's ID
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData getCastleOrderInfo(Integer castleOrderId) {
        if (authoriseLogin() == null) {
            return new ResultData(404, "user not login");
        }
        CastleOrderInfoResponse response = new CastleOrderInfoResponse();
        OrderCastleTicket orderCastleTicket = orderCastleTicketMapper.getById(castleOrderId);
        CastleTicket castleTicket = orderCastleTicketMapper.findCastleTicketById(castleOrderId);
        response.setCastleName(castleTicket.getCastle().getCastleName()).setCastleTicketType(castleTicket.getType())
                .setGuestNumber(orderCastleTicketMapper.findOrderCastleTicketQuantity(castleTicket,orderCastleTicket.getOrder().getOrderId()))
                .setCastleTicketTravelDate(castleTicket.getDate()).setCastleTicketTravelTime(castleTicket.getTime());
        return new ResultData(response,200,"view the castle order info");
    }

    /**
     * this method is used to get the bus order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData getBusOrderInfo(String orderId) {
        if (authoriseLogin() == null) {
            return new ResultData(404, "user not login");
        }
        BusOrderInfoResponse response = new BusOrderInfoResponse();
        List<BusOrderDetail> busOrderDetails = new ArrayList<>();
        List<BusTicket> allBusTickets = orderBusTicketMapper.findAllBusTicketIds(orderId);
        for (BusTicket busTicket : allBusTickets){
            BusOrderDetail busOrderDetail = new BusOrderDetail();
            busOrderDetail.setBusTicketId(busTicket.getBusTicketId()).setBusName(busTicket.getBusTimetable().getBus().getBusName())
                    .setBusTicketType(busTicket.getType()).setBusTravelDate(busTicket.getBusTimetable().getDate())
                    .setBusTravelTime(busTicket.getBusTimetable().getTime()).setBusOrderQuantity(orderBusTicketMapper.findOrderBusTicketQuantity(busTicket,orderId))
                    .setBusOperator(busTicket.getBusTimetable().getBus().getBusOperator().getName());
            busOrderDetails.add(busOrderDetail);
        }
        response.setBusOrderDetails(busOrderDetails);
        return new ResultData(response,200,"view the bus order info");
    }

    /**
     * this method is used to get the train order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    public ResultData getTrainOrderInfo(String orderId) {
        if (authoriseLogin() == null) {
            return new ResultData(404, "user not login");
        }
        TrainOrderInfoResponse response = new TrainOrderInfoResponse();
        List<TrainOrderDetail> trainOrderDetails = new ArrayList<>();
        List<TrainTicket> allTrainTickets = orderTrainTicketMapper.findAllTrainTicketIds(orderId);
        for (TrainTicket trainTicket : allTrainTickets){
            TrainOrderDetail trainOrderDetail = new TrainOrderDetail();
            Integer num = orderTrainTicketMapper.findOrderTrainTicketQuantity(trainTicket,orderId);
            trainOrderDetail.setTrainTicketId(trainTicket.getTrainTicketId()).setTrainName(trainTicket.getTrainTimetable().getTrain().getTrainName())
                    .setTrainTicketType(trainTicket.getType()).setTrainTravelDate(trainTicket.getTrainTimetable().getDate())
                    .setTrainTravelTime(trainTicket.getTrainTimetable().getTime()).setTrainOrderQuantity(num)
                    .setTrainOperator(trainTicket.getTrainTimetable().getTrain().getTrainOperator().getName());
            trainOrderDetails.add(trainOrderDetail);
        }
        response.setTrainOrderDetails(trainOrderDetails);
        return new ResultData(response,200,"view the train order info");
    }

    /**
     * this method is used to view the single order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResultData getSingleOrderInfo(String orderId) {
        if (authoriseLogin() == null) {
            return new ResultData(404, "user not login");
        }
        SingleOrderInfoResponse response = new SingleOrderInfoResponse();
        CastleOrderInfoResponse castleOrderInfoResponse = new CastleOrderInfoResponse();
        List<BusOrderDetail> busOrderDetails = new ArrayList<>();
        List<TrainOrderDetail> trainOrderDetails = new ArrayList<>();
        CastleTicket castleTicket = orderCastleTicketMapper.findAllCastleTicketIds(orderId).get(0);
        String planName = planMapper.findPlanNameByOrderCastleTicketId(castleTicket).get(0);
        List<BusTicket> allBusTickets = orderBusTicketMapper.findAllBusTicketIds(orderId);
        List<TrainTicket> allTrainTickets = orderTrainTicketMapper.findAllTrainTicketIds(orderId);
        List<Integer> orderCastleIds = orderCastleTicketMapper.findOrderCastleIds(orderId);
        List<Integer> orderBusIds = orderBusTicketMapper.findOrderBusIds(orderId);
        List<Integer> orderTrainIds = orderTrainTicketMapper.findOrderTrainIds(orderId);
        castleOrderInfoResponse.setCastleName(castleTicket.getCastle().getCastleName())
                .setGuestNumber(orderCastleTicketMapper.findOrderCastleTicketQuantity(castleTicket,orderId))
                .setCastleTicketType(castleTicket.getType()).setCastleTicketTravelDate(castleTicket.getDate())
                .setCastleTicketTravelTime(castleTicket.getTime());
        for (BusTicket busTicket : allBusTickets){
            BusOrderDetail busOrderDetail = new BusOrderDetail();
            busOrderDetail.setBusTicketId(busTicket.getBusTicketId()).setStartPoint(busTicket.getBusTimetable().getRouteDetail().getStartPointGps().getName()).setEndPoint(busTicket.getBusTimetable().getRouteDetail().getEndPointGps().getName())
                    .setBusName(busTicket.getBusTimetable().getBus().getBusName())
                    .setBusTicketType(busTicket.getType()).setBusTravelDate(busTicket.getBusTimetable().getDate())
                    .setBusTravelTime(busTicket.getBusTimetable().getTime()).setBusOrderQuantity(orderBusTicketMapper.findOrderBusTicketQuantity(busTicket,orderId))
                    .setBusOperator(busTicket.getBusTimetable().getBus().getBusOperator().getName());
            busOrderDetails.add(busOrderDetail);
        }
        for (TrainTicket trainTicket : allTrainTickets){
            TrainOrderDetail trainOrderDetail = new TrainOrderDetail();
            Integer num = orderTrainTicketMapper.findOrderTrainTicketQuantity(trainTicket,orderId);
            trainOrderDetail.setTrainTicketId(trainTicket.getTrainTicketId()).setStartPoint(trainTicket.getTrainTimetable().getRouteDetail().getStartPointGps().getName()).setEndPoint(trainTicket.getTrainTimetable().getRouteDetail().getEndPointGps().getName())
                    .setTrainName(trainTicket.getTrainTimetable().getTrain().getTrainName())
                    .setTrainTicketType(trainTicket.getType()).setTrainTravelDate(trainTicket.getTrainTimetable().getDate())
                    .setTrainTravelTime(trainTicket.getTrainTimetable().getTime()).setTrainOrderQuantity(num)
                    .setTrainOperator(trainTicket.getTrainTimetable().getTrain().getTrainOperator().getName());
            trainOrderDetails.add(trainOrderDetail);
        }
        response.setOrderCastleIds(orderCastleIds).setPlanName(planName).setCastleOrderInfoResponse(castleOrderInfoResponse)
                .setOrderBusIds(orderBusIds).setBusPlanDetails(busOrderDetails).setOrderTrainIds(orderTrainIds)
                .setTrainPlanDetails(trainOrderDetails);
        return new ResultData(response,200,"view the single order info");
    }

    /**
     * this method is to delete the order(set the status to "deleted"
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    public ResultData deleteOrder(String orderId) {
        if (authoriseLogin() == null) {
            return new ResultData(404, "user not login");
        }
        Order order = orderMapper.findByOrderId(orderId);
        order.setStatus("deleted");
        orderMapper.save(order);
        return new ResultData(200,"successfully delete the order");
    }
}
