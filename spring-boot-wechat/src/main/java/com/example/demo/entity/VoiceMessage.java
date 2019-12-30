package com.example.demo.entity;

import java.util.Map;

public class VoiceMessage extends BaseMessage{

	/** 通过素材管理中的接口上传多媒体文件，得到的id */
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	public VoiceMessage(Map<String, String> requestMap,String mediaId) {
		super(requestMap);
		this.setMsgType("voice");
		this.mediaId = mediaId;
	}

}
