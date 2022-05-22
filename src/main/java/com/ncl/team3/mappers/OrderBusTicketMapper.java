package com.ncl.team3.mappers;

import com.ncl.team3.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBusTicketMapper extends JpaRepository<OrderBusTicket,Integer>, PagingAndSortingRepository<OrderBusTicket,Integer> {

    @Query("from OrderBusTicket o where o.order.orderId = ?1")
    List<OrderBusTicket> queryAllBusTicket(String orderId);

    @Query(value = "select o.busTicket from OrderBusTicket o where o.order.orderId = ?1")
    List<BusTicket> findAllBusTicketIds(String orderId);

    @Query(value = "select o.busTicketQuantity from OrderBusTicket o where o.busTicket = ?1 and o.order.orderId = ?2")
    Integer findOrderBusTicketQuantity(BusTicket busTicket,String orderId);

    @Query(value = "select o.orderBusDetailId from OrderBusTicket o where o.order.orderId = ?1")
    List<Integer> findOrderBusIds(String orderId);
}
