package com.ncl.team3.util;

import com.ncl.team3.mappers.CastleMapper;
import com.ncl.team3.mappers.CastleTicketMapper;
import com.ncl.team3.models.Castle;
import com.ncl.team3.models.CastleTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/28 18:59:58
 */
@Service
public class CastleTicketInsert {

    @Autowired
    private CastleMapper castleMapper;
    @Autowired
    private CastleTicketMapper castleTicketMapper;


    class  PriceAndTye{
        public PriceAndTye(String type, double price) {
            this.type = type.toLowerCase();
            this.price = new BigDecimal(price);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        String type;
        BigDecimal price;
    }

    public void insertCastleTicket(){

        HashMap<Integer, ArrayList<PriceAndTye>> map = new HashMap<>();
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hhMM = new SimpleDateFormat("HHmm");
        List<Castle> castlesList = castleMapper.findAll();
        ArrayList<CastleTicket> castleTickets = new ArrayList<>();
        ArrayList<PriceAndTye> priceAndTyes1 = insertCastle1();
        ArrayList<PriceAndTye> priceAndTyes2 = insertCastle2();
        ArrayList<PriceAndTye> priceAndTyes3 = insertCastle3();
        ArrayList<PriceAndTye> priceAndTyes4 = insertCastle4();
        map.put(1,priceAndTyes1);
        map.put(2,priceAndTyes2);
        map.put(3,priceAndTyes3);
        map.put(4,priceAndTyes4);

        //获取城堡信息
        for (int i = 0; i < castlesList.size(); i++) {
            //添加90天的票
            Calendar calendar = Calendar.getInstance();
            calendar.set(2022,4,1,10,0);
            Castle castle = castlesList.get(i);
            //添加90天
            for (int day = 0; day < 30; day++) {
                ArrayList<PriceAndTye> priceAndTyes = map.get(castle.getCastleId());
                //for (int hours = 0; hours < 14; hours++) {
                    //设置时间
                    for (int j = 0; j < priceAndTyes.size(); j++) {
                        PriceAndTye priceAndTye = priceAndTyes.get(j);
                        CastleTicket castleTicket = new CastleTicket();
                        castleTicket.setCastle(castle);
                        castleTicket.setPrice(priceAndTye.getPrice());
                        castleTicket.setType(priceAndTye.getType());
                        castleTicket.setQuantity(100);
                        castleTicket.setDate(yyyyMMdd.format(calendar.getTime()));
                        castleTicket.setTime("-");
                        castleTicket.setStatus("normal");
                        castleTickets.add(castleTicket);
                    }
                //    calendar.add(Calendar.MINUTE,30);
                //}
                calendar.add(Calendar.DAY_OF_YEAR,1);
            }
        }
        castleTicketMapper.saveAll(castleTickets);


    }
    public  ArrayList<PriceAndTye> insertCastle1(){
        //PriceAndTye c11 = new PriceAndTye("CASTLE ADULT", 19.5);
        PriceAndTye c12 = new PriceAndTye("CASTLE CONCESSION", 15.75);
        //PriceAndTye c13 = new PriceAndTye("CASTLE CHILD 5-16 YRS", 10.25);
        //PriceAndTye c14 = new PriceAndTye("CASTLE UNDER 5 YRS", 0.0);
        ArrayList<PriceAndTye> c1 = new ArrayList<>();
        //c1.add(c11);
        c1.add(c12);
        //c1.add(c13);
        //c1.add(c14);
        return c1;
    }
    //auckland castle

    public  ArrayList<PriceAndTye> insertCastle2(){
        PriceAndTye c11 = new PriceAndTye("Adult", 14.0);
        //PriceAndTye c12 = new PriceAndTye("Child", 7.0);
        //PriceAndTye c13 = new PriceAndTye("Child (4 and under)", 0.0);
        ArrayList<PriceAndTye> c1 = new ArrayList<>();
        c1.add(c11);
        //c1.add(c12);
        //c1.add(c13);
        return c1;
    }
    public  ArrayList<PriceAndTye> insertCastle3(){
        PriceAndTye c11 = new PriceAndTye("ADULT", 14.10);
        //PriceAndTye c12 = new PriceAndTye("Child (aged 5-16)", 6.95);
        //PriceAndTye c13 = new PriceAndTye("Little Adventurer (aged 0-4)", 0.0);
        //PriceAndTye c14 = new PriceAndTye("Disabled", 11.40);
        ArrayList<PriceAndTye> c1 = new ArrayList<>();
        c1.add(c11);
        //c1.add(c12);
        //c1.add(c13);
        //c1.add(c14);
        return c1;
    }
    //barnard-castle
    public  ArrayList<PriceAndTye> insertCastle4(){
        //PriceAndTye c11 = new PriceAndTye("ADULT - ADMISSION ", 7.0);
        PriceAndTye c12 = new PriceAndTye("CONCESSION - ADMISSION  Students or over 65's", 6.3);
        //PriceAndTye c13 = new PriceAndTye("CHILD - ADMISSION", 4.2);
        //PriceAndTye c14 = new PriceAndTye("UNDER 5'S ADMISSION ", 0.0);
        ArrayList<PriceAndTye> c1 = new ArrayList<>();
        //c1.add(c11);
        c1.add(c12);
        //c1.add(c13);
        //c1.add(c14);
        return c1;
    }



}
