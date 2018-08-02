package com.zcib.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zcib.domain.*;
import com.zcib.util.BeanUtils;
import com.zcib.util.JDBCUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2018/1/7.
 */

public class OrderDao {

    public void addOrder(Order order) {
        String sql = "insert into orders values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.Timestamp timestamp = new java.sql.Timestamp(order.getOrdertime().getTime());
        Object params[] = {
                order.getOrderid(), timestamp, order.getStatus(),
                order.getAddress().getAddressname(), order.getAddress().getPostcode(),
                order.getAddress().getReceiver(), order.getAddress().getPhone(),
                order.getTotalprice(), order.getContent(), order.getUser().getVipid(),
        };
        JDBCUtils.update(sql, params);
    }

    public void addOrderItem(List<OrderItem> orderItemList) {
        String sql = "insert into orderitem values(null, ?, ?, ?, ?, ?, ?, ?)";
        Object params[][] = new Object[orderItemList.size()][];//创建二维数据，行数是插入的条数
        for (int i = 0; i < orderItemList.size(); i++) {
            //取出每一个条目，对应一个一维数组
            OrderItem item = orderItemList.get(i);
            params[i] = new Object[]{
                    item.getBuycount(), item.getTotal(), item.getProduct().getProductid(),
                    item.getProduct().getName(), item.getProduct().getPrice(),
                    item.getProduct().getPhoto(), item.getOrder().getOrderid(),
            };
        }
        List<Number> keys = JDBCUtils.insertBatch(sql, params);
        for (int i = 0; i < keys.size(); i++) {
            OrderItem item = orderItemList.get(i);
            item.setId(keys.get(i).intValue());
            System.out.println("**OrderDao.addOrderItem.43:" + "");
        }
    }

    public Order findById(String orderid, int vipid) {
        String sql = "select * from orders where orderid = ? and vipid = ?";
        Map<String, Object> map = JDBCUtils.select(sql, orderid, vipid).get(0);
        Order order = null;
        try {
            order = BeanUtils.toBean(map, Order.class);
            User user = BeanUtils.toBean(map, User.class);
            Address address = BeanUtils.toBean(map, Address.class);
            order.setUser(user);
            order.setAddress(address);

            //获取订单条目列表
            sql = "select * from orderitem where orderid = ?";
            List<Map<String, Object>> list = JDBCUtils.select(sql, orderid);
            List<OrderItem> orderItemList = new ArrayList<OrderItem>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map2 = list.get(i);
                OrderItem orderItem = BeanUtils.toBean(map2, OrderItem.class);
                //获取商品对象
                Product product = BeanUtils.toBean(map2, Product.class);
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItemList.add(orderItem);

            }
            order.setOrderItemList(orderItemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    //读取登录用户所有的订单
    public List<Order> findAll(int vipid) {
        String sql = "select * from orders where vipid = ?";
        List<Map<String, Object>> listOrders = JDBCUtils.select(sql, vipid);
        List<Order> orders = new ArrayList<Order>();
        for (int i = 0; i < listOrders.size(); i++) {
            Map<String, Object> map = listOrders.get(i);
            //将第i条记录转换成order对象
            Order order = null;
            try {
                order = BeanUtils.toBean(map, Order.class);
                User user = BeanUtils.toBean(map, User.class);
                Address address = BeanUtils.toBean(map, Address.class);
                order.setUser(user);
                order.setAddress(address);
                //获取订单条目
                sql = "select * from orderitem where orderid = ?";
                List<Map<String, Object>> list = JDBCUtils.select(sql, order.getOrderid());
                List<OrderItem> orderItemList = new ArrayList<OrderItem>();
                for (int j = 0; j < list.size(); j++) {
                    Map<String, Object> map1 = list.get(j);
                    OrderItem orderItem = BeanUtils.toBean(map1, OrderItem.class);
                    //获取商品对象
                    Product product = BeanUtils.toBean(map1, Product.class);
                    orderItem.setProduct(product);
                    orderItem.setOrder(order);
                    orderItemList.add(orderItem);
                }
                order.setOrderItemList(orderItemList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            orders.add(order);
        }
        return orders;
    }

    public int findCount(String skey, String svalue) {
        StringBuilder str = new StringBuilder("select count(*) from orders ");
        if (skey != null && skey.trim().length() > 0 && svalue != null && svalue.trim().length() > 0) {
            // 有查询条件
            str.append(" where " + skey.toString() + " like \"%" + svalue + "%\"");
        }
        Number count = JDBCUtils.selectScalar(str.toString());
        return count.intValue();
    }

    //查询数据库中所有订单数据
    public List<Order> findAll(int startIndex, int pageSize, String skey, String svalue) {
        StringBuilder str = new StringBuilder("select * from orders");
        List<Order> orders = new ArrayList<Order>();
        if (skey != null && skey.trim().length() > 0 && svalue != null && svalue.trim().length() > 0) {
            // 有查询条件
            str.append(" where " + skey.toString() + " like \"%" + svalue + "%\" ");
        }
        str.append(" order by ordertime limit ?, ?");
        List<Map<String, Object>> list = JDBCUtils.select(str.toString(), startIndex, pageSize);

        for (Map<String, Object> map : list) {
            Order order = new Order();
            Address address = new Address();
            try {
                order = BeanUtils.toBean(map, Order.class);
                address = BeanUtils.toBean(map, Address.class);
                order.setAddress(address);
                List<OrderItem> orderItems = new ArrayList<OrderItem>();
                String sql = "select * from orderitem where orderid = ?";
                List<Map<String, Object>> orderItemList = JDBCUtils.select(sql, order.getOrderid());
                for (Map<String, Object> item : orderItemList) {
                    OrderItem orderItem = BeanUtils.toBean(item, OrderItem.class);
                    Product product = BeanUtils.toBean(item, Product.class);
                    orderItem.setProduct(product);
                    orderItem.setOrder(order);
                    orderItems.add(orderItem);
                }
                order.setOrderItemList(orderItems);
            } catch (Exception e) {
                e.printStackTrace();
            }
            orders.add(order);
        }
        return orders;
    }

    public void deleteMore(String[] ids) {
        String sql = "delete from orders where orderid=?";
        String sql1 = "delete from orderitem where orderid = ?";
        String[][] params = new String[ids.length][1];
        for (int i = 0; i < params.length; i++) {
            params[i][0] = ids[i];
        }
        JDBCUtils.updateBatch(sql, params);
        JDBCUtils.updateBatch(sql1, params);
    }

    public void update(String orderid) {
        String sql = "update orders set status = 2 where orderid=?";
        JDBCUtils.update(sql, orderid);
    }
}
