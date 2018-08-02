package com.zcib.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zcib.dao.OrderDao;
import com.zcib.domain.Order;
import com.zcib.domain.Page;
import com.zcib.util.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by WY on 2018/1/7.
 */

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    public void addOrder(Order order) {
        try {
            //开启事务
            JDBCUtils.startTransaction();
            orderDao.addOrder(order);//添加订单
            orderDao.addOrderItem(order.getOrderItemList());//添加订单条目
            JDBCUtils.commitTransaction();//提交事务
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //回滚事务
                JDBCUtils.rollbackTranscation();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Order> findAll(int vipid) {
        return orderDao.findAll(vipid);
    }

    public Order findById(String orderid, int vipid) {
        return orderDao.findById(orderid, vipid);
    }

    public Page<Order> findAllOrder(int parseInt, String skey, String svalue) {
        int totalSize  =  orderDao.findCount(skey,svalue);
        Page<Order> page = new Page<Order>(parseInt, totalSize);
        if (totalSize>0) {
            List<Order> list = orderDao.findAll(page.getStartIndex(), page.getPageSize(),skey,svalue);
            page.setList(list);
        }
        return page;
    }

    public void deleteMore(String[] ids) {
        orderDao.deleteMore(ids);
    }

    public void update(String orderid) {
        orderDao.update(orderid);
    }
}
