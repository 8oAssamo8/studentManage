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
	<div class="container mt-2">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6 col-xl-6 mb-2">
				<div class="card text-center">
					<div class="card-header alert alert-danger">
						<h3>${requestScope.message.message}</h3>
					</div>
					<div class="card-body">
						<c:if test="${requestScope.message.redirectTime < 10}">
							<h3>
								${requestScope.message.redirectTime}秒后将跳转页面。<br> 如果没有跳转,请按
								<a href="${requestScope.message.redirectUrl} ">这里</a>!!!
							</h3>
						</c:if>
						<c:if test="${requestScope.message.redirectTime >= 10}">
							<div class="form-group row mt-4">
								<button type="submit" id="submit"
									class="col-8 offset-2 btn btn-primary btn-lg"
									onclick="javascript:history.back();">返回上一步</button>
							</div>
						</c:if>
						<div class="form-group row mt-4">
							<button type="submit" id="submit"
								class="col-8 offset-2 btn btn-primary btn-lg"
								onclick="javascript:window.opener=null;window.open('','_self');window.close();">关闭窗口</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
