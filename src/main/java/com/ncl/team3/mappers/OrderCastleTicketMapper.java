package com.ncl.team3.mappers;

import com.ncl.team3.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCastleTicketMapper extends JpaRepository<OrderCastleTicket,Integer>, PagingAndSortingRepository<OrderCastleTicket,Integer> {

    @Query("from OrderCastleTicket o where o.order.orderId = ?1")
    List<OrderCastleTicket> queryAllCastleTicket(String orderId);
    @Query("delete from Order  where orderId = ?1")
    void deleteOrderById(String orderId);

    @Query(value = "select o.castleTicket from OrderCastleTicket o where o.order.orderId = ?1")
    List<CastleTicket> findAllCastleTicketIds(String orderId);

    @Query(value = "select o.castleTicket from OrderCastleTicket o where o.orderCastleTicketId = ?1")
    CastleTicket findCastleTicketById(Integer castleOrderId);

    @Query(value = "select o.castleTicketQuantity from OrderCastleTicket o where o.castleTicket = ?1 and o.order.orderId = ?2")
    Integer findOrderCastleTicketQuantity(CastleTicket castleTicket,String orderId);

    @Query(value = "select o.orderCastleTicketId from OrderCastleTicket o where o.order.orderId = ?1")
    List<Integer> findOrderCastleIds(String orderId);

}
