<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
    <title>订单管理</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/admin.css'/>"
          rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>"
          rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/css/personal.css'/>" rel="stylesheet"
          type="text/css">
    <link href="<c:url value='/css/orstyle.css'/> " rel="stylesheet"
          type="text/css">
    <link rel="stylesheet" href="<c:url value='/manage/css/custom.css'/>" type="text/css"/>
    <script src="<c:url value='/AmazeUI-2.4.2/assets/js/jquery.min.js'/> "></script>
    <script src="<c:url value='/AmazeUI-2.4.2/assets/js/amazeui.js'/> "></script>
</head>
<body>
<div class="center" style="background-color: #ffffff">
    <!--标题 -->
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-danger am-text-lg">订单管理</strong> /
            <small>Order</small>
        </div>
    </div>
    <hr/>
    <div class="am-tabs am-tabs-d2 am-margin" data-am-tabs>
        <ul class="am-avg-sm-5 am-tabs-nav am-nav am-nav-tabs">
            <li class="am-active"><a href="#tab1">所有订单</a></li>
            <li><a href="#tab2">待付款</a></li>
            <li><a href="#tab3">待发货</a></li>
            <li><a href="#tab4">待收货</a></li>
            <li><a href="#tab5">待评价</a></li>
        </ul>

        <div class="am-tabs-bd">
            <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                <c:forEach items="${page.list }" var="order">
                <div class="order-title">
                    <span>订单编号：<a href="javascript:;">${order.orderid}</a>
                    </span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <span>订单提交时间：
                        <fmt:formatDate type="time" value="${order.ordertime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                    </span>
                </div>
                <div class="order-top">
                    <div class="th th-item">
                        <td class="td-inner">商品</td>
                    </div>
                    <div class="th th-price">
                        <td class="td-inner">单价</td>
                    </div>
                    <div class="th th-number">
                        <td class="td-inner">数量</td>
                    </div>
                    <div class="th th-operation">
                        <td class="td-inner">商品操作</td>
                    </div>
                    <div class="th th-amount">
                        <td class="td-inner">合计</td>
                    </div>
                    <div class="th th-status">
                        <td class="td-inner">交易状态</td>
                    </div>
                    <div class="th th-change">
                        <td class="td-inner">交易操作</td>
                    </div>
                </div>

                <div class="order-main">
                    <div class="order-list">

                        <!--交易成功-->
                        <!-- 一个订单 -->

                        <div class="order-status5">

                            <div class="order-content">
                                <div class="order-left">
                                    <c:forEach var="item" items="${order.orderItemList}">
                                        <ul class="item-list">
                                            <li class="td td-item">
                                                <div class="item-pic">
                                                    <a href="<c:url value='/productServlet?action=findbyid&id=${item.product.productid}'/>"
                                                       class="J_MakePoint">
                                                        <img src="<c:url value='${item.product.photo}'/>"
                                                             class="itempic J_ItemImg">
                                                    </a>
                                                </div>
                                                <div class="item-info">
                                                    <div class="item-basic-info">
                                                        <a href="<c:url value='/productServlet?action=findbyid&id=${item.product.productid}'/>">
                                                            <p>${item.product.name}</p>
                                                        </a>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="td td-price">
                                                <div class="item-price">
                                                        ${item.total}
                                                </div>
                                            </li>
                                            <li class="td td-number">
                                                <div class="item-number">
                                                    <span>×</span>${item.buycount}
                                                </div>
                                            </li>
                                            <li class="td td-operation">
                                                <div class="item-operation"></div>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </div>
                                <div class="order-right">
                                    <li class="td td-amount">
                                        <div class="item-amount">
                                                ${order.totalprice}
                                        </div>
                                    </li>
                                    <div class="move-right">
                                        <li class="td td-status">
                                            <div class="item-status">
                                                <c:choose>
                                                    <c:when test="${order.status==0}">
                                                        <p class="Mystatus">等待买家付款</p>
                                                    </c:when>
                                                    <c:when test="${order.status==1}">
                                                        <p class="Mystatus">等待卖家发货</p>
                                                    </c:when>
                                                    <c:when test="${order.status==2}">
                                                        <p class="Mystatus">等待买家收货</p>
                                                    </c:when>
                                                    <c:when test="${order.status==3}">
                                                        <p class="Mystatus">未评论</p>
                                                    </c:when>
                                                </c:choose>
                                                <p class="order-info">
                                                    <a href="#'/>">订单详情</a>
                                                </p>
                                                <p class="order-info">
                                                    <a href="logistics.html">查看物流</a>
                                                </p>
                                            </div>
                                        </li>
                                        <li class="td td-change">
                                            <c:choose>
                                                <c:when test="${order.status==0}">
                                                    <div class="am-btn am-btn-danger anniu">查看订单</div>
                                                </c:when>
                                                <c:when test="${order.status==1}">
                                                    <div class="am-btn am-btn-danger anniu">发货</div>
                                                </c:when>
                                                <c:when test="${order.status==2}">
                                                    <div class="am-btn am-btn-danger anniu">查看订单</div>
                                                </c:when>
                                                <c:when test="${order.status==3}">
                                                    <div class="am-btn am-btn-danger anniu">删除订单</div>
                                                </c:when>
                                            </c:choose>
                                        </li>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                        <!-- 一个订单end -->
                    </div>
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
                                        <a href="<c:url value='/manageOrderServlet?action=findAllOrder&current=${page.currentPage - 1}&skey=${skey}&svalue=${svalue}'/>">
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
                                            <a href="<c:url value='/manageOrderServlet?action=findAllOrder&current=${i}&skey=${skey}&svalue=${svalue}'/>">${i}</a>
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
                                        <a href="<c:url value='/manageOrderServlet?action=findAllOrder&current=${page.currentPage + 1}&skey=${skey}&svalue=${svalue}'/>">
                                            <img src="<c:url value='/manage/images/next.png'/>">
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>