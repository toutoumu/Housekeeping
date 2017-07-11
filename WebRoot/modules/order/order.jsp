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
<script src="modules/order/order.js"></script>
<title>文章管理</title>
</head>
<body class="claro">
	<div data-dojo-type="dijit.layout.BorderContainer"
		style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'"
			style="margin: 0px; padding: 0px; border: none;">
			<div dojoType="dijit.TitlePane" title="查询" style="margin: 0px; padding: 0px;">
				<div data-dojo-type="dijit/form/Form" id="myForm" data-dojo-id="myForm" encType="multipart/form-data" action=""
					method="post">
					<table class="dijitDialogPaneContentArea" cellspacing="5">
						<tr>
							<!-- 预定时间(工作时长？) 、可服务项目、下单时间、服务时间 、支付状态、订单状态 -->
							<td>
								<label for="name">服务分类</label>
							</td>
							<td>
								<input type="text" data-dojo-id="cbbCategory" data-dojo-type="dijit/form/FilteringSelect" required="false"
									name="businessCategoryId" trim="true">
							</td>
							<td>
								<label for="name">订单状态</label>
							</td>
							<td>
								<input type="text" data-dojo-id="cbbOrderState" data-dojo-type="dijit/form/FilteringSelect"
									required="false" name="orderState" trim="true">
							</td>
							<td>
								<label for="name">支付状态</label>
							</td>
							<td>
								<input type="text" data-dojo-id="cbbPaymentState" data-dojo-type="dijit/form/FilteringSelect"
									required="false" name="paymentState" trim="true">
							</td>

						</tr>

						<tr>
							<td>
								<label for="name">下单时间</label>
							</td>
							<td>
								<input type="text" data-dojo-type="TimestampTextBox"
									data-dojo-props='type:"text",   
                                             name:"orderTime",   
                                            constraints:{datePattern:"yyyy-MM-dd"},  
                                            required:false'
									dateFormat="yyyy-MM-dd" required="false" name="orderTime" trim="true">
							</td>
							<td>
								<label for="name">服务时间</label>
							</td>
							<td>
								<input type="text" data-dojo-type="TimestampTextBox"
									data-dojo-props='type:"text",   
                                             name:"startTime",   
                                            constraints:{datePattern:"yyyy-MM-dd"},  
                                            required:false'
									required="false" name="startTime" trim="true">
							</td>
							<td>
								<label for="name">工作时长</label>
							</td>
							<td>
								<input type="text" data-dojo-type="dijit/form/NumberTextBox" required="false" name="workingTime"
									trim="true">
							</td>
							<!-- <td><label for="name">服务时间</label></td>
							<td><input type="text" data-dojo-type="TimestampTextBox"
									data-dojo-props='type:"text",   
                                             name:"asd",   
                                            constraints:{datePattern:"yyyy-MM-dd HH:mm:ss"},  
                                            required:false,  
                                            value:new Date()'></td> -->
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
					</div>
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
						<th field="orderNumber" width="100px">订单编号</th>
						<th field="businessCategoryName" width="100px">服务分类名称</th>
						<th field="orderTime" width="100px">下单时间</th>
						<th field="startTime" width="100px">服务开始时间</th>
						<th field="workingTime" width="80px">工作时长</th>
						<th field="address" width="180px">服务地址</th>
						<th field="employeeName" width="100px">服务人员</th>
						<th field="employeePhoneNumber" width="120px">服务人员电话</th>
						<th field="userName" width="80px">用户名</th>
						<th field="phoneNumber" width="100px">客户手机</th>
						<th field="orderPrice" width="80px">订单金额</th>
						<th field="payway" width="80px" formatter="formatterPayway">支付方式</th>
						<th field="paymentState" width="80px" formatter="formatterPaymentState">支付状态</th>
						<th field="orderState" width="80px" formatter="formatterOrderState">订单状态</th>
						<th field="orderNumber" width="60px" width="auto" formatter="formatterDel">...</th>
						<th field="orderNumber" width="60px" width="auto" formatter="formatterDetail">...</th>
						<!-- <th field="orderNumber" width="60px" width="auto" formatter="formatterOper">...</th> -->
					</tr>
				</thead>
			</table>

			<!-- </div> -->
		</div>
		<!-- 弹出窗体添加雇员 -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="myDialog" title="添加雇员">
			<div data-dojo-type="dijit/form/Form" id="addForm" data-dojo-id="addForm" encType="multipart/form-data"
				action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
					<tr>
						<td>
							<label for="name">雇员类别</label>
						</td>
						<td>
							<input type="text" data-dojo-id="cbbCategory1" data-dojo-type="dijit/form/FilteringSelect" required="true"
								name="categoryId" trim="true">
						</td>
						<td>
							<label for="name">雇员姓名</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="name">身份证号码</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
						<td>
							<label for="name">手机号码</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="name">雇员坐标</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
						<td>
							<label for="name">工作经验</label>
						</td>
						<td>
							<input type="text" name="title" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<input type="text" name="content" data-dojo-type="dijit/form/Textarea" required="true" trim="true" />
						<td>
					</tr>

				</table>
				<div class="dijitDialogPaneActionBar">
					<!-- 这里的按钮不能是type='submit'  -->
					<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						data-dojo-props="onClick:function(){myDialog.hide();}" id="cancel">取消</button>

				</div>
			</div>
		</div>
		<div data-dojo-type="dijit/Dialog" data-dojo-id="operDialog" title="订单处理">
			<div data-dojo-type="dijit/form/Form" id="operForm" data-dojo-id="operForm" encType="multipart/form-data"
				action="" method="post">
				<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
					<tr>
						<td>处理结果</td>
						<td>
							<input type="text" data-dojo-id="operState" data-dojo-type="dijit/form/FilteringSelect" required="false"
								name="orderState" trim="true">
						</td>
					</tr>
				</table>

				<div class="dijitDialogPaneActionBar">
					<!-- 这里的按钮不能是type='submit'  -->
					<button data-dojo-type="dijit/form/Button" type="button" onclick="oper()">确定</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						data-dojo-props="onClick:function(){operDialog.hide();}">取消</button>
				</div>
			</div>
		</div>
</body>
</html>