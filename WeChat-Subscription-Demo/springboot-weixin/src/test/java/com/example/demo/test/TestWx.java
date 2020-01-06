package com.example.demo.test;

import java.util.HashMap;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.example.demo.entity.Button;
import com.example.demo.entity.ClickButton;
import com.example.demo.entity.PhotoOrAlbumButton;
import com.example.demo.entity.SubButton;
import com.example.demo.entity.ViewButton;
import com.example.demo.service.WxService;

public class TestWx {
	
	 //设置APPID/AK/SK
    public static final String APP_ID = "你的 App ID";
    public static final String API_KEY = "你的 Api Key";
    public static final String SECRET_KEY = "你的 Secret Key";
	
	public void testPic() {
		
		 //初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        String path = "test.jpg";
        org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
	}
	
	@Test
	public void TestButton() {
		//菜单对象
		Button btn = new Button();
		//第一个一级菜单
		btn.getButton().add(new ClickButton("菜单一","1"));
		//第二个一级菜单
		btn.getButton().add(new ViewButton("一级跳转","http://www.baidu.com"));
		
		//创建三个一级菜单
		SubButton sb = new SubButton("有子菜单");
		//为第三个一级菜单增加子菜单
		sb.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
		sb.getSub_button().add(new ClickButton("传图", "32"));
		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
		//加入第三个一级菜单
		btn.getButton().add(sb);
		//转为json
		JSONObject json = (JSONObject) JSON.toJSON(btn);
		System.out.println(json.toString());
		
	}
	@Test
	public void testToken() {
		System.out.println(WxService.getAccessToken());
		System.out.println(WxService.getAccessToken());
	}

}
