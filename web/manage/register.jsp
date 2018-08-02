<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>注册新用户</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<c:url value='/manage/plugins/layui/css/layui.css'/>" media="all"/>
    <link rel="stylesheet" href="<c:url value='/manage/css/login.css'/>"/>
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
    <script type="text/javascript" src="<c:url value='/manage/plugins/layui/layui.js'/>"></script>
    <script>
        layui.use(['form', 'layedit', 'laydate'], function () {
            var form = layui.form(),
                layer = layui.layer,
                layedit = layui.layedit,
                laydate = layui.laydate;

            //创建一个编辑器
            var editIndex = layedit.build('LAY_demo_editor');
            //自定义验证规则
            form.verify({
                title: function (value) {
                    if (value.length < 5) {
                        return '标题至少得5个字符啊';
                    }
                },
                pass: [/(.+){6,12}$/, '密码必须6到12位'],
                content: function (value) {
                    layedit.sync(editIndex);
                }
            });

            //监听提交
            form.on('submit(demo1)', function (data) {
                layer.alert(JSON.stringify(data.field), {
                    title: '最终的提交信息'
                })
                return false;
            });
        });
    </script>

    <script type="text/javascript">
        function check() {
            var username = document.getElementById("username");
            if (username.value == "" || username.value == null) {
                alert("请输入账号！");
                username.focus();
                return false;
            }

            var password = document.getElementById("password");
            if (password.value == "" || password.value == null) {
                alert("请输入密码！");
                password.focus();
                return false;
            }

            var password1 = document.getElementById("password1");
            if (password1.value == "" || password1.value == null) {
                alert("请输入重复密码！");
                password1.focus();
                return false;
            }
            if (password1.value != password.value) {
                alert("两次输入密码不一致！");
                password1.focus();
                return false;
            }

            var arr = document.getElementsByName("sex");
            for (var i = 0; i < arr.length; i++) {
                if (!(arr[0].checked) && !(arr[1].checked)) {
                    alert("请选择性别！");
                    return false;
                }
            }

            var birthday = document.getElementById("birthday");
            if (birthday.value == "" || birthday.value == null) {
                alert("请选择出生日期！");
                birthday.focus();
                return false;
            }

            var telephone = document.getElementById("telephone");
            if (telephone.value == "" || telephone.value == null) {
                alert("请输入手机号码！");
                telephone.focus();
                return false;
            }
            if (telephone.value.length != 11) {
                alert("请输入正确的手机号码！");
                telephone.focus();
                return false;
            }

            var email = document.getElementById("email");
            if (email.value == "" || email.value == null) {
                alert("请输入电子邮件！");
                email.focus();
                return false;
            }
            return ture;
        }
    </script>
</head>
<body class="beg-login-bg">
<div class="beg-register-box">
    <div style="margin: 25px;">
        <header>
            <h1>新用户注册</h1>
        </header>

        <form class="layui-form" action="userServlet" method="post" onsubmit="return check()">
            <input type="hidden" value="regist" name="action"/>
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="username" id="username" lay-verify="required" placeholder="请输入用户名"
                           autocomplete="off" class="layui-input" style='width:190px'>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                    <input type="password" name="password" id="password" lay-verify="pass" placeholder="请输入密码"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                    <input type="password" name="password1" id="password1" lay-verify="pass" placeholder="请输入重复密码"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <input type="radio" name="sex" id="radio" value="男" title="男" checked="">
                    <input type="radio" name="sex" id="radio" value="女" title="女">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">出生日期</label>
                <div class="layui-input-block">
                    <input type="text" name="birthday" id="birthday" lay-verify="date" placeholder="yyyy-mm-dd"
                           autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})"
                           style='width:190px'>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">验证手机</label>
                <div class="layui-input-inline">
                    <input type="tel" name="telephone" id="telephone" lay-verify="phone" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">验证邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="email" id="email" lay-verify="email" autocomplete="off"
                           class="layui-input">
                </div>
            </div>

            <div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn">立即提交</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div>
        <footer>
            <p><font size="2" face="Verdana">信息技术系 技术支持 © </font><label><a href="login.jsp">去登录</a></label></p>
        </footer>
    </div>
</div>
</body>
</html>
