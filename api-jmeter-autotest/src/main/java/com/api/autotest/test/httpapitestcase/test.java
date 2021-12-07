package com.api.autotest.test.httpapitestcase;
import cn.hutool.*;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class test {

    public static void main(String[] args) {

        DataSource ds = new SimpleDataSource("jdbc:mysql://127.0.0.1:3306/testcenter", "test", "test");
        try {
            Db.use(ds).execute("update condition_db set dbcontent = '123456' where id=7");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
