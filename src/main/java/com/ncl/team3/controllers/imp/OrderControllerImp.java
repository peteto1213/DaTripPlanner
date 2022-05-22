package com.ncl.team3.controllers.imp;

import com.ncl.team3.controllers.OrderController;
import com.ncl.team3.models.ResultData;
import com.ncl.team3.request.CreateOrderRequest;
import com.ncl.team3.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class is the implementation class of OrderController,
 * which mainly implements the response to front-end requests and user order-related information
 * as well as the data validation of the data sent from the front-end to the back-end.
 * @author Lei Chen & Weidong Zhang
 * @version 1.0
 * @StudentNumber: 200936497,210004612
 * @date 2022/03/13 11:41:32
 */
@RestController
@RequestMapping("/order")
@Slf4j
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
public class OrderControllerImp implements OrderController {

    OrderService orderService;

    @Autowired
    public OrderControllerImp(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * this method is to create the order based on the plan
     * @param createOrderRequest:this request contains all the parameters that need to verify
     * @return ResultData
     */
    @Override
    @PostMapping("/generateOrder")
    public ResultData createOrder(CreateOrderRequest createOrderRequest) {
        //Data validation
        if (createOrderRequest == null) {
            return new ResultData(404,"createOrderRequest == null");
        }
        if (createOrderRequest.getPlanId() == null){
            return new ResultData(404,"plan id is null.");
        }
        if (createOrderRequest.getCardNumber() == null){
            return new ResultData(404,"card number id is null.");
        }
        if (createOrderRequest.getExpireDate() == null){
            return new ResultData(404,"ExpireDate is null.");
        }
        if (createOrderRequest.getSecurityCode() == null){
            return new ResultData(404,"SecurityCode is null.");
        }
        if (createOrderRequest.getOwnerName() == null){
            return new ResultData(404,"OwnerName is null.");
        }
        return orderService.createOrder(createOrderRequest);
    }

    /**
     * this method is used to view the brief order the user created
     * @param page:the start page
     * @param size:the page size
     * @return ResultData
     */
    @Override
    @GetMapping("/viewOrderInfo")
    public ResultData getOrderInfo(Integer page,Integer size) {
        //Data validation
        if (page == null){
            return new ResultData(404,"page is null.");
        }
        if (size == null){
            return new ResultData(404,"size is null.");
        }
        return orderService.getOrderInfo(page,size);
    }

    /**
     * this method is used to get the castle order info
     * @param castleOrderId:the castle order's ID
     * @return ResultData
     */
    @Override
    @GetMapping("/viewCastleOrderInfo")
    public ResultData getCastleOrderInfo(Integer castleOrderId) {
        //Data validation
        if (castleOrderId == null){
            return new ResultData(404,"castleOrderId is null.");
        }
        return orderService.getCastleOrderInfo(castleOrderId);
    }

    /**
     * this method is used to get the bus order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    @GetMapping("/viewBusOrderInfo")
    public ResultData getBusOrderInfo(String orderId) {
        //Data validation
        if (orderId == null){
            return new ResultData(404,"orderId is null.");
        }
        return orderService.getBusOrderInfo(orderId);
    }

    /**
     * this method is used to get the bus order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    @GetMapping("/viewTrainOrderInfo")
    public ResultData getTrainOrderInfo(String orderId) {
        //Data validation
        if (orderId == null){
            return new ResultData(404,"orderId is null.");
        }
        return orderService.getTrainOrderInfo(orderId);
    }

    /**
     * this method is used to view the single order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    @GetMapping("/viewSingleOrderInfo")
    public ResultData getSingleOrderInfo(String orderId) {
        //Data validation
        if (orderId == null){
            return new ResultData(404,"orderId is null.");
        }
        return orderService.getSingleOrderInfo(orderId);
    }

    /**
     * this method is to delete the order(set the status to "deleted"
     * @param orderId:the order's ID
     * @return ResultData
     */
    @Override
    @DeleteMapping("/deleteOrder")
    public ResultData deleteOrder(String orderId) {
        //Data validation
        if (orderId == null){
            return new ResultData(404,"orderId is null.");
        }
        return orderService.deleteOrder(orderId);
    }
}
