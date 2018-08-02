package com.zcib.test;

import com.zcib.util.JDBCUtils;

/**
 * Created by WY on 2017/12/5.
 */

public class TestJDBCUtils {
    public static void main(String[] args){
        String sql = "insert into category values(null, ?, ?)";
        JDBCUtils.insert(sql, "家具", 7);

    }
}
