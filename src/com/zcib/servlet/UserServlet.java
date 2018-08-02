package com.zcib.servlet;

import com.zcib.domain.Activate;
import com.zcib.domain.User;
import com.zcib.exception.UserException;
import com.zcib.service.ProductService;
import com.zcib.service.UserService;
import com.zcib.util.EmailUtils;
import com.zcib.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WY on 2017/12/21.
 */

@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();
    private ProductService productService = new ProductService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("regist".equals(action)) {
            regist(request, response);
        } else if ("login".equals(action)) {
            login(request, response);
        } else if ("activate".equals(action)) {
            activate(request, response);
        } else if ("loginout".equals(action)) {
            loginout(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1.获取参数
         * 2.验证参数的正确性
         * 3.封装到User对象中
         * 4.调用Service层的regist方法进行注册
         * 5.调用Service层的addActivate（code）存入激活码
         * 6.向用户发送激活邮件
         * 7.跳转到login.jsp页面（防止刷新重复提交数据）
         */
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        String email = request.getParameter("email");
        System.out.println("**UserServlet.regist:"+"username="+username+",password="+password+",passwordRepeat="+passwordRepeat);

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "用户名不能为空！");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (userService.usernameEx(username)) {
            request.setAttribute("error", "用户名已存在！");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "密码不能为空！");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (!password.equals(passwordRepeat)) {
            request.setAttribute("error", "两次密|码输入不一致");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        String regex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (!m.find()) {
            request.setAttribute("error", "邮箱格式不正确！");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setLastlogintime(new Date());
        user.setStatus(1);

        userService.regist(user);
        //生成激活码
        String code = UUID.randomUUID().toString().replace("-", "");
        //调用Service方法
        Activate activate = new Activate();
        activate.setCode(code);
        Date now = new Date();
        now = Util.checkOption("next", now);
        activate.setExpiredate(now);
        activate.setVipid(user.getVipid());
        userService.addActivate(activate);

        //发送邮件
        EmailUtils emailUtils = new EmailUtils();
        emailUtils.sendActivateMail(email, code);

        request.setAttribute("msg", "<script>alert('注册成功！');window.location = '"+request.getContextPath()+"/login.jsp'</script>");
        request.getRequestDispatcher("/msg.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute("redirectUrl");

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "用户名不能为空！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "密码不能为空！");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userService.login(username, password);
            if (user.getStatus() != 1) {
                request.setAttribute("error", "该用户未激活，请激活！");
                request.setAttribute("username", username);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            //登录成功后，保存用户成功登录的信息
            request.getSession().setAttribute("user", user);
            if (redirectUrl != null) {
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        } catch (UserException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    //验证激活
    private void activate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1.获取激活码
         * 2.进行激活，调用service方法
         * 3.激活成功，转向登陆界面
         * 4.激活失败，撞向msg.jsp页面
         */
        String code = request.getParameter("code");
        try {
            userService.activate(code);
            //激活成功
            String msg = "<script>alert('激活成功，你现在可以登录了！');window.location.href='"+request.getContextPath()+"/login.jsp';</script>";
            request.setAttribute("msg", msg);
            return;
        } catch (UserException e) {
            //激活失败
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("/msg.jsp").forward(request,response);
        }

    }

    private void loginout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute("redirectUrl");
        session.removeAttribute("user");
        session.removeAttribute("cart");
        request.setAttribute("list", productService.findIndex());
        request.getRequestDispatcher("/myindex.jsp").forward(request, response);
    }

    //查看所有未激活用户
    private void findUnactUser() {
//        userService.findAllUser(0);
    }

    //查看所有激活用户
    private void findActUser() {
//        userService.findAllUser(1);
    }

    //查看所有黑名单用户
    private void findBlaUser() {
//        userService.findAllUser(2);
    }

}
