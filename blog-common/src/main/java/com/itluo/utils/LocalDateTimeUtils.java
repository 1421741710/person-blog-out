package com.itluo.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 生成当前时间格式是yyyy-MM-dd HH:mm:ss
 * @author Administrator
 */
@Slf4j
public class LocalDateTimeUtils {

    /**
     * 获取当前时间并转换格式为:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime getLocalDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String loginTime = now.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.parse(loginTime,formatter);
        return localDateTime;
    }

    /**
     * 获取时间差
     * @param localDateTime
     * @return
     */
    public static long getLocalDateTime(LocalDateTime localDateTime){
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesDifference = localDateTime.until(currentTime, ChronoUnit.MINUTES);
        return Math.abs(minutesDifference);
    }

    /**
     * 进行时间的比较
     * @param localDateTime
     * @param num
     * @return
     */
    public static boolean getLocalDateTime(LocalDateTime localDateTime,long num){
        long time = getLocalDateTime(localDateTime);
        return time >= num;
    }
}
