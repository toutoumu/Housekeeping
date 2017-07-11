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

import com.housekeeping.entity.ArticleCategory;
import com.housekeeping.entity.Region;
import com.housekeeping.entity.wrap.Regions;
import com.housekeeping.service.IRegionService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class MenuLoadServlet
 */
@WebServlet("/RegionServlet.do")
public class RegionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IRegionService regionService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		regionService = ctx.getBean(IRegionService.class);
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
		Region articleCategory = jsonRequest.getEntity("category", Region.class);
		if (articleCategory != null && articleCategory.getId() != 0
				&& !StringUtil.strIsEmpty(articleCategory.getName())) {
			regionService.updateRegion(articleCategory);
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
		Region category = regionService.getRegion(Integer.parseInt(id));
		jsonResponse.setData("category", category);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}

	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		regionService.deleteRegion(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Region articleCategory = jsonRequest.getEntity("category", Region.class);
		if (articleCategory != null) {
			regionService.addRegion(articleCategory);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");
	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		ArticleCategory articleCategory = jsonRequest.getEntity("category", ArticleCategory.class);
		// 查询
		Regions categorys = regionService.getRegions(0);
		if (categorys != null && categorys.getRegions().size() > 0) {
			jsonResponse.setData("data", categorys.getRegions());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
}
