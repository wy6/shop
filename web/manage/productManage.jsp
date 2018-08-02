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

    <blockquote class="layui-elem-quote">
        <a href="<c:url value='/manageProductServlet?action=categoryInfo'/>" class="layui-btn layui-btn-small" id="add">
            <i class="layui-icon">&#xe608;</i> 添加商品</a>
        <a href="javascript:form.submit();" class="layui-btn layui-btn-small">
            <i class="layui-icon">&#xe640;</i> 删除商品</a>
        <form action="<c:url value='/manageProductServlet'/>" method="post" name="form_query" style="margin: 0px;display: inline">
            <input type="hidden" name="action" value="findAll"/>
            <a style="font-size: 14px; color: black; vertical-align:middle; margin-left: 50px">搜索关键字
                <input type="text" id="keywords" name="keywords" autocomplete="off">&nbsp;&nbsp;按&nbsp;&nbsp;
                <select name="skey">
                    <option value="categoryid">分类</option>
                    <option value="name" selected="">名称</option>
                    <option value="productid">编号</option>
                </select>&nbsp;&nbsp;
                <a href="javascript:form_query.submit();" class="layui-btn layui-btn-small layui-btn-normal">
                    <i class="layui-icon">&#xe615;</i> 查询</a>
            </a>
        </form>
    </blockquote>
    <fieldset class="layui-elem-field">
        <legend>商品列表</legend>
        <div class="layui-field-box">
            <table class="site-table table-hover" id="newTable">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selected-all"></th>
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>商品价格</th>
                    <th>市场价格</th>
                    <th>库存量</th>
                    <th>浏览量</th>
                    <th>商品种类</th>
                    <th>发布时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <form action="<c:url value='/manageProductServlet'/>" method="post" name="form">
                    <input type="hidden" name="action" value="deleteMore"/>
                    <c:forEach var="item" items="${page.list}">
                    <tr>
                        <td><input name="sel" type="checkbox" value="${item.productid}"></td>
                        <td>${item.productid}</td>
                        <td><a href="<c:url value='/manageProductServlet?action=idForUpdate&id=${item.productid}'/>"
                               style="color: #0C0C0C">${item.name}</a></td>
                        <td>${item.price}</td>
                        <td>${item.markprice}</td>
                        <td>${item.quality}</td>
                        <td>${item.hit}</td>
                        <td>${item.cname}</td>
                        <td>${item.time}</td>
                        <td>
                            <a href="<c:url value='/manageProductServlet?action=idForUpdate&id=${item.productid}'/>"
                               class="layui-btn layui-btn-mini">编辑</a>
                            <a href="javascript:if(confirm('确定要删除吗？'))window.location.href='<c:url value="/manageProductServlet?action=deleteById&id=${item.productid }"/>'"
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
                            <a href="<c:url value='/manageProductServlet?action=findAll&current=${page.currentPage - 1}&skey=${skey}&keywords=${keywords}'/>">
                                <img src="<c:url value='/manage/images/pre.png'/>">
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
                                <a href="<c:url value='/manageProductServlet?action=findAll&current=${i}&skey=${skey}&keywords=${keywords}'/>">${i}</a>
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
                            <a href="<c:url value='/manageProductServlet?action=findAll&current=${page.currentPage + 1}&skey=${skey}&keywords=${keywords}'/>">
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
