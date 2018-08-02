package com.zcib.servlet;

import com.zcib.domain.Category;
import com.zcib.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by WY on 2017/12/6.
 */

@WebServlet("/manageCategoryServlet")
public class ManageCategoryServlet extends HttpServlet {

    private CategoryService categoryService = new CategoryService() ;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if("addCategory".equals(action)){
            addCategory(request, response);
        }else if("findAll".equals(action)){
            findAll(request, response);
        }else if("deleteById".equals(action)){
            deleteById(request, response);
        }else if("deleteMore".equals(action)){
            deleteMore(request, response);
        }else if("idForUpdate".equals(action)){
            idForUpdate(request, response);
        }else if("updateCategory".equals(action)){
            updateCategory(request, response);
        }
}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("categoryName");
        String sort = request.getParameter("sort");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("msg_addInfo", "商品分类名称不能为空！");
            request.getRequestDispatcher("/manage/addCategory.jsp").forward(request, response);
            return;
        }else if (sort == null || sort.trim().isEmpty()) {
            request.setAttribute("msg_addInfo", "商品排序号不能为空！");
            request.getRequestDispatcher("/manage/addCategory.jsp").forward(request, response);
            return;
        }
        Category category = new Category();
        category.setName(name);
        category.setSort(Integer.parseInt(sort));
        categoryService.add(category);
        request.setAttribute("msg", "<script>alert('商品分类添加成功!');window.location.href='/manageCategoryServlet?action=findAll'</script>");
        request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String current = request.getParameter("current");
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(current);
        } catch (Exception e){
            currentPage = 1;
        }

        request.setAttribute("page", categoryService.findAll(currentPage));
        request.getRequestDispatcher("/manage/categoryManage.jsp").forward(request, response);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String str = request.getParameter("id");
        categoryService.deleteById(Integer.parseInt(str));
        findAll(request, response);
    }

    private void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String []ids = request.getParameterValues("sel");
        if (ids != null) {
            categoryService.deleteMore(ids);
            findAll(request, response);
        }
    }

    private void idForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        request.setAttribute("item", categoryService.findById(Integer.parseInt(id)));
        request.getRequestDispatcher("/manage/updateCategory.jsp").forward(request, response);
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("categoryName");
        String sort = request.getParameter("sort");
        String categoryid = request.getParameter("categoryid");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("msg_addInfo", "商品分类名称不能为空！");
            request.getRequestDispatcher("/manage/addCategory.jsp").forward(request, response);
            return;
        }else if (sort == null || sort.trim().isEmpty()) {
            request.setAttribute("msg_addInfo", "商品排序号不能为空！");
            request.getRequestDispatcher("/manage/addCategory.jsp").forward(request, response);
            return;
        }else if (categoryid == null || categoryid.trim().isEmpty()) {
            request.setAttribute("msg_addInfo", "商品号不能为空！");
            request.getRequestDispatcher("/manage/addCategory.jsp").forward(request, response);
            return;
        }
        Category category = new Category();
        category.setCategoryid(Integer.parseInt(categoryid));
        category.setName(name);
        category.setSort(Integer.parseInt(sort));
        categoryService.update(category);
        request.setAttribute("msg", "<script>alert('商品分类信息修改成功!');window.location.href='"+request.getContextPath()+"/manageCategoryServlet?action=findAll'</script>");
        request.getRequestDispatcher("/manage/msg.jsp").forward(request, response);
    }

}
