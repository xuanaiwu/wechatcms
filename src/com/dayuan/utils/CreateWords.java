package com.dayuan.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <br>
 * <b>功能：</b>生成words<br>
 * <b>作者：</b>禤爱武<br>
 * <b>日期：</b> 2016-10-16 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class CreateWords {
	private Configuration configuration = null;
	
	public CreateWords(){
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}
	
	public boolean create(Map<String,Object> dataMap,String path,String templeName,String outFilePath,String outFileName){
		Boolean sign=false;
		configuration.setClassForTemplateLoading(this.getClass(),path);  //模板FTL文件所存在的位置
		Template t=null;
		try {
			t = configuration.getTemplate(templeName); //模板文件名
		} catch (IOException e) {
			e.printStackTrace();
		}
		File outFile = new File(outFilePath+outFileName);
		Writer out=null;
		try{
			out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try {
			t.process(dataMap, out);
			out.flush();
			out.close();
			sign=true;
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(t!=null){
				t=null;
			}
			if(outFile!=null){
				outFile=null;
			}
		}
		return sign;
	}

}
