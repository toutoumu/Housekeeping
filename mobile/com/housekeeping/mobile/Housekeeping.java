package com.housekeeping.mobile;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.housekeeping.entity.Order;
import com.housekeeping.entity.OrderDetail;
import com.housekeeping.entity.Order_Employee;
import com.housekeeping.entity.pk.Order_Employee_PK;
import com.housekeeping.service.IOrderDetailService;
import com.housekeeping.service.IOrderService;
import com.housekeeping.service.IOrder_EmployeeService;
import com.housekeeping.service.impl.OrderDetailService;
import com.housekeeping.service.impl.OrderService;
import com.housekeeping.service.impl.Order_EmployeeService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

@WebServlet("/Housekeeping.do")
public class Housekeeping extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IOrderService orderService;

	private IOrderDetailService orderDetailService;

	private IOrder_EmployeeService order_EmployeeService;

	@Override
	public void init() throws ServletException {
		super.init();
		// 初始化服务
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		orderService = ctx.getBean(OrderService.class);
		orderDetailService = ctx.getBean(OrderDetailService.class);
		order_EmployeeService = ctx.getBean(Order_EmployeeService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// 返回结构体定义
		JsonResponse jsonResponse = new JsonResponse();
		try {
			JsonRequest jsonRequest = new JsonRequest(request);
			JsonRequestHeader requestHeader = jsonRequest.getHeader();
			if (requestHeader == null || StringUtil.strIsEmpty(requestHeader.getMethod())) {
				throw new RuntimeException("请求方法不能为空");
			}
			String method = requestHeader.getMethod();

			// 根据请求的方法不同调用不同的服务方法
			if (method.equals("addOrder")) {
				addOrder(jsonRequest, jsonResponse);
			} else if (method.equals("validateCaptcha")) {
				validateCaptcha(jsonRequest, jsonResponse);
			} else {
				jsonResponse.setSuccess(false);
				jsonResponse.setMessage("请求的方法不存在");
			}
		} catch (WebApplicationException e) {
			jsonResponse.setSuccess(false);
			Response errorResponse = e.getResponse();
			if (errorResponse != null) {
				jsonResponse.setMessage(errorResponse.getEntity().toString());
			}
			e.printStackTrace();
		} catch (RuntimeException e) {
			jsonResponse.setSuccess(false);
			jsonResponse.setMessage(e.getMessage());
			e.printStackTrace();
		}
		String str = JsonUtils.toJson(jsonResponse, false);
		System.out.println(str);
		response.getWriter().println(str);
	}

	private void addOrder(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		Order order = jsonRequest.getEntity("order", Order.class);
		if (order == null) {
			throw new RuntimeException("订单不能为空");
		}
		OrderDetail orderDetail = jsonRequest.getEntity("orderDetail", OrderDetail.class);
		if (orderDetail == null) {
			throw new RuntimeException("订单明细不能为空");
		}
		String employeeId = jsonRequest.getParameter("employeeId");

		// 生成订单编号
		String orderNumber = UUID.randomUUID().toString();
		order.setOrderNumber(orderNumber);
		order = (Order) orderService.addOrder(order).getEntity();
		orderDetail.setOrderNumber(orderNumber);
		orderDetailService.addOrderDetail(orderDetail);
		if (!StringUtil.strIsEmpty(employeeId) && !employeeId.equals("0")) {
			Order_Employee_PK pk = new Order_Employee_PK();
			pk.setOrderNumber(orderNumber);
			pk.setEmployeeId(Integer.parseInt(employeeId));
			Order_Employee oe = new Order_Employee();
			oe.setPk(pk);
			order_EmployeeService.addOrder_Employee(oe);
		}

		jsonResponse.setParameter("orderNumber", orderNumber);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("订单添加成功");
	}

	private void validateCaptcha(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// TODO Auto-generated method stub

	}
}
