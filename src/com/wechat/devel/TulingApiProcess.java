package com.wechat.devel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 调用图灵机器人api接口，获取智能回复内容
 * @author pamchen-1
 *
 */
public class TulingApiProcess {
	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * @param content
	 * @return
	 */
	public final static String KEY="802a92bdc9fd40eb9f754e5c2a1cdec9";
	
	public String getTulingResult(String content){
		
		/** 此处为图灵api接口，参数key需要自己去注册申请 */
		
		String apiUrl = "http://www.tuling123.com/openapi/api?key="+KEY+"&info=";
		String param = "";
		String result = "机器人正在忙。。。";
		try {
			param = apiUrl+URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} //将参数转为url编码
		
		/** 发送httpget请求 */
		HttpGet request = new HttpGet(param);
		
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/** 请求失败处理 */
		if(null==result||result.equals("")){
			return "你说的话真是太高深了……";
		}
		
		
		try {
			JSONObject json = new JSONObject(result);
			//以code=100000为例，参考图灵机器人api文档
			
			if(100000==json.getInt("code")){
				result = json.getString("text");
			}
			
			
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return result;
	}
}
