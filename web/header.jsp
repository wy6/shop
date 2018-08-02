<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2018/1/8
  Time: 23:19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--顶部导航条 -->
<div class="am-container header">
    <ul class="message-l">
        <div class="topMessage">
            <div class="menu-hd">
                <c:choose>
                    <c:when test="${user==null}">
                        <a href="<c:url value='/login.jsp'/>" target="_top" class="h">亲，请登录</a>
                        <a href="<c:url value='/regist.jsp'/>" target="_top">免费注册</a>
                    </c:when>
                    <c:otherwise>
                        <span>Hi, <span style="color: #FF8C00">${user.username}</span>&nbsp;&nbsp;<a href="<c:url value='/userServlet?action=loginout'/>">退出</a></span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </ul>
    <ul class="message-r">
        <div class="topMessage home">
            <div class="menu-hd"><a href="<c:url value='/productServlet?action=findIndex'/>" target="_top" class="h">商城首页</a>
            </div>
        </div>
        <div class="topMessage my-shangcheng">
            <div class="menu-hd MyShangcheng"><a href="<c:url value='/person/index.html'/>" target="_top">
                <i class="am-icon-user am-icon-fw"></i>个人中心</a>
            </div>
        </div>
        <div class="topMessage mini-cart">
            <div class="menu-hd">
                <a id="mc-menu-hd" href="<c:url value='/cart.jsp'/>" target="_top">
                    <i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span>
                    <strong id="J_MiniCartNum" class="h">0</strong>
                </a>
            </div>
        </div>
        <div class="topMessage favorite">
            <div class="menu-hd"><a href="#" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span></a>
            </div>
        </div>
    </ul>
</div>

<!--悬浮搜索框-->

<div class="nav white">
    <div class="logo"><img src="<c:url value='/images/logo.png'/>"/></div>
    <div class="logoBig">
        <li><img src="<c:url value='/images/logobig.png'/>"/></li>
    </div>

    <div class="search-bar pr">
        <a name="index_none_header_sysc" href="#"></a>
        <form action="<c:url value='/productServlet'/>" method="post">
            <input type="hidden" name="action" value="findAll"/>
            <input id="searchInput" name="keywords" type="text" placeholder="搜索" autocomplete="off">
            <input id="ai-topsearch" class="submit am-btn" value="搜索" index="1" type="submit">
        </form>
    </div>
</div>
<!--悬浮搜索框结束-->
<div class="clear"></div>
</div>
<!--顶部导航条结束 -->
