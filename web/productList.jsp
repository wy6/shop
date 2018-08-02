<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <title>搜索页面</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/admin.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/css/seastyle.css'/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/basic/js/jquery-1.7.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/script.js'/>"></script>
</head>

<body>

<!--顶部导航条 -->
<%@include file="/header.jsp"%>

<!--悬浮搜索框-->
<div class="clear"></div>
<b class="line"></b>
<div class="search">
    <div class="search-list">
        <%@include file="/banner.jsp"%>

        <div class="am-g am-g-fixed">
            <div class="am-u-sm-12 am-u-md-12">
                <!--<div class="search-content">-->
                <div class="sort">
                    <li class="first"><a title="价格升序"
                                         href="<c:url value='/productServlet?action=findAll&sortkey=price&sort=asc'/>">价格升序</a>
                    </li>
                    <li><a title="价格降序" href="<c:url value='/productServlet?action=findAll&sortkey=price&sort=desc'/>">价格降序</a>
                    </li>
                    <li><a title="时间升序" href="<c:url value='/productServlet?action=findAll&sortkey=time&sort=asc'/>">上架时间升序</a>
                    </li>
                    <li class="big"><a title="时间降序"
                                       href="<c:url value='/productServlet?action=findAll&sortkey=time&sort=desc'/>">上架时间降序</a>
                    </li>
                </div>
                <div class="clear"></div>

                <ul class="am-avg-sm-2 am-avg-md-3 am-avg-lg-4 boxes">
                    <c:forEach var="item" items="${page.list}">
                        <li><a href="<c:url value='/productServlet?action=findById&id=${item.productid}'/>">
                            <div class="i-pic limit">
                                <img src="<c:url value='/${item.photo}'/>" style="height: 278px; width: 278px;"/>
                                <p class="title fl">${item.name}</p>
                                <p class="price fl">
                                    <b>¥</b>
                                    <strong>${item.price}</strong>
                                </p>
                                <p class="number fl">
                                    原价 <span>${item.markprice}</span>
                                </p>
                            </div>
                        </a>
                        </li>
                    </c:forEach>

                </ul>
                <!--</div>-->

                <div class="clear"></div>
                <!--分页 -->
                <ul class="am-pagination am-pagination-right">
                    <c:choose>
                        <c:when test="${page.currentPage == 1}">
                            <li class="am-disabled"><a href="#">&laquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="<c:url value='/productServlet?action=findAll&current=${page.currentPage - 1}&sortkey=${sortkey}&sort=${sort}&keywords=${keywords}'/>">&laquo;</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="${page.start}" end="${page.end}" var="i">
                        <c:choose>
                            <c:when test="${page.currentPage == i}">
                                <li class="am-active"><a href="#">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="<c:url value='/productServlet?action=findAll&current=${i}&sortkey=${sortkey}&sort=${sort}&keywords=${keywords}'/> ">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${page.currentPage == page.totalPage}">
                            <li class="am-disabled"><a href="#">&raquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="<c:url value='/productServlet?action=findAll&current=${page.currentPage + 1}&sortkey=${sortkey}&sort=${sort}&keywords=${keywords}'/>">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
        <%@include file="/footer.jsp"%>
    </div>
</div>

<script>
    window.jQuery || document.write('<script src="basic/js/jquery-1.9.min.js"><\/script>');
</script>
<script type="text/javascript" src="basic/js/quick_links.js"></script>
<div class="theme-popover-mask"></div>
</body>

</html>