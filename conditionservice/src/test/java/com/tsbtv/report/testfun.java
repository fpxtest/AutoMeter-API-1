package com.tsbtv.report;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanseasn on 2021/3/21.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2021/3/21
*/
public class testfun {


    public static void main(String[] args) {


        int num=100/7;
        int num1=100%7;

        ArrayList<String> ll = Lists.newArrayList("a","b","c","d","e","f","g","h","i","j","k","l");

        List<List<String>> last= SplitList(ll,5);


        System.out.println(last.size());

        System.out.println(last);

    }

    public static List<List<String>> SplitList(List<String> sList, int num) {

        if(sList.size()<num)
        {
            num=sList.size();
        }

        List<List<String>> eList = new ArrayList<List<String>>();
        List<String> gList;

        int size = (sList.size()) / num;
        int size2 = (sList.size()) % num;
        int j = 0;
        int xx = 0;
        for (int i = 0; i < num; i++) {
            gList = new ArrayList<String>();

            for (j = xx; j < (size + xx); j++) {
                gList.add(sList.get(j));
            }
            xx = j;
            eList.add(gList);
        }
        if (size2 != 0) {
            //gList = new ArrayList<String>();
            for (int y = 1; y < size2 + 1; y++) {
                eList.get(eList.size()-1).add(sList.get(sList.size() - y));
            }
        }
        return eList;
    }

    public static   ArrayList<ArrayList<String>> splitList(ArrayList<String> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        int sourceSize = source.size();
        int size;
        if (source.size() % n == 0) {
            size = source.size() / n;
        } else {
            size = (source.size() / n) + 1;
        }
        for (int i = 0; i < size; i++) {
            ArrayList<String> subset = new ArrayList<>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }






}



