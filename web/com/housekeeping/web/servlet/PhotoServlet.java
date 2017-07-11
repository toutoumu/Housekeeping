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

import com.housekeeping.entity.Photo;
import com.housekeeping.entity.wrap.Photos;
import com.housekeeping.service.IPhotoService;
import com.housekeeping.service.impl.PhotoService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class MenuLoadServlet
 */
@WebServlet("/PhotoServlet.do")
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IPhotoService photoService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		photoService = ctx.getBean(PhotoService.class);
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

			if (method.equals("init")) {
				init(jsonRequest, jsonResponse);
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
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
		Photo articleCategory = jsonRequest.getEntity("category", Photo.class);
		if (articleCategory != null && articleCategory.getId() != 0 && !StringUtil.strIsEmpty(articleCategory.getUrl())) {
			photoService.updatePhoto(articleCategory);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("修改成功");
			return;
		}
		throw new RuntimeException("修改失败");

	}

	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		photoService.deletePhoto(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String pathString = jsonRequest.getParameter("path");
		Photo photo = new Photo();
		photo.setUrl(pathString);
		if (photo != null) {
			photoService.addPhoto(photo);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");
	}

	private void init(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// 查询
		Photos categorys = photoService.get4Photo();
		if (categorys != null && categorys.getPhotos().size() > 0) {
			jsonResponse.setData("data", categorys.getPhotos());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}
}
