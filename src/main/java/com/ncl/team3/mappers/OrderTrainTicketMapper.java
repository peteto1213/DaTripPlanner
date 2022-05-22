package com.ncl.team3.mappers;

import com.ncl.team3.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTrainTicketMapper extends JpaRepository<OrderTrainTicket,Integer>, PagingAndSortingRepository<OrderTrainTicket,Integer> {

    @Query("from OrderTrainTicket o where o.order.orderId = ?1")
    List<OrderTrainTicket> queryAllTrainTicket(String orderId);

    @Query(value = "select o.trainTicket from OrderTrainTicket o where o.order.orderId = ?1")
    List<TrainTicket> findAllTrainTicketIds(String orderId);

    @Query(value = "select o.trainTicketQuantity from OrderTrainTicket o where o.trainTicket = ?1 and o.order.orderId = ?2")
    Integer findOrderTrainTicketQuantity(TrainTicket trainTicket,String orderId);

    @Query(value = "select o.orderTrainTicketId from OrderTrainTicket o where o.order.orderId = ?1")
    List<Integer> findOrderTrainIds(String orderId);
}
