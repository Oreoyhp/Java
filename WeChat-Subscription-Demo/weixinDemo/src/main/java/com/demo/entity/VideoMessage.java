package com.demo.entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class VideoMessage extends BaseMessage{

	/** 通过素材管理中的接口上传多媒体文件，得到的id */
	@XStreamAlias("MediaId")
	private String mediaId;
	
	/** 视频消息的标题 */
	@XStreamAlias("Title")
	private String title;
	
	/** 视频消息的描述 */
	@XStreamAlias("Description")
	private String description;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public VideoMessage(Map<String, String> requestMap, String mediaId, String title, String description) {
		super(requestMap);
		this.setMsgType("video");
		this.mediaId = mediaId;
		this.title = title;
		this.description = description;
	}

}
