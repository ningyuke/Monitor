<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en"
	class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<link href="/favicon.ico" type="image/x-icon" rel=icon>
<link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
<meta name="renderer" content="webkit">
<title>Remote Monitoring and Management System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/login_files/min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/login_files/login.css">
<link href="${pageContext.request.contextPath}/login_files/css.css"rel="stylesheet" type="text/css">
<!--[if lt IE 9]> 
	<script src="${ctx}/js/jquery/ie/html5shiv.js"></script> 
	<script src="${ctx}/js/jquery/ie/respond.min.js"></script>
<![endif]-->
</head>
<body onload="javascript:to_top()"
	style="background-image: url('${pageContext.request.contextPath}/login_files/9.jpg');margin-top:0px;background-repeat:no-repeat;background-size: 100% auto;">
	<div id="loginbox" style="padding-top: 10%;">
		<form id="loginform" name="loginform" class="form-vertical"
			style="background-color: rgba(0, 0, 0, 0.5) !important; background: #000; filter: alpha(opacity = 50); *background: #000; *filter: alpha(opacity = 50); /*黑色透明背景结束*/ color: #FFF; bottom: 0px; right: 0px; border: 1px solid #000;"
			action="${pageContext.request.contextPath}/login.shtml" method="post">
			<div class="control-group normal_text">
				<table style="width: 100%">
					<tr>
						<td align="left"><img
							src="${pageContext.request.contextPath}/login_files/logo_left.png"
							alt="Logo"></td>
						<td align="center" style="font-weight: bold; color: white;font-size: 18pt;">Remote Monitoring and Management System</td>
						<td align="right"><img
							src="${pageContext.request.contextPath}/login_files/logo_left.png"
							alt="Logo"></td>
					</tr>
				</table>

			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly" style="background: #28b779"><img
							src="${pageContext.request.contextPath}/login_files/account_1.png"
							alt="Please input account.."></span><input type="text" placeholder="username" value="admin"
							name="username" 
							style="height: 32px; margin-bottom: 0px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly"><img
							src="${pageContext.request.contextPath}/login_files/lock_1.png"
							alt="Please input password.."></span><input type="password" placeholder="password" value="123456"
							name="password" 
							style="height: 32px; margin-bottom: 0px;" />
					</div>
				</div>
			</div>
			<div class="form-actions">
				    <span class="pull-left" style="width: 33%">
				        <a href="javascript:void(0)"	class="flip-link btn btn-info" id="to-recover">Forget Password?</a>
				    </span>
					<!-- <span class="pull-left" style="width: 33%">
					    <a href="install.shtml" class="flip-link btn btn-danger" id="to-recover">一键初始化系统</a>
					</span> -->
				<span class="pull-right"> <button onclick="checkUserForm();" class="btn btn-success">Login</button>
				</span>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		if ("${error}" != "") {
			alert("${error}");
		};
		function checkUserForm() {
			document.loginform.submit();
		}
		function to_top() {
			if (window != top) {
				top.location.href = location.href;
			}
		}
	</script>
</body>
</html>