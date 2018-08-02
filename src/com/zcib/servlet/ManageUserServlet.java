package com.zcib.servlet;

import com.zcib.domain.Page;
import com.zcib.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by WY on 2018/1/10.
 */

@WebServlet(name = "ManageUserServlet", urlPatterns = "/manageUserServlet")
public class ManageUserServlet extends HttpServlet {

    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("findAllUser".equals(action)) {
            findAllUser(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void findAllUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String current = request.getParameter("current");
        int currentPage;
        try {
            currentPage = Integer.parseInt(current);
        } catch (Exception e) {
            currentPage = 1;
        }
        String skey = request.getParameter("skey");
        String keywords = request.getParameter("keywords");
        String status = request.getParameter("status");
        System.out.println("**ManageUserServlet.findAllUser.44:"+"skey="+skey+",keywords="+keywords+",status="+status);
        Page page;
        if (status == null || status.trim().isEmpty()) {
            page = userService.findAllUser(currentPage, skey, keywords);
        } else {
            page = userService.findAllUser(currentPage, status, skey, keywords);
        }
        request.setAttribute("keywords", keywords);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/manage/userList.jsp").forward(request, response);
        System.out.println("**ManageUserServlet.findAllUser.50:"+"keywords="+keywords+",status="+status+",page="+page);
    }

}
