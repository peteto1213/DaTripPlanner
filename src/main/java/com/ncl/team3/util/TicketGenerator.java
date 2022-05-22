package com.ncl.team3.util;

import com.ncl.team3.mappers.*;
import com.ncl.team3.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is for generating the data of testing.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/05/03 10:56:16
 */
@Service
@Slf4j
public class TicketGenerator {
    @Autowired
    private BusTimetableMapper busTimetableMapper;
    @Autowired
    private TrainTimetableMapper trainTimetableMapper;
    @Autowired
    private BusTicketMapper busTicketMapper;
    @Autowired
    private TrainTicketMapper trainTicketMapper;
    @Autowired
    private QuantityMapper quantityMapper;

    /**
     *
     * @param transportType bus/train
     * @param transportId bus/train id
     * @param travelTime the cost of time for the travel
     * @param routeDetailId roudeDetail id from routeDetail table
     * @param timeGap the time gps it will be less than 60.
     * @param price the ticket price
     * @param type the ticket type
     */
    public void generateRandomData(String transportType,int transportId,int travelTime,int routeDetailId,int timeGap,double price,String type){
        if (transportType.equalsIgnoreCase("bus")){
            insertBusInfoAndTicket( transportId, travelTime, routeDetailId, timeGap, price, type);
        }
        if (transportType.equalsIgnoreCase("train")){
            insertTrainInfoAndTicket( transportId, travelTime, routeDetailId, timeGap, price, type);
        }

    }

    private void insertTrainInfoAndTicket(int trainId, int travelTime, int routeDetailId, int timeGap, double price, String type) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat HHmm = new SimpleDateFormat("HHmm");

        instance.set(2022,4,1,7,0);
        //day
        ArrayList<TrainTimetable> trainTimetables = new ArrayList<>();
        ArrayList<Quantity> quantities = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            Quantity quantity = new Quantity();
            quantity.setQuantity(600);
            quantities.add(quantity);
            Train train = new Train();
            train.setTrainId(trainId);
            RouteDetail routeDetail=  new RouteDetail();
            routeDetail.setRouteDetailId(routeDetailId);
            for (int j = 0; j < 15; j++) {
                TrainTimetable trainTimetable = new TrainTimetable();
                trainTimetable.setTime(HHmm.format(instance.getTime()));
                trainTimetable.setDate(yyyyMMdd.format(instance.getTime()));
                trainTimetable.setTravelTime(travelTime);
                trainTimetable.setStatus("normal");
                trainTimetable.setTrain(train);
                trainTimetables.add(trainTimetable);
                //修改时间
                instance.add(Calendar.MINUTE,timeGap);
            }
            instance.set(Calendar.HOUR_OF_DAY,7);
            instance.add(Calendar.DAY_OF_YEAR,1);
        }
        log.info("save the list of bus to database");
        List<TrainTimetable> saveTrainTimetables = trainTimetableMapper.saveAll(trainTimetables);
        List<Quantity> saveQuantities = quantityMapper.saveAll(quantities);
        //随机生成可用的票的数量
//根据回传的id生成票
        ArrayList<TrainTicket> trainTickets = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < saveTrainTimetables.size(); i++) {
            Quantity quantity = saveQuantities.get(0);
            if (i%14==0 && j<quantities.size()){
                quantity=saveQuantities.get(j);
                j++;
            }
            TrainTicket trainTicket = new TrainTicket();
            trainTicket.setPrice(new BigDecimal(price));
            trainTicket.setType(type);
            trainTicket.setStatus("normal");
            trainTicket.setQuantity(quantity);
            trainTicket.setTrainTimetable(saveTrainTimetables.get(i));
            trainTickets.add(trainTicket);
        }
        trainTicketMapper.saveAll(trainTickets);
    }

    private void insertBusInfoAndTicket(int busId,int travelTime,int routeDetailId,int timeGap,double price,String type){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat HHmm = new SimpleDateFormat("HHmm");

        instance.set(2022,4,1,7,0);
        //day
        ArrayList<BusTimetable> busTimetables = new ArrayList<>();
        ArrayList<Quantity> quantities = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            Quantity quantity = new Quantity();
            quantity.setQuantity(600);
            quantities.add(quantity);
            Bus bus = new Bus();
            bus.setBusId(busId);
            RouteDetail routeDetail=  new RouteDetail();
            routeDetail.setRouteDetailId(routeDetailId);
            for (int j = 0; j < 15; j++) {
                BusTimetable busTimetable = new BusTimetable();
                busTimetable.setTime(HHmm.format(instance.getTime()));
                busTimetable.setDate(yyyyMMdd.format(instance.getTime()));
                busTimetable.setTravelTime(travelTime);
                busTimetable.setStatus("normal");
                busTimetable.setBus(bus);
                busTimetable.setRouteDetail(routeDetail);

                busTimetables.add(busTimetable);

                //修改时间
                instance.add(Calendar.MINUTE,timeGap);
            }
            instance.set(Calendar.HOUR_OF_DAY,7);
            instance.add(Calendar.DAY_OF_YEAR,1);
        }
        log.info("save the list of bus to database");
        List<BusTimetable> saveBusTimetables = busTimetableMapper.saveAll(busTimetables);
        List<Quantity> saveQuantities = quantityMapper.saveAll(quantities);
        //随机生成可用的票的数量
//根据回传的id生成票
        ArrayList<BusTicket> busTickets = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < saveBusTimetables.size(); i++) {
            Quantity quantity = saveQuantities.get(j);
            BusTicket busTicket = new BusTicket();
            busTicket.setPrice(new BigDecimal(price));
            busTicket.setType(type);
            busTicket.setStatus("normal");
            busTicket.setQuantity(quantity);
            busTicket.setBusTimetable(saveBusTimetables.get(i));
            busTickets.add(busTicket);
            if (i%14==0 && j<quantities.size()-1){
                j++;
            }
        }
        busTicketMapper.saveAll(busTickets);

    }


    public void insertTransportTicketInfo(String fileName,String transportType) throws IOException {
        //open file
        FileInputStream file = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sunday = workbook.getSheet("sunday");
        XSSFSheet workday  = workbook.getSheet("workday");
        XSSFSheet saturday = workbook.getSheet("saturday");
        XSSFSheet price = workbook.getSheet("price");
        Calendar instance = Calendar.getInstance();
        instance.set(2022,4,8);

        ArrayList<BusTimetable> saturdayBusData = null;
        ArrayList<BusTimetable> sundayBusData = null;
        ArrayList<BusTimetable> workDayBusData = null;
        ArrayList<BusTicket> busTickets = null;

        if (transportType.equalsIgnoreCase("bus")){
            saturdayBusData = getBusTimetable(saturday);
            sundayBusData = getBusTimetable(sunday);
            workDayBusData = getBusTimetable(workday);
            busTickets = getBusTicket(price);
            log.info("saturdayBusData" + saturdayBusData.size());
            log.info("sundayBusData" + sundayBusData.size());
            log.info("workDayBusData" + workDayBusData.size());
            log.info("busTickets" + busTickets.size());
            insertBusData(instance, saturdayBusData, sundayBusData, workDayBusData,busTickets);
        }
        ArrayList<TrainTimetable> saturdayTrainData = null;
        ArrayList<TrainTimetable> sundayTrainData = null;
        ArrayList<TrainTimetable> workDayTrainData = null;
        ArrayList<TrainTicket> trainTickets = null;
        if (transportType.equalsIgnoreCase("train")){
            saturdayTrainData = getTrainTimetable(saturday);
            sundayTrainData = getTrainTimetable(sunday);
            workDayTrainData = getTrainTimetable(workday);
            trainTickets = getTrainTicket(price);
            log.info("saturdayBusData" + saturdayTrainData.size());
            log.info("sundayBusData" + sundayTrainData.size());
            log.info("workDayBusData" + workDayTrainData.size());
            log.info("busTickets" + trainTickets.size());
            insertTrainData(instance,saturdayTrainData,sundayTrainData,workDayTrainData,trainTickets);
        }




    }

    private void insertBusData(Calendar instance,
                               ArrayList<BusTimetable> saturdayBusData,
                               ArrayList<BusTimetable> sundayBusData,
                               ArrayList<BusTimetable> workDayBusData,
                               ArrayList<BusTicket> busTickets) {
        for (int day = 0; day < 30; day++) {
            Quantity quantity = new Quantity();
            quantity.setQuantity(1000);
            Quantity quantity1 = quantityMapper.save(quantity);
            if (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                List<BusTimetable> busTimetables = insertBusTimetable(saturdayBusData, instance);
                insertBusTicket(busTimetables,quantity1,busTickets);
            }
            else if(instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                List<BusTimetable> busTimetables = insertBusTimetable(sundayBusData, instance);
                insertBusTicket(busTimetables,quantity1,busTickets);
            }else {
                List<BusTimetable> busTimetables = insertBusTimetable(workDayBusData, instance);
                insertBusTicket(busTimetables,quantity1,busTickets);
            }
            instance.add(Calendar.DAY_OF_YEAR,1);
        }
    }
    private void insertTrainData(Calendar instance,
                                 ArrayList<TrainTimetable> saturdayTrainData,
                                 ArrayList<TrainTimetable> sundayTrainData,
                                 ArrayList<TrainTimetable> workDayTrainData,
                                 ArrayList<TrainTicket> trainTickets) {
        for (int day = 0; day < 30; day++) {
            if (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                List<TrainTimetable> trainTimetables = insertTrainTimetable(saturdayTrainData, instance);
                insertTrainTicket( trainTimetables, trainTickets);
            }
            else if(instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                List<TrainTimetable> trainTimetables = insertTrainTimetable(sundayTrainData, instance);
                insertTrainTicket( trainTimetables,  trainTickets);
            }else {
                List<TrainTimetable> trainTimetables = insertTrainTimetable(workDayTrainData, instance);
                insertTrainTicket( trainTimetables, trainTickets);
            }
            instance.add(Calendar.DAY_OF_YEAR,1);
        }
    }

    private ArrayList<BusTimetable> getBusTimetable(XSSFSheet sheet){
        Iterator<Row> rows = sheet.iterator();
        ArrayList<BusTimetable> busTimetables = new ArrayList<>();
        while (rows.hasNext()) {
            if (rows.next().getRowNum() == 0) {
                log.warn("Skip this row.");
                continue;
            }
            try{
                Row row = rows.next();
                int busId = (int)row.getCell(0).getNumericCellValue();
                String time = row.getCell(1).getStringCellValue();
                String status = row.getCell(2).getStringCellValue();
                int routeDetailId = (int)row.getCell(3).getNumericCellValue();
                int travelTime = (int) row.getCell(4).getNumericCellValue();
                BusTimetable busTimetable = new BusTimetable();
                Bus bus = new Bus();
                bus.setBusId(busId);
                busTimetable.setTime(time);
                busTimetable.setTravelTime(travelTime);
                busTimetable.setStatus(status);
                busTimetable.setBus(bus);
                RouteDetail routeDetail = new RouteDetail();
                routeDetail.setRouteDetailId(routeDetailId);
                busTimetable.setRouteDetail(routeDetail);
                busTimetables.add(busTimetable);
            }catch (Exception e){
                log.error("skip the error of insert data.");
            }
        }
        return busTimetables;
    }
    private ArrayList<TrainTimetable> getTrainTimetable(XSSFSheet sheet){
        Iterator<Row> rows = sheet.iterator();
        ArrayList<TrainTimetable> trainTimetables = new ArrayList<>();
        while (rows.hasNext()) {
            if (rows.next().getRowNum() == 0) {
                log.warn("Skip this row.");
                continue;
            }
            try{
                Row row = rows.next();
                int trainId = (int)row.getCell(0).getNumericCellValue();
                String time = row.getCell(1).getStringCellValue();
                String status = row.getCell(2).getStringCellValue();
                int routeDetailId = (int)row.getCell(3).getNumericCellValue();
                int travelTime = (int) row.getCell(4).getNumericCellValue();
                TrainTimetable trainTimetable = new TrainTimetable();
                Train train = new Train();
                train.setTrainId(trainId);
                trainTimetable.setTime(time);
                trainTimetable.setTravelTime(travelTime);
                trainTimetable.setStatus(status);
                trainTimetable.setTrain(train);
                RouteDetail routeDetail = new RouteDetail();
                routeDetail.setRouteDetailId(routeDetailId);
                trainTimetable.setRouteDetail(routeDetail);
                trainTimetables.add(trainTimetable);
            }catch (Exception e){
                log.error("skip the error of insert data.");
            }
        }
        return trainTimetables;
    }
    private ArrayList<TrainTicket> getTrainTicket(XSSFSheet sheet){
        ArrayList<TrainTicket> trainTickets = new ArrayList<TrainTicket>();
        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            if (row.getRowNum() == 0) {
                log.info("skip the row.");
                continue;
            }else {
                try{
                    double price = row.getCell(0).getNumericCellValue();
                    String type = row.getCell(1).getStringCellValue();
                    TrainTicket trainTicket = new TrainTicket();
                    trainTicket.setPrice(BigDecimal.valueOf(price));
                    trainTicket.setStatus("normal");
                    trainTicket.setType(type);
                    trainTickets.add(trainTicket);
                }catch (Exception e){
                    log.warn("input the price wrong.");
                }
            }
        }

        return trainTickets;
    }
    private ArrayList<BusTicket> getBusTicket(XSSFSheet sheet){
        ArrayList<BusTicket> busTickets = new ArrayList<>();
        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            if (row.getRowNum() == 0) {
                log.info("skip the row.");
                continue;
            }else {
                try{
                    double price = row.getCell(0).getNumericCellValue();
                    String type = row.getCell(1).getStringCellValue();
                    BusTicket busTicket = new BusTicket();
                    busTicket.setPrice(BigDecimal.valueOf(price));
                    busTicket.setStatus("normal");
                    busTicket.setType(type);
                    busTickets.add(busTicket);
                }catch (Exception e){
                    log.warn("input the price wrong.");
                }
            }
        }

        return busTickets;
    }
    private List<BusTimetable> insertBusTimetable(ArrayList<BusTimetable> busTimetables,Calendar calendar){
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        //copy data
        ArrayList<BusTimetable> list = new ArrayList<>();
        Iterator<BusTimetable> iterator = busTimetables.iterator();
        while (iterator.hasNext()) {
            BusTimetable original = iterator.next();
            BusTimetable busTimetable = new BusTimetable();
            busTimetable.setBus(original.getBus());
            busTimetable.setTime(original.getTime());
            busTimetable.setDate(yyyyMMdd.format(calendar.getTime()));
            busTimetable.setRouteDetail(original.getRouteDetail());
            busTimetable.setStatus(original.getStatus());
            busTimetable.setTravelTime(original.getTravelTime());
            list.add(busTimetable);
        }
        List<BusTimetable> result = busTimetableMapper.saveAll(list);
        log.info("insert bus timetable size == " + result.size());
        return result;
    }
    private List<TrainTimetable> insertTrainTimetable(ArrayList<TrainTimetable> trainTimetables,Calendar calendar){
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        //copy data
        ArrayList<TrainTimetable> list = new ArrayList<>();
        Iterator<TrainTimetable> iterator = trainTimetables.iterator();
        while (iterator.hasNext()) {
            TrainTimetable original = iterator.next();
            TrainTimetable trainTimetable = new TrainTimetable();
            trainTimetable.setTrain(original.getTrain());
            trainTimetable.setTime(original.getTime());
            trainTimetable.setDate(yyyyMMdd.format(calendar.getTime()));
            trainTimetable.setRouteDetail(original.getRouteDetail());
            trainTimetable.setStatus(original.getStatus());
            trainTimetable.setTravelTime(original.getTravelTime());
            list.add(trainTimetable);
        }
        List<TrainTimetable> result = trainTimetableMapper.saveAll(list);
        return result;
    }

    private void insertBusTicket(List<BusTimetable> busTimetables,Quantity quantity,List<BusTicket> busTickets){
        int quantityIndex = 0 ;
        ArrayList<BusTicket> list = new ArrayList<>();
        for (int i = 0; i < busTimetables.size(); i++) {
            BusTimetable busTimetable = busTimetables.get(i);
            for (int j = 0; j < busTickets.size(); j++) {
                BusTicket busTicket = new BusTicket();
                busTicket.setBusTimetable(busTimetable);
                busTicket.setQuantity(quantity);
                busTicket.setStatus("normal");
                busTicket.setType(busTickets.get(j).getType());
                busTicket.setPrice(busTickets.get(j).getPrice());
                list.add(busTicket);
            }
        }
        List<BusTicket> tickets = busTicketMapper.saveAll(list);
        log.info("insert the tickets  size == " + tickets.size());
    }
    private void insertTrainTicket(List<TrainTimetable> trainTimetables,List<TrainTicket> trainTickets){
        int quantityIndex = 0 ;
        ArrayList<TrainTicket> list = new ArrayList<>();
        for (int i = 0; i < trainTimetables.size(); i++) {
            TrainTimetable trainTimetable = trainTimetables.get(i);
            Quantity quantity = new Quantity();
            quantity.setQuantity(600);
            Quantity save = quantityMapper.save(quantity);
            for (int j = 0; j < trainTickets.size(); j++) {
                TrainTicket trainTicket = new TrainTicket();
                trainTicket.setTrainTimetable(trainTimetable);
                trainTicket.setQuantity(save);
                trainTicket.setStatus("normal");
                trainTicket.setType(trainTickets.get(j).getType());
                trainTicket.setPrice(trainTickets.get(j).getPrice());
                list.add(trainTicket);
            }
        }
        trainTicketMapper.saveAll(list);
    }
}
