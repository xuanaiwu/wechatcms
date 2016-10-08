package com.dayuan.utils;


import java.util.ResourceBundle;



public class Constant {

	private static ResourceBundle res = ResourceBundle.getBundle("sysconfig");
	
	//系统名称
	public static String SYSTEM_NAME=res.getString("systemName");
	
	//系统地址
	public static String SYSTEM_PATH = res.getString("systemPath");
	
	
	//项目根路径 线上是tomcat的，本地可以配成工作空间项目
	public static String WORK_ROOT_PATH  = res.getString("work.root.path");
	
	
	//上传根目录地址 http://image.mn606.com/
	public static String UPLOAD_URL = res.getString("upload.url");
	
	
	//搜索图片过滤分数0.0-.1.0之间的小数
	public static String SEARCH_SCORE =  res.getString("search.score");
	
	
	public static String UPLOAD_PATH = res.getString("upload.path");
	
	public static String LOOK_URL=res.getString("lookUp");
	
	public static String SEARCH_URL=res.getString("searchUp");
	
	
	

	/**
	 * 超级管理员常量
	 * @author lu
	 *
	 */
	public static enum SuperAdmin {
		NO(0, "否"), YES(1,"是");
		public int key;
		public String value;
		private SuperAdmin(int key, String value) {
			this.key = key;
			this.value = value;
		}
		public static SuperAdmin get(int key) {
			SuperAdmin[] values = SuperAdmin.values();
			for (SuperAdmin object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
	}
	
}