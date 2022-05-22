package com.ncl.team3.mappers;

import com.ncl.team3.models.Castle;
import com.ncl.team3.models.CastleTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastleTicketMapper extends JpaRepository<CastleTicket,Integer>, PagingAndSortingRepository<CastleTicket,Integer> {

    @Query("update CastleTicket set quantity = quantity + ?2 where castleTicketId = ?1")
    void increaseCastleTicketById(Integer id,Integer ticketNum);

    @Query("update CastleTicket set quantity = ?2 where castleTicketId = ?1")
    void updateCastleTicketById(Integer id,Integer ticketNum);

    @Query("from CastleTicket where castle.castleId=:castleId and date =:date ")
    List<CastleTicket> getCastleTicketPrice(Integer castleId,String date);

    @Query(value = "SELECT date FROM t_castle_ticket where castle_id = :castleId and  date>=:currentDate GROUP BY date",nativeQuery = true)
    List<String> getAvailableDateByCastleId(Integer castleId,String currentDate);

    @Query("select time  from CastleTicket  where castle.castleId =:castleId  and date = :date group by time" )
    List<String> getAvailableTimeByCastleId(Integer castleId,String date);


    @Query("from CastleTicket where date=:date and castle.castleId =:castleId")
    List<CastleTicket> getCastleTicketByDateAndTimeAndAndCastleId(String date, Integer castleId);



}
