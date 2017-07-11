<%@page import="com.housekeeping.mobile.FormatUtil"%>
<%@page import="com.housekeeping.entity.User"%>
<%@page import="com.housekeeping.service.impl.UserService"%>
<%@page import="com.housekeeping.service.IUserService"%>
<%@page import="com.housekeeping.service.impl.EmployeeService"%>
<%@page import="com.housekeeping.entity.Order"%>
<%@page import="com.housekeeping.service.impl.OrderService"%>
<%@page import="com.housekeeping.service.IOrderService"%>
<%@page import="com.housekeeping.utils.StringUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.housekeeping.entity.Employee"%>
<%@page import="com.housekeeping.entity.wrap.Employees"%>
<%@page import="com.housekeeping.service.IEmployeeService"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	// 获取传递过来的订单编号
	String orderNumber = request.getParameter("orderNumber");
	//orderNumber = "201418291118464";
	if (StringUtil.strIsEmpty(orderNumber)) {
		return;
	}
	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	IOrderService orderService = ctx.getBean(OrderService.class);
	IEmployeeService employeeService = ctx.getBean(EmployeeService.class);
	IUserService userService = ctx.getBean(UserService.class);
	// 查询订单
	Order order = orderService.getOrder(orderNumber);
	pageContext.setAttribute("order", order);

	// 查询订单的雇员
	Employees employees = employeeService.getEmployeeByOrderNumber(orderNumber);
	List<Employee> employeeList = null;
	if (employees != null && employees.getEmployees() != null) {
		employeeList = employees.getEmployees();
	}
	pageContext.setAttribute("employees", employeeList);

	// 查询订单的用户信息
	user = userService.getUser(order.getUserId(), "");
	pageContext.setAttribute("user", user);
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div data-role="page" id="logopage">
    <script type="text/javascript" src="/Housekeeping/mobile/housekeeping/bj_order_info.js"></script>
    <div data-role="header" data-theme="b">
      <h1>订单详情</h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div class="ui-content" role="main">
      <ul data-role="listview" data-split-icon="gear" data-theme="c" data-inset="false">
        <li>
          <h2>
            <span><span>订单信息 </span><span class="li_right">订单金额<span class="yellow">${order.orderPrice }元</span></span></span>
          </h2>
        </li>
        <li>
          <h2></h2>
          <p>
            工作内容 <span class="li_right">${order.businessCategoryName }</span>
          </p>
          <p>
            订单状态 <span class="li_right"><%=FormatUtil.getOrderState(order.getOrderState())%></span>
          </p>
          <p>
            订单编号<span class="li_right">${order.orderNumber }</span>
          </p>
          <p>
            下单时间<span class="li_right">${order.orderTime }</span>
          </p>
          <p>
            服务时间<span class="li_right">${order.startTime }</span>
          </p>
          <p>
            支付方式<span class="li_right"><%=FormatUtil.getPaywayState(order.getPayway())%></span>
          </p>
        </li>
        <h2>雇员详情</h2>
        <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
          <c:forEach var="emp" items="${employees }">
            <li>
              <a href="/Housekeeping/mobile/housekeeping/bj_show.jsp?employeeId=${emp.id }&pageType=read"
                data-ajax="true">
                <img src="<c:out value="${emp.photo }" default="/Housekeeping/mobile/images/user_logo.png" />"
                  style="width: 69px; height: 80px;" class="listview_li_img_l">
                <h2>
                  <c:out value="${emp.name }" default="" />
                </h2>
                <p>
                  <span class="star" data-rating="${emp.grade }" style="width: 50px;"></span>
                </p>
                <p>
                  <span>${emp.age }岁&nbsp;<c:out value="${emp.nativeSlace }" default="" /></span>
                </p>
                <span class="ui-li-count"
                  style="border: none; background: none; font-weight: 500; font-size: 12px; margin-top: -23px; margin-right: -20px;">服务价格：30元/时<br />
                  服务次数：${emp.serviceTimes }次<br /> 距离：3km
                </span>
              </a>
            </li>
          </c:forEach>
        </ul>
        <li>
          <h2>客户信息</h2>
          <p>
            客户姓名<span class="li_right"><c:out value="${order.userName }" default="" /></span>
          </p>
          <p>
            联系电话<span class="li_right"><c:out value="${order.phoneNumber }" default="" /></span>
          </p>
          <p>
            服务地址<span class="li_right"><c:out value="${order.address }" default="" /></span>
          </p>
          <p>
            特殊要求<span class="li_right"><c:out value="${order.remark }" default="" /></span>
          </p>
        </li>
      </ul>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div class="ui-content">
        <table width="100%">
          <tr>
            <td>
              <a href="#" data-role="button" data-theme="d" class="footer_btn_a blue">呼叫客服</a>
            </td>
            <td>
              <a href="#" data-role="button" data-theme="d" class="footer_btn_a blue">取消订单</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>