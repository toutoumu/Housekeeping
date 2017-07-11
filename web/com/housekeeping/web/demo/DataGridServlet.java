package com.housekeeping.web.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import com.housekeeping.entity.User;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

@WebServlet("/DataGridServlet.do")
public class DataGridServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			jsonResponse.setMessage(e.getMessage());

		} catch (RuntimeException e) {
			jsonResponse.setSuccess(false);
			jsonResponse.setMessage(e.getMessage());
		}
		String ret = JsonUtils.toJson(jsonResponse, false);
		System.out.println(ret);
		response.getWriter().println(ret);

	}

	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setId(i);
			user.setCity("深圳");
			users.add(user);
			user.setUserName("UserName" + i);
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setData("user", users);
		jsonResponse.setMessage("查询成功");
	}
}
