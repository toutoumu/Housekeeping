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

import com.housekeeping.entity.Article;
import com.housekeeping.entity.ArticleCategory;
import com.housekeeping.entity.wrap.Articles;

@Path("/Article")
public interface IArticleService {

	/**
	 * 添加文章（{@link Article}）
	 * @param article
	 * @return {@link Response}
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addArticle(Article article);

	/**
	 * 删除文章（{@link Article}）
	 * @param id
	 * @return  {@link Response}
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteArticle(@PathParam("id") int id);

	/**
	 * 更新文章（{@link Article}）
	 * @param article
	 * @return  {@link Response}
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateArticle(Article article);

	/**
	 * 根据（{@link Article}）的主键查询（{@link Article}）
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Article getArticle(@PathParam("id") int id);

	/**
	 * 查询某个{@link ArticleCategory}下的所有（{@link Article}）
	 * @param categoryId
	 * @return {@link Articles}
	 */
	@GET
	@Path("/Articles/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Articles getArticleByCategory(@PathParam("id") int categoryId);

	/**
	 * 查询文章
	 * @param article
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Articles query(Article article);

}
