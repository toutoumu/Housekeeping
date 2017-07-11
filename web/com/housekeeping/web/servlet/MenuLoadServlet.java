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

import com.housekeeping.entity.Menu;
import com.housekeeping.entity.wrap.Menus;
import com.housekeeping.service.IMenuService;
import com.housekeeping.service.impl.MenuService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class MenuLoadServlet
 */
@WebServlet("/MenuLoadServlet.do")
public class MenuLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IMenuService menuService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		menuService = ctx.getBean(MenuService.class);
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
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
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
		response.getWriter().println(str);

	}

	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		menuService.deleteMenu(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Menu menu = jsonRequest.getEntity("menu", Menu.class);
		if (menu != null) {
			menuService.addMenu(menu);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");
	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		Menu menu = jsonRequest.getEntity("menu", Menu.class);
		// 查询所有菜单
		Menus menus = menuService.queryMenus(menu);
		if (menus != null && menus.getMenus().size() > 0) {
			jsonResponse.setSuccess(true);
			jsonResponse.setData("data", menus.getMenus());
			jsonResponse.setMessage("成功");

		}
	}
}
