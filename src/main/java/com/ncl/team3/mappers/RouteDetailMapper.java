package com.ncl.team3.mappers;

import com.ncl.team3.models.PlanTrainTicket;
import com.ncl.team3.models.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 20:26:16
 */
@Repository
public interface RouteDetailMapper extends JpaRepository<RouteDetail,Integer> , PagingAndSortingRepository<RouteDetail,Integer> {
    @Query("from RouteDetail where route.routeId= :routeId order by 'order'")
    List<RouteDetail> findByRouteId(Integer routeId);


    @Query("from RouteDetail where  route.destinationGps.gpsId =:castleGpsId order by route.routeId")
    List<RouteDetail> getRouteDetailInfo(int castleGpsId);
}
