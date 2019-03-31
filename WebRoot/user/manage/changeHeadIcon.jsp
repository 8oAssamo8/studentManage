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
			<div class="col-12 mb-3">
				<div class="card">
					<div class="card-header text-center">
						<h3>修改头像</h3>
					</div>
					<div class="card-body">
						<form
							action="/studentManage/servlet/UserServlet?type1=changePrivate"
							method="post" enctype="multipart/form-data" target="frame"
							onsubmit="return check()">

							<div class="form-group">
								<label for="name" class="col-form-label"><h5>当前头像</h5></label>
								<div class="text-center">
									<img class="img-responsive center-block"
										src="${ sessionScope.user.headIconUrl}" height="150" />
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-form-label"><h5>上传头像</h5></label>
								<div class="text-center">
									<img id="myImage"
										src="/studentManage/upload/images/addHeadIcon.png"
										height="150" />
								</div>
								<div class="input-group col-10 offset-1 mt-3">
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="myFile"
											name="myFile"
											accept=".dwg,.dxf,.gif,.jp2,.jpe,.jpeg,.jpg,.png,.svf,.tif,.tiff"
											onchange="preview()"><label class="custom-file-label">选择头像文件</label>
									</div>
								</div>
							</div>
							<div class="form-group row mt-4">
								<button type="submit"
									class="col-8 offset-2 btn btn-primary btn-lg">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	function check() {
		var pictureUrl = $("#myFile").val();
		if (pictureUrl == "") {
			alert("请先选择上传头像文件！");
			return false;
		} else
			return true;
	}

	function preview() {
		var preview = $('#myImage');
		var file = $('#myFile')[0].files[0];
		var reader = new FileReader();
		reader.onloadend = function() {
			preview.attr("src", reader.result);
		}

		if (file)
			reader.readAsDataURL(file);
		else
			preview.attr("src", "/studentManage/upload/images/addHeadIcon.png");
	}
</script>
