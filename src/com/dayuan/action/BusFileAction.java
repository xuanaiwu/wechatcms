package com.dayuan.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.BusBiling;
import com.dayuan.bean.BusFiles;
import com.dayuan.bean.BusLending;
import com.dayuan.bean.BusLoanInfo;
import com.dayuan.bean.BusLoanInfoController;
import com.dayuan.bean.BusLoanInfoLegal;
import com.dayuan.bean.SysUser;
import com.dayuan.form.GuaranterListForm;
import com.dayuan.form.ShopListForm;
import com.dayuan.model.BusFileModel;
import com.dayuan.service.BusFileService;
import com.dayuan.utils.DateUtil;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;

@Controller
@RequestMapping("/BusFile") 
public class BusFileAction extends BaseAction{
	
	//Service start 建档
	@Autowired(required=false)
	private BusFileService<BusFiles> busFileService;
	
	
	/**
	 * 跳转到建档页面
	 * */
	@RequestMapping("/loanAdd")
	public ModelAndView loanAdd(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		return forword("bus/busLoanAdd",context);
	}
	
	/**
	 * 跳转到信息页面
	 * */
	@RequestMapping("/filing")
	public ModelAndView filing(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		return forword("bus/filing",context);
	}
	
	
	@RequestMapping("/dataList")
	public void dataList(BusFileModel busFileModel,HttpServletRequest request,HttpServletResponse response){
		log.info("lNmae="+busFileModel.getlName());
		if(busFileModel!=null){
			SysUser user = SessionUtils.getUser(request);
			busFileModel.setlUserName(user.getNickName());
			busFileModel.setlUId(user.getId().toString());
			try{
				List<BusFiles> list=busFileService.queryByList(busFileModel);
				List<BusFiles> list2=new ArrayList<BusFiles>();
				for(BusFiles busFiles:list){
					BusFiles busFiles2=new BusFiles();
					busFiles2.setId(busFiles.getId());
					busFiles2.setlName(busFiles.getlName());
					busFiles2.setlIdCard(busFiles.getlIdCard());
					busFiles2.setlTelPhone(busFiles.getlTelPhone());
					busFiles2.setlStatus(busFiles.getlStatus());
					if(busFiles.getCreateTime()!=null){
						busFiles2.setCreateTime2(DateUtil.getFormattedDateUtil((Date)busFiles.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					}else{
						busFiles2.setCreateTime2("");
					}
					if(busFiles.getUpdateTime()!=null){
						busFiles2.setUpdateTime2(DateUtil.getFormattedDateUtil((Date)busFiles.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
					}else{
						busFiles2.setUpdateTime2("");
					}
					list2.add(busFiles2);
				}
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("total",busFileModel.getPager().getRowCount());
				map.put("rows",list2);
				HtmlUtil.writerJson(response,"",map);
			}catch(Exception e){
				log.error("获取BusFile列表出错："+e.getMessage());
			}
		}
		
	}

	@RequestMapping("/save")
	public void save(BusFiles busFiles,BusLoanInfo busLoanInfo,BusLoanInfoLegal busLoanInfoLegal,BusLoanInfoController busLoanInfoController,ShopListForm shopForm,GuaranterListForm guaranterForm,BusLending busLending,BusBiling busBiling,HttpServletRequest request,HttpServletResponse response){
		boolean flag=false;
		int num=0;
		String message="";
		if(busFiles!=null){
			busFiles.setCreateTime(new Date());
			try{
				num=busFileService.save(busFiles);
				if(num>0){
					flag=true;
				}
			}catch(Exception e){
				log.error("BusFile保存出错："+e.getMessage());
			}
		}
		if(flag){
			message="保存成功！";
		}else{
			message="保存出错";
		}
		log.info(message);
		sendSuccessMessage(response,message);
	}

}
