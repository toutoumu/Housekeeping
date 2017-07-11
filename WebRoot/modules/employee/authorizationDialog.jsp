<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 弹出窗体 -->
<div data-dojo-type="dijit/Dialog" data-dojo-id="authorizationDialog" title="员工业务管理" style="border: none; margin: 0; padding: 0; width: 800px; height: 600px;">
	<div data-dojo-type="dijit.layout.BorderContainer" style="width: 800px; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'leading'" style="width: 50%; height: 100%; border: none; margin: 0; padding: 0;">
			<div data-dojo-type="dijit/TitlePane" title="已经拥有的业务" style="height: 100%; border: none; margin: 0; padding: 0;">
				<table dojoType="dojox.grid.DataGrid" id="authorizationList" data-dojo-id="authorizationList" rowsPerPage="10" rowSelector="20px" region="center"
					style="height: 100%; width: 100%; margin: 0; padding: 0;">
					<thead>
						<tr>
							<th field="id" width="100px">类别编号</th>
							<th field="name" width="100px">类别名称</th>
							<th field="id" width="100px" formatter="unAuthorizationFormatter">...</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" style="width: 50%; height: 100%; border: none; margin: 0; padding: 0;">
			<div data-dojo-type="dijit/TitlePane" title="还没拥有的业务" style="height: 100%; border: none; margin: 0; padding: 0;">
				<table data-dojo-type="dojox/grid/DataGrid" id="unAuthorizationList" data-dojo-id="unAuthorizationList" rowsPerPage="10" rowSelector="20px" region="center"
					style="height: 100%; width: 100%; margin: 0; padding: 0;">
					<thead>
						<tr>
							<th field="id" width="100px">类别编号</th>
							<th field="name" width="100px">类别名称</th>
							<th field="id" width="100px" formatter="authorizationFormatter">...</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>