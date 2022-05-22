package com.ncl.team3.services;

import com.ncl.team3.models.Castle;
import com.ncl.team3.models.ResultData;
import org.springframework.stereotype.Service;

/**
 * This interface mainly defines functions related to the castle, such as querying castle information,
 * querying castle ticket information,
 * querying castle ticket availability dates, etc.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/07 17:33:55
 */
@Service
public interface CastleService {
    /**
     * Get the Castle info by castle id.
     * @param id castle id
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getCastleInfoById(int id);
    /**
     * Get all the information about the castle
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getAllCastleInfo();
    /**
     * Get information about the castle by the name of the castle
     * @param castleName the name of castle.
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getCastleInfoByCastleName(String castleName);
    /**
     * This method will return the price of a student ticket for the castle first,
     * or the price of an adult ticket if the student ticket is not available
     * @param castleId castle id from database.
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getCastleTicketAvgPrice(int castleId);
    /**
     * Get the available dates for a given castle
     * @param castleId  castle id from the database.
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getCastleAvailableDateById(int castleId);
    /**
     * Get the available time for a given castle
     * @param castleId  castle id from the database.
     * @param date search date of wanting to castle
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getAvailableTimeByCastleId(int castleId, String date);
    /**
     * Get information on castle tickets by date and time of visit
     * @param date  Dates when you need to visit the castle
     * @param castleId  castle id from the database.
     * @return If the code returned == 200, the execution was successful
     */
    ResultData getCastleTicketByDateAndTime(String date,Integer castleId);

}
