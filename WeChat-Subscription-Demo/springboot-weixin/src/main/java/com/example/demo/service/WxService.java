package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.example.demo.entity.AccessToken;
import com.example.demo.entity.Article;
import com.example.demo.entity.BaseMessage;
import com.example.demo.entity.ImageMessage;
import com.example.demo.entity.MusicMessage;
import com.example.demo.entity.NewsMessage;
import com.example.demo.entity.TextMessage;
import com.example.demo.entity.VideoMessage;
import com.example.demo.entity.VoiceMessage;
import com.example.demo.util.Util;
import com.thoughtworks.xstream.XStream;

public class WxService {

	private static final String TOKEN ="4a338414d3d94768823cc3051d0c3e91";
	private static final String KEY = "4a338414d3d94768823cc3051d0c3e91";
	
	public static final String GET_TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String APPID ="wx8e6ee934bda92ddd";
	public static final String APPSECRET ="85a29fdff430bbd40d9b05fa1495226b";
	
	//用于存储token
	public static AccessToken at;
	
	//百度AI识图
    public static final String APP_ID = "";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";
	
	
	/**
	 * 获取token
	 */
	private static void getToken() {
		String url = GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		String tokenStr = Util.get(url);
		JSONObject jb = JSON.parseObject(tokenStr);
		String atoken = jb.getString("access_token");
		String expireIn = jb.getString("expires_in");
		//创建token对象，并存起来
	    at = new AccessToken(atoken, expireIn);
	}
	
	/**
	 * 向外暴漏的获取token的方法
	 * @return
	 */
	public static  String getAccessToken() {
		if (at == null || at.isExpired()) {
			getToken();
		}
		return at.getAccessToken();
	}
	
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
		case "image":msg = dealImage(requestMap);break;
		case "voice":break;
		case "video":break;
		case "music":break;
		case "news":break;
		case "shortvideo":break;
		case "location":break;
		case "link":break;
		case "event": msg=dealEvent(requestMap); break;
		default:break;
		}
		if (null != msg) {
			return beanToXml(msg);
		}
		return null;
	}

	/**
	 * 进行图片识图
	 * @param requestMap
	 * @return
	 */
	private static BaseMessage dealImage(Map<String, String> requestMap) {
		 //初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        String path = requestMap.get("PicUrl");
        //org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        //进行网络图片文字识别
        org.json.JSONObject res = client.generalUrl(path, new HashMap<String, String>());
        String json = res.toString();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
        JSONArray jsonArray = jsonObject.getJSONArray("words_result");
        
        Iterator<Object> it = jsonArray.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
        	JSONObject next = (JSONObject) it.next();
        	sb.append(next.getString("words"));
		}
        
		return new TextMessage(requestMap, sb.toString());
	}

	/**
	 * 处理事件推送
	 * @param requestMap
	 * @return
	 */
	private static BaseMessage dealEvent(Map<String, String> requestMap) {
		String event = requestMap.get("Event");
		switch (event) {
		case "CLICK":return dealClick(requestMap);
		case "VIEW":return dealView(requestMap);
		default:
			break;
		}
		return null;
	}

	/**
	 * 处理Click类型的菜单
	 * @param requestMap
	 * @return
	 */
	private static BaseMessage dealClick(Map<String, String> requestMap) {
		String key = requestMap.get("EventKey");
		switch (key) {
			case "1":
				//处理点击了第一个一级菜单
				return new TextMessage(requestMap, "你点击了第一个一级菜单");
			case "32":
				//处理点击了第三菜单的第二个子菜单
				break;
	
			default:
				break;
		}
		
		return null;
	}

	/**
	 * 处理View类型的菜单
	 * @param requestMap
	 * @return
	 */
	private static BaseMessage dealView(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
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
		//用户发送的消息
		String msg = requestMap.get("Content");
		
		if(msg.equals("图文")) {
			List<Article> articles =  new ArrayList<Article>();
			articles.add(new Article("这是图文消息的标题", "这是图文消息的详细介绍", "1231", "http://www.baidu.com"));
			NewsMessage nm = new NewsMessage(requestMap, articles);
			return nm;
		}
		
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
