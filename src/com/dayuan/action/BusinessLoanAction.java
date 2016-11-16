package com.dayuan.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
import com.dayuan.utils.StringUtil;
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
			List<BusLoanInfo> list=busLoanInfoService.queryByList(busLoanInfoModel);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("total",busLoanInfoModel.getPager().getRowCount());
			map.put("rows", list);
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
	 * 导出所有数据到excel
	 * */
	@RequestMapping("/exportAllExcel")
	public void exportAllExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SysUser user = SessionUtils.getUser(request);
		BusLoanInfoModel busLoanInfoModel=new BusLoanInfoModel();
		busLoanInfoModel.setuId(user.getId().toString());
		busLoanInfoModel.setuName(user.getNickName());
		List<BusLoanInfo> list=busLoanInfoService.queryList(busLoanInfoModel);
		if(list!=null&&list.size()>0){
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("贷后台帐");
	        
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0); 
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();
	        // 创建一个居中格式
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	        
	        
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("序号");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("客户姓名"); //商贷主表，applicationName 
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("紧急联系人");//商贷主表，urgentCont 
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("关系"); //商贷主表， relationship
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("电话");  //法人，legalPhone
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("地址");  //法人，houseAddress
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("公司");  //法人，companyName
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("平台");//  经验实体，platformName
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("店铺");  //经验实体，shopName
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("子帐号");  //经验实体,subAccount
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 10);  
	        cell.setCellValue("密码");  //经验实体,sbuPassword
	        cell.setCellStyle(style);
	        
			for(int i=0;i<list.size();i++){
				BusLoanInfo busLoanInfo=list.get(i);
				Integer bid=busLoanInfo.getId();
				BusLoanInfoLegal busLoanInfoLegal=this.busLoanInfoLegalService.getBusLoanInfoLegal(bid);
				BusLoanInfoShop busLoanInfoShop=this.busLoanInfoShopService.getBusLoanInfoShop(bid);
				
				row = sheet.createRow((int) 1 + i);      
			    row.createCell((short) 0).setCellValue(1);  
			    row.createCell((short) 1).setCellValue(StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));  
			    row.createCell((short) 2).setCellValue(StringUtil.getNotNullStr(busLoanInfo.getUrgentCont())); 
			    row.createCell((short) 3).setCellValue(StringUtil.getNotNullStr(busLoanInfo.getRelationship()));
			    row.createCell((short) 4).setCellValue(StringUtil.getNotNullStr(busLoanInfoLegal.getLegalPhone()));
			    row.createCell((short) 5).setCellValue(StringUtil.getNotNullStr(busLoanInfoLegal.getHouseAddress()));
			    row.createCell((short) 6).setCellValue(StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
			    row.createCell((short) 7).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getPlatformName()));
			    row.createCell((short) 8).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getShopName()));
			    row.createCell((short) 9).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getSubAccount()));
			    row.createCell((short) 10).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getSbuPassword()));
				
				
			}
			String savePath=request.getSession().getServletContext().getRealPath("/WEB-INF/downloads/excelfiles");//文件保存位置,项目部署绝对路径（物理路径）
			savePath=savePath+"\\"+UUID.randomUUID();//文件最终保存路径
			File fileSavePath=new File(savePath);
			/**创建要保存的文件夹*/
			if(fileSavePath.exists()){
				fileSavePath.delete();
				fileSavePath.mkdirs();
			}else{
				fileSavePath.mkdirs();
			}
			String excel="生成贷后台帐"+DateUtil.getNowPlusTimeMill()+".xls";//eccel文件名称
			BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(savePath+"\\"+excel));  //创建文件
	        wb.write(fout); //写入excel数据
	        fout.flush();
	        fout.close();
	        //设置文件MIME类型
		    response.setContentType(request.getSession().getServletContext().getMimeType(excel));
		    //设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(excel,"UTF-8"));
			
			BufferedInputStream in=new BufferedInputStream(new FileInputStream(savePath+"\\"+excel));//下载时,把文件读入io流
			OutputStream out=new BufferedOutputStream(response.getOutputStream());
			byte buffer[]=new byte[1024];
			int len=0;
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);//向response写入数据
			}
			in.close();
			out.flush();
			out.close();
			/**删除文件*/
			File file=new File(savePath+"\\"+excel);
			if(file!=null){
				if(file.exists()){
					file.delete();//删除文件
				}
				file=null;
			}
			/**删除文件夹*/
			if(fileSavePath!=null){
				if(fileSavePath.exists()){
					fileSavePath.delete();
				}
				fileSavePath=null;
			}
		}
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
		BusLoanInfo busLoanInfo=this.busLoanInfoService.queryById(id);
		if(busLoanInfo==null){
			sendFailureMessage(response, "找不到对应记录！");
			return;
		}
		Integer bid=busLoanInfo.getId();
		if(bid==null){
			sendFailureMessage(response, "找不到对应记录！");
			return;
		}
		BusLoanInfoLegal busLoanInfoLegal=this.busLoanInfoLegalService.getBusLoanInfoLegal(bid);
		BusLoanInfoShop busLoanInfoShop=this.busLoanInfoShopService.getBusLoanInfoShop(bid);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("贷后台帐");
        
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0); 
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        
        
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("客户姓名"); //商贷主表，applicationName 
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("紧急联系人");//商贷主表，urgentCont 
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("关系"); //商贷主表， relationship
        cell.setCellStyle(style); 
        cell = row.createCell((short) 4);  
        cell.setCellValue("电话");  //法人，legalPhone
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);  
        cell.setCellValue("地址");  //法人，houseAddress
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);  
        cell.setCellValue("公司");  //法人，companyName
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);  
        cell.setCellValue("平台");//  经验实体，platformName
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);  
        cell.setCellValue("店铺");  //经验实体，shopName
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);  
        cell.setCellValue("子帐号");  //经验实体,subAccount
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);  
        cell.setCellValue("密码");  //经验实体,sbuPassword
        cell.setCellStyle(style);
        
        
       // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        try {
		
			  
        	row = sheet.createRow((int) 0 + 1);      
		    row.createCell((short) 0).setCellValue(1);  
		    row.createCell((short) 1).setCellValue(StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));  
		    row.createCell((short) 2).setCellValue(StringUtil.getNotNullStr(busLoanInfo.getUrgentCont())); 
		    row.createCell((short) 3).setCellValue(StringUtil.getNotNullStr(busLoanInfo.getRelationship()));
		    row.createCell((short) 4).setCellValue(StringUtil.getNotNullStr(busLoanInfoLegal.getLegalPhone()));
		    row.createCell((short) 5).setCellValue(StringUtil.getNotNullStr(busLoanInfoLegal.getHouseAddress()));
		    row.createCell((short) 6).setCellValue(StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
		    row.createCell((short) 7).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getPlatformName()));
		    row.createCell((short) 8).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getShopName()));
		    row.createCell((short) 9).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getSubAccount()));
		    row.createCell((short) 10).setCellValue(StringUtil.getNotNullStr(busLoanInfoShop.getSbuPassword()));
		    // Student stu = (Student) list.get(i);  
            // 第四步，创建单元格，并设置值  
		    //  cell = row.createCell((short) 3);  
		    //   cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu.getBirth()));  
		    //与             row.createCell((short) 10).setCellValue(busLoanInfoShop.getSbuPassword());相同
		        
		    String savePath=request.getSession().getServletContext().getRealPath("/WEB-INF/downloads/excelfiles");//文件保存位置,项目部署绝对路径（物理路径）
		    savePath=savePath+"\\"+UUID.randomUUID();//文件最终保存路径
		    File fileSavePath=new File(savePath);
		    /**创建要保存的文件夹*/
			if(fileSavePath.exists()){
				fileSavePath.delete();
				fileSavePath.mkdirs();
			}else{
				fileSavePath.mkdirs();
			}
			String excel="生成贷后台帐"+DateUtil.getNowPlusTimeMill()+".xls";//eccel文件名称
			BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(savePath+"\\"+excel));  //创建文件
	        wb.write(fout); //写入excel数据
	        fout.flush();
	        fout.close();
	        //设置文件MIME类型
		    response.setContentType(request.getSession().getServletContext().getMimeType(excel));
		    //设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(excel,"UTF-8"));
			//下载时,把文件读入io流
			BufferedInputStream in=new BufferedInputStream(new FileInputStream(savePath+"\\"+excel));
			OutputStream out=new BufferedOutputStream(response.getOutputStream());
			byte buffer[]=new byte[1024];
			int len=0;
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);//向response写入数据
			}
			in.close();
			out.flush();
			out.close();
			/**删除文件*/
			File file=new File(savePath+"\\"+excel);
			if(file!=null){
				if(file.exists()){
					file.delete();//删除文件
				}
				file=null;
			}
			/**删除文件夹*/
			if(fileSavePath!=null){
				if(fileSavePath.exists()){
					fileSavePath.delete();
				}
				fileSavePath=null;
			}
		} catch (Exception e) {
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
		Integer bid=busLoanInfo.getId();
		BusLoanInfoLegal busLoanInfoLegal=this.busLoanInfoLegalService.getBusLoanInfoLegal(bid);
		BusLoanInfoShop busLoanInfoShop=this.busLoanInfoShopService.getBusLoanInfoShop(bid);
		BusLoanInfoGuaranter busLoanInfoGuaranter=this.busLoanInfoGuaranterService.getBusLoanInfoGuaranter(bid);
		
		if(busLoanInfo==null){
			sendFailureMessage(response, "没有找到对应记录~！");
			return;
		}
		Map<String,Object> dataMap=new HashMap<String,Object>();
		String path="\\com\\dayuan\\template\\";//模板位置
		String savePath=request.getSession().getServletContext().getRealPath("/WEB-INF/downloads");//文件保存位置,项目部署绝对路径（物理路径）
		savePath=savePath+"\\"+UUID.randomUUID();//最后保存路径
		/**创建文件夹 */
		File fileSavePath=new File(savePath);
		if(fileSavePath.exists()){
			fileSavePath.delete();
			fileSavePath.mkdirs();
		}else{
			fileSavePath.mkdirs();
		}
		CreateWords createWords=new CreateWords();//封装好的word生成类
		boolean sign=false;
		String wordName="";//保存文件名
		if(wordType.equals("1")){
			dataMap.put("applicationName", StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
			wordName="电商贷客户贷后须知"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"dianshangdaihouxuzhi1.ftl",savePath+"\\",wordName);
		}else if(wordType.equals("2")){
			dataMap.put("companyName",StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
			wordName="董事会(股东会)成员名单及签字样本(参考文本)"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"chengyuanmingdan2.ftl",savePath+"\\",wordName);
		}else if(wordType.equals("3")){
			dataMap.put("legalPerson",StringUtil.getNotNullStr(busLoanInfoLegal.getLegalPerson()));
			dataMap.put("gender",StringUtil.getNotNullStr(busLoanInfoLegal.getGender()));
			dataMap.put("idCard",StringUtil.getNotNullStr(busLoanInfoLegal.getIdCard()));
			dataMap.put("companyName",StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
			wordName="法定代表人证明书及签字样本"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"fadingdaibiaoren3.ftl",savePath+"\\",wordName);
		}else if(wordType.equals("4")){
			dataMap.put("companyName1",StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
			dataMap.put("companyName2",StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
			dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
			wordName="股东会决议"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"gudonghuijueyi4.ftl",savePath+"\\",wordName);
		}else if(wordType.equals("5")){
			dataMap.put("companyName",StringUtil.getNotNullStr(busLoanInfoLegal.getCompanyName()));
			wordName="预留公章样本"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"yuliugongzhangyangben5.ftl",savePath+"\\",wordName);
		}else if(wordType.equals("61")){
			dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterName()));
			dataMap.put("guaranterCard",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterCard()));
			dataMap.put("guaranterHouseAddress",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterHouseAddress()));
			dataMap.put("guaranterEmployer",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterEmployer()));
			dataMap.put("guaranterPhone",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterPhone()));
			dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
			wordName="个人最高额保证合同1"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"baozhenghetong61.ftl",savePath+"\\",wordName);
		}else if(wordType.equals("62")){
			dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterName()));
			dataMap.put("guaranterCard",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterCard()));
			dataMap.put("guaranterHouseAddress",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterHouseAddress()));
			dataMap.put("guaranterEmployer",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterEmployer()));
			dataMap.put("guaranterPhone",StringUtil.getNotNullStr(busLoanInfoGuaranter.getGuaranterPhone()));
			dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
			wordName="个人最高额保证合同2"+DateUtil.getNowPlusTimeMill()+".doc";
			sign=createWords.create(dataMap,path,"baozhenghetong62.ftl",savePath+"\\",wordName);
		}else{
			sendFailureMessage(response, "你输入的信息无效！");
			return;
		}
		if(sign){
			String saveZipPath=request.getSession().getServletContext().getRealPath("/WEB-INF/downloads/ziptemp");//zip文件保存位置,项目部署绝对路径（物理路径）
			String zipSaveName="word"+DateUtil.getNowPlusTimeMill()+".zip";//压缩文件名
			String sourceFile=ZipUtil.fileToZip(savePath,saveZipPath,zipSaveName);//封装好的压缩方法，返回压缩后的zip绝对路径+文件名
			//File file=new File(savePath+"\\",wordName);不用zip压缩，直接下载
			if(sourceFile!=null){
				File file=new File(sourceFile);
				if(!file.exists()){
					sendFailureMessage(response, "创建文件程序出错了。");
					return;
				}
				//设置文件MIME类型
				response.setContentType(request.getSession().getServletContext().getMimeType(zipSaveName));
				//设置Content-Disposition
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(zipSaveName,"UTF-8"));
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
