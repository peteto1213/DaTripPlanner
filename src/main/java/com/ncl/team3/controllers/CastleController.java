package com.ncl.team3.controllers;

import com.ncl.team3.models.ResultData;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/03 15:48:08
 */

public interface CastleController {


    ResultData getCastleInfoById(Integer castleId);

    ResultData getCastleTicketPrice(Integer castleId);

    ResultData getAllCastleInfo();

    ResultData getCastleInfoByCastleName(String castleName);


    ResultData getCastleAvailableDateById(Integer castleId);

    ResultData getAvailableTimeByCastleId(Integer castleId, String date);

    ResultData getCastleTicketByDateAndTime(@NotNull String date, @NotNull Integer castleId);
}
