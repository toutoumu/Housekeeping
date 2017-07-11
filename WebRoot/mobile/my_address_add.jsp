<%@page import="com.housekeeping.entity.Region"%>
<%@page import="com.housekeeping.entity.wrap.Regions"%>
<%@page import="com.housekeeping.service.impl.RegionService"%>
<%@page import="com.housekeeping.service.IRegionService"%>
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
	IRegionService regionService = ctx.getBean(RegionService.class);

	// 初始化城市信息
	Regions regions = regionService.getRegions(0);
	List<Region> regionList = null;
	if (regions != null) {
		regionList = regions.getRegions();
	}
	pageContext.setAttribute("regions", regionList);
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div data-role="page" id="logopage">
    <script type="text/javascript" src="/Housekeeping/mobile/my_address_add.js"></script>
    <div data-role="header" data-theme="b">
      <h1>新增常用地址</h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div data-role="content">
      <form>
        <!-- <ul id="add_list" data-role="listview" data-inset="true" data-theme="d">
          <li data-role="fieldcontain">
            <input type="text" name="name1" id="name1" value="" data-clear-btn="true" placeholder="请输入地址"
              style="padding: 10px; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px;">
          </li>
          <li data-role="fieldcontain">
            <input type="text" name="name2" id="name2" value="" data-clear-btn="true" placeholder="请输入电话"
              style="padding: 10px; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px;" />
          </li>
        </ul> -->
        <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
          <li>
            <table width="100%">
              <tr>
                <td width="80px">
                  <label>地区选择</label>
                </td>
                <td>
                  <!--<fieldset data-role="controlgroup" data-type="horizontal">
                     <select name="province" id="province" data-mini="true" data-corners="true">
                      <option value="mon">广东省</option>
                    </select> -->
                  <select name="city" id="city" data-mini="true" data-corners="true" date-role="select">
                    <c:forEach var="regions" items="${regions }">
                      <option value="${regions.name}">${regions.name}</option>
                    </c:forEach>
                  </select>
                  <!-- <select name="section" id="section" data-mini="true">
                      <option value="08">南山区</option>
                    </select>
                  </fieldset> -->
                </td>
              </tr>
            </table>
          </li>
          <li data-role="fieldcontain">
            <table width="100%">
              <tr>
                <td width="80px">街道地址</td>
                <td>
                  <input type="text" name="name2" id="address" value="" data-clear-btn="true" placeholder="街道地址"
                    style="padding: 10px; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px;" />
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
        <a href="#" data-role="button" onclick="addAddress()" data-theme="e" class="footer_btn_a"
          style="color: #fff">保存</a>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>