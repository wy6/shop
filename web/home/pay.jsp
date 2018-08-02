<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0 ,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>结算页面</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/css/cartstyle.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/css/jsstyle.css'/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/js/address.js'/>"></script>
    <script>
        $(function () {
            $.get(
                "${pageContext.request.contextPath}/PCAServlet",
                {"action": "getprovinces"},
                function (data) {
                    $.each(data, function (index, content) {
                        $("#pro").append("<option value=\"" + content.provinceid + "\">" + content.province + "</option>");
                    });
                },
                "json"
            );
            $("#pro").change(
                function () {
                    $.post(
                        "${pageContext.request.contextPath}/PCAServlet",
                        {
                            "action": "getcities",
                            "provinceid": $(this).val()
                        },
                        function (data) {
                            //清空之前的列表
                            $("#ci").empty();
                            $("#ci").append("<option>--请选择--</option>");
                            var pro_text = $("#pro option:selected").text();
                            $("#province").val(pro_text);
                            $.each(data, function (index, content) {
                                $("#ci").append("<option value=\"" + content.cityid + "\">" + content.city + "</option>");
                            });
                        },
                        "json"
                    );
                }
            );
            $("#ci").change(
                function () {
                    $.post(
                        "${pageContext.request.contextPath}/PCAServlet",
                        {
                            "action": "getareas",
                            "cityid": $(this).val()
                        },
                        function (data) {
                            //清空之前的列表
                            $("#ar").empty();
                            $("#ar").append("<option>--请选择--</option>");
                            var ci_text = $("#ci option:selected").text();
                            $("#city").val(ci_text);
                            $.each(data, function (index, content) {
                                $("#ar").append("<option value=\"" + content.areaid + "\">" + content.area + "</option>");
                            });
                        },
                        "json"
                    );
                }
            );
            $("#ar").change(
                function () {
                    var ar_text = $("#ar option:selected").text();
                    $("#area").val(ar_text);
                }
            );
            $("#sub_address").click(
                function () {
                    var username = document.getElementById("user-name");
                    if (username.value == "" || username.value == null) {
                        alert("收货人不能为空！");
                        username.focus();
                        return;
                    }
                    var userphone = document.getElementById("user-phone");
                    if (userphone.value == "" || userphone.value == null) {
                        alert("手机号码不能为空！");
                        userphone.focus();
                        return;
                    }
                    var userpostcode = document.getElementById("user-postcode");
                    if (userpostcode.value == "" || userpostcode.value == null) {
                        alert("邮编不能为空！");
                        userpostcode.focus();
                        return;
                    }
                    var userintro = document.getElementById("user-intro");
                    if (userintro.value == "" || userintro.value == null) {
                        alert("详细地址不能为空！");
                        userintro.focus();
                        return;
                    }
                    $("#form_addAddress").submit();
                    cleanAddressInfo();
                }
            );

            $(".user-addresslist").click(function () {
                $(".user-addresslist").find(".new-addr-btn").css("display", "none");
                $(this).find(".new-addr-btn").css("display", "block");

                var receiver = $(this).find(".buy-user").text();
                $(".buy-footer-address").find(".buy-user").html(receiver);
                var phone = $(this).find(".buy-phone").text();
                $(".buy-footer-address").find(".buy-phone").html(phone);
                var postcode = $(this).find(".buy-postcode").text();
                $(".buy-footer-address").find(".buy-postcode").html(postcode);
                var province = $(this).find(".province").text();
                $(".buy-footer-address").find(".province").html(province);
                var city = $(this).find(".city").text();
                $(".buy-footer-address").find(".city").html(city);
                var area = $(this).find(".dist").text();
                $(".buy-footer-address").find(".dist").html(area);
                var street = $(this).find(".street").text();
                $(".buy-footer-address").find(".street").html(street);
                var address_id = $(this).find("#address_id").val();
                $(".buy-footer-address").find("#address_id1").val(address_id);

            });

            $(".updateAdress").click(function () {
                $(".theme-popover-mask").css("display", "block");
                $(".theme-popover").css("display", "block");

                var newUrl = '/addressServlet?action=updateById';    //设置新提交地址
                $("#form_addAddress").attr('action',newUrl);    //通过jquery为action属性赋值

                var receiver = $(".buy-footer-address").find(".buy-user").text();
                $(".am-form-content").find("#user-name").val(receiver);
                var phone = $(".buy-footer-address").find(".buy-phone").text();
                $(".am-form-content").find("#user-phone").val(phone);
                var postcode = $(".buy-footer-address").find(".buy-postcode").text();
                $(".am-form-content").find("#user-postcode").val(postcode);
                var province = $(".buy-footer-address").find(".province").text();
                $(".am-form-content").find("#pro").prepend("<option value='0' selected>" + province + "</option>");
                var city = $(".buy-footer-address").find(".city").text();
                if(city == "") city = "市辖区";
                $(".am-form-content").find("#ci").prepend("<option value='0' selected>" + city + "</option>");
                var area = $(".buy-footer-address").find(".dist").text();
                if(area == "") area = "市辖区";
                $(".am-form-content").find("#ar").prepend("<option value='0' selected>" + area + "</option>");
                var street = $(".buy-footer-address").find(".street").text();
                $(".am-form-content").find("#user-intro").val(street);
                var address_id = $(".buy-footer-address").find("#address_id1").val();
                $(".am-form-content").find("#address_id2").val(address_id);
            });

            $("addAddress").click(function(){
                var newUrl = '/addressServlet?action=addAddress';    //设置新提交地址
                $("#form_addAddress").attr('action',newUrl);    //通过jquery为action属性赋值
            });

            $("#cancel_addAdress").click(
                function () {
                    cleanAddressInfo();
                }
            );
        });

        function cleanAddressInfo() {
            $(".am-form-content").find("#user-name").val("");
            $(".am-form-content").find("#user-phone").val("");
            $(".am-form-content").find("#user-postcode").val("");
            $(".am-form-content").find("#pro option[value='0']").remove();
            $(".am-form-content").find("#ci option[value='0']").remove();
            $(".am-form-content").find("#ar option[value='0']").remove();
            $(".am-form-content").find("#user-intro").val("");
        }

        function order_submit() {
            var id = $("#addressid").val();
            if (id) {
                $("#form_addOrder").submit();
            } else {
                alert("请选择收货地址");
                return;
            }
        }
    </script>
</head>
<body>
<!--顶部导航条 -->
<%@include file="/header.jsp"%>
<div class="concent">
    <!--地址 -->
    <div class="paycont">
        <div class="address">
            <h3>确认收货地址</h3>
            <div class="control">
                <div class="tc-btn createAddr theme-login am-btn am-btn-danger addAddress">使用新地址</div>
            </div>
            <div class="clear"></div>
            <ul>
                <div class="per-border"></div>
                <!-- 地址-->
                <c:forEach var="item" items="${addresslist}">

                    <li class="user-addresslist" addressid="${item.addressid}">
                        <div class="address-left">
                            <div class="user DefaultAddr">
                                <span class="buy-line-title buy-line-title-type">收货人：</span>
                                <span class="buy-address-detail">
                                    <input type="hidden" id="address_id" value="${item.addressid}">
                                    <span class="buy-user">${item.receiver}</span>
                                    <span class="buy-phone">${item.phone}</span>
                                    <span class="buy-postcode">${item.postcode}</span>
                                </span>
                            </div>
                            <div class="default-address DefaultAddr">
                                <span class="buy-line-title buy-line-title-type">收货地址：</span>
                                <span class="buy--address-detail">
                                <span class="province">${item.province}</span>
                                <c:choose>
                                    <c:when test="${(item.city eq '市辖区') or (item.city eq '县')}">
                                        <span class="city"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="city">${item.city}</span>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${item.area == '市辖区'}">
                                        <span class="dist"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="dist">${item.area}</span>
                                    </c:otherwise>
                                </c:choose>
                                <span class="street">${item.addressname}</span>
                            </span>
                            </div>
                            <ins class="deftip hidden">默认地址</ins>
                        </div>
                        <div class="address-right">
                            <span class="am-icon-angle-right am-icon-lg"></span>
                        </div>
                        <div class="clear"></div>

                        <div class="new-addr-btn" style="display:none">
                            <a href="#" class="updateAdress">编辑</a> <span class="new-addr-bar">|</span>
                            <a href="javascript:if(confirm('确定要删除该地址吗？'))window.location.href='<c:url value="/addressServlet?action=deleteById&id=${item.addressid}"/>';">删除</a>
                        </div>
                    </li>
                </c:forEach>
                <!-- 地址end -->

            </ul>

            <div class="clear"></div>
        </div>


        <!--订单 -->
        <div class="concent">
            <div id="payTable">
                <h3>确认订单信息</h3>
                <div class="cart-table-th">
                    <div class="wp">

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
                        <div class="th th-oplist">
                            <div class="td-inner">配送方式</div>
                        </div>
                    </div>
                </div>
                <!-- 商品 -->
                <c:forEach var="item" items="${cart}">
                    <tr id="J_BundleList" class="item-list">
                        <div id="J_Bundle" class="bundle  bundle-last">
                            <div class="bundle-main">
                                <ul class="item-content clearfix">
                                    <div class="pay-phone">
                                        <li class="td td-item">
                                            <div class="item-pic">
                                                <a href="<c:url value='/productServlet?action=findById&id=${item.productid}'/>"
                                                   class="J_MakePoint">
                                                    <img src="<c:url value='/${item.photo}'/>" class="itempic J_ItemImg"
                                                         style="width: 80px; height: 80px">
                                                </a>
                                            </div>
                                            <div class="item-info">
                                                <div class="item-basic-info">
                                                    <a href="<c:url value='/productServlet?action=findById&id=${item.productid}'/>"
                                                       target="_blank" class="item-title J_MakePoint"
                                                       data-point="tbcart.8.11">${item.name}</a>
                                                </div>
                                            </div>
                                        </li>

                                        <li class="td td-price">
                                            <div class="item-price price-promo-promo">
                                                <div class="price-content">
                                                    <em class="J_Price price-now">${item.price}</em>
                                                </div>
                                            </div>
                                        </li>
                                    </div>
                                    <li class="td td-amount">
                                        <div class="amount-wrapper ">
                                            <div class="item-amount ">
                                                <span class="phone-title">购买数量</span>
                                                <div class="sl">
                                                        ${item.buyCount}
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="td td-sum">
                                        <div class="td-inner">
                                            <em tabindex="0" class="J_ItemSum number">${item.total}</em>
                                        </div>
                                    </li>
                                    <li class="td td-oplist">
                                        <div class="td-inner">
                                            <span class="phone-title">配送方式</span>
                                            <div class="pay-logis">包邮</div>
                                        </div>
                                    </li>

                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </tr>
                </c:forEach>
                <!-- 商品end -->

                <div class="clear"></div>
            </div>
        </div>


        <div class="pay-total">

            <!--留言-->
            <div class="order-extra">
                <div class="order-user-info">
                    <div id="holyshit257" class="memo">
                        <form action="<c:url value='/orderServlet'/>" method="post" id="form_addOrder">
                            <label>买家留言：</label>
                            <input type="text" name="content"
                                   title="选填,对本次交易的说明（建议填写已经和卖家达成一致的说明）"
                                   placeholder="选填,建议填写和卖家达成一致的说明"
                                   class="memo-input J_MakePoint c2c-text-default memo-close">
                            <input type="hidden" id="addressid" name="id"/>
                            <input type="hidden" name="action" value="addOrder"/>
                            <div class="msg hidden J-msg">
                                <p class="error">最多输入500个字符</p>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

            <div class="clear"></div>
        </div>
        <!--含运费小计 -->
        <div class="buy-point-discharge ">
            <p class="price g_price ">
                合计（含运费） <span>¥ </span><em class="pay-sum">${totalPrice}</em>
            </p>
        </div>

        <!--信息 -->
        <div class="order-go clearfix">
            <div class="pay-confirm clearfix">
                <div class="box">
                    <div tabindex="0" id="holyshit267" class="realPay">
                        <em class="t">实付款：</em>
                        <span class="price g_price">
                            <span>¥ </span>
                            <em class="style-large-bold-red " id="J_ActualFee">${totalPrice}</em>
                        </span>
                    </div>

                    <div id="holyshit268" class="pay-address">

                        <p class="buy-footer-address">
                            <input type="hidden" name="address"/>
                            <span class="buy-line-title buy-line-title-type">寄送至：</span>
                            <span class="buy--address-detail">
                                <input type="hidden" id="address_id1" value="">
                                <span class="province"></span>
                                <span class="city"></span>
                                <span class="dist"></span>
                                <span class="street"></span>
                            </span>
                        </p>
                        <p class="buy-footer-address">
                            <span class="buy-line-title">收货人：</span>
                            <span class="buy-address-detail">
                                <span class="buy-user"></span>
                                <span class="buy-phone"></span>
                                <span class="buy-postcode"></span>
                            </span>
                        </p>
                    </div>
                </div>

                <div id="holyshit269" class="submitOrder">
                    <div class="go-btn-wrap">
                        <a id="J_Go" onclick="order_submit();" class="btn-go" tabindex="0"
                           title="点击此按钮，提交订单">提交订单</a>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>

    <div class="clear"></div>
</div>
</div>
<%@include file="/footer.jsp"%>
</div>
<!-- 增加地址 -->
<div class="theme-popover-mask"></div>

<div class="theme-popover">

    <!--标题 -->
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-danger am-text-lg">新增地址</strong> /
            <small>Add address
            </small>
        </div>
    </div>
    <hr/>

    <div class="am-u-md-12">
        <form class="am-form am-form-horizontal" action="<c:url value='/addressServlet'/>" method="post"
              id="form_addAddress">


            <div class="am-form-group">
                <label for="user-name" class="am-form-label">收货人</label>
                <div class="am-form-content">
                    <input type="hidden" name="addressid" id="address_id2" value=""/>
                    <input type="text" id="user-name" name="receiver" placeholder="收货人">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-phone" class="am-form-label">手机号码</label>
                <div class="am-form-content">
                    <input id="user-phone" name="phone" placeholder="手机号必填" type="email">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-phone" class="am-form-label">邮编</label>
                <div class="am-form-content">
                    <input id="user-postcode" name="postcode" placeholder="邮编必填" type="email">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-phone" class="am-form-label">所在地</label>
                <div class="am-form-content address">
                    <input type="hidden" name="province" id="province"/>
                    <input type="hidden" name="city" id="city"/>
                    <input type="hidden" name="area" id="area"/>
                    <select data-am-selected id="pro">
                        <option>--请选择--</option>
                    </select>
                    <select data-am-selected id="ci">
                        <option>--请选择--</option>
                    </select>
                    <select data-am-selected id="ar">
                        <option>--请选择--</option>
                    </select>
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-intro" class="am-form-label">详细地址</label>
                <div class="am-form-content">
                    <textarea name="addressname" class="" rows="3" id="user-intro" placeholder="输入详细地址"></textarea>
                </div>
            </div>

            <div class="am-form-group theme-poptit">
                <div class="am-u-sm-9 am-u-sm-push-3">
                    <div class="am-btn am-btn-danger" id="sub_address">保存</div>
                    <div class="am-btn am-btn-danger close" id="cancel_addAdress">取消</div>
                </div>
            </div>
        </form>
    </div>

</div>
<!-- 增加地址 end-->
<div class="clear"></div>
</body>

</html>