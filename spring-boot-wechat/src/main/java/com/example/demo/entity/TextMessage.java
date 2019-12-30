package com.example.demo.entity;

import java.util.Map;

public class TextMessage extends BaseMessage{
	
	/** 文本消息内容 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TextMessage(Map<String, String> requestMap, String content) {
		super(requestMap);
		//设置文本消息的msgType为text
		this.setMsgType("text");
		this.content = content;
	}

/*	@Override
	public String toString() {
		return "TextMessage [content=" + content + ", getToUserName()=" + getToUserName() + ", getFromUserName()="
				+ getFromUserName() + ", getCreateTime()=" + getCreateTime() + "]";
	}*/
	
}
