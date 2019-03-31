<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	function isPasswordSame() {
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		if (password != null && password != "" && confirmPassword != null && confirmPassword != "") {
			if (password != confirmPassword) {
				alert("确认密码与密码不一致！");
				return false;
			} else
				return true;
		}
		return false;
	}

	$(document).ready(function() {
		$("#myform").submit(function() {
			if (!isPasswordSame())
				return false; //阻止提交
			$.ajax({
				type : "POST",
				dataType : "json",
				url : '/studentManage/servlet/UserServlet?type1=newPassword',
				data : $('#myform').serialize(),
				beforeSend : function() {
					// 禁用按钮防止重复提交
					$("#submit").attr({
						disabled : "disabled"
					});
				},
				success : function(data) {
					if (data != null) {
						alert(data.message);
						window.location.href = data.redirectUrl; //跳转网页
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest + " " + textStatus + " " + errorThrown + "设置新密码失败！未能连接到服务器！");
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
						<h3>设置新密码</h3>
					</div>
					<div class="card-body">
						<form id="myform" method="post">
							<div class="form-group">
								<label for="password" class="col-form-label"><h5>新密码</h5></label>
								<input class="form-control" id="password" name="password"
									type="password" placeholder="请输入新密码" required="required"
									pattern="(\w){6,18}" title="密码格式为字母开头，长度在6~18之间，只能包含字母、数字和下划线"
									maxlength=18>
							</div>
							<div class="form-group">
								<label for="password" class="col-form-label"><h5>确认新密码</h5></label>
								<input class="form-control" id="confirmPassword"
									name="confirmPassword" type="password" placeholder="请重新输入新密码"
									required="required" pattern="(\w){6,18}" title="确认新密码需与新密码相同"
									onBlur="isPasswordSame()" maxlength=18>
							</div>
							<div class="form-group row mt-4">
								<button type="submit" id="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">修改密码</button>
							</div>
							<input type="hidden" name="rand" id="rand" value="${param.rand}">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
