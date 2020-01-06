package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Button;
import com.example.demo.entity.ClickButton;
import com.example.demo.entity.PhotoOrAlbumButton;
import com.example.demo.entity.SubButton;
import com.example.demo.entity.ViewButton;
import com.example.demo.service.WxService;

public class CreateMenu {

	public static void main(String[] args) {
		
		//菜单对象
		Button btn = new Button();
		//第一个一级菜单
		btn.getButton().add(new ClickButton("菜单一","1"));
		//第二个一级菜单
		btn.getButton().add(new ViewButton("百度","http://www.baidu.com"));
		
		//创建三个一级菜单
		SubButton sb = new SubButton("有子菜单");
		//为第三个一级菜单增加子菜单
		sb.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
		sb.getSub_button().add(new ClickButton("点击", "32"));
		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
		//加入第三个一级菜单
		btn.getButton().add(sb);
		//转为json
		JSONObject json = (JSONObject) JSON.toJSON(btn);
		
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", WxService.getAccessToken());
		
		//发送请求
		String result = Util.post(url, json.toString());
		System.out.println(result);
		
	}
			
}
