package com.zcib.servlet;

import com.zcib.domain.User;
import com.zcib.exception.UserException;
import com.zcib.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Creat by WY on 2018/1/10.
 */
@WebServlet(urlPatterns = "/appUserServlet", name = "AppUserServlet")
public class AppUserServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("appLogin".equals(action)) {
            appLogin(request, response);
        }
    }

    private void appLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * 1.读入用户名和密码
         * 2.调用Userservice来 验证用户名和密码是否正确
         * 3.正确，返回用户id  封装到JSONArray对象中
         * 4.不正确，返回用户的id=0 封装到JSONArray对象中
         *
         */
        //1.读入用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username" + username + "password" + password);

        //2.调用Userservice来 验证用户名和密码是否正确
        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            User user = userService.login(username, password);
            //3.正确，返回用户id  封装到JSONArray对象中
            jsonObject.put("id", user.getVipid());
        } catch (UserException e) {

            //4.不正确，返回用户的id=0 封装到JSONArray对象中
            jsonObject.put("id", 0);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        response.getWriter().write(jsonArray.toString());

    }
}
