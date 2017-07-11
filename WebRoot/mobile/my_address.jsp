<%@page import="com.housekeeping.entity.wrap.Addresss"%>
<%@page import="com.housekeeping.entity.Address"%>
<%@page import="com.housekeeping.service.impl.AddressService"%>
<%@page import="com.housekeeping.service.IAddressService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
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
	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	IAddressService addressService = ctx.getBean(AddressService.class);
	Addresss address = addressService.getAddressByUserId(user.getId());
	if (address != null && address.getAddresses() != null) {
		pageContext.setAttribute("address", address.getAddresses());
		System.out.print(address.getAddresses().size());
		System.out.print(user.getId());
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
    <script type="text/javascript" src="/Housekeeping/mobile/my_address.js"></script>
    <div data-role="header" data-theme="b">
      <h1>常用地址</h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div data-role="content">
      <ul data-role="listview" data-inset="true">
        <li data-icon="forward">
          <a href="/Housekeeping/mobile/my_address_add.jsp" data-ajax="false" id="btn_block"
            style="font-weight: bolder; font-size: 16px">
            <img src="images/address_add.png" class="ui-li-icon" />
            新增常用地址
            <%-- <c:forEach var="address" items="${address }">${address.address }</c:forEach> --%>
          </a>
        </li>
      </ul>
      <c:forEach var="address" items="${address }">
        <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
          <li>
            <table style="width: 100%;">
              <tr>
                <td>
                  <div class="ui-grid-b">
                    <div class="ui-block-a">
                      <span>&nbsp;</span>
                    </div>
                    <div class="ui-block-a" style="width: 100%">
                      <span><c:out value="${address.address }" default="" /></span>
                    </div>
                    <div class="ui-block-a">
                      <span>&nbsp;</span>
                    </div>
                  </div>
                </td>
                <td align="right" valign="middle" style="border-left: 1px #d7d7d7 solid; width: 30px;">
                  <div>
                    <a href="#">
                      <img src="/Housekeeping/mobile/images/address_edit.png" width="20px" />
                    </a>
                  </div>
                  <div style="border-top: 1px #d7d7d7 solid; padding-top: 5px">
                    <a href="#">
                      <c:choose>
                        <c:when test="${address.isDefault() == true }">
                          <img src="/Housekeeping/mobile/images/address_check.png" width="20px" />
                        </c:when>
                        <c:otherwise>
                          <img src="/Housekeeping/mobile/images/address_check1.png" width="20px"
                            onclick="setDefault(${address.id })" />
                        </c:otherwise>
                      </c:choose>
                    </a>
                  </div>
                </td>
              </tr>
            </table>
          </li>
        </ul>
      </c:forEach>
<!-- 
      <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
        <li>
          <table style="width: 100%;">
            <tr>
              <td>
                <div class="ui-grid-b">
                  <div class="ui-block-a">
                    <span>蔡国民</span>
                  </div>
                  <div class="ui-block-a" style="width: 100%">
                    <span>湖南省长沙市岳麓区西大街105号</span>
                  </div>
                  <div class="ui-block-a">
                    <span>13751078888</span>
                  </div>
                </div>
              </td>
              <td align="right" valign="middle" style="border-left: 1px #d7d7d7 solid;">
                <div>
                  <a href="#">
                    <img src="/Housekeeping/mobile/images/address_edit.png" width="20px" />
                  </a>
                </div>
                <div style="border-top: 1px #d7d7d7 solid; padding-top: 5px">
                  <a href="#">
                    <img src="/Housekeeping/mobile/images/address_check.png" width="20px" />
                  </a>
                </div>
              </td>
            </tr>
          </table>
        </li>
      </ul>
      <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
        <li>
          <table style="width: 100%;">
            <tr>
              <td>
                <div class="ui-grid-b">
                  <div class="ui-block-a">
                    <span>蔡国民</span>
                  </div>
                  <div class="ui-block-a" style="width: 100%">
                    <span>湖南省长沙市岳麓区西大街105号</span>
                  </div>
                  <div class="ui-block-a">
                    <span>13751078888</span>
                  </div>
                </div>
              </td>
              <td align="right" valign="middle" style="border-left: 1px #d7d7d7 solid;">
                <div>
                  <a href="#">
                    <img src="/Housekeeping/mobile/images/address_edit.png" width="20px" />
                  </a>
                </div>
                <div style="border-top: 1px #d7d7d7 solid; padding-top: 5px;">
                  <a href="#">
                    <img src="/Housekeeping/mobile/images/address_check1.png" width="20px" />
                  </a>
                </div>
              </td>
            </tr>
          </table>
        </li>
      </ul>
       -->
    </div>
    <!-- /content -->
  </div>
  <!-- /page -->
</body>
</html>