<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--Bootstrap必须设定 -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--以下css和js都必须按此顺序引入 -->
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="/studentManage/plugin/boostrap/bootstrap-4.2.1-dist/css/bootstrap.min.css">
<!-- Font-Awesome图标 -->
<link rel="stylesheet"
	href="/studentManage/plugin/Font-Awesome-3.2.1/css/font-awesome.min.css">
<!--Bootstrap必须先引入jquery -->
<script type="text/javascript"
	src="/studentManage/plugin/jquery/jquery-3.2.1.min.js"></script>
<script
	src="/studentManage/plugin/boostrap/bootstrap-4.2.1-dist/js/popper.min.js"></script>
<script
	src="/studentManage/plugin/boostrap/bootstrap-4.2.1-dist/js/bootstrap.min.js"></script>
<link href="/studentManage/css/studentManageCSS.css" rel="stylesheet"
	type="text/css">

<!-- jqGrid组件基础样式包-必要 -->
<link rel="stylesheet"
	href="/studentManage/plugin/jqgrid/css/ui.jqgrid.css" />

<!-- jqGrid主题包-非必要 -->
<!-- 在jqgrid/css/css这个目录下还有其他的主题包，可以尝试更换看效果 -->
<link rel="stylesheet"
	href="/studentManage/plugin/jqgrid/css/css/flick/jquery-ui-1.8.16.custom.css" />

<!-- jqGrid插件包-必要 -->
<script type="text/javascript"
	src="/studentManage/plugin/jqgrid/js/jquery.jqGrid.src.js"></script>

<!-- jqGrid插件的多语言包-非必要 -->
<!-- 在jqgrid/js/i18n下还有其他的多语言包，可以尝试更换看效果 -->
<script type="text/javascript"
	src="/studentManage/plugin/jqgrid/js/i18n/grid.locale-cn.js"></script>

</head>
<body>
	<script type="text/javascript">
		$(function() {
			pageInit();
		});
		function pageInit() {
			jQuery("#list").jqGrid(
				{
					url : '/studentManage/servlet/UserServlet?type1=showStudentForJqGrid',
					datatype : "json",
					colNames : [ '学号', '姓名', '所属班级' ],
					colModel : [
						{
							name : 'studentId',
							index : 'studentId',
							width : 220,
							align : 'center'
						},
						{
							name : 'studentName',
							index : 'studentName',
							width : 170,
							sortable : false,
							align : 'center'
						},
						{
							name : 'className',
							index : 'className',
							width : 290,
							align : 'center'
						},
					],
					width : '100%',
					height : '100%',
					rowNum : 20, //默认每页显示的记录数量
					rowList : [ 15, 20, 25 ], //可以选择显示的每页记录数量
					pager : '#pager', //通过id选择器选择显示信息内容的div
					sortname : 'studentId', //默认排序的列名
					mtype : "get", //提交到服务器的方式，可选get和post
					viewrecords : true,
					sortorder : "desc", //默认的排序方式
					caption : "学生信息--JqGrid", //表名
					recordtext : "记录 {0}~{1} | 总记录数 {2}", //显示记录数的格式
					emptyrecords : "无数据", //空记录时的提示信息
					pgtext : "第{0}页  共 {1}页", //页数显示格式
	
					multiselect : true,
				});
			jQuery("#list").jqGrid('navGrid', '#pager', {
				edit : false, //不显示编辑记录图标
				add : false, //不显示添加记录图标
				del : false, //不显示删除记录图标
				search : false //不显示查询记录图标
			}).navButtonAdd('#pager', {
				caption : "删除所选学生信息",
				buttonicon : "ui-icon-del",
				onClickButton : function() {
					var s;
					s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
					if (s == "") {
						alert("请选择需要删除的学生");
						return false;
					} else {
						$.ajax({
							type : "POST",
							dataType : "json",
							url : '/studentManage/servlet/UserServlet?type1=deleteStudent',
							data : {
								"ids" : s.toString()
							},
							success : function(data) {
								console.log(data.result);
								if (data != null) {
									if (data.result > 0) {
										alert(data.message);
									//window.location.href = data.redirectUrl; //跳转网页
									} else {
										alert(data.message);
										location.reload();
									}
									jQuery("#list").trigger("reloadGrid"); //重新加载JqGrid内容
								}
							},
							error : function() {
								alert("删除失败！未能连接到服务器！");
								location.reload();
							}
						});
						return false; //阻止提交
					}
				},
				position : "last"
			});
		}
	</script>
	<center>
		<table id="list"></table>
		<div id="pager"></div>
	</center>
</body>
</html>