package com.zcib.filter;

import com.zcib.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by WY on 2018/1/9.
 */

@WebFilter( urlPatterns = {"/home/*", "/person/*"}, servletNames = {"AddressServlet", "OrderServlet"}, filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            if (null != request.getQueryString()) {
                session.setAttribute("redirectUrl",
                        request.getRequestURL().append("?").append(request.getQueryString()).toString());
            } else {
                session.setAttribute("redirectUrl", request.getRequestURL().toString());
            }
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
