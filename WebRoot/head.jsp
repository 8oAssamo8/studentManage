<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<!--bootstrap必须使用html5规范-->
<html lang="cn">
<head>
<meta charset="utf-8">
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
</head>
<body>
	<div class="container mb-2">
		<div class="row">
			<div class="col text-center">
				<!-- <div class="topTitle">欢迎使用学生信息管理系统</div> -->
				<img src="/studentManage/upload/images/logo.png"
					class="img-fluid mt-3"
					style="-moz-user-select: none;-webkit-user-select: none;-ms-user-select: none;-khtml-user-select: none;user-select: none" />
			</div>
		</div>
		<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
		<div class="row">
			<div
				class="col-6 col-sm-6 col-md-6 col-lg-5 col-xl-5 offset-lg-1 offset-xl-1 text-left">
				<c:if test="${!(empty sessionScope.user) }">
					您好，
					<c:if test="${sessionScope.user.studentName==null}">
							管理员${sessionScope.user.userId}&nbsp;
						</c:if>
					<c:if test="${sessionScope.user.studentName!=null}">
							${sessionScope.user.studentName}
						</c:if>
				</c:if>
			</div>
			<div class="col-6 col-sm-6 col-md-6 col-lg-5 col-xl-5 text-right">
				<c:choose>
					<c:when test="${empty sessionScope.user}">
						<a href="/studentManage/index.jsp">登录</a>&nbsp;<a
							href="/studentManage/register.jsp">管理员注册</a>
					</c:when>
					<c:otherwise>
						<a href="/studentManage/user/manageUIMain/manageMain.jsp">管理</a>&nbsp;
						<a href="/studentManage/servlet/UserServlet?type1=exit">注销</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>
