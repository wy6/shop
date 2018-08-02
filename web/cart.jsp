<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>购物车页面</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>" rel="stylesheet" type="text/css"/>
    <link type="text/css" href="<c:url value='/css/optstyle.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet" type="text/css"/>
    <link type="text/css" href="<c:url value='/css/cartstyle.css'/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>

    <script>
        $(function () {
            $(".increase").click(function () {
                var qinput = $(this).siblings(":text");
                qinput.val(eval(qinput.val()) + 1);
                var buyCount = qinput.val();
                var id = qinput.attr("productid");
                $.post(
                    "${pageContext.request.contextPath}/productServlet",
                    {
                        "id": id,
                        "buyCount": qinput.val(),
                        "action": "updateBuyCount"
                    },
                    function (data) {
                        $("#total" + id).text(data.total);
                        $("#totalPrice").text(data.totalPrice);
                    },
                    "json"
                );
            });

            $(".decrease").click(function () {
                var qinput = $(this).siblings(":text");
                qinput.val(eval(qinput.val()) - 1);
                var buyCount = qinput.val();
                var id = qinput.attr("productid");
                $.post(
                    "${pageContext.request.contextPath}/productServlet",
                    {
                        "id": id,
                        "buyCount": qinput.val(),
                        "action": "updateBuyCount"
                    },
                    function (data) {
                        $("#total" + id).text(data.total);
                        $("#totalPrice").text(data.totalPrice);
                    },
                    "json"
                );
            });

            $("#all").click(function () {
                if (this.checked)
                    $(":checkbox").attr("checked", true);
                else {
                    $(":checkbox").attr("checked", false);
                }
            })
        });
    </script>
</head>

<body>

<!--顶部导航条 -->
<%@include file="/header.jsp" %>

<!--购物车 -->
<div class="concent">
    <div id="cartTable">
        <div class="cart-table-th">
            <div class="wp">
                <div class="th th-chk">
                    <div id="J_SelectAll1" class="select-all J_SelectAll"></div>
                </div>
                <div class="th th-item">
                    <div class="td-inner">商品信息</div>
                </div>
                <div class="th th-price">
                    <div class="td-inner">单价</div>
                </div>
                <div class="th th-amount">
                    <div class="td-inner">数量</div>
                </div>
                <div class="th th-sum">
                    <div class="td-inner">金额</div>
                </div>
                <div class="th th-op">
                    <div class="td-inner">操作</div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <form action="<c:url value='/productServlet'/>" method="post" id="form_deleteMore"/>
        <input type="hidden" name="action" value="deleteCartMore"/>
        <c:forEach var="item" items="${cart}">
            <tr class="item-list">
                <div class="bundle  bundle-last ">

                    <div class="bundle-main">
                        <ul class="item-content clearfix">
                            <li class="td td-chk">
                                <div class="cart-checkbox ">
                                    <input class="check" id="J_CheckBox_170037950254" name="sel"
                                           value="${item.productid}"
                                           type="checkbox">
                                    <label for="J_CheckBox_170037950254"></label>
                                </div>
                            </li>
                            <li class="td td-item">
                                <div class="item-pic">
                                    <a href="#" target="_blank" data-title=""
                                       class="J_MakePoint" data-point="tbcart.8.12">
                                        <img src="<c:url value='/${item.photo}'/> " class="itempic J_ItemImg"
                                             style="width: 80px; height: 80px"></a>
                                </div>
                                <div class="item-info">
                                    <div class="item-basic-info">
                                        <a href="#" target="_blank" title=""
                                           class="item-title J_MakePoint" data-point="tbcart.8.11">${item.name}</a>
                                    </div>
                                </div>
                            </li>

                            <li class="td td-price">
                                <div class="item-price price-promo-promo">
                                    <div class="price-content">
                                        <div class="price-line">
                                            <em class="price-original">${item.markprice}</em>
                                        </div>
                                        <div class="price-line">
                                            <em class="J_Price price-now" tabindex="0">${item.price}</em>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="td td-amount">
                                <div class="amount-wrapper ">
                                    <div class="item-amount ">
                                        <div class="sl">
                                            <input class="decrease am-btn" name="" type="button" value="-"/>
                                            <input class="text_box" name="buyCount" productid="${item.productid}"
                                                   type="text" value="${item.buyCount}"
                                                   style="width:30px;" readonly/>
                                            <input class="increase am-btn" type="button" value="+"/>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="td td-sum">
                                <div class="td-inner">
                                    <span id="total${item.productid}" tabindex="0"
                                          class="J_ItemSum number">${item.total}</span>
                                </div>
                            </li>
                            <li class="td td-op">
                                <div class="td-inner">
                                    <a href="javascript:if(confirm('确定要删除吗？'))window.location.href='<c:url value="/productServlet?action=deleteCartById&id=${item.productid}"/>';"
                                       data-point-url="#" class="delete">
                                        删除</a>
                                </div>
                            </li>
                        </ul>


                    </div>
                </div>
            </tr>
        </c:forEach>

        <div class="clear"></div>

        <div class="float-bar-wrapper">
            <div id="J_SelectAll2" class="select-all J_SelectAll">
                <div class="cart-checkbox">
                    <input class="check-all check" id="all" name="select-all" value="true" type="checkbox">
                    <label for="all"></label>
                </div>
                <span>全选</span>
            </div>
            <div class="operations">
                <a href="javascript:if(confirm('确定要删除吗？'))form_deleteMore.submit();" hidefocus="true" class="deleteAll">删除</a>

            </div>
            <div class="float-bar-right">
                <div class="amount-sum">
                    <span class="txt">已选商品</span>
                    <em id="J_SelectedItemsCount">0</em><span class="txt">件</span>
                    <div class="arrow-box">
                        <span class="selected-items-arrow"></span>
                        <span class="arrow"></span>
                    </div>
                </div>
                <div class="price-sum">
                    <span class="txt">合计:</span>
                    <strong class="price">¥<span id="totalPrice">${totalPrice}</span></strong>
                </div>
                <div class="btn-area">
                    <a href="<c:url value='/addressServlet?action=payBefore'/>" id="J_Go"
                       class="submit-btn submit-btn-disabled" aria-label="请注意如果没有选择宝贝，将无法结算">
                        <span>结&nbsp;算</span></a>
                </div>
            </div>
        </div>
        <%@include file="/footer.jsp" %>
    </div>
</div>
<!--引导 -->
<div class="navCir">
    <li><a href="home2.html"><i class="am-icon-home "></i>首页</a></li>
    <li><a href="sort.html"><i class="am-icon-list"></i>分类</a></li>
    <li class="active"><a href="shopcart.html"><i class="am-icon-shopping-basket"></i>购物车</a></li>
    <li><a href="person/index.html"><i class="am-icon-user"></i>我的</a></li>
</div>
</body>

</html>