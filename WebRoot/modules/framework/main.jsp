<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>主页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="Dojo/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="Dojo/dojo/resources/dojo.css">
<script>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/framework/main.js"></script>
<style type="text/css">
html, body {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>


</head>
<body class="claro">
	<!-- 遮罩层 -->
	<%@includefile="/Dojo/extends/alpha/mask.jsp" %>
	<div data-dojo-type="dijit.layout.BorderContainer" style="width: 100%; height: 100%;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
			<div style="text-align: center;">微家政后台管理系统</div>
		</div>
		<!-- data-dojo-type="dijit/layout/AccordionContainer" -->
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'leading'" splitter="true"
			style="width: 200px; height: 100%;">
			<div id="myTree" style="width: 100%; height: 100%;"></div>
		</div>
		<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center'" data-dojo-id="tabContainer">
			<div data-dojo-type="dijit/layout/ContentPane" title="提示">
				<div style="display: none;">

					<button id="btnGet" data-dojo-type="dijit/form/Button">get请求</button>
					<p>get请求内容</p>
					<div id="getContent"></div>
					<button id="btnPost" data-dojo-type="dijit/form/Button">post请求</button>
					<p>post请求内容</p>
					<div id="postContent"></div>
					<button id="button" type="button"></button>
					<div id="result"></div>
					<button id="button" type="button" onclick="window.parent.showMask()">遮罩层</button>
				</div>
			</div>
			<!-- <div data-dojo-type="dijit/layout/ContentPane" title="第二个Tab" closable="true">tab pane #2</div> -->
			<!-- <div data-dojo-type="dijit/layout/ContentPane" title="第三个Tab" closable="true">tab pane #3</div> -->
		</div>
		<!-- <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'trailing'">Trailing pane</div> -->
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'bottom'">
			<div style="text-align: center;">版权所有@微家政</div>
		</div>
	</div>
</body>
</html>