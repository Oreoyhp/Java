package com.example.demo.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Util {
	
	/**
	 * 向指定的地址发送post请求,带着data数据
	 * @param url
	 * @return
	 */
	public static String post(String url, String data) {
		try {
			URL urlobj = new URL(url);
			URLConnection connection = urlobj.openConnection();
			
			//要发送数据，必须设置为可发送数据状态
			connection.setDoOutput(true);
			//获取输出流
			OutputStream os = connection.getOutputStream();
			//写出数据
			os.write(data.getBytes());
			os.close();
			
			//获取输入流
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len=is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 向指定的地址发送get请求
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		try {
			URL urlobj = new URL(url);
			//开链接
			URLConnection connection = urlobj.openConnection();
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len=is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
