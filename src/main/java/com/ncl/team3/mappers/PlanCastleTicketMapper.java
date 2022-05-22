package com.ncl.team3.mappers;

import com.ncl.team3.models.CastleTicket;
import com.ncl.team3.models.Plan;
import com.ncl.team3.models.PlanBusTicket;
import com.ncl.team3.models.PlanCastleTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanCastleTicketMapper extends JpaRepository<PlanCastleTicket,Integer>, PagingAndSortingRepository<PlanCastleTicket,Integer> {

    @Modifying
    @Query(value = "delete from t_plan_castle_ticket where plan_castle_ticket_id = ?1",nativeQuery = true)
    void deleteCastlePlanById(Integer id);

    @Query(value = "select p.castleTicket from PlanCastleTicket p where p.plan.planId = ?1")
    List<CastleTicket> findAllCastleTicketIds(String planId);

    @Query(value = "select p.castleTicketQuantity from PlanCastleTicket p where p.castleTicket = ?1 and p.plan.planId = ?2")
    Integer findPlanCastleTicketQuantity(CastleTicket castleTicket,String planId);

    @Query(value = "from PlanCastleTicket p where p.plan.planId = ?1")
    List<PlanCastleTicket> findAllPlanCastleTickets(String planId);
}
