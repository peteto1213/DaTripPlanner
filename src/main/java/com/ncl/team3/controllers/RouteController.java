package com.ncl.team3.controllers;

import com.ncl.team3.models.ResultData;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 10:00:19
 */

public interface RouteController {
    /**
     * This method filters the routes based on the incoming departure date, time, destination and people number ,
     * and returns the closest routes to the time of appearance.
     * @param castleId Destination id
     * @param date Departure dates.For example, if the departure date is 20 March 2022, then the incoming 20032022
     * @param departureTime If departure time is 5.30pm, then incoming 1730
     * @param ticketQuantity Quantity should be less than 6
     * @return
     */
    ResultData getRoute(int castleId, String date,String departureTime,Integer ticketQuantity) ;



    ResultData getReturnRoute(Integer castleId, @NotNull String date, String departureTime,Integer ticketQuantity);

    /**
     * Returns the timetable and ticket information for the route according to the RouteDetaiId list
     * @param routeDetailIds [1,2,3]
     * @return Returns detailed information about the corresponding route, including the start and end points of the route,
     * as well as timetable and ticket information
     */
    ResultData getRouteDetailByRouteDetailIds(ArrayList<Integer> routeDetailIds);

    /**
     *
     */
    ResultData getBusTimetableByCastleIdDateAndTime(@NotNull Integer departureId, @NotNull Integer castleId,@NotNull String date ,@NotNull String time);


    ResultData getRouteDetailByCastleId(int castleId);

    @GetMapping("/getBusTimetableByRouteDetailId")
    ResultData getBusTimetableByRouteDetailId(@NotNull Integer routeDetailId, @NotNull String date, @NotNull String time);
}
