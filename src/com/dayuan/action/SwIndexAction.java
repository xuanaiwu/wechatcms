package com.dayuan.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.BusInsuranceIntegra;
import com.dayuan.bean.SysUser;
import com.dayuan.model.BusInsuranceIntegraModel;
import com.dayuan.model.DataSwIndexModel;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;
import com.dayuan.utils.Constant.SuperAdmin;

@Controller
@RequestMapping("/SwIndex") 
public class SwIndexAction extends BaseAction{
	
	/**
	 * 跳转到信息维护页面
	 * */
	@RequestMapping("/swIndexManage")
	public ModelAndView swIndexManage(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		return forword("swindex/swIndexManage",context);
	}
	
	
	@RequestMapping("/dataList")
	public void dataList(DataSwIndexModel dataSwIndex,HttpServletRequest request,HttpServletResponse response)throws SQLException,Exception{
		if(dataSwIndex!=null){
			//List<BusInsuranceIntegra> integraList=busInsuranceIntegraService.queryByList(integraModel);
			Map<String,Object> map=new HashMap<String,Object>();
		//	map.put("total",integraModel.getPager().getRowCount());
			//map.put("rows",integraList);
			HtmlUtil.writerJson(response,map);
		}
	}

}
