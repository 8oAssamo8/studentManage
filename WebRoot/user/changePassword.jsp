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
	function checkOldNewPassword() {
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		if (oldPassword != null && oldPassword != "" && newPassword != null && newPassword != "") {
			if (oldPassword == newPassword) {
				alert("新密码与旧密码不能相同！");
				return false;
			} else
				return true;
		}
		return false;
	}

	function checkNewConfirmPassword() {
		var password = $("#newPassword").val();
		var confirmPassword = $("#confirmPassword").val();
		if (password != null && password != "" && confirmPassword != null && confirmPassword != "") {
			if (password != confirmPassword) {
				alert("确认密码与新密码不一致！");
				return false;
			} else
				return true;
		}
		return false;
	}

	function submitCheck() {
		return checkOldNewPassword() && checkNewConfirmPassword();
	}
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12 mb-2">
				<div class="card">
					<div class="card-header text-center">
						<h3>修改密码</h3>
					</div>
					<div class="card-body">
						<form
							action="/studentManage/servlet/UserServlet?type1=changePassword"
							method="post" onsubmit="return submitCheck()">
							<div class="form-group">
								<label for="name" class="col-form-label"><h5>旧密码</h5></label> <input
									class="form-control" id="oldPassword" name="oldPassword"
									type="text" placeholder="请输入旧密码" required="required">
							</div>
							<div class="form-group">
								<label for="password" class="col-form-label"><h5>新密码</h5></label>
								<input class="form-control" id="newPassword" name="newPassword"
									type="password" placeholder="请输入新密码" required="required"
									pattern="(\w){6,18}" title="密码格式为字母开头，长度在6~18之间，只能包含字母、数字和下划线"
									maxlength=18>
							</div>
							<div class="form-group">
								<label for="password" class="col-form-label"><h5>确认密码</h5></label>
								<input class="form-control" id="confirmPassword"
									name="confirmPassword" type="password" placeholder="请重新输入密码"
									required="required" pattern="(\w){6,18}" title="确认密码需与密码相同"
									onBlur="isPasswordSame()" maxlength=18>
							</div>
							<div class="form-group row mt-4">
								<button type="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">修改密码</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
