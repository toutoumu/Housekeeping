package com.housekeeping.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.housekeeping.entity.ArticleCategory;
import com.housekeeping.entity.wrap.ArticleCategorys;

@Path("/ArticleCategory")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface IArticleCategoryService {

	/**
	 * 添加文章分类{@link ArticleCategory}
	 * @param ArticleCategory {@link ArticleCategory}
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addArticleCategory(ArticleCategory ArticleCategory);

	/**
	 * 删除文章分类{@link ArticleCategory}
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteArticleCategory(@PathParam("id") int id);

	/**
	 * 更新文章分类{@link ArticleCategory}
	 * @param ArticleCategory {@link ArticleCategory}
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateArticleCategory(ArticleCategory ArticleCategory);

	/**
	 * 根据{@link ArticleCategory}的Id查询{@link ArticleCategory}
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ArticleCategory getArticleCategory(@PathParam("id") int id);

	/**
	 * 查询所有的文章分类
	 * @return {@link ArticleCategorys}
	 */
	@GET
	@Path("/ArticleCategorys")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ArticleCategorys getArticleCategorys();

	/**
	 * 查询所有的文章分类
	 * @return {@link ArticleCategorys}
	 */
	@GET
	@Path("/search/{name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ArticleCategorys search(@PathParam("name") String name);
}
