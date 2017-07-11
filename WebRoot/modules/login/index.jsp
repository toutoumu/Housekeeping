<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>登陆</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
			<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
				<meta http-equiv="description" content="This is my page">
					<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
					<title>悠然天空管理系统登陆页面</title>
					<meta name="generator" content="MShtml 8.00.6001.18783" />
					<meta name="copyright" content="Copyright 2014 - L-Sky.Cn" />
					<meta name="Author" content="悠然天空网络科技有限公司 - L-Sky.Cn" />
					<meta name="keywords" content="登录页面,登录,管理系统,网站建设,网站制作,网站设计,网页制作,网页设计,网络工作室,网站推广,企业推广,企业形象设计,工作室,悠然天空" />
					<meta name="description" content="悠然天空管理系统登陆页面" />
					<link rel="stylesheet" type="text/css" href="modules/login/css/style.css" />
					<script>
						dojoConfig = {
							parseOnLoad : true
						}
					</script>
					<script src="Dojo/dojo/dojo.js"></script>
					<script src="modules/login/js/index.js"></script>
					<style type="text/css">
.download {
	margin: 20px 33px 10px;
	*margin-bottom: 30px;
	padding: 5px;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	background: #e6e6e6;
	border: 1px dashed #df0031;
	font-size: 14px;
	font-family: Comic Sans MS;
	font-weight: bolder;
	color: #555
}

.download a {
	padding-left: 5px;
	font-size: 14px;
	font-weight: normal;
	color: #555;
	text-decoration: none;
	letter-spacing: 1px
}

.download a:hover {
	text-decoration: underline;
	color: #36F
}

.download span {
	float: right
}
</style>
</head>

<body>
	<div class="main">
		<div class="header hide">大屏管理系统 Beta 1.0</div>
		<div class="content">
			<div class="title hide">管理登录</div>
			<form name="login" id="loginForm" action="#" method="post">
				<fieldset>
					<div class="input">
						<input class="input_all name" name="userName" id="name" placeholder="用户名" type="text" onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'" maxLength="24" />
					</div>
					<div class="input">
						<input class="input_all password" name="password" id="password" type="password" placeholder="密码" onFocus="this.className='input_all password_now';" onBlur="this.className='input_all password'"
							maxLength="24" />
					</div>
					<div class="checkbox">
						<input type="checkbox" name="remember" id="remember" />
						<label for="remember"><span>记住密码</span></label>
					</div>
					<div class="enter">
						<input type="button" id="btnGet" class="button hide" value="登录" />
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="modules/login/js/placeholder.js"></script>
	<!--[if IE 6]>
<script type="text/javascript" src="modules/login/js/belatedpng.js" ></script>
<script type="text/javascript">
	DD_belatedPNG.fix("*");
</script>
<![endif]-->
</body>
</html>

