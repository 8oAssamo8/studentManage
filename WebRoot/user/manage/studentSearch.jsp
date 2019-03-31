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
	function setType() {
		var type = $('#searchType');
		var classNameArea = $('#classNameArea');
		var studentIdArea = $('#studentIdArea');
		if (type.val() == "byClassName") {
			classNameArea.removeAttr("hidden");
			studentIdArea.attr("hidden", "hidden");
		} else if (type.val() == "byStudentId") {
			classNameArea.attr("hidden", "hidden");
			studentIdArea.removeAttr("hidden");
		}
	}
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12 mb-2">
				<div class="card">
					<div class="card-header text-center">
						<h3>查询学生信息</h3>
					</div>
					<div class="card-body">
						<form
							action="/studentManage/servlet/UserServlet?type1=searchResult&page=1&pageSize=15"
							method="post">

							<div class="form-group">
								<label for="name" class="col-form-label"><h5>查询方式</h5></label>
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text">按</label>
									</div>
									<select class="form-control" id="searchType" name="searchType"
										onchange="setType()">
										<option value="byClassName" selected="selected"> 班级 </option>
										<option value="byStudentId"> 学号 </option>
									</select>
									<div class="input-group-append">
										<label class="input-group-text">查询</label>
									</div>
								</div>
							</div>
							<div class="form-group" id="classNameArea">
								<label for="name" class="col-form-label"><h5>班级名称</h5></label>
								<div class="input-group">
									<select class="form-control" id="classId" name="classId">
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
							</div>
							<div class="form-group" id="studentIdArea" hidden="hidden">
								<label for="name" class="col-form-label"><h5>学号</h5></label> <input
									class="form-control" type="text" name="studentId"
									id="studentId">
							</div>
							<div class="form-group row">
								<button type="submit"
									class="col-8 offset-2  btn btn-primary btn-lg">开始查询</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
