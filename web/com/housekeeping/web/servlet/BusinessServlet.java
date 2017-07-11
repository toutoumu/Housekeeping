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

import com.housekeeping.entity.Business;
import com.housekeeping.entity.wrap.BusinessCategorys;
import com.housekeeping.entity.wrap.Businesss;
import com.housekeeping.service.IBusinessCategoryService;
import com.housekeeping.service.IBusinessService;
import com.housekeeping.service.impl.BusinessCategoryService;
import com.housekeeping.service.impl.BusinessService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class MenuLoadServlet
 */
@WebServlet("/BusinessServlet.do")
public class BusinessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IBusinessService businessService;

	IBusinessCategoryService businessCategoryService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		businessService = ctx.getBean(BusinessService.class);
		businessCategoryService = ctx.getBean(BusinessCategoryService.class);
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
			} else if (method.equals("category")) {
				category(jsonRequest, jsonResponse);
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
			} else if (method.equals("showEdit")) {
				showEdit(jsonRequest, jsonResponse);

			} else if (method.equals("save")) {
				save(jsonRequest, jsonResponse);

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
		response.getWriter().println(str);

	}

	private void save(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Business category = jsonRequest.getEntity("category", Business.class);
		if (category != null && category.getId() != 0 && !StringUtil.strIsEmpty(category.getName())) {
			businessService.updateBusiness(category);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("修改成功");
			return;
		}
		throw new RuntimeException("修改失败");

	}

	/**
	 * 重新查询编辑的数据
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void showEdit(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		Business category = businessService.getBusiness(Integer.parseInt(id));
		jsonResponse.setData("category", category);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}

	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		businessService.deleteBusiness(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String businessCategoryId = jsonRequest.getParameter("businessCategoryId");
		if (StringUtil.strIsEmpty(businessCategoryId)) {
			throw new RuntimeException("businessCategoryId不能为空");
		}
		Business category = jsonRequest.getEntity("category", Business.class);
		if (category != null) {
			category.setBusinessCategoryId(Integer.parseInt(businessCategoryId));
			businessService.addBusiness(category);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");
	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		String businessCategoryId = jsonRequest.getParameter("businessCategoryId");
		if (StringUtil.strIsEmpty(businessCategoryId)) {
			throw new RuntimeException("businessCategoryId不能为空");
		}
		// 查询
		Businesss businesss = businessService.getBusinessByCategory(Integer.parseInt(businessCategoryId));
		if (businesss != null && businesss.getBusinesses().size() > 0) {
			jsonResponse.setData("data", businesss.getBusinesses());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void category(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		BusinessCategorys categorys = businessCategoryService.getBusinessCategorys();
		if (categorys != null && categorys.getBusinessCategories().size() > 0) {
			jsonResponse.setData("categorys", categorys.getBusinessCategories());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
}
