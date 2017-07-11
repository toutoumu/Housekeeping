<%@page import="com.housekeeping.entity.User"%>
<%@page import="com.housekeeping.mobile.FormatUtil"%>
<%@page import="com.housekeeping.utils.StringUtil"%>
<%@page import="com.housekeeping.service.impl.EmployeeService"%>
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
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect(basePath + "mobile/login/login.jsp");
		return;
	}
	// 业务类别id
	String businessCategoryId = request.getParameter("businessCategoryId");
	if (StringUtil.strIsEmpty(businessCategoryId) || businessCategoryId.equals("0")) {
		throw new RuntimeException("业务类别不能为空");
	}

	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	IEmployeeService employeeService = ctx.getBean(EmployeeService.class);
	Employees employees = employeeService.getEmployeeByBusinessCategoryId(Integer.parseInt(businessCategoryId));
	List<Employee> emList = null;
	if (employees != null) {
		emList = employees.getEmployees();
	}
	pageContext.setAttribute("emList", emList);
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%@include file="/mobile/common/common.jsp"%>

</head>
<body>
  <div data-role="page" id="logopage">
    <script type="text/javascript">
					
				<%=FormatUtil.createJSParameter("businessCategoryId", businessCategoryId)%>
					
				</script>
    <script type="text/javascript" src="/Housekeeping/mobile/movehouse/dj_list.js"></script>
    <div data-role="header" data-theme="b">
      <h1 style="width: 100%; text-align: center; margin-left: 0">
        <div data-role="controlgroup" data-type="horizontal" class="header_btn_controlgroup">
          <a href="#" data-role="button">距离</a>
          <a href="#" data-role="button">热门</a>
          <a href="#" data-role="button">评分</a>
        </div>
      </h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div class="ui-content" role="main">
      <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
        <c:forEach var="emp" items="${emList }">
          <li>
            <a
              href="/Housekeeping/mobile/movehouse/dj_show.jsp?employeeId=${emp.id }&businessCategoryId=<%=businessCategoryId%>&pageType=order"
              data-ajax="true">
              <img src="${emp.photo }" style="width: 69px; height: 80px;" class="listview_li_img_l">
              <h2>${emp.name }</h2>
              <p>
                <span class="star" data-rating="${emp.grade }" style="width: 50px;"></span>
                <!-- <img src="/Housekeeping/mobile/images/star.png" style="width: 50px" /> -->
              </p>
              <p>${emp.age }岁&nbsp;${emp.nativeSlace }</p>
              <span class="ui-li-count"
                style="border: none; background: none; font-weight: 500; font-size: 12px; margin-top: -23px; margin-right: -20px;">服务价格：30元/时<br />
                服务次数：${emp.serviceTimes }次<br /> 距离：3km
              </span>
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div class="ui-content">
        <a href="/Housekeeping/mobile/movehouse/dj_order_add.jsp?businessCategoryId=<%=businessCategoryId%>"
          data-ajax="false" data-role="button" data-theme="e" class="footer_btn_a" style="color: #fff">快速预约</a>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>