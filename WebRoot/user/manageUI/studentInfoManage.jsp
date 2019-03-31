<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() {
		$("#studentInfoDiv").attr("src", "/studentManage/user/manage/showStudentJqGrid.jsp");
		$("#v-pills-tab a").click(function() {
			var studentInfoLabel = $(this).text();
			if (studentInfoLabel === "学生信息")
				$("#studentInfoDiv").attr("src", "/studentManage/user/manage/showStudentJqGrid.jsp");
			else if (studentInfoLabel === "查看学生")
				$("#studentInfoDiv").attr("src", "/studentManage/servlet/UserServlet?type1=showStudent&page=1&pageSize=15");
			else if (studentInfoLabel === "添加学生")
				$("#studentInfoDiv").attr("src", "/studentManage/servlet/UserServlet?type1=addStudent");
			else if (studentInfoLabel === "删除学生")
				$("#studentInfoDiv").attr("src", "/studentManage/servlet/UserServlet?type1=delete&page=1&pageSize=15");
			else if (studentInfoLabel === "修改学生")
				$("#studentInfoDiv").attr("src", "/studentManage/servlet/UserServlet?type1=changeStudent&page=1&pageSize=10");
			else if (studentInfoLabel === "查询学生")
				$("#studentInfoDiv").attr("src", "/studentManage/servlet/UserServlet?type1=search");
			else if (studentInfoLabel === "学生排名")
				$("#studentInfoDiv").attr("src", "/studentManage/servlet/RankingServlet?type1=ranking");
		});
	/*
	$("#studentInfoDiv").load("/studentManage/servlet/UserServlet?type1=showStudent&page=1&pageSize=15");
	$("#v-pills-tab a").click(function() {
		var studentInfoLabel = $(this).text();
		if (studentInfoLabel === "查看学生")
			$("#studentInfoDiv").load("/studentManage/servlet/UserServlet?type1=showStudent&page=1&pageSize=15");
		else if (studentInfoLabel === "添加学生")
			$("#studentInfoDiv").load("/studentManage/servlet/UserServlet?type1=addStudent");
		else if (studentInfoLabel === "删除学生")
			$("#studentInfoDiv").load("/studentManage/servlet/UserServlet?type1=delete&page=1&pageSize=15");
		else if (studentInfoLabel === "修改学生")
			$("#studentInfoDiv").load("/studentManage/servlet/UserServlet?type1=changeStudent&page=1&pageSize=12");
		else if (studentInfoLabel === "查询学生")
			$("#studentInfoDiv").load("/studentManage/servlet/UserServlet?type1=search");
		else if (studentInfoLabel === "学生排名")
			$("#studentInfoDiv").load("/studentManage/servlet/RankingServlet?type1=ranking");
	});
	*/
	});
</script>
</head>
<body>
	<div class="container">
		<div class="row pt-3 pb-2">
			<div class="col-12 col-sm-12 col-md-12 col-lg-2 col-xl-2">
				<div class="nav flex-column nav-pills text-center" id="v-pills-tab"
					role="tablist" aria-orientation="vertical">
					<a class="nav-link active" id="v-pills-profile-tab"
						data-toggle="pill" href="#v-pills-profile" aria-selected="false">学生信息</a>
					<a class="nav-link" id="v-pills-home-tab" data-toggle="pill"
						href="#v-pills-home" aria-selected="true">查看学生</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">添加学生</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">删除学生</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">修改学生</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">查询学生</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">学生排名</a>
				</div>
			</div>
			<div class="col-12 col-sm-12 col-md-12 col-lg-10 col-xl-10">
				<div class="embed-responsive embed-responsive-1by1"
					style="height:100%">
					<iframe id="studentInfoDiv" class="embed-responsive-item"></iframe>
				</div>
			</div>
			<!-- 
			<div class="tab-content col" id="v-pills-tabContent">
				<div id="studentInfoDiv"></div>
			</div>
			 -->
		</div>
	</div>
</body>
</html>
