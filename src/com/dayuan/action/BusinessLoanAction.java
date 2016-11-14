package com.dayuan.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.BusLoanInfo;
import com.dayuan.bean.BusLoanInfoController;
import com.dayuan.bean.BusLoanInfoGuaranter;
import com.dayuan.bean.BusLoanInfoLegal;
import com.dayuan.bean.BusLoanInfoShop;
import com.dayuan.bean.SysUser;
import com.dayuan.form.GuaranterListForm;
import com.dayuan.form.ShopListForm;
import com.dayuan.model.BusLoanInfoModel;
import com.dayuan.service.BusLoanInfoControllerService;
import com.dayuan.service.BusLoanInfoGuaranterService;
import com.dayuan.service.BusLoanInfoLegalService;
import com.dayuan.service.BusLoanInfoService;
import com.dayuan.service.BusLoanInfoShopService;
import com.dayuan.utils.CreateWords;
import com.dayuan.utils.DateUtil;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;
import com.dayuan.utils.ZipUtil;





@Controller
@RequestMapping("/BusLoan") 
public class BusinessLoanAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(BusinessLoanAction.class);
	
	// Servrice start 商贷主表
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private BusLoanInfoService<BusLoanInfo> busLoanInfoService;
	
	// Servrice start 商贷法人
	@Autowired(required=false)
	private BusLoanInfoLegalService<BusLoanInfoLegal> busLoanInfoLegalService;
	
	// Servrice start 商贷实际控制人
	@Autowired(required=false)
	private BusLoanInfoControllerService<BusLoanInfoController> busLoanInfoControllerService;
	
	// Servrice start 经营实体
	@Autowired(required=false)
	private BusLoanInfoShopService<BusLoanInfoShop> busLoanInfoShopService;
	
	// Servrice start 保证人
	@Autowired(required=false)
	private BusLoanInfoGuaranterService<BusLoanInfoGuaranter> busLoanInfoGuaranterService;
	
	
	
	
	
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
	public void dataList(BusLoanInfoModel busLoanInfoModel,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(busLoanInfoModel!=null){
			SysUser user = SessionUtils.getUser(request);
			busLoanInfoModel.setuId(user.getId().toString());
			busLoanInfoModel.setuName(user.getNickName());
			List<BusLoanInfo> lsit=busLoanInfoService.queryByList(busLoanInfoModel);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("total",busLoanInfoModel.getPager().getRowCount());
			map.put("rows", lsit);
			HtmlUtil.writerJson(response, map);
		}
		
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(BusLoanInfo busLoanInfo,BusLoanInfoLegal busLoanInfoLegal,BusLoanInfoController busLoanInfoController,ShopListForm shopForm,GuaranterListForm guaranterForm,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		/**for(BusLoanInfoGuaranter guaranter:guaranterForm.getGuaranter()){
			System.out.println("guaranter.name="+guaranter.getGuaranterName());
		}*/
		
		boolean flag=false;
		busLoanInfo.setContent(busLoanInfo.getApplicationName()+","+busLoanInfo.getApplicationAmount()+","+busLoanInfo.getApplicationTerm());//拼接主表内容
		/**保存商贷主表信息*/
		if(busLoanInfo.getId()==null){
			int num=busLoanInfoService.save(busLoanInfo);//保存后返回插入记录数
			if(num==1){
				flag=true;
			}
		}else{
			int num=busLoanInfoService.updateReturnInfluences(busLoanInfo);//更新后返回影响记录数
			if(num==1){
				flag=true;
			}
		}
		/**保存商贷法人信息*/
		if(busLoanInfoLegal.getId()==null){
			busLoanInfoLegal.setBid(busLoanInfo.getId());//设置商贷主表id
			int num=busLoanInfoLegalService.save(busLoanInfoLegal);
			if(num!=1){
				flag=false;
			}
		}else{
			int num=busLoanInfoLegalService.updateReturnInfluences(busLoanInfoLegal);
			if(num!=1){
				flag=false;
			}
		}
		/**保存控制人信息*/
		if(busLoanInfoController.getId()==null){
			busLoanInfoController.setBid(busLoanInfo.getId());//设置商贷主表id
			int num=busLoanInfoControllerService.save(busLoanInfoController);
			if(num!=1){
				flag=false;
			}
		}else{
			int num=busLoanInfoControllerService.updateReturnInfluences(busLoanInfoController);
			if(num!=1){
				flag=false;
			}
		}
		
		/**保存实体或店铺信息*/
		for(BusLoanInfoShop shop:shopForm.getShop()){
			if(shop.getId()==null){
				shop.setBid(busLoanInfo.getId());
				int num=busLoanInfoShopService.save(shop);
				if(num!=1){
					flag=false;
				}
			}else{
				int num=busLoanInfoShopService.updateReturnInfluences(shop);
				if(num!=1){
					flag=false;
				}
			}
		}
		
		/**保存保证人信息*/
		if(busLoanInfo.getIfGuaranter().equals("是")){
			for(BusLoanInfoGuaranter guaranter:guaranterForm.getGuaranter()){
				if(guaranter.getId()==null){
					guaranter.setBid(busLoanInfo.getId());
					int num=busLoanInfoGuaranterService.save(guaranter);
					if(num!=1){
						flag=false;
					}
				}else{
					int num=busLoanInfoGuaranterService.updateReturnInfluences(guaranter);
					if(num!=1){
						flag=false;
					}
				}
				
			}
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
	 * 导出excel
	 * 
	 * */
	@RequestMapping("/exportExcel")
	public void exportExcel(Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(id==null||id.equals("")){
			sendFailureMessage(response, "请不要非法操作~！");
			return;
		}
		System.out.println("id="+id);
		BusLoanInfo busLoanInfo=this.busLoanInfoService.queryById(id);
		 // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();
     // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("商贷信息表");
        
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0); 
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        
        
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("客户姓名");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("紧急联系人");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("关系");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 4);  
        cell.setCellValue("电话");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);  
        cell.setCellValue("地址");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);  
        cell.setCellValue("公司");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);  
        cell.setCellValue("平台");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);  
        cell.setCellValue("店铺");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);  
        cell.setCellValue("子帐号");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);  
        cell.setCellValue("密码");  
        cell.setCellStyle(style);
        
        
     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        try {
			List list =null;
			 for (int i = 0; i < list.size(); i++)  
		        {  
		            row = sheet.createRow((int) i + 1);  
		           // Student stu = (Student) list.get(i);  
		            // 第四步，创建单元格，并设置值  
		         //   row.createCell((short) 0).setCellValue((double) stu.getId());  
		         //   row.createCell((short) 1).setCellValue(stu.getName());  
		         //   row.createCell((short) 2).setCellValue((double) stu.getAge());  
		            cell = row.createCell((short) 3);  
		      //      cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu  
		         //           .getBirth()));  
		        }
			 
			 FileOutputStream fout = new FileOutputStream("D:/students.xls");  
	         wb.write(fout); 
	         fout.flush();
	         fout.close(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
		
	}
	
	
	/**
	 * 生成文书（新）:在线下载 
	 * @author xuanaw
	 * @data 201611136
	 * */
	@RequestMapping("/createWordsOnLine")
	public void createWordsOnLine(Integer id,String wordType,HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		String path="\\com\\dayuan\\template\\";//模板位置
		String savePath=request.getSession().getServletContext().getRealPath("/WEB-INF/downloads");//文件保存位置,项目部署绝对路径（物理路径）
		savePath=savePath+"\\"+UUID.randomUUID();
		File fileSavePath=new File(savePath);
		if(fileSavePath.exists()){
			fileSavePath.delete();
			fileSavePath.mkdirs();
		}else{
			fileSavePath.mkdirs();
		}
		CreateWords createWords=new CreateWords();
		boolean sign=false;
		String wordName="";//保存文件名
		if(wordType.equals("1")){
			dataMap.put("xingming", busLoanInfo.getApplicationName());
			wordName="贷后须知"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"dianshangdaihouxuzhi.ftl",savePath+"\\",wordName);
		}else{
			sendFailureMessage(response, "你输入的信息无效！");
			return;
		}
		if(sign){
			String saveZipPath=request.getSession().getServletContext().getRealPath("/WEB-INF/downloads/ziptemp");//zip文件保存位置,项目部署绝对路径（物理路径）
			String zipSaveName="word"+DateUtil.getNowPlusTimeMill();
			String sourceFile=ZipUtil.fileToZip(savePath,saveZipPath,zipSaveName);//返回压缩后的zip绝对路径+文件名
			//File file=new File(savePath+"\\",wordName);不用zip压缩，直接下载
			if(sourceFile!=null){
				File file=new File(sourceFile);
				if(!file.exists()){
					sendFailureMessage(response, "创建文件程序出错了。");
					return;
				}
				//设置文件MIME类型
				response.setContentType(request.getSession().getServletContext().getMimeType(zipSaveName+".zip"));
				//设置Content-Disposition
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(zipSaveName+".zip","UTF-8"));
				//response.setHeader("content-disposition","attachment;filename"+URLEncoder.encode(wordName,"UTF-8"));
				
				FileInputStream in=new FileInputStream(sourceFile);
				OutputStream out=response.getOutputStream();
				byte buffer[]=new byte[1024];
				int len=0;
				while((len=in.read(buffer))>0){
					out.write(buffer,0,len);
				}
				in.close();
				out.close();
				if(file!=null){
					file.delete();//删除zip文件
					file=null;
				}
				if(fileSavePath!=null){
					fileSavePath=null;
				}
			}
			//sendSuccessMessage(response, "word生成成功！文件名是："+wordName);
		}else{
			sendFailureMessage(response, "word生成失败,请联系管理员！");
		}
		
	}
	
	
	/**
	 * 生成文书（旧）:生成在本地
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
		String path="\\com\\dayuan\\template\\";//模板位置
		String outFilePath="";//文件保存位置
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
		String wordName="";//保存的文件名
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
		}else{
			sendFailureMessage(response, "word生成失败,请联系管理员！");
		}
		System.out.println("createWords....end");
		
		
	}
	

}
