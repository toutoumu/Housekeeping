<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = application.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="<%=path%>/Dojo/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="<%=path%>/Dojo/dojo/resources/dojo.css">
<link rel="stylesheet" href="<%=path%>/Dojo/dojox/grid/resources/Grid.css">
<!-- <link rel="stylesheet" href="<%=path%>/Dojo/dojox/grid/resources/tundraGrid.css"> -->
<link rel="stylesheet" href="<%=path%>/Dojo/dojox/grid/resources/claroGrid.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DataGrid数据绑定</title>

<script>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="<%=path%>/Dojo/dojo/dojo.js"></script>
<script src="DataGrid.js"></script>
<style type="text/css">
html, body {
	width: 100%;
	height: 100%;
	margin: 0;
}
</style>

</head>
<body class="claro">
	<div dojoType="dijit.TitlePane" title="供电单位列表" style="height: 100%;">
		<table dojoType="dojox.grid.DataGrid" id="grid" rowsPerPage="5" rowSelector="20px" region="center"
			style="height: 290px;">
			<thead>
				<tr>
					<th field="id" width="200px">id主键</th>
					<th field="age" width="200px">年龄</th>
					<th field="userName" width="200px">用户名</th>
					<th field="address" width="200px">地址</th>
					<th field="city" width="200px">城市</th>
					<th field="id" width="200px" formatter="formatter">操作</th>
				</tr>
			</thead>
		</table>
		<div style="text-align: right">
			<div dojoType="dijit.form.Button" id="btnEdit" onclick="loadData()">加载数据</div>
		</div>
	</div>

	<!-- <div data-dojo-type="dojox.grid.DataGrid" data-dojo-attach-point="defaultGrid" style="width: 100%;"></div> -->
</body>
</html>