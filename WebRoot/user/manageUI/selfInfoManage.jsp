<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() {
		$("#selfInfoDiv").attr("src", "/studentManage/user/changePassword.jsp");
		$("#v-pills-tab a").click(function() {
			var selfInfoLabel = $(this).text();
			if (selfInfoLabel === "修改密码")
				$("#selfInfoDiv").attr("src", "/studentManage/user/changePassword.jsp");
			else if (selfInfoLabel === "修改头像")
				$("#selfInfoDiv").attr("src", "/studentManage/user/manage/changeHeadIcon.jsp");
			else if (selfInfoLabel === "审查管理员")
				$("#selfInfoDiv").attr("src", "/studentManage/servlet/UserServlet?type1=check&page=1&pageSize=15");
		});
	/*
		$("#selfInfoDiv").load("/studentManage/user/changePassword.jsp");
		$("#v-pills-tab a").click(function() {
			var selfInfoLabel = $(this).text();
			if (selfInfoLabel === "修改密码")
				$("#selfInfoDiv").load("/studentManage/user/changePassword.jsp");
			else if (selfInfoLabel === "修改头像")
				$("#selfInfoDiv").load("/studentManage/user/manage/changeHeadIcon.jsp");
			else if (selfInfoLabel === "审查管理员")
				$("#selfInfoDiv").load("/studentManage/servlet/UserServlet?type1=check&page=1&pageSize=15");
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
						href="#v-pills-home" aria-selected="true">修改密码</a>
					<c:if test="${sessionScope.user.getUserId().length() == 6}">
						<a class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
							href="#v-pills-profile" aria-selected="false">修改头像</a>
						<a class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
							href="#v-pills-profile" aria-selected="false">审查管理员</a>
					</c:if>
				</div>
			</div>
			<div class="col-12 col-sm-12 col-md-12 col-lg-10 col-xl-10">
				<div class="embed-responsive embed-responsive-1by1"
					style="height:100%">
					<iframe id="selfInfoDiv" class="embed-responsive-item"></iframe>
				</div>
			</div>
			<!-- 
			<div class="tab-content col" id="v-pills-tabContent">
				<div id="selfInfoDiv"></div>
			</div>
			 -->
		</div>
	</div>
</body>
</html>
