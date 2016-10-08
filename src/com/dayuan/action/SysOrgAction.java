package com.dayuan.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.SysOrg;
import com.dayuan.model.SysOrgModel;
import com.dayuan.model.SysUserModel;
import com.dayuan.service.SysOrgService;
import com.dayuan.utils.HtmlUtil;


@Controller
@RequestMapping("/sysOrg") 
public class SysOrgAction extends  BaseAction{
	
	
	@Autowired(required=false)
	private SysOrgService<SysOrg> sysOrgService;
	
	
	
	@RequestMapping("/org") 
	public ModelAndView org(SysOrgModel model,HttpServletRequest request)throws Exception{
		Map<String,Object>  context = getRootMap();
		List<SysOrg> datalist=sysOrgService.queryByList(model);
		context.put("datalist", datalist);
		return forword("sys/sysOrg",context); 
	}
	
	@RequestMapping("dataList")
	public void dataList(SysUserModel model,HttpServletResponse response)throws Exception{
		
		List<SysOrg> datalist=sysOrgService.queryByList(model);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",model.getPager().getRowCount());
		jsonMap.put("rows", datalist);
		HtmlUtil.writerJson(response, jsonMap);
	}

}
