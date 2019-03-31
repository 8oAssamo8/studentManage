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
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12">
				<div class="card-header text-center">
					<h2>学生排名信息</h2>
				</div>
				<div class="card-body">
					<form action="/studentManage/servlet/RankingServlet?type1=ranking"
						method="post">
						<div class="form-group">
							<label for="name" class="col-form-label"><h5>班级名称</h5></label>
							<div class="input-group">
								<select class="form-control" name="classId">
									<c:forEach items="${requestScope.classes}" var="class1">
										<option value="${class1.classId }"
											<c:if test="${class1.classId==requestScope.rankingClassId }">selected="selected"</c:if>
										> [${class1.classId}] ${class1.className } </option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-form-label"><h5>学期数</h5></label>
						<div class="input-group">
							<select class="form-control" name="terms">
									<c:forEach items="${requestScope.terms}" var="term">
										<option value="${term }"
											<c:if test="${term==requestScope.rankingTerms }">selected="selected"</c:if>
										> ${term } </option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<button type="submit"
							class="col-8 offset-2  btn btn-primary btn-lg">开始查询</button>
					</div>
				</form>				
			</div>
		</div>
	</div>

	<div class="row mb-2">
		<div class="col-12">
			<c:if test="${requestScope.rankingResult==null }">
				<br>
				<h3>请选择条件进行查找！</h3>
			</c:if>
		
			<c:if test="${requestScope.rankingResult.isEmpty() }">
				<h3>符合查找条件的结果为空！</h3>
			</c:if>
		
			<c:if
				test="${requestScope.rankingResult!=null && !requestScope.rankingResult.isEmpty()}">
				<br>
				<h3>查找结果：</h3>
				<table class="table text-center table-striped table-sm">
					<thead>
						<tr>
							<th>学号</th>
							<th>姓名</th>
							<th>总成绩</th>
							<th>排名</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.rankingResult}" var="ranking">
							<tr>
								<td>${ranking.studentId }</td>
								<td>${ranking.studentName }</td>
								<td>${ranking.sumScore }</td>
								<td>${ranking.ranking }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>	
</div>	
</body>
</html>
