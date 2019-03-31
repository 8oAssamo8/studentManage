<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
		$("#restore").click(function() {
			if ($("#databaserestoreId").val() == null) {
				alert("请选择备份数据！");
				return false;
			}

			$.ajax({
				type : "post",
				dataType : "json",
				url : '/studentManage/servlet/DatabaseServlet?type=restoreSecond',
				data : $("#restoreForm").serialize(),
				beforeSend : function() {
					// 禁用按钮防止重复提交
					$("#restore").attr({
						disabled : "disabled"
					});
					$("#myChart").html("<h3>正在操作,请稍后……</h3>");
				},
				success : function(message) {
					if (message != null && message.result != 0) {
						var nowDate = new Date();
						alert("还原结束！");
						$("#myChart").html("<h3>操作结束……</h3>" + message.message + "<br>结束时间：" + nowDate.toLocaleDateString() + " " + nowDate.toLocaleTimeString());
					} else if (message.result == 0) {
						alert("暂无备份数据，请先备份数据库！");
						$("#myChart").html("<h3>" + message.message + "</h3>");
					} else {
						alert("服务器异常！");
						$("#myChart").html("<h3>服务器异常！</h3>");
					}
				},
				complete : function() {
					// 完成后恢复按钮
					$('#restore').removeAttr("disabled");
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
						<h3>数据库还原</h3>
					</div>
					<form id="restoreForm">
						<select class="form-control" id="databaserestoreId"
							name="databaserestoreId" size="15">
							<c:if test="${requestScope.databasebackupsSize > 0}">
								<c:forEach items="${requestScope.databasebackups}"
									var="databasebackup">
									<option style="text-align:center;"
										value="${databasebackup.databasebackupId}">- ${databasebackup.name} -</option>
								</c:forEach>
							</c:if>
							<c:if test="${requestScope.databasebackupsSize == 0}">
								<option style="text-align:center;" disabled="disabled"
									value="null">- 暂无备份数据，请先备份数据库！ -</option>
							</c:if>
						</select>
						<div class="card-body">
							<div class="form-group row mt-4">
								<button id="restore" type="submit"
									class="col-8 offset-2 btn btn-primary btn-lg"
									<c:if test="${requestScope.databasebackupsSize == 0}">disabled="disabled"</c:if>>开始还原</button>
							</div>
							<br>
							<div id="myChart"></div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
