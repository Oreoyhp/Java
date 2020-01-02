package com.example.demo.entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class BaseMessage {
	
	/** 开发者微信号 */
	@XStreamAlias("ToUserName")
	private String toUserName;
	
	/** 发送方帐号（一个OpenID） */
	@XStreamAlias("FromUserName")
	private String fromUserName;
	
	/** 消息创建时间 （整型）秒 */
	@XStreamAlias("CreateTime")
	private String createTime;
	
	/** 消息类型，文本为text */
	@XStreamAlias("MsgType")
	private String msgType;
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public BaseMessage(Map<String, String> requestMap) {
		this.toUserName = requestMap.get("FromUserName");
		this.fromUserName = requestMap.get("ToUserName");
		this.createTime = System.currentTimeMillis()/1000+"";
	}
	
	
	
}
