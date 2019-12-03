package com.crsc.bean;

import java.io.Serializable;

/**
 * @author crsc
 * @function  发送邮件-封装接收者信息
 */
public class MailBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5356785806702574188L;
	/** 邮件接收人邮箱地址 */
	private String toEmail;   
	/** 邮件主题 */
    private String title;
    /** 邮件内容 */
    private String content;
    /** 邮件接收人 */
    private String username;
    private String cdate;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
