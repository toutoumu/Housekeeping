<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String orderNumber = request.getParameter("orderNumber");
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
	var orderNumber =
<%=orderNumber%>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/order/orderDetail.js"></script>
<title>订单详情</title>
</head>
<body class="claro">
  <div dojoType="dijit.TitlePane" title="订单详情" style="margin: 0px; padding: 0px;">
    <div data-dojo-type="dijit/form/Form" id="editForm" data-dojo-id="editForm" encType="multipart/form-data"
      action="" method="post">
      <table style="border: none; width: 100%;" cellspacing="5">
        <tr>
          <td>
            <label for="name">订单编号</label>
          </td>
          <td>
            <input type="text" name="orderNumber" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <!-- <td>
            <label for="name">雇员电话</label>
          </td>
          <td>
            <input type="text" data-dojo-id="employeePhoneNumber" name="employeePhoneNumber" required="true"
              readonly="readonly" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
            <input type="text" data-dojo-id="employeeId" name="employeeId" required="true" readonly="readonly"
              style="display: none;" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">雇员姓名</label>
          </td>
          <td>
            <input type="text" data-dojo-id="employeeName" name="employeeName" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td> -->
          <td>
            <label for="name">下单时间</label>
          </td>
          <td>
            <input type="text" name="orderTime" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">工作时长</label>
          </td>
          <td>
            <input type="text" name="workingTime" required="true" readonly="readonly"
              data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
          <td>
            <label for="name">服务分类名称</label>
          </td>
          <td>
            <input type="text" name="businessCategoryName" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">服务开始时间</label>
          </td>
          <td>
            <input type="text" name="startTime" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="name">地址</label>
          </td>
          <td>
            <input type="text" name="address" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>


        <tr>
          <td>
            <label for="name">用户名</label>
          </td>
          <td>
            <input type="text" name="userName" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="name">客户手机</label>
          </td>
          <td>
            <input type="text" name="phoneNumber" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">订单金额</label>
          </td>
          <td>
            <input type="text" name="orderPrice" required="true" readonly="readonly"
              data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
          <td>
            <label for="name">订单状态 </label>
          </td>
          <td>
            <input type="text" data-dojo-id="cbbOrderState" data-dojo-type="dijit/form/FilteringSelect"
              required="true" name="orderState" trim="true">
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">支付状态</label>
          </td>
          <td>
            <input type="text" data-dojo-id="cbbPaymentState" data-dojo-type="dijit/form/FilteringSelect"
              required="true" name="paymentState" trim="true">
          </td>
          <!-- <td><label for="name">面积</label></td>
					<td><input type="text" name="area" required="true" readonly="readonly" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
				 -->
          <td>
            <label for="name">支付方式</label>
          </td>
          <td>
            <input type="text" data-dojo-id="cbbPayway" name="payway" required="true"
              data-dojo-type="dijit/form/FilteringSelect" trim="true" />
          </td>

        </tr>
        <tr>
          <td>
            <label for="name">备注</label>
          </td>
          <td colspan="3">
            <input maxlength="100" type="text" name="remark" required="true" readonly="readonly"
              data-dojo-type="dijit/form/Textarea" trim="true" />
          </td>
        </tr>
      </table>
      <div class="dijitDialogPaneActionBar">
        <span id="count"></span>
        <button data-dojo-type="dijit/form/Button" type="button" onclick="showSelectedEmployee()">查看订单雇员</button>
        <button data-dojo-type="dijit/form/Button" type="button" onclick="showSelect()">选择雇员</button>
        <button data-dojo-type="dijit/form/Button" type="button" onclick="save()">保存</button>
      </div>
    </div>
  </div>

  <!-- 订单明细列表 -->
  <div dojoType="dijit.TitlePane" title="订单明细" style="margin: 0px; padding: 0px;">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'"
      style="border: none; margin: 0; padding: 0; width: 100%;">
      <table data-dojo-type="dojox/grid/DataGrid" data-dojo-id="gridOrderDetail" id="gridOrderDetail"
        rowsPerPage="15" rowSelector="20px" region="center" style="height: 200px; width: 240px;">
        <thead>
          <tr>
            <th field="orderNumber" width="150px">订单编号</th>
            <th field="businessDetailName" width="65px">服务名称</th>
            <th field="price" width="100px">价格</th>
            <th field="unit" width="100px">单位</th>
            <th field="count" width="100px">数量</th>
            <!-- <th field="businessId" width="65px">服务名称</th> -->
          </tr>
        </thead>
      </table>
    </div>
  </div>

  <!-- 弹出窗体已经选择的雇员 -->
  <div data-dojo-type="dijit/Dialog" data-dojo-id="selectedDialog" title="已经选择的雇员"
    style="width: 900px; height: 540px;">
    <table data-dojo-type="dojox/grid/DataGrid" id="gridSelected" data-dojo-id="grid" rowsPerPage="15"
      rowSelector="20px" region="center" style="height: 100%; width: 800px;">
      <thead>
        <tr>
          <th field="id" width="50px">雇员id</th>
          <th field="name" width="100px">雇员姓名</th>
          <th field="serviceTimes" width="50px">服务次数</th>
          <!-- <th field="businessCategoryId" width="100px">雇员类别</th> -->
          <th field="phoneNumber" width="100px">手机号码</th>
          <th field="nativeSlace" width="100px">籍贯</th>
          <th field="coordinat" width="100px">雇员坐标</th>
          <th field="workExperience" width="100px">工作经验</th>
          <th field="rank" width="50px">推荐值</th>
          <th field="credentialsCategory" width="100px">证件种类</th>
          <th field="credentialsNumber" width="100px">证件号码</th>
          <th field="id" width="100px" formatter="formatterDel">...</th>
        </tr>
      </thead>
    </table>
  </div>

  <!-- 弹出窗体选择雇员 -->
  <div data-dojo-type="dijit/Dialog" data-dojo-id="selectDialog" title="选择雇员" style="width: 900px; height: 540px;">
    <div data-dojo-type="dijit.layout.BorderContainer"
      style="width: 100%; height: 100%; border: none; margin: 0; padding: 0;">
      <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'"
        style="margin: 0px; padding: 0px; border: none;">
        <div dojoType="dijit.TitlePane" title="查询" style="margin: 0px; padding: 0px; width: 900px;">
          <div data-dojo-type="dijit/form/Form" id="selectForm" data-dojo-id="selectForm"
            encType="multipart/form-data" action="" method="post">
            <table class="dijitDialogPaneContentArea">
              <tr>
                <td>
                  <label for="name">雇员类别</label>
                </td>
                <td>
                  <input type="text" data-dojo-id="cbbCategory" required="true"
                    data-dojo-type="dijit/form/FilteringSelect" required="false" name="businessCategoryId"
                    trim="true">
                </td>
                <td>
                  <button data-dojo-type="dijit/form/Button" type="button" name="submitButton" value="Submit"
                    onclick="query()">查询</button>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>

      <table data-dojo-type="dojox/grid/DataGrid" id="grid" data-dojo-id="grid" rowsPerPage="15" rowSelector="20px"
        region="center" style="height: 320px; width: 100%; margin: 0px; padding: 0px;">
        <thead>
          <tr>
            <th field="id" width="50px">雇员id</th>
            <th field="name" width="100px">雇员姓名</th>
            <th field="serviceTimes" width="50px">服务次数</th>
            <!-- <th field="businessCategoryId" width="100px">雇员类别</th> -->
            <th field="phoneNumber" width="100px">手机号码</th>
            <th field="nativeSlace" width="100px">籍贯</th>
            <th field="coordinat" width="100px">雇员坐标</th>
            <th field="workExperience" width="100px">工作经验</th>
            <th field="rank" width="50px">推荐值</th>
            <th field="credentialsCategory" width="100px">证件种类</th>
            <th field="credentialsNumber" width="100px">证件号码</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</body>
</html>