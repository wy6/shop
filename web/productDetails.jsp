<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>商品页面</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/admin.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet" type="text/css"/>
    <link type="text/css" href="<c:url value='/css/optstyle.css‘'/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value='/css/style.css'/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value='/basic/js/jquery-1.7.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/basic/js/quick_links.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/script.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/AmazeUI-2.4.2/assets/js/amazeui.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.imagezoom.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.flexslider.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/list.js'/>"></script>
</head>
<body>
<!--顶部导航条 -->
<%@include file="/header.jsp"%>
<b class="line"></b>
<div class="listMain">
    <!--分类-->
    <%@include file="/banner.jsp"%>
    <ol class="am-breadcrumb am-breadcrumb-slash">
        <li><a href="#">首页</a></li>
        <li><a href="#">分类</a></li>
        <li class="am-active">内容</li>
    </ol>
    <script type="text/javascript">
        $(function () {
        });
        $(window).load(function () {
            $('.flexslider').flexslider({
                animation: "slide",
                start: function (slider) {
                    $('body').removeClass('loading');
                }
            });
        });
    </script>

    <!--放大镜-->
    <div class="item-inform">
        <div class="clearfixLeft" id="clearcontent">

            <div class="box">
                <script type="text/javascript">
                    $(document).ready(function () {
                        $(".jqzoom").imagezoom();
                        $("#thumblist li a").click(function () {
                            $(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
                            $(".jqzoom").attr('src', $(this).find("img").attr("mid"));
                            $(".jqzoom").attr('rel', $(this).find("img").attr("big"));
                        });
                    });
                </script>

                <div class="tb-booth tb-pic tb-s310">
                    <a href="<c:url value='/${item.photo}'/>"><img style="width: 100%; height: 100%;" src="<c:url value='/${item.photo}'/>" alt="细节展示放大镜特效" rel="<c:url value='/${item.photo}'/>" class="jqzoom"/></a>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clearfixRight">
            <div class="tb-detail-hd">
                <h1>
                    ${item.name}
                </h1>
                ${item.introduction}
            </div>
            <div class="tb-detail-list">
                <!--价格-->
                <div class="tb-detail-price">
                    <li class="price iteminfo_price">
                        <dt>促销价</dt>
                        <dd><em>¥</em><b class="sys_item_price">${item.price}</b></dd>
                    </li>
                    <li class="price iteminfo_mktprice">
                        <dt>原价</dt>
                        <dd><em>¥</em><b class="sys_item_mktprice">${item.markprice}</b></dd>
                    </li>
                    <div class="clear"></div>
                </div>

                <div class="clear"></div>
                <!--销量-->
                <ul class="tm-ind-panel">
                    <li class="tm-ind-item tm-ind-sellCount canClick">
                        <div class="tm-indcon"><span class="tm-label">库存量</span><span class="tm-count">${item.quality}</span></div>
                    </li>
                    <li class="tm-ind-item tm-ind-sellCount canClick"></li>
                    <li class="tm-ind-item tm-ind-sellCount canClick"></li>
                </ul>
                <ul class="tm-ind-panel">
                    <li class="tm-ind-item tm-ind-sellCount canClick">
                        <div class="tm-indcon"><span class="tm-label">浏览量</span><span class="tm-count">${item.hit}</span></div>
                    </li>
                    <li class="tm-ind-item tm-ind-sellCount canClick"></li>
                    <li class="tm-ind-item tm-ind-sellCount canClick"></li>
                </ul>
                <div class="clear"></div>

                <!--各种规格-->
                <dl class="iteminfo_parameter sys_item_specpara">
                    <dd>
                        <!--操作页面-->
                        <div class="theme-popover-mask"></div>
                        <div class="theme-popover">
                            <div class="theme-popbod dform">
                                <form class="theme-signin" id="form_cart" action="<c:url value='/productServlet'/>" method="post">
                                    <input type="hidden" name="action" value="addCart"/>
                                    <input type="hidden" name="productid" value="${item.productid}"/>
                                    <div class="theme-signin-left">
                                        <div class="theme-options">
                                            <div class="cart-title number">数量</div>
                    <dd>

                        <input id="min" class="am-btn am-btn-default" name="" type="button" value="-"/>
                        <input id="text_box" name="buyCount" type="text" value="1" style="width:30px;"/>
                        <input id="add" class="am-btn am-btn-default" name="" type="button" value="+"/>
                    </dd>
                    <div class="clear"></div>
                    <div class="btn-op">
                        <div class="btn am-btn am-btn-warning">确认</div>
                        <div class="btn close am-btn am-btn-warning">取消</div>
                    </div>
                    <div class="theme-signin-right">
                        <div class="img-info">
                            <img src="images/songzi.jpg"/>
                        </div>
                        <div class="text-info">
                            <span class="J_Price price-now">¥39.00</span>
                            <span id="Stock" class="tb-hidden">库存<span class="stock">1000</span>件</span>
                        </div>
                    </div>
                    </form>
                    </dd>
                </dl>
                <div class="clear"></div>
                <!--活动	-->
                <div class="shopPromotion gold">
                    <div class="hot">
                        <dt class="tb-metatit">店铺优惠</dt>
                        <div class="gold-list">
                            <p>双十一买一送二啦！</p>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>

            <div class="pay">
                <div class="pay-opt">
                    <a href="home.html"><span class="am-icon-home am-icon-fw">首页</span></a>
                    <a><span class="am-icon-heart am-icon-fw">收藏</span></a>
                </div>
                <li>
                    <div class="clearfix tb-btn tb-btn-buy theme-login">
                        <a id="LikBuy" title="点此按钮到下一步确认购买信息" href="#">立即购买</a>
                    </div>
                </li>
                <li>
                    <div class="clearfix tb-btn tb-btn-basket theme-login">
                        <a id="LikBasket" title="加入购物车" href="javascript:form_cart.submit();">加入购物车</a>
                    </div>
                </li>
            </div>

        </div>

        <div class="clear"></div>

    </div>

</div>
<div class="introduce">

    <div class="clear"></div>
    <div class="introduceMain">
        <div class="am-tabs" data-am-tabs>

            <ul class="am-avg-sm-3 am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active">
                    <a href="#"><span class="index-needs-dt-txt">宝贝详情</span></a>
                </li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active">
                    ${item.content}
                </div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<%@include file="/footer.jsp"%>
</div>
</div>
</body>
</html>