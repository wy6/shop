package com.zcib.servlet;

import com.zcib.service.ProductService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Creat by WY on 2018/1/11.
 */
@WebServlet(urlPatterns = "/appProductServlet",name = "AppProductServlet")
public class AppProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("findAll".equals(action)){
            findAll(request,response);
        }
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         *
         */
        List<Map<String,Object>> list = productService.findTotal();
        System.out.println("list"+list);
        JSONArray jsonArray = JSONArray.fromObject(list);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonArray.toString());

    }
}
