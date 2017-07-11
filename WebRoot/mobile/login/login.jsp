<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div data-role="page" id="logopage">
    <script type="text/javascript" src="/Housekeeping/mobile/login/login.js"></script>
    <div data-role="header" data-theme="b">
      <h1>登录微家政</h1>
      <a href="#" data-rel="back" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/back.png" />
      </a>
      <a href="#" data-corners="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div class="ui-content" role="main">
      <form>
        <ul data-role="listview" data-inset="true" data-theme="d">
          <li data-role="fieldcontain">
            <input type="text" name="name1" id="phone" value="" data-clear-btn="true" placeholder="请输入手机号"
              style="padding: 10px; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px;">
          </li>
          <li data-role="fieldcontain">
            <table style="width: 100%">
              <tr>
                <td style="width: 60%">
                  <input type="text" name="code" id="code" value="" data-clear-btn="true" placeholder="请输入验证码"
                    style="padding: 10px; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px;" />
                </td>
                <td style="width: 40%">
                  <label for="name2"> <a href="#" data-role="button" data-theme="e" id="getCaptcha"
                      style="-moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px;"
                      onclick="getCaptcha()">获取验证码</a></label>
                </td>
              </tr>
            </table>
          </li>
        </ul>
      </form>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div class="ui-content">
        <a href1="index.html" data-ajax="false" data-role="button" data-theme="e" class="footer_btn_a"
          style="color: #fff;" onclick="login()">登录</a>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>