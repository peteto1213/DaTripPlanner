package com.ncl.team3.util;

import com.sun.istack.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/13 13:10:01
 */
@Slf4j
@Component
public class TimeUtil {
    @Value("FlexibleTime")
    private static int flexibleTime;


    /**
     * This function is to enable the time to be added up in string format.
     * @param startTime Departure time of the trip
     * @param travelTime
     * @return
     */
    public static String timeAdd(String startTime,Integer travelTime){

        Integer hours = Integer.valueOf(startTime.substring(0, 2));
        Integer minute = Integer.valueOf(startTime.substring(2));
        travelTime = travelTime + flexibleTime;


        int h = travelTime / 60;
        int m = travelTime %60;


        Integer endHours = hours + h;
        Integer endMinute = minute + m;
        log.info("endHours = " +endHours);
        log.info("endMinute = " +endMinute);

        String endHoursStr = null;
        String endMinuteStr = null;
        //判断时间 如果时间大于10h 那么不需要处理
        if ((endHours/10)>=1){
            endHoursStr = String.valueOf(endHours);
        }else {
            endHoursStr = "0"+endHours;
        }
        if ((endMinute/10)>=1){
            endMinuteStr = String.valueOf(endMinute);
        }else {
            endMinuteStr = "0"+endMinute;
        }

        String s = endHoursStr + endMinuteStr;
        log.info("处理后的数据为-->" + s);
        return s;

    }


    public static boolean checkTimeFormat(@NotNull String time){
        log.info("Check the format of time."  + time);
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmm");
        try {
            Date parse = formatTime.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean checkDateFormat(@NotNull String date){
        log.info("Check the format of time."  + date);
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd");
        try {
            Date parse = formatTime.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getCurrentDate(){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd");
        return formatTime.format(instance.getTime());
    }

    public static String getCurrentTime(){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        return formatTime.format(instance.getTime());
    }

    public static String getCurrentDateForHorsePay(){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy");
        return formatTime.format(instance.getTime());
    }

    public static String getCurrentTimeForHorsePay(){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        return formatTime.format(instance.getTime());
    }
}
