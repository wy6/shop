package com.zcib.dao;

import com.zcib.domain.Activate;
import com.zcib.util.JDBCUtils;

/**
 * Created by WY on 2018/1/10.
 */

public class ActivateDao {
    public void add(Activate activate) {
        String sql = "insert into activate value(null, ?, ?, ?";
        Object params[] = {
                activate.getCode(), new java.sql.Timestamp(activate.getExpiredate().getTime()),
                activate.getVipid(),
        };
        JDBCUtils.insert(sql, params);
    }

    public Activate findByCode(String code) {
        String sql = "select * from activate where code = ?";
        return JDBCUtils.selectToBean(Activate.class, sql, code);
    }
}
