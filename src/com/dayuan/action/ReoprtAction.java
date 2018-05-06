package com.dayuan.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.DataSwIndex;
import com.dayuan.bean.SysUser;
import com.dayuan.edit.MyEditor;
import com.dayuan.model.DataSwIndexModel;
import com.dayuan.service.DataSwIndexService;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;

@Controller
@RequestMapping("/Report") 
public class ReoprtAction extends BaseAction{
	@Autowired
	private DataSwIndexService<DataSwIndex> dataSwIndexService;
	
	
	 @InitBinder  
	   protected void initBinder(WebDataBinder binder) {  
			 binder.registerCustomEditor(Date.class, new CustomDateEditor(
	                new SimpleDateFormat("yyyy-MM-dd"), true));  
			 binder.registerCustomEditor(int.class,new MyEditor()); 
	   }  
	
	/**
	 * 跳转到报表管理页面
	 * */
	@RequestMapping("/reportManage")
	public ModelAndView reportManage(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		List<DataSwIndex> typeList=dataSwIndexService.queryIndexType();
		context.put("typeList", typeList);
		context.put("user", user);
		return forword("report/reportManage",context);
	}
	
	/**
	 * 多个折线折线图
	 * */
	@RequestMapping("/getLineReport")
	public void getLineReport(DataSwIndexModel model,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		List<DataSwIndex> list=dataSwIndexService.querySimpleLine(model);
		map.put("result", true);
		map.put("value", list);
		HtmlUtil.writerJson(response,map);
	}
	
	
	/**
	 * 跳转到简单折线图页面
	 * */
	@RequestMapping("/toSimpleLine")
	public ModelAndView toSimpleLine(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		List<DataSwIndex> typeList=dataSwIndexService.queryIndexType();
		context.put("typeList", typeList);
		context.put("user", user);
		return forword("report/simpleLine",context);
	}
	
	/**
	 * 简单折线图
	 * */
	@RequestMapping("/getSimpleLineReport")
	public void getSimpleLineReport(DataSwIndexModel model,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		List<DataSwIndex> list=dataSwIndexService.querySimpleLine(model);
		map.put("result", true);
		map.put("value", list);
		HtmlUtil.writerJson(response,map);
	}
	
	
	
	/**
	 * 跳转到pe、pb页面
	 * */
	@RequestMapping("/peAndPbreport")
	public ModelAndView peAndPbreport(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		List<DataSwIndex> typeList=dataSwIndexService.queryIndexType();
		context.put("typeList", typeList);
		context.put("user", user);
		return forword("report/peAndPbreport",context);
	}
	
	/**
	 * pe、pb折线图
	 * */
	@RequestMapping("/getpeAndPbreport")
	public void getpeAndPbreport(DataSwIndexModel model,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		List<DataSwIndex> list=dataSwIndexService.querySimpleLine(model);
		map.put("result", true);
		map.put("value", list);
		HtmlUtil.writerJson(response,map);
	}

}
