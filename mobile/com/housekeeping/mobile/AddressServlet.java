package com.housekeeping.mobile;

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

import com.housekeeping.entity.Address;
import com.housekeeping.entity.User;
import com.housekeeping.service.IAddressService;
import com.housekeeping.service.impl.AddressService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/AddressServlet.do")
public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddressServlet() {
		super();
	}

	private IAddressService addressService;

	@Override
	public void init() throws ServletException {
		super.init();
		// 初始化服务
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		// 从Spring中获取服务
		addressService = ctx.getBean(AddressService.class);
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
			if (method.equals("addAddress")) {
				addAddress(jsonRequest, jsonResponse);
			} else if (method.equals("setDefault")) {
				setDefault(jsonRequest, jsonResponse);
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
	 * 设置默认地址
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void setDefault(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id) || id.equals("0")) {
			throw new RuntimeException("主键不能为空");
		}
		addressService.updateDefault(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("默认地址设置成功");
	}

	/**
	 * 获取验证码
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void addAddress(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String address = jsonRequest.getParameter("address");
		if (StringUtil.strIsEmpty(address)) {
			throw new RuntimeException("地址不能为空");
		}
		User user = (User) jsonRequest.getRequest().getSession().getAttribute("user");
		if (user == null || user.getId() == 0) {
			throw new RuntimeException("请登录");
		}

		Address address2 = new Address();
		address2.setAddress(address);
		address2.setUserId(user.getId());
		addressService.addAddress(address2);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("地址添加成功");

	}

}
