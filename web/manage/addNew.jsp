<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>发布新闻</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no">

		<link rel="stylesheet" href="/News/manage/plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
	
	<script type="text/javascript" src="/News/manage/plugins/layui/layui.js"></script>
		<script>
			layui.use([ 'form', 'layedit', 'laydate' ], function() {
				var form = layui.form(),
					layer = layui.layer,
					layedit = layui.layedit,
					laydate = layui.laydate;
		
				//创建一个编辑器
				var editIndex = layedit.build('LAY_demo_editor');
		
				//监听提交
				form.on('submit(demo1)', function(data) {
					layer.alert(JSON.stringify(data.field), {
						title : '最终的提交信息'
					})
					return false;
				});
			});
		</script>
	
	<script type="text/javascript">
		function check() {
			var title = document.getElementById("title");
			if (title.value == "" || title.value == null) {
				alert("新闻标题不能为空！");
				title.focus();
				return false;
			}
	
			var author = document.getElementById("author");
			if (author.value == "" || author.value == null) {
				alert("发布作者不能为空！");
				author.focus();
				return false;
			}
	
			var publishDate = document.getElementById("publishDate");
			if (publishDate.value == "" || publishDate.value == null) {
				alert("发布日期不能为空！");
				publishDate.focus();
				return false;
			}
	
			var content = document.getElementByName("content");
			if (content.value == "" || content.value == null) {
				alert("发布内容不能为空！");
				content.focus();
				return false;
			}
			return ture;
		}
	</script>
	</head>

	<body>
		<div style="margin: 15px;">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend>发布新闻 <label style="color:red;font-size: 14px;margin: 0px 150px><span id="msg"> ${msg}</span></label></legend>
			</fieldset>
			<form class="layui-form" action="/News/newServlet" method="post" onsubmit="return check()">
			<input type="hidden" value="addNew" name="action"/>
				<div class="layui-form-item">
					
					<label class="layui-form-label">新闻标题</label>
					<div class="layui-input-block">
						<input type="text" name="title" id="title" lay-verify="title" autocomplete="off" placeholder="请输入新闻标题" class="layui-input" style='width:490px'>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">作者</label>
					<div class="layui-input-block">
						<input type="text" name="author" id="author" lay-verify="required" placeholder="请输入作者" autocomplete="off" class="layui-input" style='width:190px'>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">发布日期</label>
					<div class="layui-input-block">
						<input type="text" name="publishDate" id="publishDate" lay-verify="date" placeholder="请选择发布日期" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})" style='width:190px'>
					</div>
				</div>
					
				<!-- <div class="layui-form-item">
					<label class="layui-form-label">单行选择框</label>
					<div class="layui-input-block">
						<select name="interest" lay-filter="aihao">
							<option value=""></option>
							<option value="0">写作</option>
							<option value="1" selected="">阅读</option>
							<option value="2">游戏</option>
							<option value="3">音乐</option>
							<option value="4">旅行</option>
						</select>
					</div>
				</div> -->
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">新闻内容</label>
					<div class="layui-input-block" style='width:666px'>
						<textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="LAY_demo_editor" style='width:290px'></textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn">立即发布</button>
						<button type="reset" class="layui-btn layui-btn-primary">重置</button>
					</div>
				</div>
			</form>
		</div>
	</body>

</html>