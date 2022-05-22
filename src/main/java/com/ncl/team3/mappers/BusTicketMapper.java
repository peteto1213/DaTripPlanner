package com.ncl.team3.mappers;

import com.ncl.team3.models.BusTicket;
import com.ncl.team3.models.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 17:55:38
 */
@Repository
public interface BusTicketMapper extends JpaRepository<BusTicket,Integer>, JpaSpecificationExecutor<BusTicket>, PagingAndSortingRepository<BusTicket,Integer> {
    BusTicket findByBusTicketId(Integer id);

    @Query("update BusTicket set quantity.quantity = quantity.quantity + ?2 where busTicketId = ?1")
    void increaseBusTicketById(Integer id,Integer ticketNum);

    @Query("update BusTicket set quantity.quantity = quantity.quantity - ?2 where busTicketId = ?1")
    void decreaseBusTicketById(Integer id,Integer ticketNum);

    @Query("update BusTicket set quantity.quantity = ?2 where busTicketId = ?1")
    void updateBusTicketById(Integer id,Integer ticketNum);

}
