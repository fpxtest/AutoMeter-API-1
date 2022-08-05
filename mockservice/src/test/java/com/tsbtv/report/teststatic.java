package com.tsbtv.report;

public class teststatic {
    public static  void fun()
    {
        System.out.println("I'am static");
    }

    public static int caseid;

    public static String GetRequestValue(String Param)
    {
        String Sql="Hello "+Param;
        System.out.println("caseid is "+caseid);
        System.out.println(Sql);
        return Sql;
    }

    public static void SetRequestValue(String Param,String Value)
    {
        System.out.println("Value is"+Value);
    }
}
