<%@page import="com.housekeeping.entity.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect(basePath + "mobile/login/login.jsp");
		return;
	}
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
    <script type="text/javascript" src="/Housekeeping/mobile/my_index.js"></script>
    <div data-role="header" data-theme="b">
      <h1>我的微家政</h1>

      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a"></a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div data-role="content" style="margin: 0; padding: 0;">
      <div id="main_panels">
        <div class="panels_slider">
          <ul class="slides">
            <li>
              <img src="images/wjz_bg.png" alt="" title="" border="0" />
            </li>
          </ul>
        </div>
        <div class="main_text">
          <a href="#" data-corners="false" data-ajax="false" style="color: #fff">
            <c:out value="${user.phoneNumber }" default="" />
          </a>
          <p>
            积分：
            <c:out value="${user.score }" default="0" />
          </p>
        </div>
      </div>
      <ul data-role="listview" data-inset="true" style="margin: 0.5em;">
        <li data-icon="forward">
          <a href="/Housekeeping/mobile/my_order_search.jsp" data-ajax="true">
            <img src="images/wjz01.png" class="listview_li_img_l2">
            <h4 class="li_h4">订单查询</h4>
          </a>
        </li>
      </ul>
      <ul data-role="listview" data-inset="true" style="margin: 0.5em">
        <li data-icon="forward">
          <a href="/Housekeeping/mobile/my_address.jsp" data-ajax="true">
            <img src="images/wjz02.png" class="listview_li_img_l2">
            <h4 class="li_h4">常用地址</h4>
          </a>
        </li>
      </ul>
      <ul data-role="listview" data-inset="true" style="margin: 0.5em">
        <li data-icon="forward">
          <a href="#">
            <img src="images/wjz03.png" class="listview_li_img_l2">
            <h4 class="li_h4">我的积分</h4>
          </a>
        </li>
      </ul>
      <ul data-role="listview" data-inset="true" style="margin: 0.5em">
        <li data-icon="forward">
          <a href="my_collection.html" data-ajax="true">
            <img src="images/wjz04.png" class="listview_li_img_l2">
            <h4 class="li_h4">我的收藏</h4>
          </a>
        </li>
      </ul>
      <ul data-role="listview" data-inset="true" style="margin: 0.5em">
        <li data-icon="forward">
          <a href="#">
            <img src="images/wjz05.png" class="listview_li_img_l2">
            <h4 class="li_h4">关于微家政</h4>
          </a>
        </li>
      </ul>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div data-role="navbar">
        <ul class="footer-font">
          <li>
            <a href="/Housekeeping/mobile/index.jsp" data-ajax="true">
              <img src="images/footer_home.png" alt="" title="" border="0" />
              <br /> 首页
            </a>
          </li>
          <li>
            <a href="#" onclick="toPage('/Housekeeping/mobile/my_order_search.jsp')">
              <img src="/Housekeeping/mobile/images/footer_myorder.png" alt="" title="" border="0" />
              <br /> 我的订单
            </a>
          </li>
          <li>
            <a href="#" data-ajax="true"
              onclick="toPage('/Housekeeping/mobile/housekeeping/bj_order_add.jsp?businessCategoryId=1')">
              <img src="/Housekeeping/mobile/images/footer_order.png" alt="" title="" border="0" />
              <br /> 快速下单
            </a>
          </li>
          <li>
            <a href="user_index.html" data-ajax="true">
              <img src="/Housekeeping/mobile/images/footer_user.png" alt="" title="" border="0" />
              <br /> 会员
            </a>
          </li>
          <li>
            <a href="#" data-ajax="true">
              <img src="/Housekeeping/mobile/images/footer_myhome.png" alt="" title="" border="0" />
              <br /> 我的微家政
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>