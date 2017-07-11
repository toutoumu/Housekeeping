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
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/user_management/user.js"></script>
<title>文章管理</title>
</head>
<body class="claro">
	<div data-dojo-type="dijit.layout.BorderContainer" style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'" style="margin: 0px; padding: 0px; border: none;">
			<div dojoType="dijit.TitlePane" title="查询" style="margin: 0px; padding: 0px;">
				<div data-dojo-type="dijit/form/Form" id="myForm" data-dojo-id="myForm" encType="multipart/form-data" action="" method="post">
					<table class="dijitDialogPaneContentArea">
						<tr>
							<td><label for="id">序号</label></td>
							<td><input type="text" data-dojo-type="dijit/form/ValidationTextBox" required="false" name="id" trim="true"></td>
							<td><label for="userName">会员名称</label></td>
							<td><input type="text" data-dojo-type=dijit/form/ValidationTextBox required="false" name="userName" trim="true"></td>
							<!-- <td><label for="address">会员地址</label></td>
							<td><input type="text" name="address" required="false" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td> -->
							<td><label for="phoneNumber">会员手机号</label></td>
							<td><input type="text" name="phoneNumber" required="false" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
						</tr>
					</table>

					<div style="text-align: right;">
						<table cellspacing="5" style="float: left;">
							<tr>
								<td>搜索条件</td>
								<td>
									<input type="text" data-dojo-id="searchContent" required="false"
										data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
								</td>
								<td>
									<button data-dojo-type="dijit/form/Button" type="button" name="submitButton" value="Submit"
										onclick="search()">搜索</button>
								</td>
							</tr>
						</table>
						<button data-dojo-type="dijit/form/Button" type="button" name="submitButton" value="Submit" onclick="query()">查询</button>
						<button data-dojo-type="dijit/form/Button" type="button" onclick="showAdd()">添加</button>
						<button data-dojo-type="dijit/form/Button" type="reset">重置</button>
					</div>
				</div>
			</div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida" style="border: none; margin: 0; padding: 0; width: 100%;">
			<table data-dojo-type="dojox/grid/DataGrid" id="grid" rowsPerPage="15" rowSelector="20px" region="center" style="height: 100%; width: 240px;">
				<thead>
					<tr>
						<th field="id" width="50px">序号</th>
						<th field="userName" width="100px">会员名称</th>
						<th field="phoneNumber" width="100px">手机号码</th>
						<th field="grade" width="100px">等级</th>
						<th field="consumeTimes" width="100px">消费次数</th>
						<th field="balance" width="100px">余额</th>
						<th field="id" width="100px" width="auto" formatter="formatterDetial">...</th>
						<th field="id" width="100px" width="auto" formatter="formatterDel">...</th>
						<!-- <th field="id" width="100px" formatter=formatterEdit>...</th> -->
					</tr>
				</thead>
			</table>
		</div>
		<!-- 添加会员弹出窗体 -->
		<%@include file="addDialog.jsp"%>
	</div>
</body>
</html>