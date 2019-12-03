package com.crsc.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.crsc.bean.MailBean;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author crsc
 * @function 邮件发送工具类
 */
@Component
public class MailUtil {
	
	/** 邮件发送者*/
	@Value("${crsc.mail.sender}")
	private String mailSender; 

	/** token */
	@Value("${crsc.mail.tokenvalue}")
	private String tokenValue; 

	@Autowired
	private JavaMailSender javaMailSender;

	/** freemarker */
	@Autowired
	private Configuration configuration;

	private Logger logger = LoggerFactory.getLogger(MailUtil.class);

	/**
	 * 发送一个简单格式的邮件
	 *
	 * @param mailBean
	 */
	public void sendSimpleMail(MailBean mailBean) {
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			// 邮件发送人
			simpleMailMessage.setFrom(mailSender);
			// 邮件接收人
			simpleMailMessage.setTo(mailBean.getToEmail());
			// 邮件主题
			simpleMailMessage.setSubject(mailBean.getTitle());
			// 邮件内容
			simpleMailMessage.setText(mailBean.getContent());
			javaMailSender.send(simpleMailMessage);
		} catch (Exception e) {
			logger.error("邮件发送失败", e.getMessage());
		}
	}

	/**
	 * 发送一个HTML格式的邮件
	 *
	 * @param mailBean
	 */
	public void sendHtmlMail(MailBean mailBean) {
		MimeMessage mimeMailMessage = null;
		try {
			mimeMailMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
			mimeMessageHelper.setFrom(mailSender);
			mimeMessageHelper.setTo(mailBean.getToEmail());
			mimeMessageHelper.setSubject(mailBean.getTitle());
			StringBuilder sb = new StringBuilder();
			sb.append("<h1>SpirngBoot测试邮件HTML</h1>").append("\"<p style='color:#F00'>你是真的太棒了！</p>")
					.append("<p style='text-align:right'>右对齐</p>");
			mimeMessageHelper.setText(sb.toString(), true);
			javaMailSender.send(mimeMailMessage);
		} catch (Exception e) {
			logger.error("邮件发送失败", e.getMessage());
		}
	}

	/**
	 * 发送带附件格式的邮件
	 *
	 * @param mailBean
	 */
	public void sendAttachmentMail(MailBean mailBean) {
		MimeMessage mimeMailMessage = null;
		try {
			mimeMailMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
			mimeMessageHelper.setFrom(mailSender);
			mimeMessageHelper.setTo(mailBean.getToEmail());
			mimeMessageHelper.setSubject(mailBean.getTitle());
			mimeMessageHelper.setText(mailBean.getContent());
			// 文件路径
			FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
			mimeMessageHelper.addAttachment("mail.png", file);

			javaMailSender.send(mimeMailMessage);
		} catch (Exception e) {
			logger.error("邮件发送失败", e.getMessage());
		}
	}

	/**
	 * 发送带静态资源的邮件
	 *
	 * @param mailBean
	 */
	public void sendInlineMail(MailBean mailBean) {
		MimeMessage mimeMailMessage = null;
		try {
			mimeMailMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
			mimeMessageHelper.setFrom(mailSender);
			mimeMessageHelper.setTo(mailBean.getToEmail());
			mimeMessageHelper.setSubject(mailBean.getTitle());
			mimeMessageHelper.setText("<html><body>带静态资源的邮件内容，这个一张IDEA配置的照片:<img src='cid:picture' /></body></html>",
					true);
			// 文件路径
			FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
			mimeMessageHelper.addInline("picture", file);
			javaMailSender.send(mimeMailMessage);
		} catch (Exception e) {
			logger.error("邮件发送失败", e.getMessage());
		}
	}

	/**
	 * 发送基于Freemarker模板的邮件(王飞)
	 *
	 * @param mailBean
	 */
	public void sendTemplateMail(MailBean mailBean, String token) {
		if (tokenValue.equals(token)) {
			MimeMessage mimeMailMessage = null;
			try {
				mimeMailMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
				
				int capacity = (int) (3 / 0.75 + 1);
				mimeMessageHelper.setFrom(mailSender);
				mimeMessageHelper.setTo(mailBean.getToEmail());
				mimeMessageHelper.setSubject(mailBean.getTitle());

				Map<String, Object> model = new HashMap<String, Object>(capacity);
				model.put("content", mailBean.getContent());
				model.put("title", mailBean.getTitle());
				model.put("cdate", mailBean.getCdate());
				Template template = configuration.getTemplate("mailft.ftl");
				String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				mimeMessageHelper.setText(text, true);

				javaMailSender.send(mimeMailMessage);
				logger.info("邮件发送成功");
			} catch (Exception e) {
				logger.error("邮件发送失败");
				e.printStackTrace();
			}

		} else {
			logger.info("token值不同，邮件尚未发送！");
		}
	}
}
