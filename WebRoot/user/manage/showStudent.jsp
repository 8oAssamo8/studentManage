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
	function getOnePage(type, orderFieldName) {
		var url1;
		var page = $("#page");
		var pageSize = $("#pageSize");
		var totalPageCount = $("#totalPageCount");

		var order = $("#order");
		var orderField = $("#orderField");

		if (orderFieldName != "") { //切换排序
			orderField.attr("value", orderFieldName); //设置排序字段名
			if (order.val() == "asc") //切换排序
				order.val("desc");
			else
				order.val("asc");

			page.val("1"); //排序后从第一页开始显示					
		}

		pageValue = parseInt(page.val());
		if (type == "first")
			page.val("1");
		else if (type == "pre") {
			pageValue -= 1;
			page.val(pageValue.toString());
		} else if (type == "next") {
			pageValue += 1;
			page.val(pageValue.toString());
		} else if (type == "last") {
			page.val(totalPageCount.val());
		}
		//提交
		$('#myform').submit();
	}
</script>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-12">
				<div class="card-header text-center">
					<h2>学生信息</h2>
				</div>
				<c:if test="${requestScope.users.isEmpty() }">
					<br>
					<h2>暂无对应学生信息，请检查之后再进行操作！</h2>
				</c:if>
				<c:if test="${!requestScope.users.isEmpty() }">
					<form action="/studentManage/servlet/UserServlet?type1=showStudent"
						id="myform" method="post">
						<table class="table text-center table-striped table-sm">
							<thead>
								<tr>
									<th><a href='javascript:void(0);'
										onclick="getOnePage('','studentId');">学号</a></th>
									<th>姓名</th>
									<th><a href='javascript:void(0);'
										onclick="getOnePage('','classId');">所属班级</a></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${requestScope.users}" var="user">
									<tr>
										<td>${user.userId}</td>
										<td>${user.studentName}</td>
										<td>${requestScope.classes[user.classId]}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="row">
							<div class="col-8 offset-2">
								<table class="table table-bordered table-sm text-center">
									<tr>
										<td>第${requestScope.pageInformation.page}页</td>
										<c:if test="${requestScope.pageInformation.page > 1}">
											<td><a href="javascript:void(0);"
												onclick="getOnePage('first','');">首页</a></td>
										</c:if>

										<c:if test="${requestScope.pageInformation.page > 1}">
											<td><a href="javascript:void(0);"
												onclick="getOnePage('pre','');">上一页</a></td>
										</c:if>

										<c:if
											test="${requestScope.pageInformation.page < requestScope.pageInformation.totalPageCount}">
											<td><a href="javascript:void(0);"
												onclick="getOnePage('next','');">下一页</a></td>
										</c:if>
										<c:if
											test="${requestScope.pageInformation.page < requestScope.pageInformation.totalPageCount}">
											<td><a href="javascript:void(0);"
												onclick="getOnePage('last','');">尾页</a></td>
										</c:if>
										<td>共${requestScope.pageInformation.totalPageCount}页</td>
									</tr>
								</table>
							</div>
						</div>
						<input type="hidden" name="page" id="page"
							value="${requestScope.pageInformation.page}"> <input
							type="hidden" name="pageSize" id="pageSize"
							value="${requestScope.pageInformation.pageSize}"> <input
							type="hidden" name="totalPageCount" id="totalPageCount"
							value="${requestScope.pageInformation.totalPageCount}"> <input
							type="hidden" name="allRecordCount" id="allRecordCount"
							value="${requestScope.pageInformation.allRecordCount}"> <input
							type="hidden" name="orderField" id="orderField"
							value="${requestScope.pageInformation.orderField}"> <input
							type="hidden" name="order" id="order"
							value="${requestScope.pageInformation.order}"> <input
							type="hidden" name="ids" id="ids"
							value="${requestScope.pageInformation.ids}"> <input
							type="hidden" name="searchSql" id="searchSql"
							value="${requestScope.pageInformation.searchSql}"> <input
							type="hidden" name="result" id="result"
							value="${requestScope.pageInformation.result}">
					</form>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
