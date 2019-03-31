<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
	$(document).ready(function() {
		$("#backup").click(function() {
			$.ajax({
				type : "post",
				dataType : "json",
				url : '/studentManage/servlet/DatabaseServlet?type=backup',
				data : "",
				beforeSend : function() {
					// 禁用按钮防止重复提交
					$("#backup").attr({
						disabled : "disabled"
					});
					$("#myChart").html("<h3>正在操作,请稍后……</h3>");
				},
				success : function(message) {
					if (message != null) {
						var nowDate = new Date();
						alert("操作结束！");
						$("#myChart").html("<h3>操作结束……</h3>" + message.message + "<br>结束时间：" + nowDate.toLocaleDateString() + " " + nowDate.toLocaleTimeString());
					} else {
						alert("服务器异常！");
						$("#myChart").html("<h3>服务器异常！</h3>");
					}
				},
				complete : function() {
					// 完成后恢复按钮
					$('#backup').removeAttr("disabled");
				},
				error : function() {
					$("#myChart").html("服务器异常！");
				}
			});
		})
	});
</script>
</head>
<body>
	<div class="container mt-2">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6 col-xl-6 mb-2">
				<div class="card">
					<div class="card-header text-center">
						<h3>数据库备份</h3>
					</div>
					<div class="card-body">
						<div class="form-group row mt-4">
							<button type="submit" id="backup" class="col-8 offset-2 btn btn-primary btn-lg">开始备份</button>
						</div>
						<br>
						<div id="myChart"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
