package com.housekeeping.web.servlet;

import java.io.IOException;
import java.util.List;

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

import com.housekeeping.entity.Message;
import com.housekeeping.entity.Order;
import com.housekeeping.entity.Order_Employee;
import com.housekeeping.entity.pk.Order_Employee_PK;
import com.housekeeping.entity.wrap.BusinessCategorys;
import com.housekeeping.entity.wrap.Employees;
import com.housekeeping.entity.wrap.OrderDetails;
import com.housekeeping.entity.wrap.Orders;
import com.housekeeping.service.IBusinessCategoryService;
import com.housekeeping.service.IEmployeeService;
import com.housekeeping.service.IMessageService;
import com.housekeeping.service.IOrderDetailService;
import com.housekeeping.service.IOrderService;
import com.housekeeping.service.IOrder_EmployeeService;
import com.housekeeping.service.impl.BusinessCategoryService;
import com.housekeeping.service.impl.EmployeeService;
import com.housekeeping.service.impl.MessageService;
import com.housekeeping.service.impl.OrderDetailService;
import com.housekeeping.service.impl.OrderService;
import com.housekeeping.service.impl.Order_EmployeeService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.MessageSend;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

@WebServlet("/OrderServlet.do")
public class OrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IOrderService orderService;

	private IEmployeeService employeeService;

	private IBusinessCategoryService businessCategoryService;

	private IOrderDetailService orderDetailService;

	private IMessageService messageService;

	private IOrder_EmployeeService order_EmployeeService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		orderService = ctx.getBean(OrderService.class);
		employeeService = ctx.getBean(EmployeeService.class);
		orderDetailService = ctx.getBean(OrderDetailService.class);
		messageService = ctx.getBean(MessageService.class);
		businessCategoryService = ctx.getBean(BusinessCategoryService.class);
		order_EmployeeService = ctx.getBean(Order_EmployeeService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		JsonResponse jsonResponse = new JsonResponse();
		try {
			JsonRequest jsonRequest = new JsonRequest(request);
			JsonRequestHeader requestHeader = jsonRequest.getHeader();
			if (requestHeader == null || StringUtil.strIsEmpty(requestHeader.getMethod())) {
				throw new RuntimeException("请求方法不能为空");
			}
			String method = requestHeader.getMethod();

			if (method.equals("category")) {
				category(jsonRequest, jsonResponse);
			} else if (method.equals("query")) {
				query(jsonRequest, jsonResponse);
			} else if (method.equals("search")) {
				search(jsonRequest, jsonResponse);
			} else if (method.equals("showOper")) {
				showOper(jsonRequest, jsonResponse);
			} else if (method.equals("oper")) {
				oper(jsonRequest, jsonResponse);
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("addEmployee")) {
				addEmployee(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
			} else if (method.equals("deleteEmployee")) {
				deleteEmployee(jsonRequest, jsonResponse);
			} else if (method.equals("initData")) {
				initData(jsonRequest, jsonResponse);
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

	/**
	 *  为订单添加雇员
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void addEmployee(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String orderNumber = jsonRequest.getParameter("orderNumber");
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw new RuntimeException("订单编号不能为空");
		}
		String employeeId = jsonRequest.getParameter("employeeId");

		if (StringUtil.strIsEmpty(employeeId)) {
			throw new RuntimeException("雇员ID不能为空");
		}
		Order_Employee oe = new Order_Employee();
		Order_Employee_PK pk = new Order_Employee_PK();
		pk.setEmployeeId(Integer.parseInt(employeeId));
		pk.setOrderNumber(orderNumber);
		oe.setPk(pk);
		if (order_EmployeeService.getOrder_Employee(Integer.parseInt(employeeId), orderNumber) != null) {
			throw new RuntimeException("雇员已经被选择,请选择其他雇员");
		}
		order_EmployeeService.addOrder_Employee(oe);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}

	/**
	 * 删除订单的雇员
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void deleteEmployee(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String orderNumber = jsonRequest.getParameter("orderNumber");
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw new RuntimeException("订单编号不能为空");
		}
		String employeeId = jsonRequest.getParameter("employeeId");

		if (StringUtil.strIsEmpty(employeeId)) {
			throw new RuntimeException("雇员ID不能为空");
		}
		order_EmployeeService.deleteOrder_Employee(Integer.parseInt(employeeId), orderNumber);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}

	/**
	 * 搜索
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void search(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String searchContent = jsonRequest.getParameter("searchContent");
		if (StringUtil.strIsEmpty(searchContent)) {
			throw new RuntimeException("搜索内容不能为空");
		}
		Orders orders = orderService.search(searchContent);
		if (orders != null) {
			jsonResponse.setData("orders", orders.getOrders());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 初始化数据
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void initData(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String orderNumber = jsonRequest.getParameter("orderNumber");
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw new RuntimeException("订单编号不能为空");
		}
		// 订单的雇员信息是否存在
		Employees employees = employeeService.getEmployeeByOrderNumber(orderNumber);
		if (employees == null || employees.getEmployees() == null) {
			jsonResponse.setParameter("employeeCount", "0");
		} else {
			jsonResponse.setParameter("employeeCount", String.valueOf(employees.getEmployees().size()));
		}

		Order order = orderService.getOrder(orderNumber);
		OrderDetails details = orderDetailService.getOrderDetails(orderNumber);
		if (details != null && details.getOrderDetails().size() > 0) {
			jsonResponse.setData("details", details.getOrderDetails());
		}

		BusinessCategorys categorys = businessCategoryService.getBusinessCategorys();
		if (categorys != null && categorys.getBusinessCategories().size() > 0) {
			jsonResponse.setData("categorys", categorys.getBusinessCategories());
		}

		jsonResponse.setData("order", order);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 *  操作(确定订单/取消订单等)
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void oper(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Order order = jsonRequest.getEntity("order", Order.class);
		if (order == null || StringUtil.strIsEmpty(order.getOrderNumber())) {
			throw new RuntimeException("订单信息不能为空");
		}

		Employees employees = employeeService.getEmployeeByOrderNumber(order.getOrderNumber());
		if (employees == null || employees.getEmployees() == null || employees.getEmployees().size() == 0) {
			throw new RuntimeException("订单还未分配雇员,请先选择雇员再保存");
		}
		// TODO 什么时候发送短信
		// 添加短信发送 ::订单状态   0.待支付； 1.待用户确认（已经后台确认）；2.已确认（待用户评价）；3.已评价 4.待后台确认 5.已取消
		if (order.getOrderState() == 1) {
			List<Message> messages = MessageSend.getSMSMessages(order);
			for (Message message : messages) {
				// 发送订单短信
				MessageSend.sendSMSByTemplate(message.getPhoneNumber(), message.getTemplateId(), message
						.getParameters().split(","));
			}
			messages = MessageSend.getDelaySMSMessages(order);
			for (Message message : messages) {
				// 生成时间通知短信
				messageService.addMessage(message);
			}
		}
		// 更新状态
		orderService.updateOrder(order);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 显示订单操作
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void showOper(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String orderNumber = jsonRequest.getParameter("orderNumber");
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw new RuntimeException("订单编号不能为空");
		}

		Order order = orderService.getOrder(orderNumber);
		if (order != null) {
			jsonResponse.setData("order", order);
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 删除订单
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String orderNumber = jsonRequest.getParameter("orderNumber");
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw new RuntimeException("订单编号不能为空");
		}
		orderService.deleteOrder(orderNumber);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// TODO Auto-generated method stub
		Order order = jsonRequest.getEntity("order", Order.class);
		if (order != null && !StringUtil.strIsEmpty(order.getOrderNumber())) {
			orderService.addOrder(order);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");

	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Order order = jsonRequest.getEntity("order", Order.class);
		// 如果参数为空查询所有
		if (order == null) {
			order = new Order();
		}
		Orders orders = orderService.query(order);
		if (orders != null) {
			jsonResponse.setData("orders", orders.getOrders());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void category(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// TODO Auto-generated method stub
		// 查询
		BusinessCategorys categorys = businessCategoryService.getBusinessCategorys();
		if (categorys != null && categorys.getBusinessCategories().size() > 0) {
			jsonResponse.setSuccess(true);
			jsonResponse.setData("categorys", categorys.getBusinessCategories());
			jsonResponse.setMessage("成功");
		}
	}

}
