package com.zcib.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by WY on 2017/12/14.
 */

@WebServlet("/imageUploadServlet")
public class ImageUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        Upload("uploadImages/", request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public String Upload(String path, HttpServletRequest request, HttpServletResponse response) {

        // 1.创建工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 2.通过工厂对象创建解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        String filename = "";
        try {
            // 3.解析request，获取FileItem列表
            List<FileItem> items = sfu.parseRequest(request);
            // 4.循环获取表单项
            for (FileItem item : items) {
                // 判断是否为普通表单项
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                } else {
                    //文件表单项
                    filename = item.getName();
                    // 截取文件名
                    int index = filename.indexOf("\\");
                    if (index != -1) {
                        filename = filename.substring(index + 1);
                    }
                    //使用年月日作为文件的放置路径
                    Date date = new Date();
                    //获取年月日
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    path = path + sdf.format(date).replace("-", "/") + "/";
                    // 防止上传文件重名覆盖
                    filename = UUID.randomUUID().toString().replace("-", "") + filename;
                    File file = new File(this.getServletContext().getRealPath(path + filename));
                    // 创建上层目录
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                    }
                    item.write(file);
                    request.setAttribute("filename",path+filename);
                    request.getRequestDispatcher("/manage/uploadImg.jsp").forward(request,response);
                }
            }
            return filename;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
