package com.dayuan.action;


import java.io.File;
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

import com.dayuan.bean.BusLoanInfo;
import com.dayuan.bean.BusLoanInfoLegal;
import com.dayuan.bean.SysUser;
import com.dayuan.exception.ServiceException;
import com.dayuan.model.BusLoanInfoModel;
import com.dayuan.service.BusLoanInfoService;
import com.dayuan.utils.CreateWords;
import com.dayuan.utils.DateUtil;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;




@Controller
@RequestMapping("/BusLoan") 
public class BusinessLoanAction extends BaseAction{
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private BusLoanInfoService<BusLoanInfo> busLoanInfoService;
	
	
	
	/**
	 * 跳转到loan列表
	 * */
	@RequestMapping("/loan") 
	public ModelAndView loan(HttpServletRequest request)throws Exception{
		Map<String,Object>  context = getRootMap();
		SysUser user = SessionUtils.getUser(request);
		context.put("user", user);
		return forword("bus/busLoan",context); 
	}
	
	
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList")
	public void dataList(BusLoanInfoModel busLoanInfoModel,HttpServletResponse response)throws Exception{
		List<BusLoanInfo> lsit=busLoanInfoService.queryByList(busLoanInfoModel);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total",busLoanInfoModel.getPager().getRowCount());
		map.put("rows", lsit);
		HtmlUtil.writerJson(response, map);	
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(BusLoanInfo busLoanInfo,BusLoanInfoLegal busLoanInfoLegal ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println("uName="+busLoanInfo.getuName());
		System.out.println("surveyOrgName="+busLoanInfo.getSurveyOrgName());
		System.out.println("legalPerson="+busLoanInfoLegal.getLegalPerson());
		System.out.println("idCard="+busLoanInfoLegal.getIdCard());
		int rowCount=0;
		int guaranterRowCount=0;
		boolean flag=false;
		if(!request.getParameter("rowCount").equals("")){
			rowCount=Integer.parseInt(request.getParameter("rowCount").trim().toString());
		}
		if(!request.getParameter("guaranterRowCount").equals("")){
			guaranterRowCount=Integer.parseInt(request.getParameter("guaranterRowCount").trim().toString());
		}
		busLoanInfo.setContent(busLoanInfo.getApplicationName()+","+busLoanInfo.getApplicationAmount()+","+busLoanInfo.getApplicationTerm());
		if(busLoanInfo.getId()==null){
			int num=busLoanInfoService.save(busLoanInfo);
			if(num==1){
				flag=true;
			}
			System.out.println("save="+num);
			System.out.println("busLoanInfo.id="+busLoanInfo.getId());
		}else{
			int num=busLoanInfoService.updateReturnInfluences(busLoanInfo);
			if(num==1){
				flag=true;
			}
			System.out.println("update="+num);
		}
		if(flag){
			sendSuccessMessage(response,"保存成功！");
		}else{
			sendFailureMessage(response, "保存失败！");
		}
		
	}
	
	
	/**
	 * 根据id获取对于记录
	 * @param id
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/getId")
	public void getId(Integer id,HttpServletResponse response) throws Exception{
		Map<String,Object> content=getRootMap();
		BusLoanInfo busLoanInfo=this.busLoanInfoService.queryById(id);
		if(busLoanInfo==null){
			sendFailureMessage(response,"没有找到对应记录~");
			return;
		}
		content.put(SUCCESS, true);
		content.put("data", busLoanInfo);
		HtmlUtil.writerJson(response, content);
	}
	
	
	/**
	 * 根据id删除记录
	 * */
	@RequestMapping("/delete")
	public void delete(Integer[] id,HttpServletResponse response)throws Exception{
		busLoanInfoService.delete(id);
		sendSuccessMessage(response,"删除成功！");
		
	}
	
	
	/**
	 * 生成文书
	 * */
	@RequestMapping("/createWords")
	public void createWords(Integer id,String wordType,String filePath,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(id==null||wordType.equals("")){
			sendFailureMessage(response, "请不要非法操作~！");
			return;
		}
		BusLoanInfo busLoanInfo=this.busLoanInfoService.queryById(id);
		if(busLoanInfo==null){
			sendFailureMessage(response, "没有找到对应记录~！");
			return;
		}
		Map<String,Object> dataMap=new HashMap<String,Object>();
		//String path=request.getRealPath("/")+"wordsTemplate"+"\\daihouxuzhi.ftl";
		String path="\\com\\dayuan\\template\\";
		String outFilePath="";
		if(filePath.equals("")){
			outFilePath="D://createWords//";
		}else{
			outFilePath=filePath+"/";
		}
		File file=new File(outFilePath);
		if(!file.exists()){
			file.mkdirs();
		}
		if(!file.isDirectory()){
			sendFailureMessage(response, "你输入word保存的目录格式错误！");
			return;
		}
		CreateWords createWords=new CreateWords();
		boolean sign=false;
		String wordName="";
		if(wordType.equals("0")){
			String tempName1="";
			String tempName2="";
			dataMap.put("xingming", busLoanInfo.getApplicationName());
			dataMap.put("xingming2", busLoanInfo.getApplicationName());
			dataMap.put("dianhua", busLoanInfo.getSurveyPhone());
			tempName1="贷后须知"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"dianshangdaihouxuzhi.ftl",outFilePath,tempName1);
			if(dataMap.get("xingming")!=null){
				dataMap.remove("xingming");
			}
			/*dataMap.put("xingming",busLoanInfo.getLegalPerson());
			dataMap.put("xingbie", busLoanInfo.getGender());
			dataMap.put("shenfenzheng", busLoanInfo.getIdCard());
			dataMap.put("gongsimingcheng", busLoanInfo.getCompanyName());*/
			dataMap.put("nian", DateUtil.getNowYear());
			dataMap.put("yue", DateUtil.getNowMonth());
			dataMap.put("ri",DateUtil.getNowDay());
			tempName2="签字样本"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"qianziyangben.ftl",outFilePath,tempName2);
			wordName=tempName1+","+tempName2;
		}else if(wordType.equals("1")){
			dataMap.put("xingming", busLoanInfo.getApplicationName());
			dataMap.put("xingming2", busLoanInfo.getApplicationName());
			dataMap.put("dianhua", busLoanInfo.getSurveyPhone());
			wordName="贷后须知"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"dianshangdaihouxuzhi.ftl",outFilePath,wordName);
		}else if(wordType.equals("2")){
			/*
			dataMap.put("xingming",busLoanInfo.getLegalPerson());
			dataMap.put("xingbie", busLoanInfo.getGender());
			dataMap.put("shenfenzheng", busLoanInfo.getIdCard());
			dataMap.put("gongsimingcheng", busLoanInfo.getCompanyName());*/
			dataMap.put("nian", DateUtil.getNowYear());
			dataMap.put("yue", DateUtil.getNowMonth());
			dataMap.put("ri",DateUtil.getNowDay());
			wordName="签字样本"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"qianziyangben.ftl",outFilePath,wordName);
		}else{
			sendFailureMessage(response, "你输入的信息无效！");
			return;
		}
		if(file!=null){
			file=null;
		}
		if(sign){
			sendSuccessMessage(response, "word生成成功！文件名是："+wordName);
			return;
		}else{
			sendFailureMessage(response, "word生成失败,请联系管理员！");
			return;
		}
		
		
	}
	

}
