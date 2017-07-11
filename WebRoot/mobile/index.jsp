<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%-- <base href="<%=basePath%>"> --%>
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div data-role="page" id="logopage">
    <script type="text/javascript" src="/Housekeeping/mobile/index.js"></script>
    <div data-role="header" data-theme="b">
      <h1>微家政</h1>
      <a href="wtai://wp/mc;15989348952" data-corners="false" data-shadow="false" class="header_btn_a"></a>
      <a href="wtai://wp/mc;15989348952" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div data-role="content" style="margin: 0; padding: 0;">
      <div id="main_panels">
        <div class="panels_slider">
          <ul class="slides">
            <li>
              <img src="/Housekeeping/mobile/images/panel_start.png" alt="" title="" border="0" />
            </li>
            <li>
              <img src="/Housekeeping/mobile/images/panel_start.png" alt="" title="" border="0" />
            </li>
            <li>
              <img src="/Housekeeping/mobile/images/panel_start.png" alt="" title="" border="0" />
            </li>
          </ul>
        </div>
      </div>
      <div id="grid" class="ui-grid-b">
        <div class="ui-block-a">
          <div class="ui-bar ui-bar-368FF4">
            <a href="/Housekeeping/mobile/housekeeping/bj_list.jsp?businessCategoryId=1" data-shadow="false"
              data-ajax="true" data-theme="b">
              <span>家庭保洁</span>
              <img src="/Housekeeping/mobile/images/home_jtbj.png" />
            </a>
          </div>
        </div>
        <div class="ui-block-b" data-url="ay_list.html">
          <div class="ui-bar ui-bar-747AE8">
            <a href="/Housekeeping/mobile/newhouse/xj_order_add.jsp?businessCategoryId=2" data-shadow="false">
              <span>新居开荒</span>
              <img src="/Housekeeping/mobile/images/home_xjkf.png" />
            </a>
          </div>
        </div>
        <div class="ui-block-c">
          <div class="ui-bar ui-bar-9556F3">
            <a href="qx_list.html" data-shadow="false">
              <span>家电清洗</span>
              <img src="/Housekeeping/mobile/images/home_jdqx.png" />
            </a>
          </div>
        </div>
        <div class="ui-block-a">
          <div class="ui-bar ui-bar-84D018">
            <a href="xf_list.html" data-shadow="false">
              <span>洗护服务</span>
              <img src="/Housekeeping/mobile/images/home_xhfw.png" />
            </a>
          </div>
        </div>
        <div class="ui-block-b">
          <div class="ui-bar ui-bar-F3B613">
            <a href="/Housekeeping/mobile/movehouse/dj_list.jsp?businessCategoryId=5" data-shadow="false">
              <span>搬家</span>
              <img src="/Housekeeping/mobile/images/home_dj.png" />
            </a>
          </div>
        </div>
        <div class="ui-block-c">
          <div class="ui-bar ui-bar-22AC38">
            <a href="ay_list.html" data-shadow="false">
              <span>更多</span>
              <img src="/Housekeeping/mobile/images/home_more.png" />
            </a>
          </div>
        </div>
      </div>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div data-role="navbar">
        <ul class="footer-font">
          <li>
            <a href="#" class="active">
              <img src="/Housekeeping/mobile/images/footer_home1.png" alt="" title="" border="0" />
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
            <a href="user_index.html" data-ajax="false">
              <img src="/Housekeeping/mobile/images/footer_user.png" alt="" title="" border="0" />
              <br /> 会员
            </a>
          </li>
          <li>
            <a href="#" onclick="toPage('/Housekeeping/mobile/my_index.jsp')" data-ajax="false">
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