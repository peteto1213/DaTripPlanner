package com.ncl.team3.controllers.imp;

import com.ncl.team3.mappers.BusTimetableMapper;
import com.ncl.team3.models.BusTimetable;
import com.ncl.team3.util.BusTicketInsert;
import com.ncl.team3.util.BusTimetableDataInsert;
import com.ncl.team3.util.TrainTimetableDataInsert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.IOException;

/**
 * This is class for inserting test data.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/14 20:24:53
 */
@Controller
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
public class SaveDataToDatabase {
    @Autowired
    private BusTimetableMapper busTimetableMapper;
    @Autowired
    private BusTimetableDataInsert busTimetableDataInsert;
    @Autowired
    private TrainTimetableDataInsert trainTimetableDataInsert;

    /**
     * Insert the data about bus tickets.
     * @param fileName Excel file name
     * @return String
     * @throws IOException
     */
    @PostMapping("/insertBusTicket")
    public Object insertBusTicket(String fileName) throws IOException {
        busTimetableDataInsert.insertBusTimetable(fileName);
        return "busTimetableDataInsert ok";
    }

    /**
     * This is method for inserting the data about train ticket.
     * @param fileName Excel file name
     * @return String
     * @throws InvalidFormatException
     * @throws IOException
     */
    @PostMapping("/insertTrainTicket")
    public Object insertTrainTicket(String fileName) throws InvalidFormatException, IOException {
        trainTimetableDataInsert.openFile(fileName);
        return " trainTimetableDataInsert ok";
    }
}
