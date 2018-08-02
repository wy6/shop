var navs = [{
	"title": "商品信息管理",
	"icon": "fa-cubes",
	"spread": true,
	"children": [{
		"title": "发布商品",
		"icon": "&#xe641;",
		"href": "/manageProductServlet?action=categoryInfo"
	}, {
		"title": "商品列表",
		"icon": "&#xe63c;",
		"href": "/manageProductServlet?action=findAll"
	}, {
		"title": "添加分类",
		"icon": "&#xe63c;",
		"href": "addCategory.jsp"
	}, {
		"title": "商品类别",
		"icon": "&#xe609;",
		"href": "/manageCategoryServlet?action=findAll"
	}]
}, {
	"title": "订单管理",
	"icon": "fa-cogs",
	"spread": false,
	"children": [{
		"title": "订单列表",
		"icon": "fa-table",
		"href": "/manageOrderServlet?action=findAllOrder"
	}]
}, {
    "title": "用户管理",
    "icon": "fa-cogs",
    "spread": false,
    "children": [{
        "title": "用户列表",
        "icon": "fa-table",
        "href": "/manageUserServlet?action=findAllUser"
    }]
}, {
	"title": "地址本",
	"icon": "&#xe62a;",
	"href": "",
	"spread": false,
	"children": [{
		"title": "Github",
		"icon": "fa-github",
		"href": "https://www.github.com/"
	}, {
		"title": "QQ",
		"icon": "fa-qq",
		"href": "http://www.qq.com/"
	}, {
		"title": "Fly社区",
		"icon": "&#xe609;",
		"href": "http://fly.layui.com/"
	}, {
		"title": "新浪微博",
		"icon": "fa-weibo",
		"href": "http://weibo.com/"
	}]
}, {
	"title": "搜索导航",
	"icon": "fa-stop-circle",
	"href": "https://www.google.com",
	"spread": false
}];