package com.zcib.servlet;

import com.zcib.domain.Address;
import com.zcib.domain.User;
import com.zcib.service.AddressService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by WY on 2018/1/6.
 */

@WebServlet(name = "AddressServlet", urlPatterns = "/addressServlet")
public class AddressServlet extends HttpServlet {

    private AddressService addressService = new AddressService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("addAddress".equals(action)) {
            addAddress(request, response);
        } else if ("payBefore".equals(action)) {
            payBefore(request, response);
        } else if ("deleteById".equals(action)) {
            deleteById(request, response);
        } else if ("updateById".equals(action)) {
            updateById(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    private void addAddress(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            response.sendRedirect(request.getContextPath()+"/home/login.jsp");
//            return;
//        }
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String postcode = request.getParameter("postcode");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String area = request.getParameter("area");
        String addressname = request.getParameter("addressname");
        int vipid = user.getVipid();

        if (receiver == null || receiver.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('收货人不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('手机号码不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (postcode == null || postcode.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('邮编不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (addressname == null || addressname.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('详细地址不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (province == null || province.trim().isEmpty() || city == null || city.trim().isEmpty() || area == null || area.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('所在地不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }

        Address address = new Address();
        address.setAddressname(addressname);
        address.setProvince(province);
        address.setCity(city);
        address.setArea(area);
        address.setPhone(phone);
        address.setPostcode(postcode);
        address.setReceiver(receiver);
        address.setVipid(vipid);

        addressService.add(address);
        request.setAttribute("msg", "<script>alert('添加成功！');window.location.href='" + request.getContextPath() + "/addressServlet?action=payBefore';</script>");
        request.getRequestDispatcher("/msg.jsp").forward(request, response);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1.获取addressid
         * 2.调用Service中的deleteById方法进行删除
         * 3.防止刷新重复提交
         */
        String addressid = request.getParameter("id");
        addressService.deleteById(Integer.parseInt(addressid));
        String msg = "<script>alert('删除成功！');window.location.href='" + request.getContextPath() + "/addressServlet?action=payBefore';</script>";
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/msg.jsp").forward(request, response);
    }

    private void updateById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int addressid = Integer.parseInt(request.getParameter("addressid"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String postcode = request.getParameter("postcode");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String area = request.getParameter("area");
        String addressname = request.getParameter("addressname");
        int vipid = user.getVipid();
        if (receiver == null || receiver.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('收货人不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.setAttribute("phone", phone);
            request.setAttribute("postcode", postcode);
            request.setAttribute("addressname", addressname);
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('手机号码不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.setAttribute("receiver", receiver);
            request.setAttribute("postcode", postcode);
            request.setAttribute("addressname", addressname);
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (postcode == null || postcode.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('邮编不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.setAttribute("receiver", receiver);
            request.setAttribute("phone", phone);
            request.setAttribute("addressname", addressname);
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }
        if (addressname == null || addressname.trim().isEmpty()) {
            request.setAttribute("msg", "<script>alert('详细地址不能为空！');window.location.href='" + request.getContextPath() + "/home/pay.jsp';</script>");
            request.setAttribute("receiver", receiver);
            request.setAttribute("phone", phone);
            request.setAttribute("postcode", postcode);
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
            return;
        }

        Address address = new Address();
        address.setAddressid(addressid);
        address.setAddressname(addressname);
        address.setProvince(province);
        address.setCity(city);
        address.setArea(area);
        address.setPhone(phone);
        address.setPostcode(postcode);
        address.setReceiver(receiver);

        addressService.updateById(address);
        request.setAttribute("msg", "<script>alert('修改成功！');window.location.href='" + request.getContextPath() + "/addressServlet?action=payBefore';</script>");
        request.getRequestDispatcher("/msg.jsp").forward(request, response);
    }

    private void payBefore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            response.sendRedirect(request.getContextPath() + "/home/login.jsp");
//            return;
//        }
        int vipid = user.getVipid();
        request.setAttribute("addresslist", addressService.findAll(vipid));
        request.getRequestDispatcher("/home/pay.jsp").forward(request, response);
    }

}
