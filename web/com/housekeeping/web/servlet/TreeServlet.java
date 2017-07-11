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

import com.google.gson.Gson;
import com.housekeeping.entity.Manager;
import com.housekeeping.entity.wrap.Menus;
import com.housekeeping.service.IMenuService;
import com.housekeeping.service.impl.MenuService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class TreeServlet
 */
@WebServlet("/TreeServlet.do")
public class TreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IMenuService menuService;

	@Override
	public void init() throws ServletException {

		super.init();
		ServletContext servletContext = this.getServletContext();

		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		menuService = (IMenuService) ctx.getBean(MenuService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if (manager == null) {
			return;
		}
		Menus menus = menuService.getAuthorizedMenus(manager.getId());
		if (menus != null && menus.getMenus().size() > 0) {
			Gson gson = new Gson();
			String json = gson.toJson(menus.getMenus());
			System.out.println(json);
			response.getWriter().println(json);
		}
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
		System.out.println(JsonUtils.toJson(jsonResponse, false));
		response.getWriter().println(JsonUtils.toJson(jsonResponse, false));
	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Manager manager = (Manager) jsonRequest.getRequest().getSession().getAttribute("manager");
		if (manager == null) {
			throw new RuntimeException("没有查询到数据");
		}
		Menus menus = menuService.getAuthorizedMenus(manager.getId());
		if (menus != null && menus.getMenus().size() > 0) {
			jsonResponse.setData("data", menus.getMenus());
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("成功");
		}
	}
}
