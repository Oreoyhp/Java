package com.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.demo.entity.BaseMessage;
import com.demo.entity.ImageMessage;
import com.demo.entity.MusicMessage;
import com.demo.entity.NewsMessage;
import com.demo.entity.TextMessage;
import com.demo.entity.VideoMessage;
import com.demo.entity.VoiceMessage;
import com.thoughtworks.xstream.XStream;

public class WxService {

	private static final String TOKEN ="4a338414d3d94768823cc3051d0c3e91";
	private static final String KEY = "4a338414d3d94768823cc3051d0c3e91";
	
	public static boolean ckeck(String signature,String timestamp,String nonce){
		//1）将token、timestamp、nonce三个参数进行字典序排序 
		String[] strs = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(strs);
		//2）将三个参数字符串拼接成一个字符串进行sha1加密
		String str = strs[0]+strs[1]+strs[2];
		String mysig = sha1(str);
		//3）开发者获得加密后的字符串可与signature对比，
		return mysig.equalsIgnoreCase(signature);
	}

	private static String sha1(String str) {
		try {
			//获取加密对象
			MessageDigest md = MessageDigest.getInstance("sha1");
			//加密
			byte[] digest = md.digest(str.getBytes());
			char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
			StringBuilder sb = new StringBuilder();
			//处理加密结果
			for (byte b : digest) {
				sb.append(chars[(b>>4)&15]);
				sb.append(chars[b&15]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * SHA1加密
	 * @param request
	 * @return
	 */
	public static Map<String, String> parseRequest(InputStream request) {
		SAXReader reader = new SAXReader();
		Map<String, String> strMap = new HashMap<String, String>();
		try {
			//读取输入流，获取文档对象
			Document document = reader.read(request);
			//根据文档，获取根节点
			Element root = document.getRootElement();
			//根据根节点获取所有子节点
			@SuppressWarnings("unchecked")
			List<Element> elements = root.elements();
			for (Element o : elements) {
				strMap.put(o.getName(), o.getStringValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return strMap;
	}


	/**
	 * @param requestMap
	 * @return 返回xml数据包
	 */
	public static String getRespose(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String msgType = requestMap.get("MsgType");
		switch (msgType) {
		case "text":msg = dealTextMessage(requestMap);break;
		case "image":break;
		case "voice":break;
		case "video":break;
		case "music":break;
		case "news":break;
		case "shortvideo":break;
		case "location":break;
		case "link":break;
		default:break;
		}
		if (null != msg) {
			return beanToXml(msg);
		}
		return null;
	}

	/**
	 *	把消息对象处理为xml数据包
	 * @param msg
	 * @return
	 */
	private static String beanToXml(BaseMessage msg) {
		XStream stream = new XStream();
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(VideoMessage.class);
		stream.processAnnotations(VoiceMessage.class);
		String xml = stream.toXML(msg);
		return xml;
	}

	/**
	 * 	调用图灵聊天机器人
	 * @param requestMap
	 * @return 处理文本消息
	 */
	private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
		String msg = requestMap.get("Content");
		
		String resp = getAnswerResult(msg);
//		String resp = new TulingApiProcess().getTulingResult(msg);
		TextMessage tm = new TextMessage(requestMap, resp);
		System.out.println(tm);
		return tm;
	}
	
	
	public static String getAnswerResult(String content) {
		String apiUrl = "http://127.0.0.1:8099/answer/sendmsg?token="+KEY+"&info=";
		String param = "";
		
		try {
			param = apiUrl+URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		HttpGet request = new HttpGet(param);
		String result = "";
		
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			result = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
