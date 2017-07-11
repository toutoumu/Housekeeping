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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<script src="Dojo/extends/common/hashTable.js"></script>
<script src="modules/menu_management/menu.js"></script>
<title>Insert title here</title>
</head>
<body class="claro">
	<div data-dojo-type="dijit.layout.BorderContainer" style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'" style="margin: 0px; padding: 0px; border: none;">
			<div dojoType="dijit.TitlePane" title="菜单管理" style="margin: 0px; padding: 0px;">
				<div data-dojo-type="dijit/form/Form" id="myForm"  style="margin: 0px; padding: 0px;" data-dojo-id="myForm" encType="multipart/form-data" action="" method="post">

					<table style="border: 0px solid #9f9f9f;" cellspacing="5">
						<tr>
							<td width="90px">标题</td>
							<td width="200px"><input type="text" name="title" required="false" data-dojo-type="dijit/form/ValidationTextBox" /></td>

							<td width="90px">类型</td>

							<td width="200px"><input type="text" readonly="readonly" value="2" name="type" required="false" data-dojo-id="cbbType" data-dojo-type="dijit/form/FilteringSelect" /></td>
							<td></td>
						</tr>

						<tr>
							<td width="90px">URL</td>
							<td width="200px"><input type="text" name="url" required="false" data-dojo-type="dijit/form/ValidationTextBox" /></td>

							<td width="90px">父节点</td>
							<td width="200px"><input type="text" name="parent" readonly="readonly" value="1" required="false" data-dojo-type="dijit/form/NumberTextBox" /></td>
						</tr>
 					</table>

					<div style="text-align: right;">
						<button data-dojo-type="dijit/form/Button" type="button" name="submitButton" value="Submit" onclick="query()">查询</button>
						<button data-dojo-type="dijit/form/Button" type="button" onclick="showAdd()">添加</button>
						<!-- <button data-dojo-type="dijit/form/Button" type="reset">重置</button> -->
					</div>
				</div>
			</div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida" style="border: none; margin: 0; padding: 0; width: 100%;">
			<!-- <div dojoType="dijit.TitlePane" title="菜单列表" style="width: 100%;"> -->
			<table data-dojo-type="dojox/grid/DataGrid" id="grid" rowsPerPage="15" rowSelector="0px" region="center" style="height: 100%; width: 240px;">
				<thead>
					<tr>
						<th field="title" width="100px">标题</th>
						<th field="url" width="480px">URL</th>
						<th field="type" width="80px" formatter="decode">菜单类型</th>
						<th field="id" width="60px" formatter="formatter">...</th>
						<th width="auto"></th>
					</tr>
				</thead>
			</table>

			<!-- </div> -->
		</div>
		<!-- 弹出窗体 -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="myDialog" title="添加菜单">
			<div data-dojo-type="dijit/form/Form" id="addForm" data-dojo-id="addForm" encType="multipart/form-data" action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="5">
					<tr>
						<td>标题</td>
						<td><input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" /></td>

						<td>类型</td>
						<td><input type="text" readonly="readonly" value="2" name="type" required="false" data-dojo-id="cbbType1" data-dojo-type="dijit/form/FilteringSelect" /></td>
					</tr>

					<tr>
						<td>URL</td>
						<td><input type="text" name="url" required="true" data-dojo-type="dijit/form/ValidationTextBox" /></td>

						<td>父节点</td>
						<td><input type="text" name="parent" required="true" readonly="readonly" value="1" data-dojo-type="dijit/form/NumberTextBox" /></td>
					</tr>

				</table>
				<div class="dijitDialogPaneActionBar">
					<!-- 这里的按钮不能是type='submit'  -->
					<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
					<button data-dojo-type="dijit/form/Button" type="button" data-dojo-props="onClick:function(){myDialog.hide();}" id="cancel">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>