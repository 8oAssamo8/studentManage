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
<!--引入highcharts文件 -->
<script src="/studentManage/plugin/Highcharts-7.0.2/code/highcharts.js"></script>
<script
	src="/studentManage/plugin/Highcharts-7.0.2/code/modules/exporting.js"></script>
<script
	src="/studentManage/plugin/Highcharts-7.0.2/code/modules/export-data.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12">
				<div class="card-header text-center">
					<h2>查询所有成绩</h2>
				</div>
				<div class="card-body">
					<form
						action="/studentManage/servlet/ScoreServlet?type1=getAllScore"
						method="post">
						<div class="form-group">
							<label for="name" class="col-form-label"><h5>学期数</h5></label>
							<div class="input-group">
								<select class="form-control" name="term">
									<c:if test="${empty requestScope.terms}">
										<option value="noValue">暂无选课信息</option>
									</c:if>
									<c:if test="${not empty requestScope.terms}">
										<c:forEach items="${requestScope.terms}" var="term">
											<option value="${term }"
												<c:if test="${requestScope.term==term }">selected="selected"</c:if>
											> ${term } </option>
										</c:forEach>
									</c:if>
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
			<c:if test="${empty requestScope.scoreResult}">
				<br>
				<h3>请选择条件进行查找！</h3>
			</c:if>

			<c:if test="${not empty requestScope.scoreResult}">
				<br>
				<h3>查找结果：</h3>
				<table class="table text-center table-striped table-sm">
					<thead>
						<tr>
							<th>科目名称</th>
							<th>成绩</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.scoreResult}" var="score">
							<tr align="center" height="25">
								<td>${score.courseName }</td>
								<td><c:if test="${score.score==0}">尚未考试</c:if> <c:if
										test="${score.score!=0}">${score.score }</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<div id="container"
						style="min-width: 300px; height: 400px; margin: 0 auto"></div>
			</c:if>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		Highcharts.setOptions({
			lang : {
				contextButtonTitle : "图表导出菜单",
				decimalPoint : ".",
				downloadJPEG : "下载JPEG图片",
				downloadPDF : "下载PDF文件",
				downloadPNG : "下载PNG文件",
				downloadSVG : "下载SVG文件",
				drillUpText : "返回 {series.name}",
				loading : "加载中",
				months : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
				noData : "没有数据",
				numericSymbols : [ "千", "兆", "G", "T", "P", "E" ],
				printChart : "打印图表",
				resetZoom : "恢复缩放",
				resetZoomTitle : "恢复图表",
				shortMonths : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ],
				thousandsSep : ",",
				weekdays : [ "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天" ],
				downloadCSV : "下载CSV文件",
				downloadXLS : "下载XLS文件",
				viewData : "查看数据表",
				openInCloud : "在Highcharts Cloud中打开"
			}
		});
		Highcharts.chart('container', {
			chart : {
				type : 'column'
			},
			title : {
				text : '${sessionScope.user.studentName}-${requestScope.term}学期成绩情况'
			},
			subtitle : {
				text : '数据来源:学生信息管理系统 '
			},
			xAxis : {
				type : 'category',
				labels : {
					rotation : -45,
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			},
			yAxis : {
				min : 0,
				title : {
					text : '成绩'
				}
			},
			legend : {
				enabled : false
			},
			tooltip : {
				pointFormat : '<b>{point.y:.1f} 分</b>'
			},
			series : [ {
				name : '成绩',
				data : [
					<c:if test="${not empty requestScope.scoreResult}">
						<c:forEach items="${requestScope.scoreResult}" var="score">
							<c:if test="${score.score!=0}">[ '${score.courseName }', ${score.score }],</c:if>
						</c:forEach>
					</c:if>
				],
				dataLabels : {
					enabled : true,
					rotation : -90,
					color : '#FFFFFF',
					align : 'right',
					format : '{point.y:.1f}', // one decimal
					y : 10, // 10 pixels down from the top
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			} ]
		});
	</script>
</body>
</html>
