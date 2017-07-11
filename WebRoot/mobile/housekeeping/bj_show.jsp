<%@page import="com.housekeeping.entity.User"%>
<%@page import="com.housekeeping.entity.Comment"%>
<%@page import="com.housekeeping.entity.wrap.Comments"%>
<%@page import="com.housekeeping.service.impl.CommentService"%>
<%@page import="com.housekeeping.service.ICommentService"%>
<%@page import="com.housekeeping.service.impl.EmployeeService"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.housekeeping.entity.Employee"%>
<%@page import="com.housekeeping.entity.wrap.Employees"%>
<%@page import="com.housekeeping.service.IEmployeeService"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	// 说明:连接到此页面的时候的参数
	// 1. 雇员id(employeeId) 必须
	// 2. 业务类别id(businessCategoryId) 非必须(如果从雇员列表进入则这个参数必须 ,快速预约模式业务类别id不能为空)
	// 3. 页面类别 (pageType) 两种模式( 查看模式 read /快速预约模式 order)
	// 4. 标题 (title) 保留做扩展用 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	// 验证用户是否登录
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect(basePath + "mobile/login/login.jsp");
		return;
	}
	// 雇员Id 
	String employeeId = request.getParameter("employeeId");
	//业务类别id
	String businessCategoryId = request.getParameter("businessCategoryId");
	if (employeeId == null || employeeId.equals("0")) {
		throw new RuntimeException("雇员Id不能为空");
	}

	//标题
	String title = request.getParameter("title");
	// 是否显示快速预约按钮
	String pageType = request.getParameter("pageType");
	String btnVisable = "";
	if (pageType.equals("order")) {
		btnVisable = "";
		// 快速预约模式业务类别id不能为空 
		if (businessCategoryId == null || businessCategoryId.equals("0")) {
			throw new RuntimeException("业务类别Id不能为空");
		}
	} else if (pageType.equals("read")) {
		btnVisable = "none";
	} else {
		throw new RuntimeException("参数pageType不能为空");
	}
	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	ICommentService commentService = ctx.getBean(CommentService.class);
	IEmployeeService employeeService = ctx.getBean(EmployeeService.class);

	// 雇员信息
	Employee employee = employeeService.getEmployee(Integer.parseInt(employeeId));
	pageContext.setAttribute("employee", employee);

	// 评论信息
	Comments comments = commentService.getCommentByEmployeeId(Integer.parseInt(employeeId));
	List<Comment> commentLit = null;
	if (comments != null) {
		commentLit = comments.getComments();
	}

	pageContext.setAttribute("commentLit", commentLit);
%>

<!DOCTYPE html>
<html>
<head>
<title>微家政</title>
<%@include file="/mobile/common/common.jsp"%>
</head>
<body>
  <div data-role="page" id="logopage">
    <script type="text/javascript" src="/Housekeeping/mobile/housekeeping/bj_show.js"></script>
    <div data-role="header" data-theme="b">
      <h1>阿姨详情</h1>
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
        <li>
          <div class="ui-grid-b">
            <div class="ui-block-a">
              <span style="font-weight: bolder;">${employee.name }</span>
            </div>
            <div class="ui-block-b">
              <span class="yellow">30元</span>/时
            </div>
            <div class="ui-block-a">
              <span>${employee.age }岁 &nbsp;${employee.nativeSlace }</span>
            </div>
            <div class="ui-block-b">
              <span><span class="star" data-rating="${employee.grade }" style="width: 100px;"></span> <!--  <img src="../images/star.png" style="width: 60px" /> --></span>
            </div>
            <div class="ui-block-a">
              <span>${fn:substring( employee.idCard , 0, 5)}*****<span class="yuan">验</span></span>
            </div>
            <div class="ui-block-b"></div>
          </div>
          <span class="ui-li-count" style="border: none; background: none; margin-top: -40px;"> <img
              src="${employee.photo }" width="65px"></span>
        </li>
      </ul>
      <div data-role="navbar">
        <ul class="navbar_ul">
          <li id="Li1" class="active" onclick="setTab('Li',1,3)">
            <a href="#anylink">很满意(1024)</a>
          </li>
          <li id="Li2" onclick="setTab('Li',2,3)">
            <a href="#anylink">一般(1000)</a>
          </li>
          <li id="Li3" onclick="setTab('Li',3,3)">
            <a href="#anylink">待提高(200)</a>
          </li>
        </ul>
      </div>
      <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true" id="con_Li_1">
        <c:forEach var="comment" items="${commentLit }">
          <li>
            <p>

              <span class="star" data-rating="${comment.grade }" style="width: 100px;"></span>
              <!-- <img class="star" src="../images/star.png" style="width: 100px" /> -->
              <span style="font-size: 16px;">来自&nbsp;${fn:substring( comment.userName , 0, 4)}*****</span>
            </p>

            <h2>${comment.content }</h2>
            <p>${comment.commentTime }</p>
          </li>
        </c:forEach>
      </ul>
      <ul data-role="listview" style="display: none;" data-split-icon="gear" data-theme="d" data-inset="true"
        id="con_Li_2">
        <c:forEach var="comment" items="${commentLit }">
          <li>
            <p>

              <span class="star" data-rating="${comment.grade }" style="width: 100px;"></span>
              <!-- <img class="star" src="../images/star.png" style="width: 100px" /> -->
              <span style="font-size: 16px;">来自&nbsp;${fn:substring( comment.userName , 0, 4)}*****</span>
            </p>

            <h2>${comment.content }</h2>
            <p>${comment.commentTime }</p>
          </li>
        </c:forEach>
      </ul>
      <ul data-role="listview" style="display: none;" data-split-icon="gear" data-theme="d" data-inset="true"
        id="con_Li_3">
        <c:forEach var="comment" items="${commentLit }">
          <li>
            <p>

              <span class="star" data-rating="${comment.grade }" style="width: 100px;"></span>
              <!-- <img class="star" src="../images/star.png" style="width: 100px" /> -->
              <span style="font-size: 16px;">来自&nbsp;${fn:substring( comment.userName , 0, 4)}*****</span>
            </p>

            <h2>${comment.content }</h2>
            <p>${comment.commentTime }</p>
          </li>
        </c:forEach>
      </ul>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" style="display: <%=btnVisable%>" data-theme="c">
      <div class="ui-content">
        <table width="100%">
          <tr>
            <td>
              <a
                href="/Housekeeping/mobile/housekeeping/bj_order_add.jsp?employeeId=<%=employeeId%>&businessCategoryId=<%=businessCategoryId%>"
                data-role="button" data-theme="e" class="footer_btn_a" style="color: #fff">预约阿姨</a>
            </td>
            <td>
              <a href="#" data-role="button" data-theme="e" class="footer_btn_a" style="color: #fff"> 收藏信息</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>