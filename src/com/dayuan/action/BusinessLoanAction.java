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

import com.dayuan.bean.BusLoanInfo;
import com.dayuan.exception.ServiceException;
import com.dayuan.model.BusLoanInfoModel;
import com.dayuan.service.BusLoanInfoService;
import com.dayuan.utils.CreateWords;
import com.dayuan.utils.DateUtil;
import com.dayuan.utils.HtmlUtil;




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
	public void save(BusLoanInfo busLoanInfo,HttpServletResponse response) throws Exception{
		if(busLoanInfo==null){
			sendFailureMessage(response,"请不要非法操作~！");
			return;
		}
		if(busLoanInfo.getId()==null){
			busLoanInfoService.add(busLoanInfo);
		}else{
			busLoanInfoService.update(busLoanInfo);
		}
		sendSuccessMessage(response,"保存成功！");
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
	public void createWords(Integer id,String wordType,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
		String path="/";
		String outFilePath="D:/";
		System.out.println("path="+path);
		CreateWords createWords=new CreateWords();
		boolean sign=false;
		String wordName="";
		if(wordType.equals("0")){
			String tempName1="";
			String tempName2="";
			dataMap.put("kehuxingming",busLoanInfo.getApplicationName());
			dataMap.put("xingming", busLoanInfo.getApplicationName());
			dataMap.put("nian", DateUtil.getNowYear());
			dataMap.put("yue", DateUtil.getNowMonth());
			dataMap.put("ri",DateUtil.getNowDay());
			tempName1="贷后须知"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"daihouxuzhi.ftl",outFilePath,tempName1);
			if(dataMap.get("xingming")!=null){
				dataMap.remove("xingming");
			}
			dataMap.put("xingming",busLoanInfo.getLegalPerson());
			dataMap.put("xingbie", busLoanInfo.getGender());
			dataMap.put("shenfenzheng", busLoanInfo.getIdCard());
			dataMap.put("gongsimingcheng", busLoanInfo.getCompanyName());
			tempName2="签字样本"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"qianziyangben.ftl",outFilePath,tempName2);
			wordName=tempName1+","+tempName2;
		}else if(wordType.equals("1")){
			dataMap.put("kehuxingming",busLoanInfo.getApplicationName());
			dataMap.put("xingming", busLoanInfo.getApplicationName());
			dataMap.put("nian", DateUtil.getNowYear());
			dataMap.put("yue", DateUtil.getNowMonth());
			dataMap.put("ri",DateUtil.getNowDay());
			wordName="贷后须知"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"daihouxuzhi.ftl",outFilePath,wordName);
		}else if(wordType.equals("2")){
			dataMap.put("xingming",busLoanInfo.getLegalPerson());
			dataMap.put("xingbie", busLoanInfo.getGender());
			dataMap.put("shenfenzheng", busLoanInfo.getIdCard());
			dataMap.put("gongsimingcheng", busLoanInfo.getCompanyName());
			dataMap.put("nian", DateUtil.getNowYear());
			dataMap.put("yue", DateUtil.getNowMonth());
			dataMap.put("ri",DateUtil.getNowDay());
			wordName="签字样本"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"qianziyangben.ftl",outFilePath,wordName);
		}else{
			sendFailureMessage(response, "你输入的信息无效！");
			return;
		}
		if(sign){
			sendSuccessMessage(response, "文书生成成功！文件名是："+wordName);
			return;
		}else{
			sendFailureMessage(response, "文书生成失败,请联系管理员！");
			return;
		}
		
		
	}
	

}
