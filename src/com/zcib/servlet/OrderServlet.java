package com.zcib.servlet;

import com.zcib.domain.*;
import com.zcib.service.AddressService;
import com.zcib.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by WY on 2018/1/7.
 */

@WebServlet(name = "OrderServlet", urlPatterns = "/orderServlet")
public class OrderServlet extends HttpServlet {

    private AddressService addressService = new AddressService();
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("addOrder".equals(action)) {
            addOrder(request, response);
        } else if ("findAll".equals(action)) {
            findAll(request, response);
        } else if ("findById".equals(action)) {
            findById(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * 1.判断用户是否登录
         * 2.从表单中取出收货地址
         * 3.创建Order
         * 4.调用Service层添加订单
         * 5.清空购物车
         * 6.转向paying.jsp页面（注意刷新重复提交问题）
         */
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String addressid = request.getParameter("id");
        String content = request.getParameter("content");
        System.out.println("**OrderServlet.addOrder.59:"+"addressid="+addressid+",content="+content);

        Order order = new Order();
        String orderid = UUID.randomUUID().toString().replace("-", "");
        order.setOrderid(orderid);
        order.setOrdertime(new Date());
        order.setStatus(0);
        order.setTotalprice((Float)session.getAttribute("totalPrice"));
        order.setContent(content);
        order.setUser(user);

        Address address = addressService.findById(Integer.parseInt(addressid));
        //把省市区合并到addressname中
        StringBuilder addressname = new StringBuilder(address.getProvince());
        String city = address.getCity();
        if ("市辖区".equals(city) || "县".equals(city)) {
            city = "";
        }
        String area = address.getArea();
        if ("市辖区".equals(area) || "县".equals(area)) {
            area = "";
        }
        addressname.append(city);
        addressname.append(area);
        address.setAddressname(addressname.toString());
        order.setAddress(address);

        //设置订单条目项
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        //把购物车中每一个商品取出生成对应的订单条目
        for (int i = 0; i < cart.size(); i++) {
            Map<String, Object> map = cart.get(i);
            //创建订单条目项
            OrderItem orderItem = new OrderItem();
            orderItem.setBuycount((Integer) map.get("buyCount"));
            orderItem.setTotal((Float) map.get("total"));
            //设置Product
            Product product = new Product();
            product.setProductid((Integer) map.get("productid"));
            product.setName((String) map.get("name"));
            product.setPhoto((String) map.get("photo"));
            product.setPrice((Float) map.get("price"));
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);
        orderService.addOrder(order);
        cart.removeAll(cart);
        session.removeAttribute("cart");
        session.removeAttribute("totalPrice");

        String msg = "<script>window.location.href='" + request.getContextPath() + "/home/paying.jsp';</script>";
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/msg.jsp").forward(request, response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int vipid = user.getVipid();
        System.out.println("**OrderServlet.findAll.123:"+"vipid="+vipid);
        request.setAttribute("orderlist", orderService.findAll(vipid));
        request.getRequestDispatcher("/person/order.jsp").forward(request, response);
    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         *1.判断用户是否登陆
         * 2.获取订单id
         * 3.0通过service的findById方法查询所有订单
         * 3.1把读出的order对象存入request中
         * 3.2重定向到订单详情页面/person/orderinfo.jsp
         */
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String orderid = request.getParameter("id");
        int vipid = user.getVipid();
        Order order = orderService.findById(orderid, vipid);
        request.setAttribute("order",order);
        request.getRequestDispatcher("/person/orderinfo.jsp").forward(request,response);
    }
}
