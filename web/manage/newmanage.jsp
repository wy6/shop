<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Table</title>
		<link rel="stylesheet" href="/News/manage/plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="/News/manage/css/global.css" media="all">
		<link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
		<link rel="stylesheet" href="/News/manage/css/table.css" />
	</head>

	<body>
		<div class="admin-main">

			<blockquote class="layui-elem-quote">
				<a href="javascript:if(confirm('确定要删除吗？')) location='<c:url value="/newServlet?action=delete&id=${item.id }"/>'" class="layui-btn layui-btn-small">
					<i class="layui-icon">&#xe608;</i> 批量删除
				</a>
				<!-- <a href="#" class="layui-btn layui-btn-small" id="import">
					<i class="layui-icon">&#xe608;</i> 导入新闻
				</a>
				<a href="#" class="layui-btn layui-btn-small">
					<i class="fa fa-shopping-cart" aria-hidden="true"></i> 导出新闻
				</a> -->
				<a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a>
			</blockquote>
			<fieldset class="layui-elem-field">
				<legend>新闻列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover" id="newTable">
						<thead>
							<tr>
								<th><input id="iCheck" type="checkbox" id="selected-all"></th>
								<th>标题</th>
								<th>作者</th>
								<th>发布时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="item" items="${list}">
							<tr>
								<td><input  type="checkbox" value="${item.id}"></td>
								<td>
									<a href="<c:url value='/newServlet?action=idForUpdate&id=${item.id}'/>">${item.title}</a>
								</td>
								<td>${item.author}</td>
								<td>${item.publishDate}</td>
								<td>
									<a href="<c:url value='/newServlet?action=idForUpdate&id=${item.id}'/>" class="layui-btn layui-btn-mini">编辑</a>
									<a href="javascript:if(confirm('确定要删除吗？')) location='<c:url value="/newServlet?action=delete&id=${item.id }"/>'" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>

				</div>
			</fieldset>
			<div class="admin-table-page">
				<div id="page" class="page">
				</div>
			</div>
		</div>
		<script type="text/javascript" src="/News/manage/plugins/layui/layui.js"></script>
		<script>
			/* layui.config({
				base: '/News/manage/plugins/layui/modules/'
			}); */

			layui.use(['icheck', 'laypage','layer'], function() {
				var $ = layui.jquery,
					laypage = layui.laypage,
					layer = parent.layer === undefined ? layui.layer : parent.layer;
				$('input').iCheck({
					checkboxClass: 'icheckbox_flat-green'
				});
				
				

				//page
				laypage({
					cont: 'page',
					pages: 25 //总页数
						,
					groups: 5 //连续显示分页数
						,
					jump: function(obj, first) {
						//得到了当前页，用于向服务端请求对应数据
						var curr = obj.curr;
						if(!first) {
							//layer.msg('第 '+ obj.curr +' 页');
						}
					}
				});

				$('#search').on('click', function() {
					parent.layer.alert('搜索功能正在开发中，敬请期待...')
				});

				$('#add').on('click', function() {
					$.get('addNew.jsp', null, function(form) {
						layer.open({
							type: 1,
							title: '添加表单',
							content: form,
							btn: ['保存', '取消'],
							area: ['600px', '400px'],
							maxmin: true,
							yes: function(index) {
								console.log(index);
							},
							full: function(elem) {
								var win = window.top === window.self ? window : parent.window;
								$(win).on('resize', function() {
									var $this = $(this);
									elem.width($this.width()).height($this.height()).css({
										top: 0,
										left: 0
									});
									elem.children('div.layui-layer-content').height($this.height() - 95);
								});
							}
						});
					});
				});

				$('#import').on('click', function() {
					var that = this;
					var index = layer.tips('只想提示地精准些', that,{tips: [1, 'white']});
					$('#layui-layer'+index).children('div.layui-layer-content').css('color','#000000');
				});

				$('.site-table tbody tr').on('click', function(event) {
					var $this = $(this);
					var $input = $this.children('td').eq(0).find('input');
					$input.on('ifChecked', function(e) {
						$this.css('background-color', '#EEEEEE');
					});
					$input.on('ifUnchecked', function(e) {
						$this.removeAttr('style');
					});
					$input.iCheck('toggle');
				}).find('input').each(function() {
					var $this = $(this);
					$this.on('ifChecked', function(e) {
						$this.parents('tr').css('background-color', '#EEEEEE');
					});
					$this.on('ifUnchecked', function(e) {
						$this.parents('tr').removeAttr('style');
					});
				});
				$('#selected-all').on('ifChanged', function(event) {
					var $input = $('.site-table tbody tr td').find('input');
					$input.iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
				});
			});
			
			
			var $ = layui.$, active = {
				    getCheckData: function(){ //获取选中数据
				      var checkStatus = table.checkStatus('newTable')
				      ,data = checkStatus.data;
				      layer.alert(JSON.stringify(data));
				    }
				    ,getCheckLength: function(){ //获取选中数目
				      var checkStatus = table.checkStatus('idTest')
				      ,data = checkStatus.data;
				      layer.msg('选中了：'+ data.length + ' 个');
				    }
				    ,isAll: function(){ //验证是否全选
				      var checkStatus = table.checkStatus('idTest');
				      layer.msg(checkStatus.isAll ? '全选': '未全选')
				    }
				  };
		</script>
	</body>

</html>
