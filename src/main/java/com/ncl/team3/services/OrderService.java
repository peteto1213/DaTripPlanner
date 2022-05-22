package com.ncl.team3.services;

import com.ncl.team3.models.ResultData;
import com.ncl.team3.request.CreateOrderRequest;
import org.springframework.stereotype.Service;

/**
 * @author Weidong Zhang
 * @version 2.0
 * @StudentNumber: 200936497
 * @date 2022/05/01 15:50:21
 */
@Service
public interface OrderService {

    /**
     * this method is to create the order based on the plan
     * @param createOrderRequest:this request contains all the parameters that need to verify
     * @return ResultData
     */
    ResultData createOrder(CreateOrderRequest createOrderRequest);

    /**
     * this method is used to view the brief order the user created
     * @param page:the start page
     * @param size:the page size
     * @return ResultData
     */
    ResultData getOrderInfo(Integer page,Integer size);

    /**
     * this method is used to get the castle order info
     * @param castleOrderId:the castle order's ID
     * @return ResultData
     */
    ResultData getCastleOrderInfo(Integer castleOrderId);

    /**
     * this method is used to get the bus order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    ResultData getBusOrderInfo(String orderId);

    /**
     * this method is used to get the train order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    ResultData getTrainOrderInfo(String orderId);

    /**
     * this method is used to view the single order info
     * @param orderId:the order's ID
     * @return ResultData
     */
    ResultData getSingleOrderInfo(String orderId);

    /**
     * this method is to delete the order(set the status to "deleted"
     * @param orderId:the order's ID
     * @return ResultData
     */
    ResultData deleteOrder(String orderId);
}
