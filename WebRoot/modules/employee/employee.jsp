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
<script src="modules/employee/employee.js"></script>
<title>雇员管理</title>
</head>
<body class="claro">
	<div data-dojo-type="dijit.layout.BorderContainer"
		style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'"
			style="margin: 0px; padding: 0px; border: none;">
			<div dojoType="dijit.TitlePane" title="查询" style="margin: 0px; padding: 0px;">
				<div data-dojo-type="dijit/form/Form" id="myForm" data-dojo-id="myForm" encType="multipart/form-data" action=""
					method="post">
					<table cellspacing="5">
						<tr>
							<td>雇员类别</td>
							<td>
								<input type="text" data-dojo-id="cbbCategory" data-dojo-type="dijit/form/FilteringSelect" required="false"
									name="businessCategoryId" trim="true">
							</td>
							<td>雇员姓名</td>
							<td>
								<input type="text" name="name" required="false" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
							</td>

							<td>籍贯</td>
							<td>
								<input type="text" name="nativeSlace" required="false" data-dojo-type="dijit/form/ValidationTextBox"
									trim="true" />
							</td>

						</tr>

						<tr>
							<td>
								<label for="name">雇员状态</label>
							</td>
							<td>
								<input type="text" data-dojo-id="cbbState" required="false" name="state"
									data-dojo-type="dijit/form/FilteringSelect" trim="true" />

							</td>
							<td>身份证</td>
							<td>
								<input type="text" name="idCard" required="false" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
							</td>
							<td>电话号码</td>
							<td>
								<input type="text" name="phoneNumber" required="false" data-dojo-type="dijit/form/ValidationTextBox"
									trim="true" />
							</td>
						</tr>
						<tr>

							<td>星星评分</td>
							<td>
								<input type="text" name="grade" required="false" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
							</td>
							<td>推荐值</td>
							<td>
								<input type="text" name="rank" required="false" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
							</td>
							<td>服务区域</td>
							<td>
								<input type="text" name="serviceArea" required="false" data-dojo-type="dijit/form/ValidationTextBox"
									trim="true" />
							</td>
						</tr>
						<tr>

							<td>工作经验</td>
							<td>
								<input type="text" name="workExperience" required="false" data-dojo-type="dijit/form/NumberTextBox"
									trim="true" />
							</td>

							<td>相关证件</td>
							<td>
								<input type="text" name="credentialsCategory" required="false"
									data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
							</td>
							<td>证件号码</td>
							<td>
								<input type="text" name="credentialsNumber" required="false" data-dojo-type="dijit/form/ValidationTextBox"
									trim="true" />
							</td>
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
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida"
			style="border: none; margin: 0; padding: 0; width: 100%;">
			<table data-dojo-type="dojox/grid/DataGrid" id="grid" rowsPerPage="15" rowSelector="20px" region="center"
				style="height: 100%; width: 240px;">
				<thead>
					<tr>
						<!-- <th field="id" width="60px">雇员id</th> -->
						<th field="name" width="100px">姓名</th>
						<th field="nativeSlace" width="100px">籍贯</th>
						<th field="idCard" width="150px">身份证</th>
						<th field="phoneNumber" width="100px">手机号码</th>
						<th field="workExperience" width="100px">工作经验</th>
						<th field="serviceTimes" width="80px">服务次数</th>
						<th field="serviceArea" width="50px">服务区域</th>
						<th field="state" width="100px" formatter="formatterState">雇员状态</th>
						<th field="grade" width="100px">星星评分</th>
						<th field="rank" width="50px">推荐值</th>
						<th field="credentialsCategory" width="100px">证件种类</th>
						<th field="credentialsNumber" width="100px">证件号码</th>
						<!-- <th field="coordinat" width="100px">雇员坐标</th> -->
						<th field="id" width="80px" width="auto" formatter="formatterDetial">...</th>
						<th field="id" width="80px" width="auto" formatter="formatter">...</th>
						<th field="id" width="80px" formatter="showAuthorizationFormatter">...</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- 添加雇员弹出窗体 -->
		<%@include file="addDialog.jsp"%>
		<!-- 雇员业务管理弹出窗体 -->
		<%@include file="authorizationDialog.jsp"%>

	</div>
</body>
</html>