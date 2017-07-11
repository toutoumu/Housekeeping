<%@page import="com.housekeeping.service.impl.BusinessCategoryService"%>
<%@page import="com.housekeeping.service.impl.UserService"%>
<%@page import="com.housekeeping.service.IUserService"%>
<%@page import="com.housekeeping.entity.User"%>
<%@page import="com.housekeeping.entity.BusinessCategory"%>
<%@page import="com.housekeeping.mobile.FormatUtil"%>
<%@page import="com.housekeeping.entity.wrap.BusinessDetails"%>
<%@page import="com.housekeeping.entity.BusinessDetail"%>
<%@page import="com.housekeeping.entity.wrap.Businesss"%>
<%@page import="com.housekeeping.entity.Business"%>
<%@page import="com.housekeeping.service.impl.BusinessDetailService"%>
<%@page import="com.housekeeping.service.IBusinessDetailService"%>
<%@page import="com.housekeeping.utils.StringUtil"%>
<%@page import="com.housekeeping.service.IBusinessCategoryService"%>
<%@page import="com.housekeeping.service.impl.BusinessService"%>
<%@page import="com.housekeeping.service.IBusinessService"%>
<%@page import="com.housekeeping.entity.Region"%>
<%@page import="com.housekeeping.entity.wrap.Regions"%>
<%@page import="com.housekeeping.service.impl.RegionService"%>
<%@page import="com.housekeeping.service.IRegionService"%>
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
    // 验证用户是否登录
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      response.sendRedirect(basePath + "mobile/login/login.jsp");
      return;
    }
	// 判断业务类别参数
	String businessCategoryId = request.getParameter("businessCategoryId");
	// 雇员Id
	String employeeId = request.getParameter("employeeId");
	if (StringUtil.strIsEmpty(businessCategoryId) || businessCategoryId.equals("0")) {
		throw new RuntimeException("业务类别id不能为空");
	}
	
  

	ServletContext servletContext = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	IUserService userService = ctx.getBean(UserService.class);
    IRegionService regionService = ctx.getBean(RegionService.class);
    IBusinessCategoryService businessCategoryService = ctx.getBean(BusinessCategoryService.class);
	IBusinessService businessService = ctx.getBean(BusinessService.class);
	IBusinessDetailService businessDetailService = ctx.getBean(BusinessDetailService.class);
    
	 
    // 查询业务类别
    BusinessCategory businessCategory = businessCategoryService.getBusinessCategory(Integer.parseInt(businessCategoryId));
    if(businessCategory == null)
    {
      throw new RuntimeException("业务类别数据有误");
    }
  
	// 查询业务, 如果这个下面有多个业务那么数据库数据有误
	Businesss businesss = businessService.getBusinessByCategory(Integer.parseInt(businessCategoryId));
	if (businesss == null || businesss.getBusinesses() == null || businesss.getBusinesses().size() != 1) {
		throw new RuntimeException("家庭保洁业务数据有误");
	}
    Business business = businesss.getBusinesses().get(0);
	// 查询业务明细
	int id = businesss.getBusinesses().get(0).getId();
	BusinessDetails businessDetails = businessDetailService.getBusinessDetailByBusiness(id);
	if (businessDetails == null || businessDetails.getDetails() == null
	|| businessDetails.getDetails().size() != 1) {
		throw new RuntimeException("家庭保洁业务明细数据有误");
	}
        BusinessDetail businessDetail =  businessDetails.getDetails().get(0);
	pageContext.setAttribute("businessDetail", businessDetail);

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
    <script type="text/javascript">
					
				<%=FormatUtil.createJSParameter("user", request.getSession().getAttribute("user"))%>
					
				<%=FormatUtil.createJSParameter("businessCategory", businessCategory)%>
					
				<%=FormatUtil.createJSParameter("business", business)%>
					
				<%=FormatUtil.createJSParameter("businessDetail", businessDetail)%>
					
				<%=FormatUtil.createJSParameter("employeeId", employeeId)%>
					
				</script>
    <script type="text/javascript" src="/Housekeeping/mobile/movehouse/dj_order_add.js"></script>
    <div data-role="header" data-theme="b">
      <h1>订单填写</h1>
      <a href="#" data-rel="back" data-ajax="false" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/back.png" />
      </a>
      <a href="#" data-corners="false" data-shadow="false" class="header_btn_a">
        <img src="/Housekeeping/mobile/images/phone.png" />
      </a>
    </div>
    <!-- /header -->
    <div class="ui-content" role="main">
      <div>
        <h4 style="margin: 0; padding: 0;">
          <c:out value="${businessDetail.name }" />
          <!--   家庭保洁 -->
        </h4>
        <p>
          <c:out value="${businessDetail.remark }" />
        </p>
        <span>价格 <c:out value="${businessDetail.price }" /> <c:out value="${businessDetail.unit }" /></span>
        <!-- 40元/小时(两小时起),80平以内一般2小时,80-150平 一般三小时,150平以上推荐两个阿姨 -->
      </div>
      <form id="form" method="post" action="">
        <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
          <li>
            <table width="100%">
              <tr>
                <td>预约时间</td>
                <td>
                  <input type="text" readonly="readonly" name="startTime" id="startTime" value="" />
                </td>
              </tr>
            </table>
          </li>
          <li>
            <table width="100%">
              <tr>
                <td width="60%">服务时长</td>
                <td>
                  <div class="ui-grid-b li_right"
                    style="border: 1px solid #1491C5; height: 30px; width: 100px; background: #1491C5">
                    <div class="ui-block-a" id="min"
                      style="width: 35%; text-align: center; border: 1px solid #1491C5; padding: 4px; background: #1491C5">
                      <img src="/Housekeeping/mobile/images/jian.png" style="width: 15px; padding-top: 3px" />
                    </div>
                    <div class="ui-block-b" style="background: #fff; width: 30%; text-align: center;">
                      <input name="workingTime" id="workingTime" readonly="readonly" type="text" value="2"
                        style="text-align: center;" />
                    </div>
                    <div class="ui-block-c" id="add"
                      style="width: 35%; text-align: center; border: 1px solid #1491C5; padding: 4px; background: #1491C5">
                      <img src="/Housekeeping/mobile/images/add.png" style="width: 15px; padding-top: 3px" />
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </li>
        </ul>
        <ul data-role="listview" data-split-icon="gear" data-theme="d" data-inset="true">
          <li>
            <table width="100%">
              <tr>
                <td>地域选择</td>
                <td>
                  <fieldset data-role="controlgroup" data-type="horizontal">
                    <select name="section" id="section" data-mini="true" data-corners="true">
                      <c:forEach var="regions" items="${regions }">
                        <option value="${regions.name}" height="40px">${regions.name}</option>
                      </c:forEach>
                    </select>
                  </fieldset>
                </td>
              </tr>
            </table>
          </li>
          <li>
            <table width="100%">
              <tr>
                <td>街道地址</td>
                <td>
                  <input type="text" name="address" id="address" value="" />
                </td>
              </tr>
            </table>
          </li>
          <li>
            <table width="100%">
              <tr>
                <td>特殊要求</td>
                <td>
                  <input type="text" name="remark" id="remark" value="" />
                </td>
              </tr>
            </table>
          </li>
        </ul>
      </form>
      <div>备注：此处文案可编辑此处文案可编辑此处文案</div>
      <h4 style="text-align: center; color: #E24D00">
        订单金额：￥<span id="total">0</span>
      </h4>
    </div>
    <!-- /content -->
    <div data-role="footer" data-position="fixed" data-theme="c">
      <div class="ui-content">
        <a href="#" data-role="button" onclick="return  addOrder()" data-theme="e" class="footer_btn_a"
          style="color: #fff">提交</a>
      </div>
    </div>
  </div>
  <!-- /page -->
</body>
</html>