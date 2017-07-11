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

<title>菜单管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="Dojo/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="Dojo/dojo/resources/dojo.css">
<link rel="stylesheet" href="Dojo/dojox/grid/resources/Grid.css">
<!-- <link rel="stylesheet" href="Dojo/dojox/grid/resources/tundraGrid.css"> -->
<link rel="stylesheet" href="Dojo/dojox/grid/resources/claroGrid.css">
<style type="text/css">
#grida {
	margin: 0;
	padding: 0;
	border: none;
}
</style>
<script>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/demos/ComboBox.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="claro">

	<div data-dojo-type="dijit/form/Form" id="myForm" data-dojo-id="myForm" encType="multipart/form-data" action=""
		method="post">

		<table style="border: 0px solid #9f9f9f;" cellspacing="5">
			<tr>
				<td width="90px">类型</td>
				<td width="200px"><input type="text" name="type" required="false" data-dojo-id="cbbType"
					data-dojo-type="dijit/form/FilteringSelect" /></td>
				<td></td>
			</tr>
 		</table>

		<div style="text-align: right;">
			<button data-dojo-type="dijit/form/Button" type="button" name="submitButton" value="Submit" onclick="Submit()">查询</button>
		</div>
	</div>

</body>
</html>