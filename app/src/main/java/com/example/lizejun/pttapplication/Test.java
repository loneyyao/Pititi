package com.example.lizejun.pttapplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * create by lizejun
 * date 2018/11/16
 */
public class Test {
    public static void main(String[] args){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse("2019-1-10");//初始日期
            long time = date.getTime();
            int year = date.getYear();
            System.out.println(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
