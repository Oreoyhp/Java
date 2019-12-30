package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.WxService;

@WebServlet(name = "indexController", urlPatterns = "/WeChat")
public class IndexController extends HttpServlet {

	private static final long serialVersionUID = -1738436998943267070L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的
		 * timestamp	时间戳参数、
		 * nonce		随机数参数、
		 * echostr		随机字符串
		 */
		System.out.println(123);
		/*
		 * String signature = request.getParameter("signature"); String timestamp =
		 * request.getParameter("timestamp"); String nonce =
		 * request.getParameter("nonce"); String echostr =
		 * request.getParameter("echostr"); System.out.println(signature);
		 * System.out.println(timestamp); System.out.println(nonce);
		 * System.out.println(echostr);
		 */

		/*
		 * if (WxService.ckeck(signature, timestamp, nonce)) { PrintWriter out =
		 * response.getWriter(); out.print(echostr); out.flush(); out.close();
		 * System.out.println("接入成功"); } else { System.out.println("接入失败"); }
		 */
	}

	/**
	 * 接收消息和事件推送
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 接收
		Map<String, String> requestMap = WxService.parseRequest(request.getInputStream());
		System.out.println(requestMap);

		// 回复
//		String respXml = "<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName><FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+"晚安！"+"]]></Content></xml>";
		String respXml = WxService.getRespose(requestMap);
		PrintWriter out = response.getWriter();
		out.print(respXml);
		out.flush();
		out.close();

		/*
		 * ServletInputStream is = request.getInputStream(); byte[] b =new byte[1024];
		 * int len; StringBuilder sb = new StringBuilder(); while ((len=is.read(b))!=-1)
		 * { sb.append(new String(b,0,len)); } System.out.println(sb.toString());
		 */
	}

}
