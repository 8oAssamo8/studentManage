<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
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

	$(document).ready(function() { //资源加载后才执行的 代码，就放到这个函数中，jquery能保证网页所有资源（html代码，图片，js文件，css文件等）都加载后，才执行此函数
		$("#checkImg").click(function() { //为id是checkImg的标签绑定  鼠标单击事件  的处理函数
			//$(selector).attr(attribute,value)  设置被选元素的属性值
			//网址后加如一个随机值rand，表示了不同的网址，防止缓存导致的图片内容不变
			$("#checkImg").attr("src", "/studentManage/servlet/ImageCheckCodeServlet?rand=" + Math.random());
		});

		$("#myform").submit(function() {
			if (!isPasswordSame())
				return false; //阻止提交
			$.ajax({
				type : "POST",
				dataType : "json",
				url : '/studentManage/servlet/UserServlet?type1=register',
				data : $('#myform').serialize(),
				success : function(data) {
					console.log(data.result);
					if (data != null) {
						if (data.result == 1) {
							alert(data.message);
							window.location.href = data.redirectUrl; //跳转网页
						} else {
							alert(data.message);
							location.reload();
						}
					}
				},
				error : function() {
					alert("注册失败！未能连接到服务器！");
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
			<div class="mb-2">
				<div class="card">
					<div class="card-header text-center">
						<h3>管理员注册页面</h3>
					</div>
					<div class="card-body">
						<form id="myform" method="post">
							<div class="form-group">
								<label for="name" class="col-form-label"><h5>管理员账号（6位字母或数字）</h5></label>
								<input class="form-control" id="userId" name="userId"
									type="text" placeholder="请输入管理员账号" required="required"
									aria-describedby="nameHelp" pattern="([a-zA-Z0-9]{6})"
									oninvalid="setCustomValidity('管理员账号由6位字母或者数字组成');"
									oninput="setCustomValidity('');">
							</div>
							<div class="form-group">
								<label for="password" class="col-form-label"><h5>密码</h5></label>
								<input class="form-control" id="password" name="password"
									type="password" placeholder="请输入密码" required="required"
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
							<div class="form-group">
								<label for="email" class="col-form-label"><h5>邮箱</h5></label> <input
									class="form-control" id="email" name="email" type="text"
									placeholder="请输入邮箱" required="required"
									pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" title="请输入邮箱的正确格式"
									maxlength=25>
							</div>

							<div class="form-group">
								<label class="col-form-label"><h5>
										验证码<small>（点击图片刷新验证码）</small>
									</h5></label>
								<div class="input-group mb-3">
									<input class="form-control" id="checkCode" name="checkCode"
										type="text" placeholder="请输入验证码" required="required"
										title="请输入验证码" maxlength="4"
										style="height: 64px;font-size:1.2em;">
									<div class="input-group-append">
										<span class="input-group-text" id="checkImgSpan"
											style="cursor: pointer;"><img id="checkImg"
											src="/studentManage/servlet/ImageCheckCodeServlet?rand=-1" /></span>
									</div>
								</div>
							</div>
							<div class="form-group row mt-4">
								<button type="submit" id="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">注册</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
