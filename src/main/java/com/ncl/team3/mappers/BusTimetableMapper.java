package com.ncl.team3.mappers;

import com.ncl.team3.models.Bus;
import com.ncl.team3.models.BusTicket;
import com.ncl.team3.models.BusTimetable;
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
 * @date 2022/04/10 23:05:11
 */
@Repository
public interface BusTimetableMapper extends JpaRepository<BusTimetable,Integer>, JpaSpecificationExecutor<BusTimetable>, PagingAndSortingRepository<BusTimetable,Integer> {

    @Query("from BusTimetable where date=:date and routeDetail.route.routeId=:routeId order by routeDetail.order")
    List<BusTimetable> findByCastleIdDateAndTime(Integer routeId, String date);


    List<BusTimetable> findBusTimetableByBus(Bus bus);

    List<BusTimetable> findBusTimetableByRouteDetailAndDateAndTimeGreaterThanEqual(RouteDetail routeDetail,String date,String time);
}
