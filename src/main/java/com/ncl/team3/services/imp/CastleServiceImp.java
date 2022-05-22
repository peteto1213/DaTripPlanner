package com.ncl.team3.services.imp;

import com.ncl.team3.mappers.CastleMapper;
import com.ncl.team3.mappers.CastleTicketMapper;
import com.ncl.team3.models.Castle;
import com.ncl.team3.models.CastleTicket;
import com.ncl.team3.models.ResultData;
import com.ncl.team3.services.CastleService;
import com.ncl.team3.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * This method mainly implements castle-related functions, such as querying castle information,
 * querying castle ticket information,
 * querying castle ticket availability dates, etc.
 * @Team team3
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/03/27 17:18:39
 */
@Service
@Slf4j
public class CastleServiceImp implements CastleService {
    @Autowired
    private CastleMapper castleMapper;
    @Autowired
    private CastleTicketMapper castleTicketMapper;

    /**
     * Get the Castle info by castle id.
     * @param id castle id
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getCastleInfoById(int id) {
        Castle castle = castleMapper.findByCastleId(id);
        if (castle != null){
            return new ResultData(castle,200,"ok");
        }else {
            return new ResultData(404,"please check the castle id.");
        }
    }

    /**
     * Get all the information about the castle
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getAllCastleInfo() {
        List<Castle> all = castleMapper.findAll();
        //Determine if the castle is null
        if (all.isEmpty()){
            return new ResultData(404,"No find castle information.");
        }

        return new ResultData(all,200,"ok");
    }

    /**
     * Get information about the castle by the name of the castle
     * @param castleName the name of castle.
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getCastleInfoByCastleName(String castleName) {
        Castle castle = castleMapper.findCastleByCastleName(castleName);
        if (castle==null){
            return new ResultData(404,"No information on castles corresponding to this castle name.");
        }
        return new ResultData(castle,200,"Ok!");
    }

    /**
     * This method will return the price of a student ticket for the castle first,
     * or the price of an adult ticket if the student ticket is not available
     * @param castleId castle id from database.
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getCastleTicketAvgPrice(int castleId) {
        String currentDate = TimeUtil.getCurrentDate();
        List<CastleTicket> castleTicketPrice = castleTicketMapper.getCastleTicketPrice(castleId,currentDate);
        if (castleTicketPrice == null | castleTicketPrice.size()<=0){
            return new ResultData(404,"No information found for the corresponding castle tickets");
        }

        int size = castleTicketPrice.size();
        //Check if the type of ticket includes a student ticket
        for (int i = 0; i <size; i++) {
            CastleTicket castleTicket = castleTicketPrice.get(i);
            BigDecimal price = castleTicket.getPrice();
            if (castleTicket.getType().contains("student")) {
                return new ResultData(price,200,"ok");
            }
        }
        //Check if the type of ticket includes a adult ticket
        for (int i = 0; i <size; i++) {
            CastleTicket castleTicket = castleTicketPrice.get(i);
            BigDecimal price = castleTicket.getPrice();
            if (castleTicket.getType().contains("adult")) {
                return new ResultData(price,200,"ok");
            }
        }

        return new ResultData(castleTicketPrice.get(0).getPrice(),200,"ok");
    }

    /**
     * Get the available dates for a given castle
     * @param castleId  castle id from the database.
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getCastleAvailableDateById(int castleId) {
        String currentDate = TimeUtil.getCurrentDate();
        log.info(currentDate);
        List<String> dateList = castleTicketMapper.getAvailableDateByCastleId(castleId,currentDate);
        if (dateList== null | dateList.size()<=0){
            return new ResultData(404,"No available date for this castle.");
        }
        return new ResultData(dateList,200,"ok");
    }

    /**
     * Get the available time for a given castle
     * @param castleId  castle id from the database.
     * @param date search date of wanting to castle
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getAvailableTimeByCastleId(int castleId, String date) {
        List<String> times = castleTicketMapper.getAvailableTimeByCastleId(castleId, date);
        if (times ==null | times.size() <= 0 ){
            return new ResultData(404,"No available time for this castle.");
        }
        return new ResultData(times,200,"ok");
    }

    /**
     * Get information on castle tickets by date and time of visit
     * @param date  Dates when you need to visit the castle
     * @param castleId  castle id from the database.
     * @return If the code returned == 200, the execution was successful
     */
    @Override
    public ResultData getCastleTicketByDateAndTime(String date,Integer castleId) {
        List<CastleTicket> castleTickets = castleTicketMapper.getCastleTicketByDateAndTimeAndAndCastleId(date,castleId);
        if (castleTickets!=null && castleTickets.size()>0){
            return new ResultData(castleTickets,200,"ok");
        }
        return  new ResultData(404,"No information found for available Castle tickets, please check the parameters.");
    }


}
