package com.ncl.team3.util;

import com.ncl.team3.mappers.BusTicketMapper;
import com.ncl.team3.mappers.BusTimetableMapper;
import com.ncl.team3.mappers.QuantityMapper;
import com.ncl.team3.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class provides method which bases insert the exact data of bus timetables from Excel Document.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/18 18:09:30
 */
@Slf4j
@Service
public class BusTimetableDataInsert {

    @Autowired
    private BusTimetableMapper busTimetableMapper;
    @Autowired
    private BusTicketMapper busTicketMapper;
    @Autowired
    private QuantityMapper quantityMapper;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar instance = Calendar.getInstance();

    /**
     *This is a tool class that generates the corresponding
     * bus schedule and the corresponding
     * ticket information by passing in the file path,
     * and at the same time, generates the corresponding number of tickets by day
     * @param fileName path of saving of Excel file of bus timetable data.
     * @throws IOException
     */
    public void insertBusTimetable(String fileName) throws IOException {
        ArrayList<BusTicket> busTickets = new ArrayList<>();
        instance.set(2022, 4, 26);
        //"src/main/resources/data/busTimetable.xlsx"
        FileInputStream file = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //get price
        XSSFSheet priceSheet = workbook.getSheetAt(3);
        Iterator<Row> iterator = priceSheet.iterator();
        //????????????
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (row.getRowNum() == 0){
                continue;
            }else {
                try{
                    log.info("????????????...");
                    Cell priceCell = row.getCell(0);
                    BusTicket busTicket = new BusTicket();
                    double price = priceCell.getNumericCellValue();
                    Cell typeCell = row.getCell(1);
                    String type = typeCell.getStringCellValue();
                    BigDecimal bigDecimal = new BigDecimal(price);

                    busTicket.setType(type);
                    busTicket.setPrice(bigDecimal);

                    busTickets.add(busTicket);
                }catch (Exception e){
                    log.info("????????????....");
                    e.printStackTrace();
                    break;
                }

            }
        }

        for (int i = 0; i < 15; i++) {
            Quantity quantity = new Quantity();
            quantity.setQuantity(10000);
            Quantity quantityResult = quantityMapper.save(quantity);
            //01012022 ->sa
            if (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                insertDate(rows,busTickets,quantityResult);
            }

            if (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                XSSFSheet sheet = workbook.getSheetAt(2);
                Iterator<Row> rows = sheet.iterator();
                insertDate(rows,busTickets,quantityResult);
                //????????????
            } else {
                XSSFSheet sheet = workbook.getSheetAt(1);
                Iterator<Row> rows = sheet.iterator();
                insertDate(rows,busTickets,quantityResult);
            }
            instance.add(Calendar.DAY_OF_YEAR,1);
        }
    }

    /**
     *
     * @param rows excel?????????
     * @param busTickets ?????????????????????
     * @param quantity ???????????????????????????
     */
    public void insertDate(Iterator<Row> rows,ArrayList<BusTicket> busTickets,Quantity quantity) {

        List<BusTimetable> busTimetables = new ArrayList<>();
        List<BusTicket> busTicketsList = new ArrayList<>();
        while (rows.hasNext()) {
            Row row = rows.next();
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                log.info("????????????");
            } else {
                BusTimetable busTimetable = new BusTimetable();
                //id
                Cell busIdCell = row.getCell(0);
                if (busIdCell==null){
                    break;
                }
                int busId = (int) busIdCell.getNumericCellValue();
                //time
                Cell timeCell = row.getCell(1);
                String time ;
                try{
                    time = timeCell.getStringCellValue();
                }catch (Exception e){
                    time = String.valueOf(timeCell.getNumericCellValue());
                }

                //status
                Cell statusCell = row.getCell(2);
                String status = statusCell.getStringCellValue();
                //route_detail_id
                Cell routeIdCell = row.getCell(3);
                int routeId = (int) routeIdCell.getNumericCellValue();

                //travel time
                Cell travelCell = row.getCell(4);
                int travelTime = (int) travelCell.getNumericCellValue();

                //??????Bus Id
                busTimetable.setBus(new Bus().setBusId(busId));
                //????????????
                busTimetable.setDate(simpleDateFormat.format(instance.getTime()));
                //????????????Id
                busTimetable.setRouteDetail(new RouteDetail().setRouteDetailId(routeId));
                //????????????
                busTimetable.setTime(time);
                //????????????
                busTimetable.setStatus(status);
                //??????????????????
                busTimetable.setTravelTime(travelTime);
                busTimetables.add(busTimetable);
            }
        }
        List<BusTimetable> busTimetablesId = busTimetableMapper.saveAll(busTimetables);
        for (int i = 0; i < busTimetablesId.size(); i++) {
            log.info("????????????????????????");
            BusTimetable busTimetable = busTimetablesId.get(i);
            for (int j = 0; j < busTickets.size(); j++) {
                BusTicket busTicket = busTickets.get(j);
                BusTicket newBusTicket = new BusTicket();
                //?????????????????????????????????
                newBusTicket.setBusTimetable(busTimetable);
                //???????????????????????????
                Quantity quantity1 = new Quantity();
                quantity1.setQuantityId(quantity.getQuantityId());
                newBusTicket.setQuantity(quantity1);
                newBusTicket.setPrice(busTicket.getPrice());
                newBusTicket.setType(busTicket.getType());


                busTicketsList.add(newBusTicket);
            }
        }
        //??????????????????
        busTicketMapper.saveAll(busTicketsList);
    }


}
