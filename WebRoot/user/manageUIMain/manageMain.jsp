<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<link href="/studentManage/css/studentManageCSS.css" rel="stylesheet"
	type="text/css">
<meta charset="utf-8">
<script type="text/javascript">
	function getOnePage(type) {
		var url1;
		var page = document.getElementById("page");
		var pageSize = document.getElementById("pageSize");
		var totalPageCount = document.getElementById("totalPageCount");

		pageValue = parseInt(page.value);
		if (type == "pre") {
			pageValue -= 1;
			page.value = pageValue.toString();
		} else if (type == "next") {
			pageValue += 1;
			page.value = pageValue.toString();
		}
		//提交
		document.getElementById('myform').submit();
	}

	function setNoScrolling() {
		var frame = document.getElementById("frame");
		frame.setAttribute("scrolling", "no");
	}

	function setYesScrolling() {
		var frame = document.getElementById("frame");
		frame.setAttribute("scrolling", "yes");
	}

	function test1() {
		$("#functionDiv").html("https://www.baidu.com/");
	}


	$(document).ready(function() {
		$("#functionDiv").load("/studentManage/user/manageUI/selfInfoManage.jsp");
		$("#nav-tab a").click(function() {
			var label = $(this).text();
			if (label === "用户信息管理")
				$("#functionDiv").load("/studentManage/user/manageUI/selfInfoManage.jsp");
			else if (label === "学生信息管理")
				$("#functionDiv").load("/studentManage/user/manageUI/studentInfoManage.jsp");
			else if (label === "班级信息管理")
				$("#functionDiv").load("/studentManage/user/manageUI/classInfoManage.jsp");
			else if (label === "课程信息管理")
				$("#functionDiv").load("/studentManage/user/manageUI/courseInfoManage.jsp");
			else if (label === "成绩信息管理")
				$("#functionDiv").load("/studentManage/user/manageUI/scoreInfoManage.jsp");
			else if (label === "数据库备份管理")
				$("#functionDiv").load("/studentManage/user/manageUI/databaseBackupManage.jsp");
			else if (label === "查看成绩")
				$("#functionDiv").load("/studentManage/user/manageUI/showMyScore.jsp");
		});
	});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div
				class="col-12 col-sm-12 col-md-12 col-lg-10 col-xl-10 offset-lg-1 offset-xl-1">
				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<a class="nav-item nav-link active" id="nav-home-tab"
							data-toggle="tab" href="#nav-home">用户信息管理</a>
						<c:if test="${sessionScope.user.getUserId().length() == 6}">
							<a class="nav-item nav-link" id="nav-profile-tab"
								data-toggle="tab" href="#nav-profile">学生信息管理</a>
							<a class="nav-item nav-link" id="nav-profile-tab"
								data-toggle="tab" href="#nav-profile">班级信息管理</a>
							<a class="nav-item nav-link" id="nav-profile-tab"
								data-toggle="tab" href="#nav-profile">课程信息管理</a>
							<a class="nav-item nav-link" id="nav-profile-tab"
								data-toggle="tab" href="#nav-profile">成绩信息管理</a>
							<a class="nav-item nav-link" id="nav-profile-tab"
								data-toggle="tab" href="#nav-profile">数据库备份管理</a>
						</c:if>
						<c:if test="${sessionScope.user.getUserId().length() == 12}">
							<a class="nav-item nav-link" id="nav-profile-tab"
								data-toggle="tab" href="#nav-profile">查看成绩</a>
						</c:if>
					</div>
				</nav>
				<div class="col" id="functionDiv"
					style="border-left:1px solid #dee2e6;border-right:1px solid #dee2e6;border-bottom:1px solid #dee2e6;border-bottom-left-radius: .25rem;border-bottom-right-radius: .25rem;"></div>
			</div>
		</div>
	</div>
</body>
</html>
