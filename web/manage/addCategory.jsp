<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>添加商品分类</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<c:url value = '/manage/plugins/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
    <script type="text/javascript" src="<c:url value='/manage/js/jquery.js'/>"></script>

    <script type="text/javascript">

        function validateName() {
            var categoryName = $('input[name="categoryName"]');
            var result_categoryName = $("#result_categoryName");
            result_categoryName.html("");
            if (categoryName.val() == null||($.trim(categoryName.val())) == ""){
                result_categoryName.html("分类名称不能为空！");
                categoryName.focus();
                return false;
            }
            return true;
        }
        function validateSort() {
            var sort = $('input[name = "sort"]');
            var result_sort = $("#result_sort");
            result_sort.html("");
            if (sort.val() == null||($.trim(sort.val())) == ""){
                result_sort.html("分类排序号不能为空！");
                sort.focus();
                return false;
            }
            var regix = new RegExp("^\\+?[1-9][0-9]*$");
            if(!regix.test($.trim(sort.val()))){
                result_sort.html("分类排序号必须为正整数！");
                sort.focus();
                return false;
            }
            return true;
        }
        function validate() {
            if (validateName() && validateSort()){
                $("#form_addCategory").submit();
            }
            return false;
        }
    </script>
</head>

<body>
<div style="margin: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>添加商品分类</legend>
    </fieldset>
    <form id="form_addCategory" class="layui-form" action="<c:url value='/manageCategoryServlet'/>" method="post">
        <input type="hidden" value="addCategory" name="action"/>
        <div class="layui-form-item">

            <label class="layui-form-label">分类名称<b style="color:red;font-size: 20px">*</b></label>
            <div class="layui-input-block">
                <input type="text" name="categoryName" id="categoryName" autocomplete="off"
                       placeholder="请输入分类名称" class="layui-input" style='width: 300px' onblur="validateName()"/>
                <span style="color: red; width:278px;" id="result_categoryName"></span>
            </div>

        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">分类排序<b style="color:red;font-size:20px">*</b></label>
            <div class="layui-input-block">
                <input type="text" name="sort" id="sort" placeholder="请输入商品排序号" autocomplete="off"
                       class="layui-input" style='width: 300px' onblur="validateSort()"/>
                <span style="color: red; width:278px;" id="result_sort"></span>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" onclick="return validate()">添加</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
</body>

</html>