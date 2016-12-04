package com.dayuan.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.dayuan.utils.json.JSONUtil;

import net.sf.json.JSONObject;


/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>禤爱武<br>
 * <b>日期：</b> Dec 14, 2015 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class HtmlUtil {
	
	private final static Logger log=Logger.getLogger(HtmlUtil.class.getName());
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>禤爱武<br>
	 * <b>日期：</b> Dec 14, 2015 <br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerJson(HttpServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,JSONUtil.toJSONString(object));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>禤爱武<br>
	 * <b>日期：</b> Dec 4, 2016 <br>
	 * @param response
	 * @param jsonStr
	 * @param type
	 * @throws Exception
	 */
	public static void writerJson(HttpServletResponse response,String type,Map map){
		try {
			response.setContentType("application/json");
			JSONObject jsonObject = JSONObject.fromMap(map);
			writer(response,jsonObject.toString());
		} catch (Exception e) {
			log.error("writerJson(HttpServletResponse response,Map map)出错："+e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * <b>作者：</b>禤爱武<br>
	 * <b>日期：</b> Dec 14, 2015 <br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(HttpServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	private static void writer(HttpServletResponse response,String str){
		try {
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
