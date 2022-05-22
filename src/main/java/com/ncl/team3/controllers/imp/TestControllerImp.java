package com.ncl.team3.controllers.imp;

import com.ncl.team3.mappers.BusMapper;
import com.ncl.team3.mappers.CastleMapper;
import com.ncl.team3.mappers.RouteDetailMapper;
import com.ncl.team3.mappers.RouteMapper;
import com.ncl.team3.models.Bus;
import com.ncl.team3.models.RouteDetail;
import com.ncl.team3.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * This is class for test the network available and some base unit test.
 * In addition,this is an example Class for team members to learn.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/03/13 11:37:40
 */
@Controller
@ResponseBody
@Slf4j
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
@RequestMapping("/test")
public class TestControllerImp {
    @Autowired
    BusTimetableDataInsert busTimetableDataInsert;
    @Autowired
    BusTicketInsert busTicketInsert;

    @Autowired
    private TicketGenerator generator;

    @Autowired
    private TrainTimetableDataInsert trainTimetableDataInsert;
    @RequestMapping("/test")
    public Object Test(String time){
        log.info(time);


        return null;
    }
    @Autowired
    private EmailUtil emailUtil;
    @PostMapping("/sendEmail")
    public void sendEmail(){
        emailUtil.sendHtmlMail("lei.chen0525@outlook.com","hello","hello");
    }

    @PostMapping("/insertData")
    public void inserBusTimetableData(String filename) throws IOException {
        busTimetableDataInsert.insertBusTimetable(filename);
    }
    @PostMapping("/insertBusTicket")
    public void insertBusTicket(int busId, int quantity, BigDecimal price,String type){
        busTicketInsert.insertBusTicket(busId,quantity,price,type);
    }
    // The BusMapper is injected here, generally not in the Controller inside the injected mapper layer, here for demonstration purposes only.
    // This interface needs to be implemented --> JpaRepository<Bus,Integer>, PagingAndSortingRepository<Bus,Integer>
    @Autowired
    private BusMapper busMapper;
    @GetMapping("/forWeiDong")
    public Object forWeiDong(){
        // Get a Pageable class which sets the size and number of pages to be displayed,
        // the page parameters are passed in from the front end, as is the number of displays, the default value should be 10.
        PageRequest pageAble = PageRequest.of(0, 10);
        // here I have written a query statement inside this Mapper interface,
        // if hibernate finds a pageable parameter and the return type is Page class, then it will query by paging
        Page<Bus> all = busMapper.findAll(pageAble);
        // This method is to get the content,
        // I am querying for a bus, so what I get is a collection of Bus' List classes.
        List<Bus> content = all.getContent();
        return content;
    }


    @Autowired
    private CastleTicketInsert castleTicketInsert;
    @GetMapping("/insertCastleTicket")
    public void insertCastleTicket(){
        castleTicketInsert.insertCastleTicket();
    }

    @GetMapping("/insertTrainTicket")
    public void insertTrainTicket(String fileName) throws IOException, InvalidFormatException {
        trainTimetableDataInsert.openFile(fileName);
    }

    @PostMapping("/insertTestData")
    public String insertTestData(String transportType,int transportId,int travelTime,int routeDetailId,int timeGap,double price,String type){
        generator.generateRandomData( transportType, transportId, travelTime, routeDetailId, timeGap, price, type);
        return "Ok";
    }


    @PostMapping("/insertRealData")
    public String insertRealData(String file,String type) throws IOException {
        generator.insertTransportTicketInfo(file, type);
        return "Ok";
    }
}
