<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>上传图片</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/manage/css/custom.css'/>">

    <link rel="stylesheet" href="<c:url value='/manage/plugins/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
    <script type="text/javascript" src="<c:url value='/manage/plugins/layui/layui.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/manage/js/jquery.js'/> "></script>

    <script type="text/javascript">
        $(function(){
            $("#sure").click(function(){
                var path = $("#path").val();
                $("#filepath",window.opener.document).val(path);
                $("#productImg",window.opener.document).attr("src", '/' +path+"?t="+Math.random());
                window.close();
            });
        });
    </script>

    <style>
        body {
            text-align: center
        }
        .div {
            margin: 0 auto;
            width: 385px;
            height: 100px;
            position: relative;
            left: 0px;
            top: 50px;
            border: 0px solid #F00
        }
        /* css注释：为了观察效果设置宽度 边框 高度等样式 */
    </style>
</head>
<body>
<form action="<c:url value='/imageUploadServlet'/> " method="post" enctype="multipart/form-data">
    <div class="htmleaf-container">
        <header class="htmleaf-header" style="margin:0 auto">
            <h1>选择图片 <span>Please choose a image</span></h1>
            <div class="htmleaf-links"></div>
        </header>
        <div class="conwtainer">
            <div class="col-md-12">
                <div class="div">
                    <input name="imgpath" type="file" class="layui-btn layui-btn-primary layui-btn-small"
                           style="width:200px;"/>
                    <input id="path" type="hidden" value="${filename}"/>
                    <input type="submit" class="layui-btn layui-btn-small layui-btn-normal" value="上传"/>
                    <input type="submit" class="layui-btn layui-btn-small" value="确定" id="sure"/>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
