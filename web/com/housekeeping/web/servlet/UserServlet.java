package com.housekeeping.web.servlet;

import java.io.IOException;
import java.util.Date;

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
import com.housekeeping.entity.User;
import com.housekeeping.entity.wrap.Orders;
import com.housekeeping.entity.wrap.Users;
import com.housekeeping.service.IOrderService;
import com.housekeeping.service.IUserService;
import com.housekeeping.service.impl.OrderService;
import com.housekeeping.service.impl.UserService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

@WebServlet("/UserServlet.do")
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IUserService userService;
	
	private IOrderService orderService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		userService = ctx.getBean(UserService.class);
		orderService = ctx.getBean(OrderService.class);
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

			if (method.equals("query")) {
				query(jsonRequest, jsonResponse);
			} else if (method.equals("search")) {
				search(jsonRequest, jsonResponse);
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("edit")) {
				edit(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
			} else if (method.equals("init")) {
				init(jsonRequest, jsonResponse);
			} else if (method.equals("queryOrder")) {
				queryOrder(jsonRequest, jsonResponse);
			} else {
				jsonResponse.setSuccess(false);
				jsonResponse.setMessage("请求的方法不存在");
			}
		} catch (WebApplicationException e) {
			e.printStackTrace();
			jsonResponse.setSuccess(false);
			Response errorResponse = e.getResponse();
			if (errorResponse != null) {
				jsonResponse.setMessage(errorResponse.getEntity().toString());
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResponse.setSuccess(false);
			jsonResponse.setMessage(e.getMessage());
		}
		String str = JsonUtils.toJson(jsonResponse, false);
		response.getWriter().println(str);

	}

	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("删除时主键不能为空");
		}
		userService.deleteUser(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("删除成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		User user = jsonRequest.getEntity("user", User.class);
		if (user != null && !StringUtil.strIsEmpty(user.getUserName())) {
			user.setCreateTime(new Date());
			userService.addUser(user);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");

	}
	
	/**
	 * 编辑
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void edit(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		User user = jsonRequest.getEntity("user", User.class);
		if (user == null) {
			user = new User();
		}
		userService.updateUser(user);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}


	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		User user = jsonRequest.getEntity("user", User.class);
		if (user == null) {
			user = new User();
		}
		Users users = userService.getUsers(user);
		if (user != null) {
			jsonResponse.setData("users", users.getUsers());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
	
	private void search(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String searchContent = jsonRequest.getParameter("searchContent");
		if (StringUtil.strIsEmpty(searchContent)) {
			throw new RuntimeException("搜索内容不能为空");
		}

		Users users = userService.search(searchContent);
		if (users != null) {
			jsonResponse.setData("users", users.getUsers());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
	
	/**
	 * 初始化编辑表单
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void init(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		User user = userService.getUser(Integer.parseInt(id), null);
		if (user == null) {
			throw new RuntimeException("查询的会员不存在");
		}
		Orders orders = orderService.getOrderByUserId(Integer.parseInt(id));
		if (orders != null && orders.getOrders().size() > 0) {
			jsonResponse.setData("orders", orders.getOrders());
		}
		Order order = new Order();
		order.setUserId(Integer.parseInt(id));
		order.setOrderState(2);
		Orders accuntOrders = orderService.query(order);
		if (accuntOrders != null && accuntOrders.getOrders().size() > 0) {
			jsonResponse.setData("accounts", accuntOrders.getOrders());
		}
		jsonResponse.setData("user", user);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 查询用户订单列表
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void queryOrder(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		Orders orders = orderService.getOrderByUserId(Integer.parseInt(id));
		if (orders != null && orders.getOrders().size() > 0) {
			jsonResponse.setSuccess(true);
			jsonResponse.setData("orders", orders.getOrders());
			jsonResponse.setMessage("成功");
		}
	}

	/**
	 * 查询用户支出明细列表
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void showExpendList(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		int userId = Integer.parseInt(id);
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderState(2);
		Orders orders = orderService.query(order);
		if (orders != null && orders.getOrders().size() > 0) {
			jsonResponse.setSuccess(true);
			jsonResponse.setData("orders", orders.getOrders());
			jsonResponse.setMessage("成功");
		}
	}

}
