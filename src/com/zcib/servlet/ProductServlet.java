package com.zcib.servlet;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import com.sun.xml.internal.org.jvnet.staxex.Base64EncoderStream;
import com.zcib.domain.Page;
import com.zcib.service.ProductService;
import com.zcib.util.BaseCalculate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/22.
 */

@WebServlet(name = "ProductServlet", urlPatterns = "/productServlet")
public class ProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("findIndex".equals(action)) {
            findIndex(request, response);
        } else if ("findAll".equals(action)) {
            findAll(request, response);
        } else if ("findById".equals(action)) {
            findById(request, response);
        } else if ("addCart".equals(action)) {
            addCart(request, response);
        } else if ("updateBuyCount".equals(action)) {
            updateBuyCount(request, response);
        } else if ("deleteCartById".equals(action)) {
            deleteCartById(request, response);
        } else if ("deleteCartMore".equals(action)) {
            deleteCartMore(request, response);
        } else if ("payBefore".equals(action)) {
            payBefore(request, response);
        } else {
            findIndex(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    private void findIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("list", productService.findIndex());
        request.getRequestDispatcher("/myindex.jsp").forward(request, response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String current = request.getParameter("current");
        int currentPage;
        try {
            currentPage = Integer.parseInt(current);
        } catch (Exception e) {
            currentPage = 1;
        }
        String skey = "name";
        String keywords = request.getParameter("keywords");
        String sortkey = request.getParameter("sortkey");
        String sort = request.getParameter("sort");
        System.out.println("**ProductServlet.findAll.77:"+"skey="+skey+",keywords="+keywords+",sortkey="+sortkey+",sort="+sort);

        Page page = productService.findAll(currentPage, skey, keywords, sortkey, sort);
        request.setAttribute("sortkey", sortkey);
        request.setAttribute("sort", sort);
        request.setAttribute("keywords", keywords);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/productList.jsp").forward(request, response);
    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sid = request.getParameter("id");
        request.setAttribute("item", productService.findById(Integer.parseInt(sid)));
        request.getRequestDispatcher("/productDetails.jsp").forward(request, response);
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("productid"));
        int buyCount = Integer.parseInt(request.getParameter("buyCount"));
        System.out.println("**ProductServlet.addCart:"+"productid="+id+",buyCount="+buyCount);
        Map<String, Object> map = productService.findById(id);
        List<Map<String, Object>> cart = null;
        HttpSession session = request.getSession();
        cart = (List<Map<String, Object>>) session.getAttribute("cart");
        float price = Float.parseFloat(map.get("price").toString());
        float totalPrice = 0;
        if (cart == null) {
            cart = new ArrayList<Map<String, Object>>();
            map.put("buyCount", buyCount);
            map.put("total", BaseCalculate.multiply(price, buyCount));
            cart.add(map);
            totalPrice = BaseCalculate.multiply(price, buyCount);
            System.out.println("**ProductServlet.addCart.99:"+"目前没有cart，创建并放入了商品id为："+map.get("productid")+"的商品"+map.get("buyCount")+"件,商品名为："+map.get("name"));
        } else {
            //循环查找是否存在该商品
            boolean inCart = false;
            for (int i = 0; i < cart.size(); i++) {
                Map<String, Object> item = cart.get(i);
                if (item.get("productid").equals(map.get("productid"))) {
                    buyCount += Integer.parseInt(item.get("buyCount").toString());
                    item.put("buyCount", buyCount);
                    float total = BaseCalculate.multiply(price, buyCount);
                    item.put("total", total);
                    totalPrice = BaseCalculate.add(totalPrice, total);
                    inCart = true;
                    System.out.println("**ProductServlet.addCart.111:"+"目前在cart中查找id为"+map.get("productid")+"的商品，且找到了,修改商品购买数量为："+item.get("buyCount"));
                    break;
                } else{
                    totalPrice = BaseCalculate.add(totalPrice, Float.parseFloat(item.get("total").toString()));
                }
            }
            if (!inCart) {
                map.put("buyCount", buyCount);
                float total = BaseCalculate.multiply(price, buyCount);
                map.put("total", total);
                totalPrice = BaseCalculate.add(totalPrice, total);
                cart.add(map);
                System.out.println("**ProductServlet.addCart.120:"+"目前在cart中没有找到id为"+map.get("productid")+"的商品，直接新加放入,商品购买数量为:"+map.get("buyCount"));
            }
        }
        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", totalPrice);
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }

    private void updateBuyCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sid = request.getParameter("id");
        String sbuyCount = request.getParameter("buyCount");
        int id = Integer.parseInt(sid);
        int buyCount = Integer.parseInt(sbuyCount);
        float totalPrice = 0;
        float idtotal = 0;

        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        for (int i = 0; i < cart.size(); i++) {
            Map<String, Object> item = cart.get(i);
            if (item.get("productid").toString().equals(sid)) {
                item.put("buyCount", buyCount);
                float price = Float.parseFloat(item.get("price").toString());
                float total = BaseCalculate.multiply(price, buyCount);
                item.put("total", total);
                totalPrice = BaseCalculate.add(totalPrice, total);
                idtotal = total;
            } else {
                float price = Float.parseFloat(item.get("price").toString());
                float total = BaseCalculate.multiply(price, Float.parseFloat(item.get("buyCount").toString()));
                totalPrice = BaseCalculate.add(totalPrice, total);
            }
        }
        String jsonstr = "{\"total\":" + idtotal + ",\"totalPrice\":" + totalPrice + "}";
        response.getWriter().print(jsonstr);
    }

    private void deleteCartById(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        float totalPrice = (float) session.getAttribute("totalPrice");

        for (int i = 0; i < cart.size(); i++) {
            Map<String, Object> item = cart.get(i);
            if (item.get("productid").toString().equals(id)) {
                float total = Float.parseFloat(item.get("total").toString());
                totalPrice = BaseCalculate.substract(totalPrice, total);
                cart.remove(i);
                break;
            }
        }
        session.setAttribute("totalPrice", totalPrice);
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }

    private void deleteCartMore(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String ids[] = request.getParameterValues("sel");
        if (ids != null && ids.length > 0) {
            HttpSession session = request.getSession();
            List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
            float totalPrice = (float) session.getAttribute("totalPrice");
            for (int i = 0; i < ids.length; i++) {
                for (int j = 0; j < cart.size(); j++) {
                    Map<String, Object> item = cart.get(j);
                    String itemProductid = item.get("productid").toString();
                    if (itemProductid.equals(ids[i])) {
                        System.out.println("**ProductServlet.deleteCartMore.198:"+"ids.length="+ids.length+",ids[0]="+ids[0]+",ids[1]="+ids[1]+",当前删除id为"+ids[i]);
                        float total = (float) item.get("total");
                        totalPrice = BaseCalculate.substract(totalPrice, total);
                        cart.remove(j);
                        break;
                    }
                }
            }
            session.setAttribute("totalPrice", totalPrice);
        }
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }

    private void payBefore(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
//        if (session.getAttribute("user")!=null) {
//            response.sendRedirect(request.getContextPath()+"/home/pay.jsp");
//        }else {
//            response.sendRedirect(request.getContextPath()+"/login.jsp");
//        }
        response.sendRedirect(request.getContextPath()+"/home/pay.jsp");
    }


}
