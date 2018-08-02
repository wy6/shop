package com.zcib.servlet;

import com.zcib.domain.Order;
import com.zcib.domain.Page;
import com.zcib.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by WY on 2018/1/14.
 */

@WebServlet(name = "ManageOrderServlet", urlPatterns = "/manageOrderServlet")
public class ManageOrderServlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("findAllOrder".equals(action)) {
            findAllOrder(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void findAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("current");
        if (currentPage == null) {
            currentPage = "1";
        }
        String skey = request.getParameter("skey");
        String svalue = request.getParameter("svalue");
        System.out.println("**ManageOrderServlet.findAllOrder.40:"+"skey="+skey+",svalue="+svalue);

        Page<Order> page = orderService.findAllOrder(Integer.parseInt(currentPage), skey, svalue);
        request.setAttribute("page", page);
        request.setAttribute("skey", skey);
        request.setAttribute("svalue", svalue);
        request.getRequestDispatcher("/manage/orderManage.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderid = request.getParameter("orderid");
        orderService.update(orderid);
        request.setAttribute("msg", "<script>alert('发货成功');window.location.href='/manageOrderServlet?method=findAllOrder'</script>");
        request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);

    }

    public void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ids = request.getParameterValues("orderids");
        orderService.deleteMore(ids);
        findAllOrder(request, response);
    }
}
