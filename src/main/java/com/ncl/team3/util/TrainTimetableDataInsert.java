package com.ncl.team3.util;

import com.ncl.team3.mappers.QuantityMapper;
import com.ncl.team3.mappers.TrainTicketMapper;
import com.ncl.team3.mappers.TrainTimetableMapper;
import com.ncl.team3.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/26 10:44:13
 */
@Slf4j
@Service
public class TrainTimetableDataInsert {
    @Autowired
    private TrainTimetableMapper trainTimetableMapper;
    @Autowired
    private QuantityMapper quantityMapper;
    @Autowired
    private TrainTicketMapper trainTicketMapper;

    public Object openFile(String fileName) throws IOException, InvalidFormatException {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(fileName));


        XSSFSheet sundayWorkbook = xssfWorkbook.getSheetAt(0);
        XSSFSheet workdayWorkbook = xssfWorkbook.getSheetAt(1);
        XSSFSheet saturdayWorkbook = xssfWorkbook.getSheetAt(2);
        XSSFSheet priceWorkbook  = xssfWorkbook.getSheetAt(3);



        Calendar instance = Calendar.getInstance();
        instance.set(2022,4,26);


        for (int i = 0; i < 15; i++) {
            Iterator<Row> sundayIterator = sundayWorkbook.iterator();
            Iterator<Row> workdayIterator = workdayWorkbook.iterator();
            Iterator<Row> saturdayIterator = saturdayWorkbook.iterator();
            Iterator<Row> priceIterator = priceWorkbook.iterator();
            List<TrainTimetable> sundayIteratorData= new ArrayList<>();
            List<TrainTimetable> saturdayIteratorData= new ArrayList<>();
            List<TrainTimetable> workdayIteratorData= new ArrayList<>();
            String date = yyyyMMdd.format(instance.getTime());
            log.info("开始存入 -  "+ date + " - 数据");

            if (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                log.info("处理周末");
                sundayIteratorData = getDataFromWorksheet(sundayIterator,date);
            }
            else if (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                log.info("处理周六");
                saturdayIteratorData = getDataFromWorksheet(saturdayIterator,date);
            }
            else {
                log.info("处理工作日");
                workdayIteratorData = getDataFromWorksheet(workdayIterator,date);
            }
            log.info("开始保存火车时刻表数据");
            //保存火车时刻表到数据库
            List<TrainTimetable> trainTimetables = trainTimetableMapper.saveAll(sundayIteratorData);
            List<TrainTimetable> trainTimetables1 = trainTimetableMapper.saveAll(saturdayIteratorData);
            List<TrainTimetable> trainTimetables2 = trainTimetableMapper.saveAll(workdayIteratorData);
            //获取价格和类型
            List<TrainTicket> trainTicket = getTrainTicket(priceIterator);
            //插入票务到数据库
            log.info("开始保存火车票数据");
            insertTrainTicket(trainTimetables,trainTicket);
            insertTrainTicket(trainTimetables1,trainTicket);
            insertTrainTicket(trainTimetables2,trainTicket);
            instance.add(Calendar.DAY_OF_YEAR,1);

        }




        return "ok";
    }

    public List<TrainTimetable> getDataFromWorksheet(Iterator<Row>  iterator,String date){
        ArrayList<TrainTimetable> trainTimetables = new ArrayList<>();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Row next = iterator.next();
            if (next.getRowNum() == 0){
                log.info("跳过该行...");
                continue;
            }else {
                try {
                    String time = next.getCell(0).getStringCellValue();
                    String status = next.getCell(1).getStringCellValue();
                    int travelTime = (int) next.getCell(2).getNumericCellValue();

                    RouteDetail routeDetail = new RouteDetail();
                    int routeDetailId = (int) next.getCell(3).getNumericCellValue();
                    routeDetail.setRouteDetailId(routeDetailId);

                    int trainId = (int) next.getCell(4).getNumericCellValue();
                    Train train = new Train();
                    train.setTrainId(trainId);

                    TrainTimetable trainTimetable = new TrainTimetable();
                    trainTimetable.setDate(date);
                    trainTimetable.setTime(time);
                    trainTimetable.setStatus(status);
                    trainTimetable.setTravelTime(travelTime);
                    trainTimetable.setRouteDetail(routeDetail);
                    trainTimetable.setTrain(train);
                    trainTimetables.add(trainTimetable);
                }catch (Exception e){
                    e.printStackTrace();
                    log.info("结束录入火车时刻表循环...,共执行了" + i + "次");
                    break;
                }
            }
        }
        return trainTimetables;
    }


    public List<TrainTicket> getTrainTicket(Iterator<Row>  iterator){
        ArrayList<TrainTicket> trainTickets = new ArrayList<>();
        while (iterator.hasNext()) {
            Row next = iterator.next();
            if (next.getRowNum() == 0){
                continue;
            }else {
                try {
                    double price = next.getCell(0).getNumericCellValue();
                    String type = next.getCell(1).getStringCellValue();
                    TrainTicket trainTicket = new TrainTicket();
                    trainTicket.setPrice(BigDecimal.valueOf(price));
                    trainTicket.setType(type);
                    trainTickets.add(trainTicket);
                }catch (Exception e){
                    e.printStackTrace();
                    log.info("结束录入火车票价格循环...");
                    break;
                }

            }

        }
        return trainTickets;
    }

    public void insertTrainTicket(List<TrainTimetable> trainTimetables,List<TrainTicket> trainTickets){
        log.info("插入火车票数据");
        ArrayList<TrainTicket> trainTicketList = new ArrayList<>();
        Quantity quantity = new Quantity();
        quantity.setQuantity(10000);
        Quantity resultOfSaving = quantityMapper.save(quantity);
        for (int i = 0; i < trainTimetables.size(); i++) {
            TrainTimetable trainTimetable = trainTimetables.get(i);
            for (int j = 0; j < trainTickets.size(); j++) {
                TrainTicket ticket = new TrainTicket();
                TrainTicket trainTicket = trainTickets.get(j);
                ticket.setPrice(trainTicket.getPrice());
                ticket.setType(trainTicket.getType());
                ticket.setTrainTimetable(trainTimetable);
                ticket.setQuantity(resultOfSaving);
                ticket.setStatus("normal");
                trainTicketList.add(ticket);
            }
        }
        trainTicketMapper.saveAll(trainTicketList);


    }


}
