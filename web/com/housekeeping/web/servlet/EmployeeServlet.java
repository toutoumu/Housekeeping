package com.housekeeping.web.servlet;

import java.io.IOException;

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

import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.Employee;
import com.housekeeping.entity.pk.BusinessCategory_Employee_PK;
import com.housekeeping.entity.wrap.BusinessCategorys;
import com.housekeeping.entity.wrap.Comments;
import com.housekeeping.entity.wrap.Employees;
import com.housekeeping.entity.wrap.Orders;
import com.housekeeping.service.IBusinessCategoryService;
import com.housekeeping.service.IBusinessCategory_EmployeeService;
import com.housekeeping.service.ICommentService;
import com.housekeeping.service.IEmployeeService;
import com.housekeeping.service.IOrderService;
import com.housekeeping.service.impl.BusinessCategoryService;
import com.housekeeping.service.impl.BusinessCategory_EmployeeService;
import com.housekeeping.service.impl.CommentService;
import com.housekeeping.service.impl.EmployeeService;
import com.housekeeping.service.impl.OrderService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

@WebServlet("/EmployeeServlet.do")
public class EmployeeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IEmployeeService employeeService;

	private ICommentService commentService;

	private IOrderService orderService;

	private IBusinessCategoryService businessCategoryService;

	private IBusinessCategory_EmployeeService businessCategory_EmployeeService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		orderService = ctx.getBean(OrderService.class);
		commentService = ctx.getBean(CommentService.class);
		employeeService = ctx.getBean(EmployeeService.class);
		businessCategoryService = ctx.getBean(BusinessCategoryService.class);
		businessCategory_EmployeeService = ctx.getBean(BusinessCategory_EmployeeService.class);
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
			} else if (method.equals("querySelected")) {
				querySelected(jsonRequest, jsonResponse);
			} else if (method.equals("search")) {
				search(jsonRequest, jsonResponse);
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
			} else if (method.equals("showAuthorization")) {
				showAuthorization(jsonRequest, jsonResponse);
			} else if (method.equals("authorization")) {
				authorization(jsonRequest, jsonResponse);
			} else if (method.equals("unAuthorization")) {
				unAuthorization(jsonRequest, jsonResponse);
			} else if (method.equals("init")) {
				init(jsonRequest, jsonResponse);
			} else if (method.equals("edit")) {
				edit(jsonRequest, jsonResponse);
			} else if (method.equals("deleteComment")) {
				deleteComment(jsonRequest, jsonResponse);
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
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
			jsonResponse.setSuccess(false);
			jsonResponse.setMessage(e.getMessage());
			e.printStackTrace();
		}
		String str = JsonUtils.toJson(jsonResponse, false);
		response.getWriter().println(str);

	}

	/**
	 * 查询已经选择的雇员
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void querySelected(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String orderNumber = jsonRequest.getParameter("orderNumber");
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw new RuntimeException("订单编号不能为空");
		}

		Employees employees = employeeService.getEmployeeByOrderNumber(orderNumber);
		if (employees != null) {
			jsonResponse.setData("employees", employees.getEmployees());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}

	private void search(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String searchContent = jsonRequest.getParameter("searchContent");
		if (StringUtil.strIsEmpty(searchContent)) {
			throw new RuntimeException("搜索内容不能为空");
		}

		Employees employees = employeeService.search(searchContent);
		if (employees != null) {
			jsonResponse.setData("employees", employees.getEmployees());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 删除评论
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void deleteComment(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		commentService.deleteComment(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}

	/**
	 * 编辑
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void edit(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Employee employee = jsonRequest.getEntity("employee", Employee.class);
		if (employee == null) {
			employee = new Employee();
		}
		employeeService.updateEmployee(employee);
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
		Employee employee = employeeService.getEmployee(Integer.parseInt(id));
		if (employee == null) {
			throw new RuntimeException("查询的雇员不存在");
		}
		Orders orders = orderService.getOrderByEmployeeId(Integer.parseInt(id));
		if (orders != null && orders.getOrders().size() > 0) {
			jsonResponse.setData("orders", orders.getOrders());
		}
		Comments comments = commentService.getCommentByEmployeeId(Integer.parseInt(id));
		if (comments != null && comments.getComments().size() > 0) {
			jsonResponse.setData("comments", comments.getComments());
		}
		jsonResponse.setData("employee", employee);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("删除时主键不能为空");
		}
		employeeService.deleteEmployee(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("删除成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Employee employee = jsonRequest.getEntity("employee", Employee.class);
		if (employee != null && !StringUtil.strIsEmpty(employee.getName())) {
			employeeService.addEmployee(employee);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");

	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Employee employee = jsonRequest.getEntity("employee", Employee.class);
		if (employee == null) {
			employee = new Employee();
		}
		Employees employees = employeeService.query(employee);
		if (employees != null) {
			jsonResponse.setData("employees", employees.getEmployees());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 查询所有业务类别
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void category(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		BusinessCategorys categorys = businessCategoryService.getBusinessCategorys();
		if (categorys != null && categorys.getBusinessCategories().size() > 0) {
			jsonResponse.setSuccess(true);
			jsonResponse.setData("categorys", categorys.getBusinessCategories());
			jsonResponse.setMessage("成功");
		}
	}

	/**
	 * 查询已经拥有和还没拥有的业务
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void showAuthorization(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("查询条件不能为空");
		}
		BusinessCategorys authCategorys = businessCategoryService.getAuthorizedCategorys(Integer.parseInt(id));
		BusinessCategorys unauthCategorys = businessCategoryService.getUnAuthorizedCategorys(Integer.parseInt(id));

		if (unauthCategorys != null) {
			jsonResponse.setData("UnAuthorizedCategorys", unauthCategorys.getBusinessCategories());
		}
		if (authCategorys != null) {
			jsonResponse.setData("AuthorizedCategorys", authCategorys.getBusinessCategories());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 授权
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void authorization(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String employeeId = jsonRequest.getParameter("employeeId");
		String businessCategoryId = jsonRequest.getParameter("businessCategoryId");

		BusinessCategory_Employee_PK pk = new BusinessCategory_Employee_PK();
		pk.setEmployeeId(Integer.parseInt(employeeId));
		pk.setBusinessCategoryId(Integer.parseInt(businessCategoryId));

		BusinessCategory_Employee mm = new BusinessCategory_Employee();
		mm.setPk(pk);
		businessCategory_EmployeeService.addBusinessCategory_Employee(mm);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 取消授权
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void unAuthorization(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String employeeId = jsonRequest.getParameter("employeeId");
		String businessCategoryId = jsonRequest.getParameter("businessCategoryId");

		businessCategory_EmployeeService.deleteBusinessCategory_Employee(Integer.parseInt(businessCategoryId),
				Integer.parseInt(employeeId));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
}
