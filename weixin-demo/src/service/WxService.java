package service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entity.BaseMessage;
import entity.TextMessage;

public class WxService {

	private static final String TOKEN ="zmcxvnkjasd";
	
	
	public static boolean ckeck(String signature,String timestamp,String nonce){
		//1）将token、timestamp、nonce三个参数进行字典序排序 
		String[] strs = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(strs);
//		2）将三个参数字符串拼接成一个字符串进行sha1加密
		String str = strs[0]+strs[1]+strs[2];
		String mysig = sha1(str);
		System.out.println("=====================");
		System.out.println(mysig);
		System.out.println(signature);
//		3）开发者获得加密后的字符串可与signature对比，
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
		case "text":
			msg = dealTextMessage(requestMap);
			break;
		case "image":
			
			break;
		case "voice":
			
			break;
		case "video":
			
			break;
		case "music":
			
			break;
		case "news":
			
			break;
		case "shortvideo":
			
			break;
		case "location":
			
			break;
		case "link":
			
			break;

		default:
			break;
		}
		System.out.println(msg);
		return null;
	}


	/**
	 * 
	 * @param requestMap
	 * @return 处理文本消息
	 */
	private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
		TextMessage tm = new TextMessage(requestMap, "测试ing....");
		return tm;
	}
}
