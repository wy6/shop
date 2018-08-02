package com.zcib.servlet;

import com.zcib.domain.Page;
import com.zcib.domain.Product;
import com.zcib.service.CategoryService;
import com.zcib.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by WY on 2017/12/12.
 */

@WebServlet("/manageProductServlet")
public class ManageProductServlet extends HttpServlet {

    private CategoryService categoryService = new CategoryService();
    private ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("categoryInfo".equals(action)) {
            categoryInfo(request, response);
        }else if ("addProduct".equals(action)) {
            addProduct(request, response);
        }else if ("findAll".equals(action)) {
            findAll(request, response);
        }else if ("findById".equals(action)) {
            findById(request, response);
        }else if ("idForUpdate".equals(action)) {
            idForUpdate(request, response);
        }else if ("update".equals(action)) {
            update(request, response);
        }else if ("deleteById".equals(action)) {
            deleteById(request, response);
        }else if ("deleteMore".equals(action)) {
            deleteMore(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    private void categoryInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("clist", categoryService.findAll());
        request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("productName");
        String photo = request.getParameter("photo");
        String price = request.getParameter("price");
        String markPrice = request.getParameter("markPrice");
        String quality = request.getParameter("quality");
        String hit = request.getParameter("hit");
        String publishDate = request.getParameter("publishDate");
        String introduction = request.getParameter("introduction");
        String content = request.getParameter("content");
        String categoryid = request.getParameter("categoryid");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("msg","商品名称不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (photo == null || photo.trim().isEmpty()) {
            request.setAttribute("msg","上传图片不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (price == null || price.trim().isEmpty()) {
            request.setAttribute("msg","商品价格不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (markPrice == null || markPrice.trim().isEmpty()) {
            request.setAttribute("msg","商品市场价格不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (quality == null || quality.trim().isEmpty()) {
            request.setAttribute("msg","商品数量不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (hit == null || hit.trim().isEmpty()) {
            request.setAttribute("msg","商品浏览量不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (publishDate == null || publishDate.trim().isEmpty()) {
            request.setAttribute("msg","商品上架时间不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("msg","商品描述不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setMarkprice(Float.parseFloat(markPrice));
        product.setPhoto(photo);
        product.setQuality(Integer.parseInt(quality));
        product.setHit(Integer.parseInt(hit));
        try {
            product.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(publishDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        product.setIntroduction(introduction);
        product.setContent(content);
        product.setCategoryid(Integer.parseInt(categoryid));

        productService.add(product);
        request.setAttribute("msg", "<script>alert('商品添加成功!');window.location.href='"+request.getContextPath()+"/manageProductServlet?action=findAll'</script>");
        request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String current = request.getParameter("current");
        String skey = request.getParameter("skey");
        String keywords = request.getParameter("keywords");
        System.out.println("**ManageProductservlet.findAll:current=" + current + ",skey=" + skey + ",keywords=" + keywords);
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(current);
        } catch (Exception e) {
            currentPage = 1;
        }
        Page page = productService.findAll(currentPage, skey, keywords);
        request.setAttribute("page", page);
        request.setAttribute("skey", skey);
        request.setAttribute("keywords", keywords);
        request.getRequestDispatcher("/manage/productManage.jsp").forward(request, response);
    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        request.setAttribute("item", productService.findById(Integer.parseInt(id)));
        request.getRequestDispatcher("/manage/updateProduct.jsp").forward(request, response);
    }

    private void idForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        request.setAttribute("item", productService.findById(Integer.parseInt(id)));
        request.setAttribute("clist", categoryService.findAll());
        request.getRequestDispatcher("/manage/updateProduct.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productid = request.getParameter("productid");
        String name = request.getParameter("productName");
        String photo = request.getParameter("photo");
        String price = request.getParameter("price");
        String markPrice = request.getParameter("markPrice");
        String quality = request.getParameter("quality");
        String hit = request.getParameter("hit");
        String publishDate = request.getParameter("publishDate");
        String introduction = request.getParameter("introduction");
        String content = request.getParameter("content");
        String categoryid = request.getParameter("categoryid");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("msg","商品名称不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (photo == null || photo.trim().isEmpty()) {
            request.setAttribute("msg","上传图片不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (price == null || price.trim().isEmpty()) {
            request.setAttribute("msg","商品价格不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (markPrice == null || markPrice.trim().isEmpty()) {
            request.setAttribute("msg","商品市场价格不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (quality == null || quality.trim().isEmpty()) {
            request.setAttribute("msg","商品数量不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (hit == null || hit.trim().isEmpty()) {
            request.setAttribute("msg","商品浏览量不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (publishDate == null || publishDate.trim().isEmpty()) {
            request.setAttribute("msg","商品上架时间不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }
        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("msg","商品描述不能为空！");
            request.getRequestDispatcher("/manage/addProduct.jsp").forward(request, response);
            return;
        }

        Product product = new Product();
        product.setProductid(Integer.parseInt(productid));
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setMarkprice(Float.parseFloat(markPrice));
        product.setPhoto(photo);
        product.setQuality(Integer.parseInt(quality));
        product.setHit(Integer.parseInt(hit));
        try {
            product.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(publishDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        product.setIntroduction(introduction);
        product.setContent(content);
        product.setCategoryid(Integer.parseInt(categoryid));

        productService.update(product);
        request.setAttribute("msg", "<script>alert('商品添加成功!');window.location.href='"+request.getContextPath()+"/manageProductServlet?action=findAll'</script>");
        request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        try {
            int productid = Integer.parseInt(id);
            productService.deleteById(productid);
            request.setAttribute("msg", "<script>alert('商品删除成功!');window.location.href='"+request.getContextPath()+"/manageProductServlet?action=findAll'</script>");
            request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ids[] = request.getParameterValues("sel");
        if (ids == null || ids.length == 0) {
            findAll(request, response);
            return;
        }
        productService.deleteMore(ids);
        request.setAttribute("msg", "<script>alert('商品删除成功!');window.location.href='"+request.getContextPath()+"/manageProductServlet?action=findAll'</script>");
        request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);
    }


}
