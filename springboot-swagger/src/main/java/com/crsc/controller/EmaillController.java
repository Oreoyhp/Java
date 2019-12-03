package com.crsc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crsc.bean.MailBean;
import com.crsc.util.MailUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * @author crsc
 */
@RestController
@RequestMapping("email/system")
@Api("邮箱")
public class EmaillController {

	@Autowired
	private MailUtil mailUtil;

	/**
	 * 员工签收
	 * 
	 * @param recipient 接收方邮件地址
	 * @param subject   邮件主题
	 * @param content   邮件内容
	 * @param token     验证值
	 * @return
	 */
	@RequestMapping(value = "sendmsg", method = RequestMethod.POST)
	@ApiOperation("发送邮件")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "toEmail", value = "接收方邮件地址", required = true, paramType = "query", allowMultiple = true, dataType = "String"),
			@ApiImplicitParam(name = "title", value = "邮件主题", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "content", value = "邮件内容", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "token", value = "验证值", required = true, paramType = "query", dataType = "string") })
	public String sendmsg(@RequestParam(name = "toEmail", required = true) String[] toEmail,
			@RequestParam(name = "title", required = true) String title,
			@RequestParam(name = "content", required = true) String content,
			@RequestParam(name = "token", required = true) String token) {
		MailBean mailBean = new MailBean();
		for (String s : toEmail) {
			mailBean.setToEmail(s);
			mailBean.setTitle(title);
			mailBean.setContent(content);
			mailBean.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			mailUtil.sendTemplateMail(mailBean, token);
		}
		return "success";
	}
}
