<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function() { //资源加载后才执行的 代码，就放到这个函数中，jquery能保证网页所有资源（html代码，图片，js文件，css文件等）都加载后，才执行此函数
		$("#checkImgSpan").click(function() { //为id是checkImg的标签绑定  鼠标单击事件  的处理函数
			//$(selector).attr(attribute,value)  设置被选元素的属性值
			//网址后加如一个随机值rand，表示了不同的网址，防止缓存导致的图片内容不变
			$("#checkImg").attr("src", "/studentManage/servlet/ImageCheckCodeServlet?rand=" + Math.random());
		});

		$("#myform").submit(function() {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : '/studentManage/servlet/UserServlet?type1=login',
				data : $('#myform').serialize(),
				success : function(data) {
					console.log(data.result);
					if (data != null) {
						if (data.result == 1) {
							window.location.href = data.redirectUrl; //跳转网页
						} else {
							alert(data.message);
							location.reload();
						}
					}
				},
				error : function(XMLHttpRequest, XMLHttpRequest, errorThrown) {
					alert(XMLHttpRequest + " 登录失败！未能连接到服务器！ " + XMLHttpRequest + " lalala " + errorThrown);
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
						<h3>用户登录</h3>
					</div>
					<div class="card-body">
						<form id="myform" method="post">
							<div class="form-group">
								<label for="name" class="col-form-label"><h5>学号/账号</h5></label>
								<input class="form-control" id="userId" name="userId"
									type="text" placeholder="请输入学号/账号" required="required"
									aria-describedby="nameHelp" title="请输入学号/账号">
							</div>
							<div class="form-group">
								<label for="password" class="col-form-label"><h5>登录密码</h5></label>
								<input class="form-control" id="password" name="password"
									type="password" placeholder="请输入登录密码" required="required"
									title="请输入登录密码">
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
							<div class="form-group text-right pt-3">
								<a class="breadcrumb-item"
									href="/studentManage/findPassword.jsp">找回密码 </a>
							</div>
							<div class="form-group row mt-4">
								<button id="submit" type="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">登录</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
