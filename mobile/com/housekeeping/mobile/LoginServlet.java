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

import com.housekeeping.entity.Captcha;
import com.housekeeping.entity.User;
import com.housekeeping.service.ICaptchaService;
import com.housekeeping.service.IUserService;
import com.housekeeping.service.impl.CaptchaService;
import com.housekeeping.service.impl.UserService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	private ICaptchaService captchaService;

	private IUserService userService;

	@Override
	public void init() throws ServletException {
		super.init();
		// 初始化服务
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		// 从Spring中获取服务
		captchaService = ctx.getBean(CaptchaService.class);
		userService = ctx.getBean(UserService.class);
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
			if (method.equals("getCaptcha")) {
				getCaptcha(jsonRequest, jsonResponse);
			} else if (method.equals("validateCaptcha")) {
				validateCaptcha(jsonRequest, jsonResponse);
			} else if (method.equals("isLogin")) {
				isLogin(jsonRequest, jsonResponse);
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

	private void isLogin(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Object object = jsonRequest.getRequest().getSession().getAttribute("user");
		if (object != null && object instanceof User) {
			jsonResponse.setParameter("isLogin", "true");
		} else {
			jsonResponse.setParameter("isLogin", "false");
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 验证验证码
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void validateCaptcha(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// 用来测试的只要点击登录则生成用户信息放到session中
		if (true) {
			User mUser = userService.getUser(0, "15989348952");
			jsonRequest.getRequest().getSession().setAttribute("user", mUser);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("登录成功");
			return;
		}

		Captcha captcha = jsonRequest.getEntity("captcha", Captcha.class);
		if (captcha == null || captcha.getCode() == 0 || StringUtil.strIsEmpty(captcha.getPhoneNumber())) {
			throw new RuntimeException("输入的数据有误");
		}
		// 验证验证码
		Response response = captchaService.validateCaptcha(captcha);
		if (response.getStatus() == 200) {
			// 查询用户是否存在
			User user = userService.getUser(0, captcha.getPhoneNumber());
			if (user == null) {
				user = new User();
				user.setPhoneNumber(captcha.getPhoneNumber());
				response = userService.addUser(user);
				user = (User) response.getEntity();
			}
			jsonRequest.getRequest().getSession().setAttribute("user", user);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("验证成功");
		}
	}

	/**
	 * 获取验证码
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void getCaptcha(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// 模拟验证码验证
		if (true) {
			//模拟验证码
			Captcha captcha = new Captcha();
			captcha.setCode(111111);
			captcha.setPhoneNumber("15989348952");
			jsonResponse.setData("captcha", captcha);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("获取验证码成功");
			return;
		}

		String phoneNumber = jsonRequest.getParameter("phoneNumber");
		if (StringUtil.strIsEmpty(phoneNumber)) {
			throw new RuntimeException("电话号码不能为空");
		}
		Captcha captcha = captchaService.getCaptcha(phoneNumber);
		if (captcha.getCode() == 0) {
			throw new RuntimeException(captcha.getMessage());
		}
		//模拟验证码		 
		jsonResponse.setData("captcha", captcha);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("获取验证码成功");
	}

}
