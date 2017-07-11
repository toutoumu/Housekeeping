package com.housekeeping.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import cn.jpush.api.Constant;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;

import com.housekeeping.entity.Article;
import com.housekeeping.entity.wrap.ArticleCategorys;
import com.housekeeping.entity.wrap.Articles;
import com.housekeeping.service.IArticleCategoryService;
import com.housekeeping.service.IArticleService;
import com.housekeeping.service.impl.ArticleCategoryService;
import com.housekeeping.service.impl.ArticleService;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;
import com.housekeeping.web.common.JsonRequest;
import com.housekeeping.web.common.JsonRequestHeader;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class MenuLoadServlet
 */
@WebServlet("/ArticleServlet.do")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 需要调用的服务在这里定义,注意只能是service结尾的,如果有多个一一列出
	private IArticleCategoryService articleCategoryService;

	private IArticleService articleService;

	@Override
	public void init() throws ServletException {
		super.init();
		// 初始化服务
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		// 从Spring中获取服务
		articleCategoryService = ctx.getBean(ArticleCategoryService.class);
		articleService = ctx.getBean(ArticleService.class);
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
			if (method.equals("category")) {
				category(jsonRequest, jsonResponse);
			} else if (method.equals("query")) {
				query(jsonRequest, jsonResponse);
			} else if (method.equals("add")) {
				add(jsonRequest, jsonResponse);
			} else if (method.equals("delete")) {
				delete(jsonRequest, jsonResponse);
			} else if (method.equals("showEdit")) {
				showEdit(jsonRequest, jsonResponse);
			} else if (method.equals("save")) {
				save(jsonRequest, jsonResponse);
			} else if (method.equals("push")) {
				push(jsonRequest, jsonResponse);
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
	 * 推送消息
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void push(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String appKey = Constant.appKey;
		String masterSecret = Constant.masterSecret;
		JPushClient _client = new JPushClient(masterSecret, appKey);
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(masterSecret)) {
			throw new RuntimeException("推送文章时主键不能为空");
		}
		Article article = articleService.getArticle(Integer.parseInt(id));
		if (article == null) {
			throw new RuntimeException("推送的文章不存在");
		}
		try {
			Map<String, String> extras = new HashMap<String, String>();
			extras.put("id", id);
			extras.put("title", article.getTitle());
			// 推送IOS消息,当前没有注册,因此注释掉
			/*PushResult result = _client.sendIosNotification(article.getTitle(), extras);
			if (!result.isResultOK()) {
				throw new RuntimeException("推送消息失败");
			}*/
			PushResult result = _client.sendAndroidNotification(article.getTitle(), article.getTitle(), extras);
			if (!result.isResultOK()) {
				throw new RuntimeException("推送消息失败");
			}
			jsonResponse.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("推送消息失败");
		}
	}

	/**
	 * 保存修改
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void save(JsonRequest jsonRequest, JsonResponse jsonResponse) {

		Article article = jsonRequest.getEntity("article", Article.class);
		if (article != null//
				&& article.getId() != 0//
				&& article.getCategoryId() != 0//
				&& !StringUtil.strIsEmpty(article.getContent()) //
				&& !StringUtil.strIsEmpty(article.getContent())) {
			articleService.updateArticle(article);
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
		Article article = articleService.getArticle(Integer.parseInt(id));
		jsonResponse.setData("article", article);
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 查询所有文章类别
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void category(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		// 查询
		ArticleCategorys categorys = articleCategoryService.getArticleCategorys();
		if (categorys != null && categorys.getArticleCategories().size() > 0) {
			jsonResponse.setSuccess(true);
			// 这里的categorys必须与js中对应
			jsonResponse.setData("categorys", categorys.getArticleCategories());
			jsonResponse.setMessage("成功");
		}
	}

	/**
	 * 删除文章
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void delete(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		String id = jsonRequest.getParameter("id");
		if (StringUtil.strIsEmpty(id)) {
			throw new RuntimeException("主键不能为空");
		}
		articleService.deleteArticle(Integer.parseInt(id));
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");
	}

	/**
	 * 添加文章
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void add(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Article article = jsonRequest.getEntity("article", Article.class);
		if (article != null && article.getCategoryId() != 0 && !StringUtil.strIsEmpty(article.getContent())
				&& !StringUtil.strIsEmpty(article.getContent())) {
			articleService.addArticle(article);
			jsonResponse.setSuccess(true);
			jsonResponse.setMessage("添加成功");
			return;
		}
		throw new RuntimeException("添加失败");
	}

	/**
	 * 查询文章
	 * @param jsonRequest
	 * @param jsonResponse
	 */
	private void query(JsonRequest jsonRequest, JsonResponse jsonResponse) {
		Article article = jsonRequest.getEntity("article", Article.class);
		Articles articles = articleService.getArticleByCategory(article.getCategoryId());
		if (articles != null) {
			jsonResponse.setData("articles", articles.getArticles());
		}
		jsonResponse.setSuccess(true);
		jsonResponse.setMessage("成功");

	}
}
