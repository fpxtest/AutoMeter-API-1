package com.zoctan.api.core.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * Created by fanseasn on 2020/11/21.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/21
*/
public class MyTimeTask extends TimerTask {

    private static Logger logger = LoggerFactory.getLogger(MyTimeTask.class);

    private String name;
    public MyTimeTask(String inputName){
        name = inputName;
    }
    @Override
    public void run() {
        //打印当前name 的内容

        System.out.println("Current exec name is " + name);
        logger.info(System.currentTimeMillis()+"111");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
