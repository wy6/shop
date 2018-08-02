<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2018/1/8
  Time: 23:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--顶部功能栏-->
    <div class="nav-table">
        <div class="long-title"><span class="all-goods">全部分类</span></div>
        <div class="nav-cont">
            <ul>
                <li class="index"><a href="<c:url value='/productServlet?action=findIndex'/>">首页</a></li>
                <li class="qc"><a href="<c:url value='/cart.jsp'/>">购物车</a></li>
                <li class="qc"><a href="<c:url value='/orderServlet?action=findAll'/>">我的订单</a></li>
                <li class="qc last"><a href="<c:url value='/person/index.html'/>">个人中心</a></li>
            </ul>
        </div>
    </div>
<!--顶部功能栏结束-->
