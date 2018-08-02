<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录-新闻管理系统</title>
    <link rel="stylesheet" href="<c:url value='/manage/plugins/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<c:url value='/manage/css/login.css'/>"/>

    <style>
        * {
            margin: 0;
            padding: 0;
        }

        .z1 {
            width: 45px;
            height: 100%;
            position: relative;
            left: 270px;
            top: 148px;
            border: 0px solid #F00;
            z-index: 3
        }
    </style>

    <script type="text/javascript">
        function changevcimg() {
            var img = document.getElementById("vcimg");
            img.src = "verifyCodeServlet?d=" + new Date();
        }

        function login() {
            var username = document.getElementById("username");
            var password = document.getElementById("password");
            var verifycode = document.getElementById("verifycode");
            if (username.value == "" || username.value == null) {
                alert("请输入账号！");
                username.focus();
                return false;
            } else if (password.value == "" || password.value == null) {
                alert("请输入密码！");
                password.focus();
                return false;
            } else if (verifycode.value == "" || verifycode.value == null) {
                alert("请输入验证码！");
                verifycode.focus();
                return false;
            }
            document.getElementById("form").submit();
        }
    </script>

</head>

<body class="beg-login-bg">
<div class="beg-login-box">
    <header>
        <h1>新闻系统登录</h1>
        <div class="z1">
            <img style="vertical-align:middle;"
                 src="/verifyCodeServlet" onclick="changevcimg()" id="vcimg"
                 title="看不清,点击换一张"/>
        </div>
    </header>
    <div class="beg-login-main">
        <form action="userServlet" class="layui-form" method="post" onsubmit="return  login()">
            <input type="hidden" value="login" name="action"/>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe612;</i>
                </label>
                <input type="text" name="username" id="username" autocomplete="off" placeholder="请输入登录名"
                       class="layui-input">
            </div>

            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input type="password" name="password" id="password" autocomplete="off" placeholder="请输入密码"
                       class="layui-input">
            </div>

            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input type="text" name="verifycode" id="verifycode" autocomplete="off" placeholder="请输入验证码"
                       class="layui-input" style='width:180px'>
            </div>

            <div class="layui-form-item">
                <div class="beg-pull-left beg-login-remember">
                    <label><a href="register.jsp">注册新用户</a></label>
                </div>
                <div class="beg-pull-right">
                    <button class="layui-btn layui-btn-primary">
                        <i class="layui-icon">&#xe650;</i>登录
                    </button>
                </div>
                <div class="beg-clear"></div>
            </div>
        </form>
    </div>
    <footer>
        <p><font size="2" face="Verdana">信息技术系 技术支持 © </font></p>
    </footer>
</div>

</body>

</html>

