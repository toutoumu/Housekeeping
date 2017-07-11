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

import com.housekeeping.entity.Manager;
import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.pk.Manager_Menu_PK;
import com.housekeeping.entity.wrap.Managers;
import com.housekeeping.entity.wrap.Menus;
import com.housekeeping.service.IManagerService;
import com.housekeeping.service.IManager_MenuService;
import com.housekeeping.service.IMenuService;
import com.housekeeping.service.impl.ManagerService;
import com.housekeeping.service.impl.Manager_MenuService;
import com.housekeeping.service.impl.MenuService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class MenuLoadServlet
 */
@WebServlet("/ManagerServlet.do")
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IManagerService managerService;

	private IMenuService menuService;

	private IManager_MenuService manager_MenuService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		managerService = ctx.getBean(ManagerService.class);
		menuService = ctx.getBean(MenuService.class);
		manager_MenuService = ctx.getBean(Manager_MenuService.class);
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
			} else if (method.equals("showEdit")) {
				showEdit(jsonRequest, jsonResponse);
			} else if (method.equals("save")) {
				save(jsonRequest, jsonResponse);
			} else if (method.equals("login")) {
				login(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
			} else if (method.equals("showAuthorization")) {
				showAuthorization(jsonRequest, jsonResponse);
			} else if (method.equals("authorization")) {
				authorization(jsonRequest, jsonResponse);
			} else if (method.equals("unAuthorization")) {
				unAuthorization(jsonRequest, jsonResponse);
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
		String ret = JsonUtils.toJson(jsonResponse, false);
		System.out.println(ret);
		response.getWriter().println(ret);
	}

	private void showEdit(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("删除失败");
		}

		Manager manager = managerService.getManager(Integer.parseInt(id));
		if (manager != null) {
			jsonResponse.setData("manager", manager);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("成功");
			return;
		}
		throw new RuntimeException("没有查询到数据");
	}

	/**
	 * 修改密码
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void save(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Manager manager = jsonRequest.getEntity("manager", Manager.class);
		managerService.updateManager(manager);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void delete(JsonRequest request, JsonResponse response) throws IOException {
		Manager manager = request.getEntity("manager", Manager.class);
		if (manager != null && manager.getId() != 0) {
			managerService.deleteManager(manager.getId());
			response.setSuccess(true);
			response.setMessage("删除成功");
			return;
		}
		throw new RuntimeException("删除失败");
	}

	/**
	 * 查询
	 * 
	 * @param response
	 * @throws IOException
	 */
	public void query(JsonRequest request, JsonResponse response) throws IOException {
		Managers managers = managerService.getAll();
		response.setSuccess(true);
		response.setMessage("查询成功");
		if (managers != null && managers.getManagers().size() > 0) {
			response.setData("data", managers.getManagers());
		}
		return;
	}

	/**
	 * Add Manager
	 * 
	 * @param response
	 * @param manager
	 * @throws IOException
	 */
	public void add(JsonRequest request, JsonResponse response) throws IOException {
		Manager manager = request.getEntity("manager", Manager.class);
		if (managerService.getManagerByUserName(manager.getUserName()) != null) {
			throw new RuntimeException("该用户名已经存在");
		}
		managerService.addManager(manager);
		response.setSuccess(true);
		response.setMessage("添加成功");
	}

	public void login(JsonRequest request, JsonResponse response) throws IOException {
		Manager manager = request.getEntity("manager", Manager.class);
		Manager manager2 = managerService.getManagerByUserName(manager.getUserName());
		if (manager2 == null) {
			throw new RuntimeException("用户不存在");
		}
		if (manager2.getPassword() == null || manager2.getPassword().trim().equals("")
				|| !manager.getPassword().equals(manager2.getPassword())) {
			throw new RuntimeException("用户名或密码错误");
		}
		if (manager.getPassword().equals(manager2.getPassword())) {
			request.getRequest().getSession().setAttribute("manager", manager2);
			response.setSuccess(true);
			response.setMessage("登录成功");
			return;
		}
	}

	private void showAuthorization(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("查询条件不能为空");
		}
		Menus authMenus = menuService.getAuthorizedMenus(Integer.parseInt(id));
		Menus unAuthMenus = menuService.getUnAuthorizedMenus(Integer.parseInt(id));

		if (unAuthMenus != null) {
			jsonResponse.setData("UnAuthorizedMenus", unAuthMenus.getMenus());
		}
		if (authMenus != null) {
			jsonResponse.setData("AuthorizedMenus", authMenus.getMenus());
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

		String managerId = jsonRequest.getParameter("managerId");
		String menuId = jsonRequest.getParameter("menuId");

		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setManagerId(Integer.parseInt(managerId));
		pk.setMenuId(Integer.parseInt(menuId));

		Manager_Menu mm = new Manager_Menu();
		mm.setPk(pk);
		manager_MenuService.addManager_Menu(mm);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 授权
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void unAuthorization(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String managerId = jsonRequest.getParameter("managerId");
		String menuId = jsonRequest.getParameter("menuId");

		manager_MenuService.deleteManager_Menu(Integer.parseInt(menuId), Integer.parseInt(managerId));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
}
