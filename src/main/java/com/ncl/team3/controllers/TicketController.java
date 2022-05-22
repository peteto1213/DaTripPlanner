package com.ncl.team3.controllers;

import com.ncl.team3.models.ResultData;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.Date;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/03 15:48:56
 */


public interface TicketController {
    //查询票信息

    ResultData getCastleTicketsInfo(int castleId, Date date, Time time);

    ResultData getTrainTicketByCastleId(int castleId, Date date, Time time);

    ResultData getBusTicketByCastleId(int castleId, Date date, Time time);
    //添加票到计划
    //Add ticket to plan

    ResultData addSingleCastleTicketToPlan(int castleTicketId);

    ResultData addCastleTicketsToPlan(int castleTicketId,int amount);


}
