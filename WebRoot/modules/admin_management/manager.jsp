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

<title>管理员管理</title>
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
<script>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/admin_management/manager.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="claro">
	<div dojoType="dijit.TitlePane" title="管理员列表" style="height: 100%;">
		<table dojoType="dojox.grid.DataGrid" id="grid" rowsPerPage="5" rowSelector="20px" region="center" style="height: 290px;">
			<thead>
				<tr>
					<th field="id" width="100px">序号</th>
					<th field="userName" width="200px">用户名</th>
					<th field="id" width="100px" formatter="deleteFormatter">编辑</th>
					<th field="id" width="100px" formatter="editFormatter">修改密码</th>
					<th field="id" width="100px" formatter="showAuthorizationFormatter">权限管理</th>
					<th width="auto"/>
				</tr>
			</thead>
		</table>
		<div style="text-align: right;">
			<button data-dojo-type="dijit/form/Button" type="button" onclick="query()">刷新</button>
			<button data-dojo-type="dijit/form/Button" type="button" onclick="showAdd()">添加</button>
		</div>
	</div>

	<!-- 弹出窗体 -->
	<div data-dojo-type="dijit/Dialog" data-dojo-id="myDialog" title="添加管理员">
		<div data-dojo-type="dijit/form/Form" id="myForm" data-dojo-id="myForm" encType="multipart/form-data" action="" method="post">
			<table class="dijitDialogPaneContentArea"  cellspacing="5">
				<tr>
					<td><label for="name">用户名:</label></td>
					<td><input data-dojo-type="dijit/form/ValidationTextBox" required="true" name="userName" id="userName" trim="true"></td>
				</tr>
				<tr>
					<td><label for="address">密码:</label></td>
					<td><input data-dojo-type="dijit/form/ValidationTextBox" type="password" required="true" name="password" id="password" trim="true"></td>
				</tr>
				<tr>
					<td><label for="address">确认密码:</label></td>
					<td><input data-dojo-type="dijit/form/ValidationTextBox" type="password" required="true" name="password1" id="password1" trim="true"></td>
				</tr>
			</table>
			<div class="dijitDialogPaneActionBar">
				<!-- 这里的按钮不能是type='submit'  -->
				<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
				<button data-dojo-type="dijit/form/Button" type="button" data-dojo-props="onClick:function(){myDialog.hide();}" id="cancel">取消</button>
			</div>
		</div>
	</div>

	<!-- 弹出窗体编辑 -->
	<div data-dojo-type="dijit/Dialog" data-dojo-id="editDialog" title="修改密码">
		<div data-dojo-type="dijit/form/Form" id="editForm" data-dojo-id="editForm" encType="multipart/form-data" action="" method="post">
			<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="5">
				<tr>
					<td><label for="address">原始密码:</label></td>
					<td><input data-dojo-type="dijit/form/ValidationTextBox" type="password" required="true" name="password0" id="pwd0" trim="true"></td>
				</tr>
				<tr>
					<td><label for="address">新密码:</label></td>
					<td><input data-dojo-type="dijit/form/ValidationTextBox" type="password" required="true" name="password1" id="pwd1" trim="true"></td>
				</tr>
				<tr>
					<td><label for="address">确认密码:</label></td>
					<td><input data-dojo-type="dijit/form/ValidationTextBox" type="password" required="true" name="password2" id="pwd2" trim="true"></td>
				</tr>
			</table>
			<div class="dijitDialogPaneActionBar">
				<!-- 这里的按钮不能是type='submit'  -->
				<button data-dojo-type="dijit/form/Button" type="button" onclick="save()">确定</button>
				<button data-dojo-type="dijit/form/Button" type="button" data-dojo-props="onClick:function(){editDialog.hide();}">取消</button>
			</div>
		</div>
	</div>
	<%@include file="authorization_management.jsp"%>
</body>
</html>