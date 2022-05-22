package com.ncl.team3.mappers;

import com.ncl.team3.models.Castle;
import com.ncl.team3.models.Route;

import com.ncl.team3.models.RouteDetail;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 19:14:09
 */
@Repository
public interface RouteMapper extends JpaRepository<Route,Integer>, JpaSpecificationExecutor<Route>, PagingAndSortingRepository<Route,Integer> {
    @Query("from Route where destinationGps.gpsId=:destinationGps order by 'route_detail_order'")
    List<Route> getRouteByCastleAndOrderByRouteDetail(int destinationGps);

    @Query("from Route where destinationGps.gpsId=:destinationId and departureGps.gpsId =:departureId")
    List<Route> findRouteByDateDepartureIdAndDestinationId(Integer departureId,Integer destinationId);

    @Query("from Route where departureGps.gpsId =:gpsId  order by 'route_detail_order'")
    List<Route> findRouteByDepartureGps(Integer gpsId);

}
