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
<script src="Dojo/extends/common/hashTable.js"></script>
<script src="modules/article_managerment/article.js"></script>
<title>文章管理</title>
</head>
<body class="claro">
	<div data-dojo-type="dijit.layout.BorderContainer"
		style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'"
			style="margin: 0px; padding: 0px; border: none;">
			<div dojoType="dijit.TitlePane" title="查询" style="margin: 0px; padding: 0px;">
				<div data-dojo-type="dijit/form/Form" id="myForm" style="margin: 0px; padding: 0px;" data-dojo-id="myForm"
					encType="multipart/form-data" action="" method="post">

					<table style="border: 0px solid #9f9f9f;" cellspacing="5" cellpadding="5">
						<tr>
							<td>文章类别</td>
							<!-- 这里的name对应你需要添加的Java对象对应的表中的字段名称,区分大小写 required="true"表示必填 -->
							<td>
								<input type="text" data-dojo-id="cbbCategory" data-dojo-type="dijit/form/FilteringSelect" required="true"
									name="categoryId" trim="true">
							</td>
							<td>文章标题</td>
							<td>
								<input type="text" name="title" required="false" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
							</td>
						</tr>
					</table>

					<div style="text-align: right;">
						<button data-dojo-type="dijit/form/Button" type="button" name="submitButton" value="Submit" onclick="query()">查询</button>
						<button data-dojo-type="dijit/form/Button" type="button" onclick="showAdd()">添加</button>
						<button data-dojo-type="dijit/form/Button" type="reset">重置</button>
					</div>
				</div>
			</div>
		</div>

		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida"
			style="border: none; margin: 0; padding: 0; width: 100%;">
			<!-- <div dojoType="dijit.TitlePane" title="菜单列表" style="width: 100%;"> -->
			<!-- 网格列表,如果只有id那么必须使用dijit.byId('').xxx获取如果有data-dojo-id="grid" 那么可以直接使用grid.xxx -->
			<table data-dojo-type="dojox/grid/DataGrid" id="grid" rowsPerPage="15" rowSelector="20px" region="center"
				style="height: 100%; width: 240px;">
				<thead>
					<tr>
						<th field="categoryId" width="100px" formatter="decode">文章类别名称</th>
						<!-- <th field="categoryId" width="100px" cellType="dojox.grid.cells.Select" options="A,B,C" values="0,1,2" editable="true">文章类别名称</th> -->
						<th field="title" width="150px">标题</th>
						<th field="createTime" width="150px">创建时间</th>
						<th field="updateTime" width="150px">最后修改时间</th>
						<th field="id" width="80px" formatter="formatterDel">...</th>
						<th field="id" width="80px" formatter="formatterEdit">...</th>
						<th field="id" width="80px" formatter="formatterPush">...</th>
					</tr>
				</thead>
			</table>

			<!-- </div> -->
		</div>
		<!-- 弹出窗体添加data-dojo-id 可以根据这个直接调用它的方法如addDialog.show() -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="addDialog" title="添加文章">
			<div data-dojo-type="dijit/form/Form" id="addForm" data-dojo-id="addForm" encType="multipart/form-data"
				action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
					<tr>
						<td>
							<label for="name">文章类别</label>
						</td>
						<td>
							<input type="text" data-dojo-id="cbbCategory1" data-dojo-type="dijit/form/FilteringSelect" required="true"
								name="categoryId" trim="true">
						</td>
						<td>
							<label for="name">文章标题</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<!-- <input type="text" name="content" data-dojo-type="dijit/form/Textarea" required="true" trim="true" /> -->
							<div data-dojo-type="dijit.Editor" data-dojo-id="addContent" required="true"
								data-dojo-props="extraPlugins:['foreColor','hiliteColor','|','createLink','insertImage','fullscreen','viewsource','newpage']"></div>
						<td>
					</tr>

				</table>
				<div class="dijitDialogPaneActionBar">
					<!-- 这里的按钮不能是type='submit'  -->
					<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						data-dojo-props="onClick:function(){addDialog.hide();}">取消</button>
				</div>
			</div>
		</div>

		<!-- 弹出窗体编辑 -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="editDialog" title="文章编辑">
			<div data-dojo-type="dijit/form/Form" id="editForm" data-dojo-id="editForm" encType="multipart/form-data"
				action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
					<tr>
						<td>
							<label for="name">文章类别</label>
						</td>
						<td>
							<input type="text" data-dojo-id="cbbCategory2" data-dojo-type="dijit/form/FilteringSelect" required="true"
								name="categoryId">
						</td>
						<td>
							<label for="name">文章标题</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<!-- <input type="text" name="content" data-dojo-type="dijit/form/Textarea" required="true" trim="true" /> -->
							<div data-dojo-type="dijit.Editor" data-dojo-id="editContent" required="true"
								data-dojo-props="extraPlugins:['foreColor','hiliteColor','|','createLink','insertImage','fullscreen','viewsource','newpage']"></div>
						<td>
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