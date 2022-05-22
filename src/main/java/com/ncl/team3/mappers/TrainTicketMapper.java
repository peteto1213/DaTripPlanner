package com.ncl.team3.mappers;

import com.ncl.team3.models.BusTicket;
import com.ncl.team3.models.Train;
import com.ncl.team3.models.TrainTicket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 21:56:34
 */

@Repository
public interface TrainTicketMapper extends JpaRepository<TrainTicket,Integer>,
        JpaSpecificationExecutor<TrainTicket>,
        PagingAndSortingRepository<TrainTicket,Integer> {
    TrainTicket findByTrainTicketId(Integer id);

    @Query("update TrainTicket set quantity.quantity = quantity.quantity + ?2 where trainTicketId = ?1")
    void increaseTrainTicketById(Integer id,Integer ticketNum);

    @Query("update TrainTicket set quantity.quantity = quantity.quantity - ?2 where trainTicketId = ?1")
    void decreaseTrainTicketById(Integer id,Integer ticketNum);

    @Query("update TrainTicket set quantity.quantity = ?2 where trainTicketId = ?1")
    void updateTrainTicketById(Integer id,Integer ticketNum);
}
