<%@page import="com.housekeeping.entity.Comment"%>
<%@page import="com.housekeeping.entity.Employee"%>
<%@page import="com.housekeeping.service.impl.CommentService"%>
<%@page import="com.housekeeping.service.ICommentService"%>
<%@page import="com.housekeeping.service.impl.EmployeeService"%>
<%@page import="com.housekeeping.service.IEmployeeService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	/* IEmployeeService employeeService;
	ICommentService commentService;
	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	employeeService = ctx.getBean(EmployeeService.class);
	commentService = ctx.getBean(CommentService.class);
	
	Employee employee = employeeService.getEmployee(Integer.parseInt(id));
	pageContext.setAttribute("employee", employee);
	List<Comment> comments=null;
	if(employee!=null){
		comments = commentService.getCommentByEmployeeId(Integer.parseInt(id)).getComments();
		pageContext.setAttribute("comments", comments);
	} */
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
	var id =
<%=id%>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="Dojo/dojo/dojo.js"></script>
<script src="modules/user_management/userDetail.js"></script>

<title>会员信息</title>
</head>
<body class="claro">
  <div dojoType="dijit.TitlePane" title="会员信息" style="margin: 0px; padding: 0px;">
    <div data-dojo-type="dijit/form/Form" id="editForm" data-dojo-id="editForm" encType="multipart/form-data"
      action="" method="post">
      <table style="border: none; width: 100%;" cellspacing="5">
        <tr>
          <td>
            <label for="userName">会员姓名</label>
          </td>
          <td>
            <input type="text" name="userName" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="grade">等级</label>
          </td>
          <td>
            <input type="text" name="grade" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="city">城市</label>
          </td>
          <td>
            <input type="text" name="city" readonly data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="balance">余额</label>
          </td>
          <td>
            <input type="text" name="balance" readonly data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="phoneNumber">手机号码</label>
          </td>
          <td>
            <input type="text" name="phoneNumber" required="true" readonly
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="consumeTimes">消费次数</label>
          </td>
          <td>
            <input type="text" name="consumeTimes" readonly data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="createTime">创建日期</label>
          </td>
          <td>
            <input type="text" name="createTime" readonly data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="score">积分</label>
          </td>
          <td>
            <input type="text" name="score" readonly data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
        </tr>
      </table>
      <div class="dijitDialogPaneActionBar">
        <!-- 这里的按钮不能是type='submit'  -->
        <button data-dojo-type="dijit/form/Button" type="button" onclick="save()">保存</button>
      </div>
    </div>
  </div>
  <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'"
    style="margin: 0px; padding: 0px; border: none;">
    <!-- <div dojoType="dijit.TitlePane" title="支出明细列表" id="gridb" style="border: none; margin: 0; padding: 0; width: 100%;"> -->
    <div dojoType="dijit.TitlePane" title="支出明细列表" style="margin: 0px; padding: 0px;">
      <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'north'" id="gridb"
        style="border: none; margin: 0; padding: 0; width: 100%;">
        <table data-dojo-type="dojox/grid/DataGrid" data-dojo-id="gridAccount" id="gridAccount" rowsPerPage="15"
          rowSelector="20px" region="center" style="height: 200px; width: 240px;">
          <thead>
            <tr>
              <th field="orderTime" width="100px">时间</th>
              <th field="businessCategoryName" width="100px">项目内容</th>
              <th field="orderPrice" width="100px">金额</th>
            </tr>
          </thead>
        </table>
      </div>
    </div>
    <div dojoType="dijit.TitlePane" title="订单列表" style="margin: 0px; padding: 0px;">
      <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida"
        style="border: none; margin: 0; padding: 0; width: 100%;">
        <table data-dojo-type="dojox/grid/DataGrid" data-dojo-id="gridOrder" id="gridOrder" rowsPerPage="15"
          rowSelector="20px" region="center" style="height: 200px; width: 240px;">
          <thead>
            <tr>
              <!-- <th field="orderNumber" width="100px">订单编号</th>
							<th field="businessCategoryName" width="100px">服务分类名称</th>
							<th field="orderTime" width="100px">下单时间</th>
							<th field="startTime" width="100px">服务开始时间</th>
							<th field="workingTime" width="65px">工作时长</th>
							<th field="userId" width="100px">用户id</th>
							<th field="userName" width="100px">用户名</th>
							<th field="phoneNumber" width="80px">客户手机</th>
							<th field="orderPrice" width="65px">订单金额</th>
							<th field="payway" width="80px">支付方式</th>
							<th field="paymentState" width="80px">支付状态</th>
							<th field="orderState" width="80px">订单状态</th> -->
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
              <th field="orderNumber" width="60px" width="auto" formatter="formatterDetail">...</th>
            </tr>
          </thead>
        </table>
      </div>
    </div>
  </div>
</body>
</html>