package com.ncl.team3.controllers.imp;

import com.ncl.team3.controllers.CastleController;
import com.ncl.team3.models.ResultData;
import com.ncl.team3.services.CastleService;
import com.ncl.team3.util.TimeUtil;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;


/**
 * It mainly provides front-end access to back-end services related to Castle.
 * @author Lei Chen
 * @version 1.0
 * @date 2022/03/27 16:20:27
 */

@Slf4j
@RequestMapping("/castle")
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
@RestController
public class CastleControllerImp implements CastleController {
    @Autowired
    private CastleService castleService;

    /**
     * This mehtod can find castle information by castle id;
     * @param castleId castle id from front-end
     * @return
     */
    @Override
    @GetMapping(value = "/getCastleInfoById")
    public ResultData getCastleInfoById(Integer castleId) {
        log.info("Execute getCastleInfoById");
        if (castleId == 0){
            return new ResultData(404,"Parameter is null or zero.");
        }
        return  castleService.getCastleInfoById(castleId);
    }


    /**
     * This method will return the price of castle if the student price is available,
     * Otherwise return to adult ticket price
     * @param castleId
     * @return castle id
     */
    @Override
    @GetMapping("/getCastleTicketPrice")
    public ResultData getCastleTicketPrice(Integer castleId){
        log.info("Execute getCastleTicketAvgPrice->castleId == " +castleId);
        if (castleId < 0){
            return new ResultData(404,"The id of the castle is less than 0 and does not meet the parameter requirements");
        }
        return castleService.getCastleTicketAvgPrice(castleId);
    }

    /**
     * This method will return  information of all castles;
     * @return
     */
    @Override
    @GetMapping("/getAllCastleInfo")
    public ResultData getAllCastleInfo(){
        log.info("Execute getAllCastleInfo.");
        return castleService.getAllCastleInfo();
    }

    /**
     * This method will return information for searching function.
     * @param castleName
     * @return
     */
    @Override
    @GetMapping("/getCastleInfoByCastleName")
    public ResultData getCastleInfoByCastleName(String castleName) {
        log.info("Execute getCastleInfoByCastleName.");
        if (castleName == null){
            return new ResultData(404,"castleName is null");
        }
        return castleService.getCastleInfoByCastleName(castleName);
    }

    /**
     * This method will return the available date information of specify castle.
     * @param castleId castle id from front-end
     * @return date list
     */
    @Override
    @GetMapping("/getCastleAvailableDateById")
    public ResultData getCastleAvailableDateById(Integer castleId){
        if (castleId == null | castleId<=0){
            return new ResultData(404,"illegal parameter");
        }
        return castleService.getCastleAvailableDateById(castleId);
    }

    /**
     * This method returns the time when a particular castle is accessible on the specified date.
     * @param castleId Castle id information
     * @param date Date of visit
     * @return The specific times when the castle is accessible.
     */
    @Override
    @GetMapping("/getAvailableTimeByCastleId")
    public ResultData getAvailableTimeByCastleId(Integer castleId, String date){
        log.info("Execute the method of getAvailableTimeByCastleId.");
        boolean b = TimeUtil.checkTimeFormat(date);
        if (!b){
            return new ResultData(404,"Parameter of Date is not a correct format.");
        }
        return castleService.getAvailableTimeByCastleId(castleId,date);
    }

    /**
     * This method will return the information of castle ticket when inputting of  the date and time is correct;
     * @param date date of visit; Format : 20220320
     * @param castleId castle id
     * @return
     */
    @Override
    @GetMapping("/getCastleTicketByDateAndTime")
    public ResultData getCastleTicketByDateAndTime(@NotNull String date,  @NotNull Integer castleId){
        log.info("Execute the method of getCastleTicketByDateAndTime.");
        return castleService.getCastleTicketByDateAndTime(date,castleId);
    }



}
