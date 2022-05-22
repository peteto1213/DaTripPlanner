package com.ncl.team3.mappers;

import com.ncl.team3.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanBusTicketMapper extends JpaRepository<PlanBusTicket,Integer>, PagingAndSortingRepository<PlanBusTicket,Integer> {

    @Modifying
    @Query(value = "delete from t_plan_bus_ticket where plan_bus_ticket_id = ?1",nativeQuery = true)
    void deleteBusPlanById(Integer id);

    @Query(value = "select p.busTicket from PlanBusTicket p where p.plan.planId = ?1")
    List<BusTicket> findAllBusTicketIds(String planId);

    @Query(value = "select p.busTicketQuantity from PlanBusTicket p where p.busTicket = ?1 and p.plan.planId = ?2")
    Integer findPlanBusTicketQuantity(BusTicket busTicket,String planId);

    @Query(value = "from PlanBusTicket p where p.plan.planId = ?1")
    List<PlanBusTicket> findAllPlanBusTickets(String planId);
}
