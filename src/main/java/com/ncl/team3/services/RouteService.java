package com.ncl.team3.services;

import com.ncl.team3.models.ResultData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 10:15:39
 */
@Service
public interface RouteService {

    ResultData getRoute(int castleId,String date,String departureTime,int ticketQuantity);

    ResultData getRouteDetail(ArrayList<Integer> routeDetailId);

    ResultData getBusTimetableByCastleIdDateAndTime(Integer departureId,Integer castleId, String date, String time);

    ResultData getRouteDetail(int castleId);

    ResultData getBusTimetableByRouteDetailId(Integer routeDetailId, String date, String time);


    ResultData getReturnRoute(Integer castleId, String date, String departureTime,int ticketQuantity);

}
