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
	$(document).ready(function() {
		$("input[name='checkbox1']").click(function() {
			$("input[name='checkbox1']").not(this).prop("checked", false);
		});
	});

	function setNewName() {
		var checkname = $("#newName1").val();
		var newName = "";
		var checkterm = $("#newTerm1").val();
		var newTerm = "";
		newName = checkname;
		newName1 = $("#newName");
		newName1.val(newName);
		newTerm = checkterm;
		newTerm1 = $("#newTerm");
		newTerm1.val(newTerm);
	}

	function changeStudent() {
		var checkboxs = $("input[name='checkbox1']");
		var ids = "";
		var num = 0;
		for (i = 0; i < checkboxs.length; i++) {
			if (checkboxs.eq(i).prop("checked")) {
				ids = checkboxs.eq(i).val();
				num++;
			}
		}
		if (num != 1) {
			alert("请选择一条需要修改的学生信息！");
			return false; //阻止提交
		}

		setNewName();

		ids1 = $("#ids");
		ids1.val(ids);
		//提交
		$('#myform').submit();
	}

	function setNewNameNull() {
		var checkterm = $("#newTerm1").val();
		if (checkterm == null || checkterm == "") {
			newTerm1 = $("#newTerm");
			newTerm1.val(checkterm);
		}
		var checkname = $("#newName1").val();
		if (checkname == null || checkname == "") {
			newName1 = $("#newName");
			newName1.val(checkname);
		}
	}


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
					<h2>修改学生信息</h2>
				</div>
				<c:if test="${requestScope.users.isEmpty() }">
					<br>
					<h2>暂无对应学生信息，请检查之后再进行操作！</h2>
				</c:if>
				<c:if test="${!requestScope.users.isEmpty() }">
					<form
						action="/studentManage/servlet/UserServlet?type1=changeStudent"
						id="myform" method="post">
						<table class="table text-center table-striped table-sm">
							<thead>
								<tr>
									<th></th>
									<th><a href='javascript:void(0);'
										onclick="getOnePage('','studentId');">学号</a></th>
									<th>姓名</th>
									<th><a href='javascript:void(0);'
										onclick="getOnePage('','classId');">所属班级</a></th>
									<th>密码</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="user">
									<tr class="trDefault">
										<td align="center"><input name="checkbox1"
											type="checkbox" value="${user.userId}"></td>
										<td align="center">${user.userId}</td>
										<td align="center">${user.studentName}</td>
										<td width="200" align="center">${requestScope.classes[user.classId]}</td>
										<td align="center">${user.password}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="row">
							<div class="col-10 offset-1">
								<table class="table table-striped table-sm"
									style="border:1px solid #dee2e6;">
									<tr>
										<td class="text-right">姓名：</td>
										<td class="text-left"><input class="form-control"
											type="text" name="newName1" id="newName1"></td>
									</tr>
									<tr>
										<td class="text-right">班级：</td>
										<td class="text-left"><select class="form-control"
											name="classId">
												<option value="">-默认不修改班级-</option>
												<c:forEach items="${requestScope.classes1}" var="class1">
													<option value="${class1.classId }"> [${class1.classId}] ${class1.className } </option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td class="text-right">密码：</td>
										<td class="text-left"><input class="form-control"
											type="text" name="newTerm1" id="newTerm1"></td>
									</tr>
									<tr class="text-center">
										<td colspan="2">
											<button type="button" class="btn btn-primary"
												onclick="changeStudent()">修改所选项</button>
										</td>
									</tr>
								</table>
							</div>
						</div>
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
							value="${requestScope.pageInformation.result}"><input
							type="hidden" name="newName" id="newName"
							value="${requestScope.pageInformation.newName}"><input
							type="hidden" name="newTerm" id="newTerm"
							value="${requestScope.pageInformation.newTerm}">
					</form>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
