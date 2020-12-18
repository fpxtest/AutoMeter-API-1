package com.api.autotest.core;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/
public  class TestAssert {

    public boolean isCaseresult() {
        return caseresult;
    }

    public void setCaseresult(boolean caseresult) {
        this.caseresult = caseresult;
    }

    private    boolean caseresult=false;
    public  String assertinfo="";


    private   String collectioninfo(String expect,String actual,boolean result)
    {
        return "expect value is ："+expect+", actual value is ：" +actual+ " assert result is: "+result+" || ";
    }

    private   String collectioninfomore(String expect,String actual,boolean result)
    {
        return "expect value 大于 ："+expect+" actual value is ：" +actual+ " assert result is: "+result+" || ";
    }

    private   String collectioninfoless(String expect,String actual,boolean result)
    {
        return "expect value 小于 ："+expect+" actual value is ：" +actual+ " assert result is: "+result+" || ";
    }


    public  String AssertEqual(String expect,String actual)
    {
        if (expect.equals(actual)) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(expect, actual, caseresult);
        return assertinfo;
    }

    public  String AssertEqual(int expect,int actual)
    {
        if (expect==actual) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(double expect,double actual)
    {
        if (expect==actual) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(long expect,long actual)
    {
        if (expect==actual) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(float expect,float actual)
    {
        if (expect==actual) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(Integer expect,Integer actual)
    {
        if (expect.equals(actual)) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(Long expect,Long actual)
    {
        if (expect.equals(actual)) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(Float expect,Float actual)
    {
        if (expect.equals(actual)) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(Double expect,Double actual)
    {
        if (expect.equals(actual)) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(boolean expect,boolean actual)
    {
        if (expect==actual) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(Boolean expect,Boolean actual)
    {
        if (expect.equals(actual)) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertEqual(char expect,char actual)
    {
        if (expect==actual) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfo(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }


    public  String AssertMore(int expect,int actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfomore(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }


    public  String AssertMore(long expect,long actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfomore(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertMore(float expect,float actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfomore(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertMore(double expect,double actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfomore(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }



    public  String AssertLess(int expect,int actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfoless(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }


    public  String AssertLess(long expect,long actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfoless(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertLess(float expect,float actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfoless(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }

    public  String AssertLess(double expect,double actual)
    {
        if (actual>expect) {
            caseresult = true;
        }
        else
        {
            caseresult = false;
        }
        assertinfo =assertinfo +  collectioninfoless(String.valueOf(expect), String.valueOf(actual), caseresult);
        return assertinfo;
    }
}
