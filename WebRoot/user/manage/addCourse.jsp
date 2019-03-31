<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	$(document).ready(function() { //资源加载后才执行的 代码，就放到这个函数中，jquery能保证网页所有资源（html代码，图片，js文件，css文件等）都加载后，才执行此函数
		$("#myform").submit(function() {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : '/studentManage/servlet/CourseServlet?type1=addCourse',
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
						<h3>添加课程信息</h3>
					</div>
					<div class="card-body">
						<form id="myform" method="post">
							<div class="form-group">
								<label class="col-form-label"><h5>课程编号</h5></label> <input
									class="form-control" id="courseId" name="courseId" type="text"
									placeholder="请输入课程编号" required="required" pattern="(\d){6}"
									title="课程编号只能由6位数字组成" maxlength=6>
							</div>
							<div class="form-group">
								<label class="col-form-label"><h5>课程名称</h5></label> <input
									class="form-control" id="courseName" name="courseName"
									type="text" placeholder="请输入课程名称" required="required">
							</div>
							<div class="form-group">
								<label class="col-form-label"><h5>课程学期</h5></label> <input
									class="form-control" id="terms" name="terms" type="text"
									placeholder="请输入课程学期" required="required"
									pattern="(19|20)\d{2}(01|02)" title="课程学期只能由年份+01/02组成"
									maxlength=6>
							</div>
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
