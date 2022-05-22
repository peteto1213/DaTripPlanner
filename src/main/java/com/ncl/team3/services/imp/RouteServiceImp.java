package com.ncl.team3.services.imp;

import com.ncl.team3.mappers.*;
import com.ncl.team3.models.*;
import com.ncl.team3.services.RouteService;
import com.ncl.team3.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.sql.Time;
import java.util.*;

import static antlr.build.ANTLR.root;

/**
 * This class specifies the logical processing and data acquisition
 * for replication and route-related operations
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 10:16:26
 */
@Service
@Slf4j
public class RouteServiceImp implements RouteService {
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private BusTimetableMapper busTimetableMapper;
    @Autowired
    private RouteDetailMapper routeDetailMapper;
    @Autowired
    private BusTicketMapper busTicketMapper;
    @Autowired
    private TrainTicketMapper trainTicketMapper;
    @Autowired
    private CastleMapper castleMapper;

    /**
     * This is a method can according to the castle id, departure date and time to search the route.
     * @param castleId castle id;
     * @param date departure date
     * @param departureTime departure time
     * @param ticketQuantity people number
     * @return route info
     */
    @Override
    public ResultData getRoute(int castleId, String date,String departureTime,int ticketQuantity) {
        log.info("get Route...");
        //根据Id查询城堡路线id
        //查询城堡的目的地gps Id;
        Castle byCastleId = castleMapper.findByCastleId(castleId);
        if (byCastleId == null){
            return new ResultData(404,"No this castle id");
        }
        //获取route id 并根据RouteDetail order 排序
        List<Route> routeByCastle = routeMapper.getRouteByCastleAndOrderByRouteDetail(byCastleId.getCastleGps().getGpsId());
        if (routeByCastle==null){
            return new ResultData(404,"No route found to this castle");
        }
        HashMap<String, List> container = new HashMap<>();
        //遍历路线，routeDetail大小,去判断是否是多路线 Iterate over routes, routeDetail size, to determine if it is a multi-route
        for (int i = 0; i < routeByCastle.size(); i++) {
            log.info("执行第"+i+"次. 处理routeId = " + routeByCastle.get(i).getRouteId());

            String newDepartureTime = departureTime;
            Route route = routeByCastle.get(i);
            //获取路线细节对象 Get route details object
            List<RouteDetail> routeDetail = route.getRouteDetail();
            int routeDetailSize = routeDetail.size();
            ArrayList<Object> tempList = new ArrayList<>();
            for (int j = 0; j < routeDetailSize; j++) {
                log.info("开始处理 routeDetail 内容，routeDetail == " +routeDetail.get(j).getRouteDetailId() +"");

                RouteDetail routeDetail1 = routeDetail.get(j);
                Integer routeDetailId = routeDetail1.getRouteDetailId();
                String type = routeDetail1.getType();

                if (type.equalsIgnoreCase("bus")){
                    BusTicket ticket = findTickets(routeDetailId, date, newDepartureTime, new BusTicket(), ticketQuantity);

                    if (ticket!=null){
                        //更新时间
                        newDepartureTime = ticket.getBusTimetable().getTime();
                        newDepartureTime = TimeUtil.timeAdd(newDepartureTime, ticket.getBusTimetable().getTravelTime());
                        tempList.add(ticket);
                    }
                    else {
                        log.warn("bus ticket == null");
                        break;
                    }
                }
                if (type.equalsIgnoreCase("train")){
                    TrainTicket ticket = findTickets(routeDetailId, date, newDepartureTime, new TrainTicket(), ticketQuantity);
                    if (ticket!=null){
                        //更新出发时间 Updated departure times
                        newDepartureTime = ticket.getTrainTimetable().getTime();
                        newDepartureTime = TimeUtil.timeAdd(newDepartureTime, ticket.getTrainTimetable().getTravelTime());
                        tempList.add(ticket);
                    }
                    else {
                        log.warn("train ticket == null");
                        break;
                    }
                }
                //根据Id查询数据库，将最符合出发时间的时刻加入
                //qQuery the database based on the Id and add the moment that best matches the departure time
            }
            //double-check the size is for complete route.
            if (tempList!=null){
                log.info("tempList size == "+tempList.size());
                if (tempList.size() == routeDetailSize && tempList.size()>0){
                    container.put("route_id:"+route.getRouteId()+",route_name:"+route.getRouteName(),tempList);
                }
                tempList = new ArrayList<>();
            }
        }
        if (container==null|container.size()==0){
            return new ResultData(404,"No available route for this search, please check the parameter.");
        }
        //根据查询的routeId 去获取对应的车票信息
        return new ResultData(container,200,"ok");
    }


    /**
     * This is general method to find the ticket list by input these parameters.
     * @param routeId route id
     * @param date departure date such as 20200928
     * @param time departure time
     * @param ticketModel the entity model for check the ticket type.
     * @param ticketQuantity the ticket quantity must garter then 0
     * @param <T> the generfiy type
     * @return
     */

    private <T> T findTickets(int routeId,String date, String time,T ticketModel,int ticketQuantity){
        Integer departureTime = Integer.valueOf(time);
        if (ticketModel instanceof BusTicket){
            Specification<BusTicket> specification = (root, query, criteriaBuilder) -> {
                Path<BusTimetable> busTimetablePath = root.get("busTimetable");
                Path<Quantity> quantityPath = root.get("quantity");
                Path ticketQuantityPath = quantityPath.get("quantity");
                Path<String> tableDate = busTimetablePath.get("date");
                Path<Object> orderTime = busTimetablePath.get("time");
                Path<RouteDetail> routeDetail = busTimetablePath.get("routeDetail");
                Path<Object> routeDetailId = routeDetail.get("routeDetailId");

                Predicate condition = criteriaBuilder.equal(tableDate, date);
                Predicate routeDetailPre = criteriaBuilder.equal(routeDetailId, routeId);
                Predicate ge = criteriaBuilder.ge(ticketQuantityPath, ticketQuantity);
                Predicate and = criteriaBuilder.and(routeDetailPre, condition);

                query.orderBy(criteriaBuilder.asc(orderTime));
                query.where(and,ge);
                return query.getRestriction();
            };
            List<BusTicket> result = busTicketMapper.findAll(specification);
            //将结果时间转换成对应
            log.info("Find bus ticket result == " + result.size());
            for (int i = 0; i < result.size(); i++) {
                log.info("try find ticket of bus");
                BusTicket busTicket = result.get(i);
                String tableTime = busTicket.getBusTimetable().getTime();
                Integer tableTimeInt = Integer.valueOf(tableTime);
                if (tableTimeInt>=departureTime){
                    log.info("get result of tableTimeInt>=departureTime");
                    return (T) busTicket;
                }
            }
        }
        if (ticketModel instanceof TrainTicket){
            Specification<TrainTicket> specification = (root, query, criteriaBuilder) -> {
                Path<BusTimetable> busTimetablePath = root.get("trainTimetable");
                Path<String> tableDate = busTimetablePath.get("date");
                Path<Object> orderTime = busTimetablePath.get("time");
                Path<RouteDetail> routeDetail = busTimetablePath.get("routeDetail");
                Path<Object> routeDetailId = routeDetail.get("routeDetailId");
                Path<Quantity> quantityPath = root.get("quantity");
                Path ticketQuantityPath = quantityPath.get("quantity");

                Predicate condition = criteriaBuilder.equal(tableDate, date);
                Predicate routeDetailPre = criteriaBuilder.equal(routeDetailId, routeId);
                Predicate and = criteriaBuilder.and(routeDetailPre, condition);
                Predicate ge = criteriaBuilder.ge(ticketQuantityPath, ticketQuantity);

                query.orderBy(criteriaBuilder.asc(orderTime));
                query.where(and,ge);
                return query.getRestriction();
            };
            List<TrainTicket> result =  trainTicketMapper.findAll(specification);
            log.info("Find train ticket result == " + result.size());
            for (int i = 0; i < result.size(); i++) {
                log.info("try find ticket of train");
                TrainTicket trainTicket = result.get(i);
                String tableTime = trainTicket.getTrainTimetable().getTime();
                Integer tableTimeInt = Integer.valueOf(tableTime);
                if (tableTimeInt>=departureTime){
                    log.info("get result of tableTimeInt>=departureTime");
                    return (T) trainTicket;
                }
            }
        }
        return null;


    }

    public ResultData getRouteDetail(ArrayList<Integer> routeDetailIds){
        //根据RouteDetailId查询路线信息
        List<RouteDetail> allRouteDetail = routeDetailMapper.findAllById(routeDetailIds);
        if (allRouteDetail.isEmpty()){
            return new ResultData(404,"No find the result of route detail according by the route detail id");
        }
        return new ResultData(allRouteDetail,200,"Successful access to detailed route data.");
    }

    /**
     * This is a method which can return the time of available time of castle.
     * @param departureId departure place id;
     * @param castleId castle id
     * @param date departure date
     * @param time departure time
     * @return the bus timetable entity;
     */
    @Override
    public ResultData getBusTimetableByCastleIdDateAndTime(Integer departureId,Integer castleId, String date , String time) {
        Castle castle = castleMapper.findByCastleId(castleId);
        Integer gpsId = castle.getCastleGps().getGpsId();
        List<Route> routes = routeMapper.findRouteByDateDepartureIdAndDestinationId(departureId, gpsId);
        if (routes==null | routes.size()<=0){
            return new ResultData(404,"No available route for this castle.");
        }

        HashMap<String, List<BusTimetable>> result = new HashMap<>();
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            List<BusTimetable> busTimetables = busTimetableMapper.findByCastleIdDateAndTime(route.getRouteId(), date);
            if (busTimetables!=null && busTimetables.size()>0){
                result.put("routeId:"+route.getRouteId(),busTimetables);
            }
        }
        return new ResultData(result,200,"ok");
    }

    /**
     * get route detail according to the castle id;
     * @param castleId
     * @return route detail entity.
     */
    @Override
    public ResultData getRouteDetail(int castleId){
        //查询castle的GPSid
        Castle castle = castleMapper.findByCastleId(castleId);
        log.info("castle gps = " + castle.getCastleGps().getGpsId());
        List<RouteDetail> routeDetailInfo = routeDetailMapper.getRouteDetailInfo(castle.getCastleGps().getGpsId());
        log.info("routeDetailInfo size ==  " + routeDetailInfo.size());
        HashMap<Integer, ArrayList<RouteDetail>> result = new HashMap<>();
        for (int i = 0; i < routeDetailInfo.size(); i++) {
            RouteDetail routeDetail = routeDetailInfo.get(i);
            ArrayList<RouteDetail> routeDetails = result.get(routeDetail.getRoute().getRouteId());
            if (routeDetails == null){
                log.info("create new route detail list.");
                ArrayList<RouteDetail> list = new ArrayList<>();
                list.add(routeDetail);
                result.put(routeDetail.getRoute().getRouteId(),list);
            }else {
                log.info("add a new routeDetail.");
                routeDetails.add(routeDetail);
            }
        }

        return new ResultData(result,200,"ok");
    }

    /**
     * Get the bus timetable by input the routeDetail id, departure date and time
     * @param routeDetailId routeDetail id from database;
     * @param date the departure date
     * @param time the departure time
     * @return bus timetable entity.
     */
    @Override
    public ResultData getBusTimetableByRouteDetailId(Integer routeDetailId, String date, String time) {
        RouteDetail routeDetail = new RouteDetail();
        routeDetail.setRouteDetailId(routeDetailId);
        List<BusTimetable> list = busTimetableMapper.findBusTimetableByRouteDetailAndDateAndTimeGreaterThanEqual(routeDetail, date, time);
        return new ResultData(list,200,"ok");
    }

    /**
     * get return route info by inputting the back time and date and the castle id from database.
     * @param castleId castle id from database;
     * @param date departure date
     * @param departureTime departure time
     * @param ticketQuantity people number
     * @return route detail.
     */
    @Override
    public ResultData getReturnRoute(Integer castleId, String date, String departureTime,int ticketQuantity) {
        //查询从当前城堡出发的gps位置
        Castle castle = castleMapper.getById(castleId);
        if (castle== null){
            return  new ResultData(404,"No found the information of castle, please check the parameter of castle id.");
        }
        //获取GPS id。
        GPS castleGps = castle.getCastleGps();
        List<Route> routes = routeMapper.findRouteByDepartureGps(castleGps.getGpsId());
        if (routes == null){
            return  new ResultData(404,"No available routes");
        }
        HashMap<String, Object> result = new HashMap<>();
        //查询和这条线相关的车辆
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            //获取到这条路线所需要的小路线信息
            List<RouteDetail> routeDetails = routeDetailMapper.findByRouteId(route.getRouteId());
            //查询路线对应时间点是否有相应的公交车
            ArrayList<Object> listOfTicket = new ArrayList<>();
            for (RouteDetail routeDetail : routeDetails) {
                if (routeDetail.getType().equalsIgnoreCase("bus")){
                    BusTicket ticket = findTickets(routeDetail.getRouteDetailId(), date, departureTime, new BusTicket(), ticketQuantity);
                    if (ticket == null){
                        log.warn("No find the ticket about buses");
                        break;
                    }else {
                        //update the departure time
                        String time = ticket.getBusTimetable().getTime();
                        departureTime = TimeUtil.timeAdd(time, ticket.getBusTimetable().getTravelTime());
                        listOfTicket.add(ticket);
                    }
                }
                if (routeDetail.getType().equalsIgnoreCase("train")){
                    TrainTicket ticket = findTickets(routeDetail.getRouteDetailId(), date, departureTime, new TrainTicket(), ticketQuantity);
                    if (ticket == null){
                        log.warn("No find the ticket about buses");
                        break;
                    }else {
                        listOfTicket.add(ticket);
                    }
                }
            }
            //Check that the route is complete
            if (listOfTicket.size()==routeDetails.size() && listOfTicket.size()>0){
                result.put("route id ="+route.getRouteId()+"route name = "+route.getRouteName(),listOfTicket);
            }
        }


        if (result.size() >=0){
            return new ResultData(result,200,"ok");
        }
        else {
            return new ResultData(404,"No available route for you.");
        }
    }


}
