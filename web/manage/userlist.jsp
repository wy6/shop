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
        <a href="<c:url value='/manageUserServlet?action=findAllUser&status=1'/>" class="layui-btn layui-btn-small">
            <i class="layui-icon">&#xe6af;</i> 正常用户</a>
        <a href="<c:url value='/manageUserServlet?action=findAllUser&status=0'/>" class="layui-btn layui-btn-small">
            <i class="layui-icon">&#xe6af;</i> 未激活用户</a>
        <a href="<c:url value='/manageUserServlet?action=findAllUser&status=2'/>" class="layui-btn layui-btn-small">
            <i class="layui-icon">&#xe69c;</i> 黑名单用户</a>
        <a href="javascript:form.submit();" class="layui-btn layui-btn-small">
            <i class="layui-icon">&#xe640;</i> 拉黑用户</a>
        <%-- --%>
    </blockquote>
    <fieldset class="layui-elem-field">
        <legend>

        </legend>
        <div class="layui-field-box">
            <table class="site-table table-hover" id="newTable">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selected-all"></th>
                    <th>会员ID</th>
                    <th>用户名</th>
                    <th>性别</th>
                    <th>邮箱</th>
                    <th>积分</th>
                    <th>最后登录时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <form action="<c:url value='/manageUserServlet'/>" method="post" name="form">
                    <input type="hidden" name="action" value="deleteMore"/>
                    <c:forEach var="item" items="${page.list}">
                    <tr>
                        <td><input name="sel" type="checkbox" value="${item.productid}"></td>
                        <td>${item.vipid}</td>
                        <td><a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                               style="color: #0C0C0C">${item.username}</a></td>
                        <td>
                            <c:choose>
                                <c:when test="${item.sex==null}">
                                    未知
                                </c:when>
                                <c:otherwise>
                                    ${item.sex}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${item.email}</td>
                        <td>${item.score}</td>
                        <td>${item.lastlogintime}</td>
                        <td>
                            <c:choose>
                                <c:when test="${item.status==0}">
                                    未激活
                                </c:when>
                                <c:when test="${item.status==1}">
                                    正常
                                </c:when>
                                <c:when test="${item.status==2}">
                                    黑名单用户
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.status==0}">
                                    <a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                                       class="layui-btn layui-btn-mini">编辑</a>
                                    <a href="javascript:if(confirm('确定要拉黑该用户吗？'))window.location.href='<c:url value="/manageUserServlet?action=deleteById&id=${item.productid }"/>'"
                                       data-id="1" data-opt="del"
                                       class="layui-btn layui-btn-danger layui-btn-mini">拉黑</a>
                                </c:when>
                                <c:when test="${item.status==1}">
                                    <a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                                       class="layui-btn layui-btn-mini">查询订单</a>
                                    <a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                                       class="layui-btn layui-btn-mini">编辑</a>
                                    <a href="javascript:if(confirm('确定要拉黑该用户吗？'))window.location.href='<c:url value="/manageUserServlet?action=deleteById&id=${item.productid }"/>'"
                                       data-id="1" data-opt="del"
                                       class="layui-btn layui-btn-danger layui-btn-mini">拉黑</a>
                                </c:when>
                                <c:when test="${item.status==2}">
                                    <a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                                       class="layui-btn layui-btn-mini">查询订单</a>
                                    <a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                                       class="layui-btn layui-btn-mini">恢复</a>
                                    <a href="<c:url value='/manageUserServlet?action=idForUpdate&id=${item.productid}'/>"
                                       class="layui-btn layui-btn-mini">编辑</a>
                                    <a href="javascript:if(confirm('确定要删除该用户吗？'))window.location.href='<c:url value="/manageUserServlet?action=deleteById&id=${item.productid }"/>'"
                                       data-id="1" data-opt="del"
                                       class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
                                </c:when>
                            </c:choose>
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
                            <a href="<c:url value='/manageUserServlet?action=findAllUser&status=${status}&current=${page.currentPage - 1}&skey=${skey}&keywords=${keywords}'/>">
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
                                <a href="<c:url value='/manageUserServlet?action=findAllUser&status=${status}&current=${i}&skey=${skey}&keywords=${keywords}'/>">${i}</a>
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
                            <a href="<c:url value='/manageUserServlet?action=findAllUser&status=${status}&current=${page.currentPage + 1}&skey=${skey}&keywords=${keywords}'/>">
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
