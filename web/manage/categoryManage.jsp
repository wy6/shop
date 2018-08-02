<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="<c:url value='/manage/plugins/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<c:url value='/manage/css/global.css'/>" media="all">
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
    <link rel="stylesheet" href="<c:url value='/manage/css/table.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/manage/css/custom.css'/>" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/manage/js/jquery.js'/>"></script>

    <script type="text/javascript">
        $(function () {
            $("#selected-all").click(function () {
                if (this.checked) {
                    $(":checkbox").attr("checked", true);
                } else {
                    $(":checkbox").attr("checked", false);
                }
            });
        });
    </script>
</head>

<body>
<div class="admin-main">
    <form action="<c:url value='/manageCategoryServlet'/>" method="post" name="form">
        <input type="hidden" name="action" value="deleteMore"/>
        <blockquote class="layui-elem-quote">

            <a href="<c:url value='/manage/addCategory.jsp'/>" class="layui-btn layui-btn-small" id="add">
                <i class="layui-icon">&#xe608;</i> 添加分类
            </a>
            <a href="javascript:form.submit();" class="layui-btn layui-btn-small">
                <i class="layui-icon">&#xe640;</i> 删除分类
            </a>
        </blockquote>
        <fieldset class="layui-elem-field">
            <legend>商品分类列表</legend>
            <div class="layui-field-box">
                <table class="site-table table-hover" id="newTable">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="selected-all"></th>
                        <th>分类编号</th>
                        <th>分类名称</th>
                        <th>分类排序</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${page.list}">
                        <tr>
                            <td><input name="sel" type="checkbox" value="${item.categoryid}"></td>
                            <td>${item.categoryid}</td>
                            <td>${item.name}</td>
                            <td>${item.sort}</td>
                            <td>
                                <a href="<c:url value='/manageCategoryServlet?action=idForUpdate&id=${item.categoryid}'/>"
                                   class="layui-btn layui-btn-mini">编辑</a>
                                <a href="javascript:if(confirm('确定要删除吗？'))window.location.href='<c:url value="/CategoryServlet?action=deleteById&id=${item.productid }"/>'"
                                   data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="pagin">
                <div class="message" style="margin-left: 5px">
                    共<i class="blue">${page.totalSize}</i>条记录，当前显示第&nbsp;
                    <i class="blue">${page.currentPage}&nbsp;</i>页/共${page.totalPage}页
                </div>
                <ul class="paginList">
                    <c:choose>
                        <c:when test="${page.currentPage == 1}">
                            <li class="paginItem">
                                <a href="javascript:;">
                                    <img src="<c:url value='/manage/images/pre.png'/>">
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem">
                                <a href="<c:url value='/manageCategoryServlet?action=findAll&current=${page.currentPage - 1}'/>">
                                    <img src="<c:url value='/manage/images/pre.png'/>"></span>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach var="i" begin="${page.start}" end="${page.end}">
                        <c:choose>
                            <c:when test="${page.currentPage == i}">
                                <li class="paginItem current"><a href="javascript:;">${i}</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="paginItem">
                                    <a href="<c:url value='/manageCategoryServlet?action=findAll&current=${i}'/>">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${page.currentPage == page.totalPage}">
                            <li class="paginItem">
                                <a href="javascript:;">
                                    <img src="<c:url value='/manage/images/next.png'/>">
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem">
                                <a href="<c:url value='/manageCategoryServlet?action=findAll&current=${page.currentPage + 1}'/>">
                                    <img src="<c:url value='/manage/images/next.png'/>">
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </fieldset>
    </form>
    <div class="admin-table-page">
        <div id="page" class="page">
        </div>
    </div>
</div>
</body>

</html>
