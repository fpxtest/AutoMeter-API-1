package com.zoctan.api.util;


import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RadomVariables {
    //获取GUID
    public String GetGuid()
    {
        String Result="";
        UUID uuid = UUID.randomUUID();
        Result=uuid.toString();
        return Result;
    }

    //随机获取Long
    public long GetRadmomNum(Long Start,Long End)
    {
        long Result=0;
        long longnum= ThreadLocalRandom.current().nextLong(Start,End);
        Result = longnum;
        return Result;
    }

    //随机获取Double
    public double GetRadmomDouble(double Start,double End)
    {
        double Result=0;
        double doublenum= ThreadLocalRandom.current().nextDouble(Start,End);
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
        String tmp=df.format(doublenum);
        Result =Double.parseDouble(tmp);
        return Result;
    }

    //随机获取指定长度的字符串
    public String GetRadmomStr(int length)
    {
        String Result="";
        String RadomStr= RandomStringUtils.randomAlphanumeric(length);
        Result= RadomStr;
        return Result;
    }

    //获取当前时间
    public String GetCurrentTime()
    {
        String Result="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Result=df.format(new Date());
        return Result;
    }


    //获取当前时间
    public String GetCurrentDate(String Condition)
    {
        String Result="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Result=df.format(new Date());
        if(Condition.equalsIgnoreCase("/"))
        {
            df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
            Result=df.format(new Date());
        }
        if(Condition.equalsIgnoreCase("-"))
        {
            df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Result=df.format(new Date());
        }
        return Result;
    }

    //获取当前时间戳
    public long GetCurrentTimeMillis()
    {
        long Result=0;
        Result=System.currentTimeMillis();
        return Result;
    }


    //随机获取IP
    public String GetRadmonIP()
    {
        String Result="";
        Long start=new Long(1);
        Long end=new Long(256);
        String IP=GetRadmomNum(start,end)+"."+GetRadmomNum(start,end)+"."+GetRadmomNum(start,end)+"."+GetRadmomNum(start,end);
        Result=IP;
        return Result;
    }

    public static void main(String[] args) {


        String Result="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Result=df.format(new Date());
        System.out.println(Result.toString());





//        long longnum= ThreadLocalRandom.current().nextLong(100,999);
//        double doublenum= ThreadLocalRandom.current().nextDouble(0.1,0.8);
//        double floatnum= ThreadLocalRandom.current().nextFloat();
//        System.out.println(longnum);
//        System.out.println(doublenum);
//        System.out.println(floatnum);
    }
}
