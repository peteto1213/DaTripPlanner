package com.ncl.team3.controllers.imp;

import com.ncl.team3.controllers.RouteController;
import com.ncl.team3.models.ResultData;
import com.ncl.team3.services.RouteService;
import com.ncl.team3.util.TimeUtil;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * This class mainly provides the front-end with interfaces related to route and ticket information.
 * The main function is to return data and perform checks and preliminary processing on the incoming request data from the front end.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 10:12:49
 */
@RestController
@RequestMapping("/route")
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
@Slf4j
public class RouteControllerImp implements RouteController {
    @Autowired
    private RouteService routeService;


    /**
     * The method will return the route detail and the ticket information according the date , time and destination.
     * @param castleId Destination id
     * @param date Departure dates.For example, if the departure date is 20 March 2022, then the incoming 20032022
     * @param departureTime If departure time is 5.30pm, then incoming 1730
     * @param ticketQuantity Quantity should be less than 6
     * @return
     */
    @Override
    @GetMapping("/getRoute")
    public ResultData getRoute(int castleId, @NotNull String date,@NotNull String departureTime,@NotNull Integer ticketQuantity) {
        log.info("get Route...");
        if (!TimeUtil.checkTimeFormat(departureTime)) {
            return new ResultData(404,"The format of  time  is incorrect.");
        }
        if (!TimeUtil.checkDateFormat(date)) {
            return new ResultData(404,"The format  of date  is incorrect.");
        }
        if (ticketQuantity == null){
            log.warn("the ticketQuantity is null , set the ticketQuantity == 5");
            ticketQuantity = 5;
        }
        return routeService.getRoute( castleId,  date, departureTime,ticketQuantity);
    }

    /**
     * It is our system main function. it can search the database base on the departure time and ticket info to sort the available route.
     * @param castleId castle id
     * @param date The departure date the user wants to return to
     * @param departureTime The departure time the user wants to return to
     * @param ticketQuantity Number of tickets required by the user
     * @return
     */

    @Override
    @GetMapping("/getReturnRoute")
    public ResultData getReturnRoute(Integer castleId,@NotNull String date,String departureTime,Integer ticketQuantity){
        if (castleId == null){
            return new ResultData(404,"please check the parameter of castle id");
        }
        if (date == null){
            return new ResultData(404,"please check the parameter of date");
        }
        if (departureTime == null){
            return new ResultData(404,"please check the parameter of departureTime");
        }
        if (ticketQuantity == null | ticketQuantity<=0 ){
            return new ResultData(404,"please check the parameter of ticketQuantity");
        }
       return routeService.getReturnRoute(castleId,date,departureTime,ticketQuantity);
    }

    /**
     * Data example:
     *
     * [
     *     1,2
     * ]
     * @param routeDetailIds ids of route detail
     * @return ResultData
     */
    @Override
    @GetMapping("/getRouteDetailByRouteDetailIds")
    public ResultData getRouteDetailByRouteDetailIds(@RequestBody ArrayList<Integer> routeDetailIds){
        if (routeDetailIds == null){
            return new ResultData(404,"The parameter of routeDetailId is null.");
        }
        log.info("getRouteDetail size = " + routeDetailIds.size()+"");
        return routeService.getRouteDetail(routeDetailIds);


    }

    /**
     * 获取公交车时刻表
     * @param departureId 出发地Id
     * @param castleId 目的地城堡id
     * @param date 预计出发日期 20220325
     * @param time 预计出发时间 1122
     * @return
     */
    @Override
    @GetMapping("/getBusTimetableByCastleIdDateAndTime")
    public ResultData getBusTimetableByCastleIdDateAndTime(@NotNull Integer departureId, @NotNull Integer castleId,@NotNull String date ,@NotNull String time) {
        if (!TimeUtil.checkTimeFormat(date)) {
            return new ResultData(404,"The parameter of date does not meet the required format of date.");
        }
        return routeService.getBusTimetableByCastleIdDateAndTime(departureId,castleId,date,time);
    }

    /**
     * 获取路线信息通过目的地城堡的id
     * 返回数据参考：
     *{
     *     "data": {
     *         "1": [
     *             {
     *                 "routeDetailId": 1,
     *                 "route": {
     *                     "routeId": 1,
     *                     "routeName": "Haymarket To Alnwick Castle Bus Route By X15 max",
     *                     "departureGps": {
     *                         "gpsId": 6,
     *                         "name": "Haymarket bus station",
     *                         "longitude": "-1.6146524905880326",
     *                         "altitude": "54.976968764271376",
     *                         "addressCode": "NE1 7RY"
     *                     },
     *                     "destinationGps": {
     *                         "gpsId": 1,
     *                         "name": "Alnwick Castle",
     *                         "longitude": "-1.7059633176049682",
     *                         "altitude": "55.41571066816563",
     *                         "addressCode": "NE66 1NQ"
     *                     }
     *                 },
     *                 "startPointGps": {
     *                     "gpsId": 6,
     *                     "name": "Haymarket bus station",
     *                     "longitude": "-1.6146524905880326",
     *                     "altitude": "54.976968764271376",
     *                     "addressCode": "NE1 7RY"
     *                 },
     *                 "endPointGps": {
     *                     "gpsId": 12,
     *                     "name": "Playhouse",
     *                     "longitude": "-1.7026578567583743",
     *                     "altitude": "55.41210566995569",
     *                     "addressCode": "NE66 1PR"
     *                 },
     *                 "type": "bus",
     *                 "name": "X15 Max From Haymarket To PlayHouse Then walk 7 mintues",
     *                 "order": 1
     *             }
     *         ],
     *         "2": [
     *             {
     *                 "routeDetailId": 2,
     *                 "route": {
     *                     "routeId": 2,
     *                     "routeName": "Newcastle Train Station To Alnwick Castle Train Route And Bus Route",
     *                     "departureGps": {
     *                         "gpsId": 7,
     *                         "name": "Newcastle Train Station",
     *                         "longitude": "-1.6170098282516339",
     *                         "altitude": "54.968586101194255",
     *                         "addressCode": "NE1 5DL"
     *                     },
     *                     "destinationGps": {
     *                         "gpsId": 1,
     *                         "name": "Alnwick Castle",
     *                         "longitude": "-1.7059633176049682",
     *                         "altitude": "55.41571066816563",
     *                         "addressCode": "NE66 1NQ"
     *                     }
     *                 },
     *                 "startPointGps": {
     *                     "gpsId": 7,
     *                     "name": "Newcastle Train Station",
     *                     "longitude": "-1.6170098282516339",
     *                     "altitude": "54.968586101194255",
     *                     "addressCode": "NE1 5DL"
     *                 },
     *                 "endPointGps": {
     *                     "gpsId": 17,
     *                     "name": "Alnmouth Train Station",
     *                     "longitude": "-1.6366520392424546",
     *                     "altitude": "55.39278145037906",
     *                     "addressCode": "NE66 3QF"
     *                 },
     *                 "type": "train",
     *                 "name": "Newcastle To Alnmouth Train Station By LNER",
     *                 "order": 1
     *             },
     *             {
     *                 "routeDetailId": 3,
     *                 "route": {
     *                     "routeId": 2,
     *                     "routeName": "Newcastle Train Station To Alnwick Castle Train Route And Bus Route",
     *                     "departureGps": {
     *                         "gpsId": 7,
     *                         "name": "Newcastle Train Station",
     *                         "longitude": "-1.6170098282516339",
     *                         "altitude": "54.968586101194255",
     *                         "addressCode": "NE1 5DL"
     *                     },
     *                     "destinationGps": {
     *                         "gpsId": 1,
     *                         "name": "Alnwick Castle",
     *                         "longitude": "-1.7059633176049682",
     *                         "altitude": "55.41571066816563",
     *                         "addressCode": "NE66 1NQ"
     *                     }
     *                 },
     *                 "startPointGps": {
     *                     "gpsId": 9,
     *                     "name": "Curly Lane North End",
     *                     "longitude": "-1.6349489868392497",
     *                     "altitude": "55.392625233099835",
     *                     "addressCode": "NE66 3QB"
     *                 },
     *                 "endPointGps": {
     *                     "gpsId": 12,
     *                     "name": "Playhouse",
     *                     "longitude": "-1.7026578567583743",
     *                     "altitude": "55.41210566995569",
     *                     "addressCode": "NE66 1PR"
     *                 },
     *                 "type": "bus",
     *                 "name": "Newcastle To Alnwick Castle Bus Route 2",
     *                 "order": 2
     *             }
     *         ]
     *     },
     *     "code": 200,
     *     "message": "ok"
     * }
     * @param castleId
     * @return
     */
    @Override
    @GetMapping("/getRouteDetailByCastleId")
    public ResultData getRouteDetailByCastleId(int castleId){
        log.info("Execute Route info");
        return routeService.getRouteDetail(castleId);
    }

    /**
     * This method provide interface to get the bus time table info by input the routeDetail id , date and time
     * @param routeDetailId route detail id
     * @param date the departure date
     * @param time the departure time
     * @return
     */
    @Override
    @GetMapping("/getBusTimetableByRouteDetailId")
    public ResultData getBusTimetableByRouteDetailId(@NotNull Integer routeDetailId,@NotNull String date,@NotNull String time){
        if (routeDetailId== null ||date == null ||time==null){
            return new ResultData(404,"please check the parameter not is null...");
        }
        return  routeService.getBusTimetableByRouteDetailId(routeDetailId,date,time);
    }


}
