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
<script src="modules/employee/comment.js"></script>

<title>雇员信息</title>
</head>
<body class="claro">
  <div dojoType="dijit.TitlePane" title="雇员信息" style="margin: 0px; padding: 0px;">
    <div data-dojo-type="dijit/form/Form" id="editForm" data-dojo-id="editForm" encType="multipart/form-data"
      action="" method="post">
      <table style="border: none; width: 100%;" cellspacing="5">
        <tr>
          <td>
            <label for="name">雇员姓名</label>
          </td>
          <td>
            <input type="text" name="name" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
          <td>
            <label for="name">推荐值</label>
          </td>
          <td>
            <input type="text" name="rank" required="true" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
          <td rowspan="9" style="text-align: center; width: 200px;">
            <div style="text-align: center; width: 200px;">
              <img id="imgPhoto" alt="照片" width="200px" style="text-align: center;" />
            </div>
            <button data-dojo-type="dijit/form/Button" style="text-align: center;" type="button"
              onclick="showUpload()">选择头像</button>
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">身份证号码</label>
          </td>
          <td>
            <input type="text" name="idCard" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
          <td>
            <label for="name">手机号码</label>
          </td>
          <td>
            <input type="text" name="phoneNumber" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">雇员坐标</label>
          </td>
          <td>
            <input type="text" name="coordinat" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
          <td>
            <label for="name">工作经验</label>
          </td>
          <td>
            <input type="text" name="workExperience" required="true" data-dojo-type="dijit/form/NumberTextBox"
              trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">雇员年龄</label>
          </td>
          <td>
            <input type="text" name="age" required="true" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>
          <td>
            <label for="name">雇员特长</label>
          </td>
          <td>
            <input type="text" name="speciality" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
        </tr>


        <tr>
          <td>
            <label for="name">证件种类</label>
          </td>
          <td>
            <input type="text" name="credentialsCategory" required="true"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="name">证件号码</label>
          </td>
          <td>
            <input type="text" name="credentialsNumber" required="true"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">服务区域</label>
          </td>
          <td>
            <input type="text" name="serviceArea" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
          <td>
            <label for="name">籍贯</label>
          </td>
          <td>
            <input type="text" name="nativeSlace" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">亲友姓名</label>
          </td>
          <td>
            <input type="text" name="relationName" required="true" data-dojo-type="dijit/form/ValidationTextBox"
              trim="true" />
          </td>
          <td>
            <label for="name">亲友电话</label>
          </td>
          <td>
            <input type="text" name="relationPhoneNumber" required="true"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">雇员照片</label>
          </td>
          <td>
            <input type="text" data-dojo-id="photoSrc" name="photo" required="true" readonly="readonly"
              data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
          </td>
          <td>
            <label for="name">雇员状态</label>
          </td>
          <td>
            <input type="text" data-dojo-id="cbbState" name="state" required="true"
              data-dojo-type="dijit/form/FilteringSelect" trim="true" />
          </td>
        </tr>
        <tr>
          <td>
            <label for="name">星星评分</label>
          </td>
          <td>
            <input type="text" name="grade" required="true" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
          </td>

          <td>
            <label for="name">备注</label>
          </td>
          <td>
            <input maxlength="100" type="text" name="remark" data-dojo-type="dijit/form/Textarea" required="true"
              trim="true" />
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
    <div dojoType="dijit.TitlePane" title="雇员评论信息" style="margin: 0px; padding: 0px;">
      <div id="comment">
        <%-- <c:forEach var="comment" items="${comments }">
					<div>
						<div>
							<span>${comment.userId }</span>
							<span>评分:${comment.grade }</span>
							<span>${comment.content }</span>
						</div>
						<div>${comment.commentTime }</div>
						<div>
							<a href="#" onclick="return deleteComment(${comment.id})">删除</a>
						</div>
					</div>
					<hr />
				</c:forEach> --%>
      </div>
    </div>

    <div dojoType="dijit.TitlePane" title="雇员订单列表" style="margin: 0px; padding: 0px;">
      <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" id="grida"
        style="border: none; margin: 0; padding: 0; width: 100%;">
        <table data-dojo-type="dojox/grid/DataGrid" data-dojo-id="gridOrder" id="gridOrder" rowsPerPage="15"
          rowSelector="20px" region="center" style="height: 200px; width: 240px;">
          <thead>
            <tr>
              <th field="orderNumber" width="100px">订单编号</th>
              <th field="businessCategoryName" width="100px">服务分类名称</th>
              <th field="orderTime" width="100px">下单时间</th>
              <th field="startTime" width="100px">服务开始时间</th>
              <th field="workingTime" width="65px">工作时长</th>
              <!-- <th field="userId" width="100px">用户id</th> -->
              <th field="userName" width="100px">用户名</th>
              <th field="phoneNumber" width="80px">客户手机</th>
              <th field="orderPrice" width="65px">订单金额</th>
              <!-- <th field="payway" width="80px">支付方式</th>
							<th field="paymentState" width="80px">支付状态</th>
							<th field="orderState" width="80px">订单状态</th> -->

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
  <div data-dojo-type="dijit/Dialog" data-dojo-id="uploadDialog" title="修改头像">
    <div data-dojo-type="dijit/form/Form" id="uploadForm" data-dojo-id="uploadForm" encType="multipart/form-data"
      action="" method="post">
      <div>
        选择图片
        <input type="file" name="file1" id="file" required="true" />
      </div>

      <div class="dijitDialogPaneActionBar">
        <!-- 这里的按钮不能是type='submit'  -->
        <button data-dojo-type="dijit/form/Button" type="button" onclick="upload()">上传</button>
        <button data-dojo-type="dijit/form/Button" type="button"
          data-dojo-props="onClick:function(){uploadDialog.hide();}">取消</button>
      </div>
    </div>
  </div>

</body>
</html>