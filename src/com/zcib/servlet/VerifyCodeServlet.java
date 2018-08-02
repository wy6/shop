package com.zcib.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zcib.util.VerifyCode;
@WebServlet(name = "VerifyCodeServlet", urlPatterns = "/verifyCodeServlet")

public class VerifyCodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		 * 输出一张验证码图片
		 */
		
		VerifyCode vc = new VerifyCode();
		//获取验证码图片
		BufferedImage image = vc.getImage();
		//
		request.getSession().setAttribute("verifyCode", vc.getText());
		//把验证码输出到客户端
		vc.output(image, response.getOutputStream());
	}

}
