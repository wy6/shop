package com.zcib.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by WY on 2018/1/9.
 */

@WebFilter(filterName = "EncodingFilter", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}, urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");//处理post的请求
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getMethod().equalsIgnoreCase("get")){
            EncodingServletRequest esr = new EncodingServletRequest(request);
            chain.doFilter(esr, resp);
        }else if (request.getMethod().equalsIgnoreCase("post")){
            chain.doFilter(req, resp);
        }else {
            chain.doFilter(req, resp);
        }
        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
