<%@page import="com.housekeeping.service.impl.UserService"%>
<%@page import="com.housekeeping.service.IUserService"%>
<%@page import="com.housekeeping.entity.wrap.Orders"%>
<%@page import="com.housekeeping.service.impl.OrderService"%>
<%@page import="com.housekeeping.service.IOrderService"%>
<%@page import="com.housekeeping.entity.User"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
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

	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	IUserService userService = ctx.getBean(UserService.class);
	IOrderService orderService = ctx.getBean(OrderService.class);

	Orders orders = orderService.getOrderByUserId(user.getId());
	if (orders != null && orders.getOrders() != null) {
		pageContext.setAttribute("orders", orders.getOrders());
	}
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div id="filter"
    style="position: relative; top: 10; left: 0; z-index: 150; width: 100%; height: 100%; background: #000; filter: alpha(opacity = 60); opacity: 0.6; display: none">
  </div>
  <div id="pageone"
    style="width: 90%; height: auto; background: #FFFFFF; position: fixed; z-index: 155; top: 18%; left: 5%; border-radius: 5px; box-shadow: 0 0 0 #DB1F05, 0 9px 25px rgba(0, 0, 0, 0.7); display: none">
    <div style="text-align: center; width: 114%; height: 50px; margin-left: -38px">
      <ul class="navbar_ul1">
        <li id="Li1" onclick="setTab('span',1,3)">
          <a href="#anylink">
            <span id="span1" class="check"> </span>
            <img src="images/p1.png" />
            好评
          </a>
        </li>
        <li id="Li2" onclick="setTab('span',2,3)">
          <a href="#anylink">
            <span id="span2" class="check1"> </span>
            <img src="images/p2.png" />
            中评
          </a>
        </li>
        <li id="Li3" onclick="setTab('span',3,3)" style="border-right: none;">
          <a href="#anylink">
            <span id="span3" class="check1"></span>
            <img src="images/p3.png" />
            差评
          </a>
        </li>
      </ul>
    </div>
    <div style="text-align: center; height: 30px">
      <span class="star"  style="width: 120px"></span>
      <!-- <img src="images/star.png" style="width: 120px" /> -->
    </div>
    <div style="background: #FFFFFF; text-align: center">
      <textarea
        style="width: 90%; border-radius: 5px; min-height: 300px; height: 150px; min-height: 50px; border: 1px solid #CCCCCC;">动作很麻利，清洁的也很干净，很不错的阿姨。</textarea>
    </div>
    <div style="color: #666666; text-align: right; width: 90%; line-height: 35px; font-size: 14px;">
      您还可以再输入80个字</div>
    <div style="color: #fff; text-align: center; height: 55px; line-height: 50px; border-top: 1px solid #CCCCCC;">
      <table width="100%">
        <tr>
          <td class="tck_btn_a">
            <a href="#" data-role="button" class="tck_btn_a" id="a_button_ok">确定</a>
          </td>
          <td class="tck_btn_a" style="border-left: 1px solid #CCCCCC;">
            <a href="#" data-role="button" id="a_button_closed" class="tck_btn_a">取消</a>
          </td>
        </tr>
      </table>
    </div>
  </div>
  <div data-role="page" id="logopage">
  <script type="text/javascript" src="/Housekeeping/mobile/my_order_search.js"></script>
    <div data-role="header" data-theme="b">
      <h1>订单查询</h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div data-role="content">
      <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
        <c:forEach var="order" items="${orders }">
          <li>
            <h2></h2>
            <p>订单项目：${order.businessCategoryName }</p>
            <p>
              订单金额：<span class="yellow">￥${order.orderPrice }</span>
            </p>
            <p>服务时间：${order.startTime }</p>
            <p>订单编号：${order.orderNumber }</p>
            <p>
              <span class="blue">[待确认]</span><span class="li_right"><a href="i" data-ajax="false"
                  data-role="button" data-theme="e" class="middle_btn_a" style="color: #fff;">支付托管</a> <a href=""
                  data-ajax="false" data-role="button" data-theme="e" class="middle_btn_a black5"
                  style="color: #fff;">确认完成</a></span>
            </p>
          </li>
        </c:forEach>
        <li>
          <h2></h2>
          <p>订单项目：家庭保洁</p>
          <p>
            订单金额：<span class="yellow">￥255</span>
          </p>
          <p>服务时间：2014-8-6 16:00</p>
          <p>订单编号：25258888</p>
          <p>
            <span class="blue">[待确认]</span><span class="li_right"><a href="i" data-ajax="false"
                data-role="button" data-theme="e" class="middle_btn_a" style="color: #fff;">支付托管</a> <a href=""
                data-ajax="false" data-role="button" data-theme="e" class="middle_btn_a black5"
                style="color: #fff;">确认完成</a></span>
          </p>
        </li>
        <li>
          <h2></h2>
          <p>订单项目：家庭保洁</p>
          <p>
            订单金额：<span class="yellow">￥255</span>
          </p>
          <p>服务时间：2014-8-6 16:00</p>
          <p>订单编号：25258888</p>
          <p>
            <span class="blue">[已确认]</span><span class="li_right"><a href="" data-ajax="false"
                data-role="button" data-theme="e" class="middle_btn_a black5" style="color: #fff;">确认完成</a></span>
          </p>
        </li>
        <li>
          <h2></h2>
          <p>订单项目：家庭保洁</p>
          <p>
            订单金额：<span class="yellow">￥255</span>
          </p>
          <p>服务时间：2014-8-6 16:00</p>
          <p>订单编号：25258888</p>
          <p>
            <span class="blue">[已完成]</span><span class="li_right"><a href="#" id="a_button_open"
                data-ajax="false" data-role="button" data-theme="e" class="middle_btn_a" style="color: #fff;">&nbsp;评&nbsp;&nbsp;&nbsp;&nbsp;价&nbsp;&nbsp;</a></span>
          </p>
        </li>
      </ul>
    </div>
    <!-- /content -->
  </div>
  <!-- /page -->
</body>
</html>