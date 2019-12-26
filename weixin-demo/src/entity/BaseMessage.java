package entity;

import java.util.Map;

public class BaseMessage {
	
	/** 开发者微信号 */
	private String toUserName;
	/** 发送方帐号（一个OpenID） */
	private String fromUserName;
	/** 消息创建时间 （整型）秒 */
	private String createTime;
	/** 消息类型，文本为text */
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
