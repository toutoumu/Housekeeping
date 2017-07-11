<%@page import="com.housekeeping.entity.User"%>
<%@page import="com.housekeeping.entity.Order"%>
<%@page import="com.housekeeping.service.impl.OrderService"%>
<%@page import="com.housekeeping.service.IOrderService"%>
<%@page import="com.housekeeping.utils.StringUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.housekeeping.service.IEmployeeService"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	// 验证用户是否登录
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect(basePath + "mobile/login/login.jsp");
		return;
	}
	// 订单编号
	String orderNumber = request.getParameter("orderNumber");

	if (orderNumber == null || StringUtil.strIsEmpty(orderNumber)) {
		System.out.print("订单编号不能为空");
		return;
	}

	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	IOrderService orderService = ctx.getBean(OrderService.class);
	Order order = orderService.getOrder(orderNumber);
	pageContext.setAttribute("order", order);
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div data-role="page" id="logopage">
    <div data-role="header" data-theme="b">
      <h1>订单结果</h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div class="ui-content" role="main">
      <div style="text-align: center">
        <h4>
          <img src="/Housekeeping/mobile/images/btn_success.png" style="vertical-align: middle" />
          您的订单已经成功提交
        </h4>
        <p style="font-size: 14px">我们会在第一时间处理，稍后通知您预订结果。</p>
      </div>
      <form method="post" action="">
        <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
          <li>
            <a href="/Housekeeping/mobile/movehouse/dj_order_info.jsp?orderNumber=<%=orderNumber%>">
              <h2></h2>
              <p>
                订单项目：
                <c:out value="${order.businessCategoryName }" default="" />
              </p>
              <p>
                订单金额：￥
                <c:out value="${order.orderPrice }" default="0" />
              </p>
              <p>
                服务时间：
                <fmt:formatDate value="${order.startTime }" pattern="yyyy-MM-dd HH:mm:ss" />
              </p>
              <p>
                订 单 号：
                <c:out value="${order.orderNumber }" default="0" />
              </p>
            </a>
          </li>
        </ul>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div class="ui-content">
        <table width="100%">
          <tr>
            <td>
              <a href="dj_order_pay.html" data-role="button" data-theme="e" class="footer_btn_a"
                style="color: #fff">支付托管</a>
            </td>
            <td>
              <a href="#" data-role="button" data-theme="e" class="footer_btn_a" style="color: #fff">上门收款</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>