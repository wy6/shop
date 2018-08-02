package com.zcib.service;

import com.zcib.dao.AddressDao;
import com.zcib.domain.Address;
import com.zcib.util.JDBCUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2018/1/6.
 */

public class AddressService {

    private AddressDao addressDao = new AddressDao();
    public void add(Address address) {
        addressDao.add(address);
    }

    public List<Map<String, Object>> findAll(int vipid) {
        String sql = "select * from address where vipid=?";
        return JDBCUtils.select(sql, vipid);
    }

    public void deleteById(int addressid) {
        addressDao.deleteById(addressid);
    }

    public void updateById(Address address) {
        addressDao.updateById(address);
    }

    public Address findById(int addressid) {
        return addressDao.findById(addressid);
    }
}
