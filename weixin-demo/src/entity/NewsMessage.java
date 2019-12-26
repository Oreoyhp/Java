package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsMessage extends BaseMessage{

	/** 图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息*/
	private String articleCount;
	
	private List<Article> articles = new ArrayList<Article>();

	public String getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public NewsMessage(Map<String, String> requestMap, String articleCount, List<Article> articles) {
		super(requestMap);
		setMsgType("news");
		this.articleCount = articleCount;
		this.articles = articles;
	}
	
}
