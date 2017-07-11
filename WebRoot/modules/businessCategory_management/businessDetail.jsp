<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String businessId = request.getParameter("businessId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="Dojo/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="Dojo/dojo/resources/dojo.css">
<link rel="stylesheet" href="Dojo/dojox/grid/resources/Grid.css">
<link rel="stylesheet" href="Dojo/dojox/grid/resources/claroGrid.css">
<style type="text/css">
#grida {
	margin: 0;
	padding: 0;
	border: none;
}
</style>
<script>
	var businessId =
<%=businessId%>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/businessCategory_management/businessDetail.js"></script>
<title>业务明细管理</title>
</head>
<body class="claro">
	<div data-dojo-type="dijit.layout.BorderContainer"
		style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'"
			style="margin: 0px; padding: 0px; border: none;">
			<div dojoType="dijit.TitlePane" title="查询" style="margin: 0px; padding: 0px;">
				<div data-dojo-type="dijit/form/Form" id="myForm" data-dojo-id="myForm" encType="multipart/form-data" action=""
					method="post">

					<label width="90px" style="visibility: hidden;">业务明细名称</label>
					<input data-dojo-id="cbbCategory" id="cbbCategory" style="margin-left: 5px; visibility: hidden;" type="text"
						name="name" required="false" data-dojo-type="dijit/form/FilteringSelect" />
					<span style="text-align: right; float: right;">
						<button data-dojo-type="dijit/form/Button" type="button" onclick="showAdd()">添加</button>
					</span>
				</div>
			</div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida"
			style="border: none; margin: 0; padding: 0; width: 100%;">
			<!-- <div dojoType="dijit.TitlePane" title="菜单列表" style="width: 100%;"> -->
			<table data-dojo-type="dojox/grid/DataGrid" id="grid" rowsPerPage="15" rowSelector="20px" region="center"
				style="height: 100%; width: 240px;">
				<thead>
					<tr>
						<th field="name" width="100px">业务明细名称</th>
						<th field="price" width="100px">现价</th>
						<th field="originalPrice" width="100px">原价</th>
						<th field="unit" width="100px">单位</th>
						<th field="remark" width="300px">备注</th>
						<th field="id" width="80px" formatter="formatterDel">...</th>
						<th field="id" width="80px" formatter="formatterEdit">...</th>
						<th width="auto"></th>
					</tr>
				</thead>
			</table>

			<!-- </div> -->
		</div>
		<!-- 弹出窗体添加 -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="addDialog" title="添加业务明细">
			<div data-dojo-type="dijit/form/Form" id="addForm" data-dojo-id="addForm" encType="multipart/form-data"
				action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
					<tr>
						<td>业务明细名称</td>
						<td>
							<input type="text" name="name" required="true" data-dojo-type="dijit/form/ValidationTextBox" />
						</td>
						<td>现价</td>
						<td>
							<input type="text" name="price" required="true" data-dojo-type="dijit/form/NumberTextBox" />
						</td>
						<td>原价</td>
						<td>
							<input type="text" name="originalPrice" required="true" data-dojo-type="dijit/form/NumberTextBox" />
						</td>
						<td>单位</td>
						<td>
							<input type="text" name="unit" required="true" data-dojo-type="dijit/form/ValidationTextBox" />
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td>
							<input type="text" name="remark" maxlength="100" required="true"
								data-dojo-type="dijit/form/ValidationTextBox" />
						</td>
					</tr>

				</table>
				<div class="dijitDialogPaneActionBar">
					<!-- 这里的按钮不能是type='submit'  -->
					<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						data-dojo-props="onClick:function(){addDialog.hide();}" id="cancel">取消</button>
				</div>
			</div>
		</div>

		<!-- 弹出窗体编辑 -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="editDialog" title="编辑业务明细">
			<div data-dojo-type="dijit/form/Form" id="editForm" data-dojo-id="editForm" encType="multipart/form-data"
				action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
					<tr>
						<td>业务明细名称</td>
						<td>
							<input type="text" name="name" required="true" data-dojo-type="dijit/form/ValidationTextBox" />
						</td>
						<td>现价</td>
						<td>
							<input type="text" name="price" required="true" data-dojo-type="dijit/form/NumberTextBox" />
						</td>
						<td>原价</td>
						<td>
							<input type="text" name="originalPrice" required="true" data-dojo-type="dijit/form/NumberTextBox" />
						</td>
						<td>单位</td>
						<td>
							<input type="text" name="unit" required="true" data-dojo-type="dijit/form/ValidationTextBox" />
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td>
							<input type="text" name="remark" maxlength="100" required="true"
								data-dojo-type="dijit/form/ValidationTextBox" />
						</td>
					</tr>
				</table>
				<div class="dijitDialogPaneActionBar">
					<!-- 这里的按钮不能是type='submit'  -->
					<button data-dojo-type="dijit/form/Button" type="button" onclick="save()">确定</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						data-dojo-props="onClick:function(){editDialog.hide();}">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>