package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.ArticleCategory;

@XmlRootElement(name = "ArticleCategorys")
public class ArticleCategorys {
	private List<ArticleCategory> articleCategories;

	/**
	 * @return the articleCategories
	 */
	public List<ArticleCategory> getArticleCategories() {
		return articleCategories;
	}

	/**
	 * @param articleCategories the articleCategories to set
	 */
	public void setArticleCategories(List<ArticleCategory> articleCategories) {
		this.articleCategories = articleCategories;
	}
}
