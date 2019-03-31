<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
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
<script type="text/javascript">
	function valClassId() {
		var classIdValue = $('#classId').val();
		if (classIdValue == "" || classIdValue == null) {
			alert("请选择班级信息后再提交！");
			return false;
		} else
			return true;
	}

	function check() {
		return valClassId();
	}

	$(document).ready(function() { //资源加载后才执行的 代码，就放到这个函数中，jquery能保证网页所有资源（html代码，图片，js文件，css文件等）都加载后，才执行此函数
		$("#myform").submit(function() {
			if (!check())
				return false; //阻止提交
			$.ajax({
				type : "POST",
				dataType : "json",
				url : '/studentManage/servlet/UserServlet?type1=addStudent',
				data : $('#myform').serialize(),
				success : function(data) {
					console.log(data.result);
					if (data != null) {
						if (data.result == 1) {
							alert(data.message);
							window.location.href = data.redirectUrl; //跳转网页
						} else {
							alert(data.message);
							location.reload();
						}
					}
				},
				error : function() {
					alert("添加失败！未能连接到服务器！");
					location.reload();
				}
			});
			return false; //阻止提交
		});
	});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12 mb-2">
				<div class="card">
					<div class="card-header text-center">
						<h3>添加学生信息</h3>
					</div>
					<div class="card-body">
						<form id="myform" method="post">
							<div class="form-group">
								<label class="col-form-label"><h5>学号</h5></label> <input
									class="form-control" id="studentId" name="studentId"
									type="text" placeholder="请输入学号" required="required"
									pattern="(\d){12}" title="学号只能由12位数字组成" maxlength=12>
							</div>
							<div class="form-group">
								<label class="col-form-label"><h5>姓名</h5></label> <input
									class="form-control" id="studentName" name="studentName"
									type="text" placeholder="请输入学生姓名" required="required">
							</div>
							<div class="form-group">
								<label class="col-form-label"><h5>班级名称</h5></label> <select
									class="form-control" id="classId" name="classId">
									<c:if test="${empty requestScope.classes}">
										<option value="" disabled selected>-系统内暂无班级信息-</option>
									</c:if>
									<c:if test="${not empty requestScope.classes}">
										<option value="" disabled selected>-请选择班级-</option>
										<c:forEach items="${requestScope.classes}" var="class1">
											<option value="${class1.classId }"> [${class1.classId}] ${class1.className } </option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<!-- 
							<div class="form-group">
								<label class="col-form-label"><h5>密码</h5></label> <input
									class="form-control" id="confirmPassword"
									name="confirmPassword" type="password"
									placeholder="默认密码:123456">
							</div>
							 -->
							<div class="form-group row mt-4">
								<button type="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
