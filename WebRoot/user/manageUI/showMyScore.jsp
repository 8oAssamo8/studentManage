<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() {
		$("#myScoreInfoDiv").attr("src", "/studentManage/servlet/ScoreServlet?type1=getOneScore");
		$("#v-pills-tab a").click(function() {
			var myScoreInfoLabel = $(this).text();
			if (myScoreInfoLabel === "单科成绩")
				$("#myScoreInfoDiv").attr("src", "/studentManage/servlet/ScoreServlet?type1=getOneScore");
			else if (myScoreInfoLabel === "所有成绩")
				$("#myScoreInfoDiv").attr("src", "/studentManage/servlet/ScoreServlet?type1=getAllScore");
		});
	/*
	$("#myScoreInfoDiv").load("/studentManage/servlet/ScoreServlet?type1=getOneScore");
	$("#v-pills-tab a").click(function() {
		var myScoreInfoLabel = $(this).text();
		if (myScoreInfoLabel === "单科成绩")
			$("#myScoreInfoDiv").load("/studentManage/servlet/ScoreServlet?type1=getOneScore");
		else if (myScoreInfoLabel === "所有成绩")
			$("#myScoreInfoDiv").load("/studentManage/servlet/ScoreServlet?type1=getAllScore");
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
						href="#v-pills-home" aria-selected="true">单科成绩</a> <a
						class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
						href="#v-pills-profile" aria-selected="false">所有成绩</a>
				</div>
			</div>
			<div class="col-12 col-sm-12 col-md-12 col-lg-10 col-xl-10">
				<div class="embed-responsive embed-responsive-1by1"
					style="height:100%">
					<iframe id="myScoreInfoDiv" class="embed-responsive-item"></iframe>
				</div>
			</div>
			<!-- 
			<div class="tab-content col" id="v-pills-tabContent">
				<div id="myScoreInfoDiv"></div>
			</div>
			 -->
		</div>
	</div>
</body>
</html>
