<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() {
		$("#myform").submit(function() {
			//符合条件往下执行修改密码操作：
			$.ajax({
				type : "POST",
				dataType : "json",
				url : '/studentManage/servlet/UserServlet?type1=findPassword',
				data : $('#myform').serialize(),
				beforeSend : function() {
					// 禁用按钮防止重复提交
					$("#submit").attr({
						disabled : "disabled"
					});
				},
				success : function(data) {
					if (data != null) {
						if (data.result == 1 || data.result == -2) {
							alert(data.message);
							window.location.href = data.redirectUrl; //跳转网页
						} else {
							alert(data.message);
							location.reload();
						}
					}
				},
				error : function() {
					alert("找回密码失败！未能连接到服务器！");
					location.reload();
				}
			});
			return false; //阻止提交
		});
	});
</script>
</head>
<body>
	<div class="container mt-2">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6 col-xl-6 mb-2">
				<div class="card">
					<div class="card-header text-center">
						<h3>找回密码</h3>
					</div>
					<div class="card-body">
						<form id="myform" method="post">
							<div class="form-group">
								<label for="email" class="col-form-label"><h5>邮箱</h5></label> <input
									class="form-control" id="email" name="email" type="text"
									placeholder="请输入邮箱" required="required"
									pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" title="请输入邮箱的正确格式"
									maxlength=25>
							</div>
							<div class="form-group row mt-4">
								<button type="submit" id="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">找 回 密 码</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
