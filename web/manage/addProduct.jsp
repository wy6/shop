<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发布商品</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<c:url value='/manage/plugins/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
    <script type="text/javascript" src="<c:url value='/manage/plugins/layui/layui.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/ckeditor/ckeditor.js'/>"></script>

    <script>
        layui.use(['layedit', 'laydate'], function () {
            var $ = layui.jquery,
                layer = layui.layer,
                layedit = layui.layedit,
                laydate = layui.laydate;

        });

        function openWin(f, n, w, h, s) {
            sb = s == "1" ? "1" : "0";
            l = (screen.width - w) / 2;
            t = (screen.height - h) / 2;
            sFeatures = "left=" + l + ",top=" + t + ",height=" + h + ",width=" + w
                + ",center=1,scrollbars=" + sb + ",status=0,directories=0,channelmode=0";
            openwin = window.open(f, n, sFeatures);
            if (!openwin.opener)
                openwin.opener = self;
            openwin.focus();
            return openwin;
        }
    </script>

    <style>
        * {
            margin: 0;
            padding: 0;
        }

        .z1 {
            width: 45px;
            height: 100%;
            position: relative;
            left: 200px;
            top: -4px;
            border: 0px solid #F00;
            z-index: 3
        }
    </style>

    <script type="text/javascript">
        function check() {
            var productName = document.getElementById("productName");
            if (productName.value == "" || productName.value == null) {
                alert("商品名称不能为空！");
                productName.focus();
                return false;
            }
            var filepath = document.getElementById("filepath");
            if (filepath.value == "" || filepath.value == null) {
                alert("商品图片不能为空！");
                filepath.focus();
                return false;
            }
            var price = document.getElementById("price");
            if (price.value == "" || price.value == null) {
                alert("商品价格不能为空！");
                price.focus();
                return false;
            }
            var markPrice = document.getElementById("markPrice");
            if (markPrice.value == "" || markPrice.value == null) {
                alert("商品市场价格不能为空！");
                markPrice.focus();
                return false;
            }
            var quality = document.getElementById("quality");
            if (quality.value == "" || quality.value == null) {
                alert("商品库存量不能为空！");
                quality.focus();
                return false;
            }
            var hit = document.getElementById("hit");
            if (hit.value == "" || hit.value == null) {
                alert("商品浏览量不能为空！");
                hit.focus();
                return false;
            }
            var publishDate = document.getElementById("publishDate");
            if (publishDate.value == "" || publishDate.value == null) {
                alert("发布日期不能为空！");
                publishDate.focus();
                return false;
            }
            var introduction = document.getElementById("introduction");
            if (introduction.value == "" || introduction.value == null) {
                alert("商品简介不能为空！");
                introduction.focus();
                return false;
            }
        }
    </script>
</head>

<body>
<div style="margin: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>发布商品
        </legend>
    </fieldset>

    <form class="layui-form" action="/manageProductServlet" method="post">
        <input type="hidden" name="action" value="addProduct"/>
        <div class="layui-form-item">
            <label class="layui-form-label">商品名称</label>
            <div class="layui-input-inline">
                <input type="text" name="productName" id="productName" autocomplete="off" placeholder="请输入商品名称"
                       class="layui-input" style='width:490px'>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品图片</label>
            <div class="layui-input-inline">
                <input type="text" name="photo" id="filepath" autocomplete="off" placeholder="请选择商品图片"
                       class="layui-input" style='width:390px'>
            </div>
            <div class="layui-form-mid layui-word-aux z1">
                <li class="layui-btn layui-btn-small layui-btn-normal" id="addImg" style="vertical-align:middle;">
                    <a href="javascript:;" onclick="openWin('/manage/uploadImg.jsp','文件上传','600','350','1')"><span
                            style="color: white"><i class="layui-icon">&#xe608;</i> 添加图片</span></a>
                </li>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品分类</label>
            <div class="layui-input-inline" style='width:190px'>
                <select name="categoryid">
                    <c:forEach var="ci" items="${clist}">
                        <option value="${ci.categoryid}">${ci.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品价格</label>
            <div class="layui-input-inline">
                <input type="text" name="price" id="price" placeholder="请输入商品价格"
                       autocomplete="off" class="layui-input" style='width:190px'>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">市场价格</label>
            <div class="layui-input-inline">
                <input type="text" name="markPrice" id="markPrice" placeholder="请输入商品市场价格"
                       autocomplete="off" class="layui-input" style='width:190px'>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品数量</label>
            <div class="layui-input-inline">
                <input type="text" name="quality" id="quality" placeholder="请输入商品数量"
                       autocomplete="off" class="layui-input" style='width:190px'>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">浏览量</label>
            <div class="layui-input-inline">
                <input type="text" name="hit" id="hit" placeholder="请输入商品浏览量"
                       autocomplete="off" class="layui-input" style='width:190px'>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">发布日期</label>
            <div class="layui-input-inline">
                <input type="text" name="publishDate" id="publishDate" lay-verify="date" placeholder="请选择发布日期"
                       autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})" style='width:190px'>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品简介</label>
            <div class="layui-input-inline">
                <input type="text" name="introduction" id="introduction" placeholder="请输入商品简介"
                       autocomplete="off" class="layui-input" style='width: 490px'>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">商品详情</label>
            <div class="layui-input-inline" style='width: 666px'>
                <textarea id="content" name="content" style="width:700px;height:250px;"></textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content');
                </script>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" onclick="return check();">立即发布</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
</body>

</html>