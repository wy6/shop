package com.zcib.test;

import com.zcib.dao.UserDao;
import com.zcib.domain.Order;
import com.zcib.domain.Page;
import com.zcib.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/7.
 */

public class Test {

    public static void main(String[] args) {
//        Connection conn = null;
//        PreparedStatement pre = null;
//        conn = JDBCUtils.getConnection();


//        String sql = "insert into bankaccount values(null, ?, ?)";
//        Object params[][] = {{"fate", 1000}, {"tom", 200}, {"jack", 500}};
//        JDBCUtils.insertBatch(sql, params);
//        System.out.println(JDBCUtils.insertBatch(sql, params));

//        String sql = "select count(*) from category";
//        Number n = JDBCUtils.selectScalar(sql);
//        System.out.println(n.intValue());

        UserDao userDao = new UserDao();
//        List<Map<String, Object>> list = userDao.findAllUser(1, 10, 0);
//        System.out.println("**Test.main.33:"+list);
    }
}
