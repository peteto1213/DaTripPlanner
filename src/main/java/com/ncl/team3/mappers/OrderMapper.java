package com.ncl.team3.mappers;

import com.ncl.team3.models.Order;
import com.ncl.team3.models.OrderCastleTicket;
import com.ncl.team3.models.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends JpaRepository<Order,Integer>, PagingAndSortingRepository<Order,Integer> {

    @Query("select order from Order order where order.orderId = ?1")
    Order findByOrderId(String id);

    @Query("select order from Order order where order.user.email = ?1")
    List<Order> queryAllOrders(String email);

    @Query("from Order o where o.user.email = ?1 and o.status = ?2")
    Page<Order> allOrders(String email, String status,Pageable pageable);
}
