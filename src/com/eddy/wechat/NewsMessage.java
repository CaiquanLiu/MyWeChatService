package com.eddy;

import java.util.List;

/**
 * 
 * @author ��·��
 *
 * @data 2016��6��18��21:31:41
 */
public class NewsMessage extends BaseMessage {
	private int ArticleCount;
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
