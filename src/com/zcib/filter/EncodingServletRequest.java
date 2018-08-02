package com.zcib.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by WY on 2018/1/9.
 */

public class EncodingServletRequest extends HttpServletRequestWrapper {
    private HttpServletRequest req;

    public EncodingServletRequest(HttpServletRequest request) {
        super(request);
        this.req = request;
    }

    @Override
    public String getParameter(String name) {
        String value = req.getParameter(name);
        try {
            if (value != null) {
                value = new String(value.getBytes("iso-8859-1"), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
