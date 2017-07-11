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
<script src="Dojo/dojo/dojo.js" type="text/javascript"></script>
<script src="modules/file_upload/fileUpload.js" type="text/javascript"></script>
<title>文件上传</title>
</head>
<body class="claro">
	<div dojoType="dijit.TitlePane" title="图片上传" style="margin: 0px; padding: 0px;">
		<form data-dojo-type="dijit/form/Form" id="uploadForm" enctype="multipart/form-data" action="" method="post">
			<input id="file" type="file" name="file1" />
			<input type="button" onclick="upload()" value="上传" />
		</form>
		<img id="imgPhoto" src="" style="height: 150px;" /> <br />
		<input data-dojo-type="dijit/form/ValidationTextBox" data-dojo-id="path" type="text" readonly="readonly" />
		<button data-dojo-type="dijit/form/Button" style="text-align: center;" type="button" onclick="save()">保存</button>
	</div>
	<div dojoType="dijit.TitlePane" title="图片列表" style="margin: 0px; padding: 0px;">

		<table data-dojo-type="dojox/grid/DataGrid" id="grid" rowsPerPage="15" rowSelector="20px" region="center" style="height: 300px; width: 100%;">
			<thead>
				<tr>
					<th field="url" width="120px" formatter="formatterImage">。。。</th>
					<th field="id" width="auto" width="80" formatter="formatterDel">...</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
