<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0 ,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>结算页面</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>"
          rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet"
          type="text/css"/>
    <link type="text/css" href="<c:url value='/css/cartstyle.css'/>"
          rel="stylesheet"/>
    <link href="<c:url value='/css/jsstyle.css'/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/js/address.js'/>"></script>
</head>
<body>

<!--顶部导航条 -->
<%@include file="/header.jsp"%>
<div class="concent">
    <!--地址 -->
    <div class="paycont">
        <!--物流 -->
        <div class="logistics">
            <h3>选择物流方式</h3>
            <ul class="op_express_delivery_hot">
                <li data-value="yuantong" class="OP_LOG_BTN  "><i
                        class="c-gap-right" style="background-position:0px -468px"></i>圆通<span></span>
                </li>
                <li data-value="shentong" class="OP_LOG_BTN  "><i
                        class="c-gap-right" style="background-position:0px -1008px"></i>申通<span></span>
                </li>
                <li data-value="yunda" class="OP_LOG_BTN  "><i
                        class="c-gap-right" style="background-position:0px -576px"></i>韵达<span></span>
                </li>
                <li data-value="zhongtong"
                    class="OP_LOG_BTN op_express_delivery_hot_last "><i
                        class="c-gap-right" style="background-position:0px -324px"></i>中通<span></span>
                </li>
                <li data-value="shunfeng"
                    class="OP_LOG_BTN  op_express_delivery_hot_bottom"><i
                        class="c-gap-right" style="background-position:0px -180px"></i>顺丰<span></span>
                </li>
            </ul>
        </div>
        <div class="clear"></div>

        <!--支付方式-->
        <div class="logistics">
            <h3>选择支付方式</h3>
            <ul class="pay-list">
                <li class="pay card"><img src="<c:url value='/images/wangyin.jpg'/>"/>银联<span></span>
                </li>
                <li class="pay qq"><img src="<c:url value='/images/weizhifu.jpg'/>"/>微信<span></span>
                </li>
                <li class="pay taobao"><img src="<c:url value='/images/zhifubao.jpg'/>"/>支付宝<span></span>
                </li>
            </ul>
        </div>
    </div>
    <div id="holyshit269" class="submitOrder">
        <div class="go-btn-wrap">
            <a id="J_Go" href="<c:url value='/home/paysuccess.jsp'/>" class="btn-go" tabindex="0"
            >确认付款</a>
        </div>
    </div>
    <div class="clear"></div>
</div>
</div>
<%@include file="/footer.jsp" %>
<div class="clear"></div>
</body>

</html>