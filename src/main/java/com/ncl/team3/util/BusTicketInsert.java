package com.ncl.team3.util;

import com.ncl.team3.mappers.BusTicketMapper;
import com.ncl.team3.mappers.BusTimetableMapper;
import com.ncl.team3.models.Bus;
import com.ncl.team3.models.BusTicket;
import com.ncl.team3.models.BusTimetable;
import com.ncl.team3.models.Quantity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is for the Test.
 * It can insert the test data to the database.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/24 18:19:16
 */
@Slf4j
@Service
public class BusTicketInsert {
    @Autowired
    private BusTimetableMapper busTimetableMapper;
    @Autowired
    private BusTicketMapper busTicketMapper;
    public void insertBusTicket(int busId, int quantityId, BigDecimal price,String type){
        Bus bus = new Bus();
        bus.setBusId(busId);
        List<BusTimetable> all = busTimetableMapper.findBusTimetableByBus(bus);
        int size = all.size();
        ArrayList<BusTicket> list = new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            BusTimetable busTimetable = all.get(i);
            BusTicket busTicket = new BusTicket();
            //设置类型
            busTicket.setType(type);
            //设置时刻表id
            busTimetable.setBusTimetableId(busTimetable.getBusTimetableId());
            busTicket.setBusTimetable(busTimetable);
            busTicket.setPrice(price);
            Quantity quantity = new Quantity();
            quantity.setQuantityId(quantityId);
            busTicket.setQuantity(quantity);
            list.add(busTicket);
        }

        busTicketMapper.saveAll(list);


    }
}
