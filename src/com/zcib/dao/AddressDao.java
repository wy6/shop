package com.zcib.dao;

import com.zcib.domain.Address;
import com.zcib.util.JDBCUtils;

import java.util.Map;

/**
 * Created by WY on 2018/1/6.
 */

public class AddressDao {

    public void add(Address address) {
        String sql = "insert into address values(null, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object params[] ={
               address.getAddressname(), address.getProvince(), address.getCity(),
                address.getArea(), address.getPostcode(), address.getReceiver(),
                address.getPhone(), address.getVipid(),
        };
        int id = ((Number)JDBCUtils.insert(sql, params)).intValue();
        address.setAddressid(id);
    }

    public void deleteById(int addressid) {
        String sql = "delete from address where addressid = ?";
        JDBCUtils.update(sql, addressid);
    }

    public void updateById(Address address) {
        if (address.getProvince().trim().isEmpty() && address.getCity().trim().isEmpty() && address.getArea().trim().isEmpty()) {
            System.out.println("**AddressDao.updateById.32:"+"province=0");
            String sql = "update address set addressname = ?, postcode = ?, receiver = ?, phone = ? where addressid = ?";
            Object params[] = {
                    address.getAddressname(), address.getPostcode(), address.getReceiver(),
                    address.getPhone(), address.getAddressid(),
            };
            JDBCUtils.update(sql, params);
        } else {
            String sql = "update address set addressname = ?, province = ?, city = ?, area = ?, postcode = ?, receiver = ?, phone = ? where addressid = ?";
            Object params[] = {
                    address.getAddressname(), address.getProvince(), address.getCity(),
                    address.getArea(), address.getPostcode(), address.getReceiver(),
                    address.getPhone(), address.getAddressid(),
            };
            JDBCUtils.update(sql, params);
        }
    }

    public Address findById(int addressid) {
        String sql = "select * from address where addressid = ?";
        Address address = JDBCUtils.selectToBean(Address.class, sql, addressid);
//        Map<String, Object> map = JDBCUtils.select(sql, addressid).get(0);
//        Address address = new Address();
//        address.setAddressid((Integer) map.get("addressid"));
//        address.setAddressname((String) map.get("addressname"));
//        address.setPhone((String) map.get("phone"));
//        address.setProvince((String) map.get("province"));
//        address.setCity((String) map.get("city"));
//        address.setArea((String) map.get("area"));
//        address.setPostcode((String) map.get("postcode"));
//        address.setReceiver((String) map.get("receiver"));
//        address.setVipid((Integer) map.get("vipid"));
        return address;
    }

}
