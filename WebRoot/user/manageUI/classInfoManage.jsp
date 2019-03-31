<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() {
		$("#classInfoDiv").attr("src", "/studentManage/user/manage/showClassJqGrid.jsp");
		$("#v-pills-tab a").click(function() {
			var classInfoLabel = $(this).text();
			if (classInfoLabel === "班级信息")
				$("#classInfoDiv").attr("src", "/studentManage/user/manage/showClassJqGrid.jsp");
			else if (classInfoLabel === "添加班级")
				$("#classInfoDiv").attr("src", "/studentManage/user/manage/addClass.jsp");
			else if (classInfoLabel === "修改班级")
				$("#classInfoDiv").attr("src", "/studentManage/servlet/ClassServlet?type1=changeClass&page=1&pageSize=12");
		});
	/*
	$("#classInfoDiv").load("/studentManage/user/manage/addClass.jsp");
	$("#v-pills-tab a").click(function() {
		var classInfoLabel = $(this).text();
		if (classInfoLabel === "添加班级")
			$("#classInfoDiv").load("/studentManage/user/manage/addClass.jsp");
		else if (classInfoLabel === "修改班级")
			$("#classInfoDiv").load("/studentManage/servlet/ClassServlet?type1=changeClass&page=1&pageSize=15");
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
					<a class="nav-link active" id="v-pills-home-tab" data-toggle="pill"
						href="#v-pills-home" aria-selected="true">班级信息</a> <a
						class="nav-link" id="v-pills-home-tab" data-toggle="pill"
						href="#v-pills-home" aria-selected="true">添加班级</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">修改班级</a>
				</div>
			</div>
			<div class="col-12 col-sm-12 col-md-12 col-lg-10 col-xl-10">
				<div class="embed-responsive embed-responsive-1by1"
					style="height:100%">
					<iframe id="classInfoDiv" class="embed-responsive-item"></iframe>
				</div>
			</div>
			<!-- 
			<div class="tab-content col" id="v-pills-tabContent">
				<div id="classInfoDiv"></div>
			</div>
			 -->
		</div>
	</div>
</body>
</html>
