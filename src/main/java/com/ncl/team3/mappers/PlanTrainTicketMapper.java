package com.ncl.team3.mappers;

import com.ncl.team3.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTrainTicketMapper extends JpaRepository<PlanTrainTicket,Integer>, PagingAndSortingRepository<PlanTrainTicket,Integer> {

    @Modifying
    @Query(value = "delete from t_plan_train_ticket where plan_train_ticket_id = ?1",nativeQuery = true)
    void deleteTrainPlanById(Integer id);

    @Query(value = "select p.trainTicket from PlanTrainTicket p where p.plan.planId = ?1")
    List<TrainTicket> findAllTrainTicketIds(String planId);

    @Query(value = " select distinct  p.trainTicketQuantity  from PlanTrainTicket p where p.trainTicket = ?1 and p.plan.planId = ?2")
    Integer findPlanTrainTicketQuantity(TrainTicket trainTicket,String planId);

    @Query(value = "from PlanTrainTicket p where p.plan.planId = ?1")
    List<PlanTrainTicket> findAllPlanTrainTickets(String planId);
}
