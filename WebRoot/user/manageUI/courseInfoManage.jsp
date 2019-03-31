<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() {
		$("#courseInfoDiv").attr("src", "/studentManage/user/manage/showCourseJqGrid.jsp");
		$("#v-pills-tab a").click(function() {
			var courseInfoLabel = $(this).text();
			if (courseInfoLabel === "课程信息")
				$("#courseInfoDiv").attr("src", "/studentManage/user/manage/showCourseJqGrid.jsp");
			else if (courseInfoLabel === "添加课程")
				$("#courseInfoDiv").attr("src", "/studentManage/user/manage/addCourse.jsp");
			else if (courseInfoLabel === "修改课程")
				$("#courseInfoDiv").attr("src", "/studentManage/servlet/CourseServlet?type1=changeCourse&page=1&pageSize=12");
		});
	/*
	$("#courseInfoDiv").load("/studentManage/user/manage/addCourse.jsp");
	$("#v-pills-tab a").click(function() {
		var courseInfoLabel = $(this).text();
		if (courseInfoLabel === "添加课程")
			$("#courseInfoDiv").load("/studentManage/user/manage/addCourse.jsp");
		else if (courseInfoLabel === "修改课程")
			$("#courseInfoDiv").load("/studentManage/servlet/CourseServlet?type1=changeCourse&page=1&pageSize=15");
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
						href="#v-pills-home" aria-selected="true">课程信息</a> <a
						class="nav-link" id="v-pills-home-tab" data-toggle="pill"
						href="#v-pills-home" aria-selected="true">添加课程</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">修改课程</a>
				</div>
			</div>
			<div class="col-12 col-sm-12 col-md-12 col-lg-10 col-xl-10">
				<div class="embed-responsive embed-responsive-1by1"
					style="height:100%">
					<iframe id=courseInfoDiv class="embed-responsive-item"></iframe>
				</div>
			</div>
			<!-- 
			<div class="tab-content col" id="v-pills-tabContent">
				<div id="courseInfoDiv"></div>
			</div>
			 -->
		</div>
	</div>
</body>
</html>
