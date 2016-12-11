package com.dayuan.action;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.BusBiling;
import com.dayuan.bean.BusFiles;
import com.dayuan.bean.BusLending;
import com.dayuan.bean.BusLoanInfo;
import com.dayuan.bean.BusLoanInfoController;
import com.dayuan.bean.BusLoanInfoGuaranter;
import com.dayuan.bean.BusLoanInfoLegal;
import com.dayuan.bean.BusLoanInfoShop;
import com.dayuan.bean.SysUser;
import com.dayuan.form.GuaranterListForm;
import com.dayuan.form.ShopListForm;
import com.dayuan.model.BusFileModel;
import com.dayuan.service.BusBilingService;
import com.dayuan.service.BusFileService;
import com.dayuan.service.BusLendingService;
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
@RequestMapping("/BusFile") 
public class BusFileAction extends BaseAction{
	
	//Service start 建档
	@Autowired(required=false)
	private BusFileService<BusFiles> busFileService;
	
	// Service start 商贷主表
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private BusLoanInfoService<BusLoanInfo> busLoanInfoService;
	
	
	// Service start 商贷法人
	@Autowired(required=false)
	private BusLoanInfoLegalService<BusLoanInfoLegal> busLoanInfoLegalService;
	
	
	// Service start 商贷实际控制人
	@Autowired(required=false)
	private BusLoanInfoControllerService<BusLoanInfoController> busLoanInfoControllerService;
	
	
	// Service start 经营实体
	@Autowired(required=false)
	private BusLoanInfoShopService<BusLoanInfoShop> busLoanInfoShopService;
	
	
	// Service start 保证人
	@Autowired(required=false)
	private BusLoanInfoGuaranterService<BusLoanInfoGuaranter> busLoanInfoGuaranterService;
	
	// Service start 放款信息
	@Autowired(required=false)
	private BusLendingService<BusLending> busLendingService;
	
	// Service start 贷后台帐信息
	@Autowired(required=false)
	private BusBilingService<BusBiling> busBilingService;
	
	
	//DATE属性编辑器
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 跳转到建档页面
	 * */
	@RequestMapping("/loanAdd")
	public ModelAndView loanAdd(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		//return forword("bus/busLoanAdd",context);
		return forword("bus/busLoanEdit",context);
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
		/**保存建档表信息*/
		if(busFiles!=null){
			try{
				if(busFiles.getId()==null){
					busFiles.setCreateTime(new Date());
					num=busFileService.save(busFiles);
					if(num==1){
						flag=true;
					}
				}else{
					busFiles.setUpdateTime(new Date());
					num=busFileService.updateReturnInfluences(busFiles);
					if(num==1){
						flag=true;
					}
				}
			}catch(Exception e){
				log.error("BusFile保存出错："+e.getMessage());
			}
		}
		Integer lId=busFiles.getId();
		/**保存商贷主表信息*/
		if(busLoanInfo!=null){
			try{
				busLoanInfo.setlId(lId);
				busLoanInfo.setId(busLoanInfo.getLoanInfoTempId());
				if(busLoanInfo.getId()==null){
					num=busLoanInfoService.save(busLoanInfo);
					if(num!=1){
						flag=false;
					}
				}else{
					num=busLoanInfoService.updateReturnInfluences(busLoanInfo);
					if(num!=1){
						flag=false;
					}
				}
			}catch(Exception e){
				flag=false;
				log.error("BusLoanInfo保存出错："+e.getMessage());
			}
		}
		/**保存法人表信息*/
		if(busLoanInfoLegal!=null){
			try{
				busLoanInfoLegal.setBid(lId);
				busLoanInfoLegal.setId(busLoanInfoLegal.getLegalTempId());
				if(busLoanInfoLegal.getId()==null){
					num=busLoanInfoLegalService.save(busLoanInfoLegal);
					if(num!=1){
						flag=false;
					}
				}else{
					num=busLoanInfoLegalService.updateReturnInfluences(busLoanInfoLegal);
					if(num!=1){
						flag=false;
					}
				}
			}catch(Exception e){
				flag=false;
				log.error("BusLoanInfoLegal保存出错："+e.getMessage());
			}
		}
		if(busLoanInfoLegal.getIfController()!=null&&busLoanInfoLegal.getIfController().equals("否")){
			/**保存实际控制人信息*/
			if(busLoanInfoController!=null){
				try{
					busLoanInfoController.setBid(lId);
					busLoanInfoController.setId(busLoanInfoController.getControllerTempId());
					if(busLoanInfoController.getId()==null){
						num=busLoanInfoControllerService.save(busLoanInfoController);
						if(num!=1){
							flag=false;
						}
					}else{
						num=busLoanInfoControllerService.updateReturnInfluences(busLoanInfoController);
						if(num!=1){
							flag=false;
						}
					}
				}catch(Exception e){
					flag=false;
					log.error("BusLoanInfoController保存出错："+e.getMessage());
				}
			}
		}
		/**保存实体或店铺信息*/
		for(BusLoanInfoShop shop:shopForm.getShop()){
			try{
				shop.setBid(lId);
				shop.setId(shop.getTempShopId());
				if(shop.getId()==null){
					num=busLoanInfoShopService.save(shop);
					if(num!=1){
						flag=false;
					}
				}else{
					num=busLoanInfoShopService.updateReturnInfluences(shop);
					if(num!=1){
						flag=false;
					}
				}
			}catch(Exception e){
				flag=false;
				log.error("BusLoanInfoShop保存出错："+e.getMessage());
			}
			
		}
		
		/**保存保证人信息*/
		if(busLoanInfo!=null){
			if(busLoanInfo.getIfGuaranter().equals("是")){
				for(BusLoanInfoGuaranter guaranter:guaranterForm.getGuaranter()){
					guaranter.setBid(lId);
					guaranter.setId(guaranter.getGuaranterTempId());
					if(guaranter.getId()==null){
						num=busLoanInfoGuaranterService.save(guaranter);
						if(num!=1){
							flag=false;
						}
					}else{
						num=busLoanInfoGuaranterService.updateReturnInfluences(guaranter);
						if(num!=1){
							flag=false;
						}
					}
					
				}
			}
		}
		
		/**保存放款信息*/
		if(busLending!=null){
			try{
				busLending.setBid(lId);
				busLending.setId(busLending.getLendingTempId());
				if(busLending.getId()==null){
					num=busLendingService.save(busLending);
					if(num!=1){
						flag=false;
					}
				}else{
					num=busLendingService.updateReturnInfluences(busLending);
					if(num!=1){
						flag=false;
					}
				}
			}catch(Exception e){
				flag=false;
				log.error("BusLending保存出错："+e.getMessage());
			}
			
		}
		
		/**保存贷后台帐信息*/
		if(busBiling!=null){
			try{
				busBiling.setBid(lId);
				busBiling.setId(busBiling.getBilingTempId());
				if(busBiling.getId()==null){
					num=busBilingService.save(busBiling);
					if(num!=1){
						flag=false;
					}
				}else{
					num=busBilingService.updateReturnInfluences(busBiling);
					if(num!=1){
						flag=false;
					}
					
				}
			}catch(Exception e){
				flag=false;
				log.error("busBiling保存出错："+e.getMessage());
			}
			
		}
		
		if(flag){
			message="保存成功！";
		}else{
			message="保存出错";
		}
		log.info("BusFileAction.save："+message);
		sendSuccessMessage(response,message);
	}
	
	/**
	 * 根据id获取busfiles
	 * */
	@RequestMapping("getId")
	public void getId(Integer id,HttpServletResponse response){
		if(id!=null){
			try{
				Map<String,Object> content=getRootMap();
				BusFiles busFiles=busFileService.queryById(id);
				if(busFiles==null){
					sendFailureMessage(response,"没有找到对应记录!");
					return;
				}
				content.put(SUCCESS, true);
				content.put("data", busFiles);
				HtmlUtil.writerJson(response,"",content);
			}catch(Exception e){
				log.error("getId方法出错："+e.getMessage());
			}
		}
	}
	
	/**
	 * 跳转到建档编辑页面
	 * */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		Integer id=Integer.parseInt(request.getParameter("id").trim());
		if(id==null||id.equals("")){
			return forword("error/error",context);
		}
		try{
			BusFiles busFiles=busFileService.queryById(id);
			if(busFiles!=null){
				context.put("busFiles",busFiles);
			}
			BusLoanInfo busLoanInfo=busLoanInfoService.queryByLId(id);
			if(busLoanInfo!=null){
				context.put("busLoanInfo",busLoanInfo);
			}
			BusLoanInfoLegal busLoanInfoLegal=busLoanInfoLegalService.getBusLoanInfoLegal(id);
			if(busLoanInfoLegal!=null){
				context.put("busLoanInfoLegal", busLoanInfoLegal);
				if(busLoanInfoLegal.getIfController().equals("否")){
					BusLoanInfoController busLoanInfoController=busLoanInfoControllerService.getBusLoanInfoController(id);
					if(busLoanInfoController!=null){
						context.put("busLoanInfoController", busLoanInfoController);
					}
				}
			}
			List<BusLoanInfoShop> shopList=busLoanInfoShopService.queryListByBId(id);
			if(shopList!=null&&shopList.size()>0){
				context.put("shopList", shopList);
			}
			if(busLoanInfo!=null&&busLoanInfo.getIfGuaranter().equals("是")){
				List<BusLoanInfoGuaranter> guaranterList=busLoanInfoGuaranterService.queryListByBId(id);
				if(guaranterList!=null&&guaranterList.size()>0){
					context.put("guaranterList", guaranterList);
				}
			}
			BusLending busLending=busLendingService.queryByBId(id);
			if(busLending!=null){
				context.put("busLending", busLending);
			}
			BusBiling busBiling=busBilingService.queryByBId(id);
			if(busBiling!=null){
				context.put("busBiling", busBiling);
			}
		}catch(Exception e){
			log.error("toEdit出错："+e.getMessage());
		}
		return forword("bus/busLoanEdit",context);
	}
	
	
	/**单个或批量删除文件*/
	@RequestMapping("/delete")
	public void delete(Integer[] id,HttpServletResponse response){
		boolean flag=false;
		if(id!=null&&id.length>0){
			try{
				for(int i=0;i<id.length;i++){
					BusFiles busFiles=busFileService.queryById(id[i]);
					if(busFiles!=null){
						Integer lId=busFiles.getId();
						busLoanInfoService.deleteByLId(lId);
						busLoanInfoLegalService.deleteByBid(lId);
						busLoanInfoControllerService.deleteByBid(lId);
						busLoanInfoShopService.deleteByBid(lId);
						busLoanInfoGuaranterService.deleteByBid(lId);
						busLendingService.deleteByLId(lId);
						busBilingService.deleteByLId(lId);
						busFileService.delete(lId);
						flag=true;
					}
				}
			}catch(Exception e){
				log.error("删除失败！"+e.getMessage());
			}
		}
		if(flag){
			log.info("删除成功！");
			sendSuccessMessage(response,"删除成功！");
		}else{
			log.info("删除失败！");
			sendFailureMessage(response,"删除失败！");
		}
		
	}
	
	/**贷后台帐，导出Excel*/
	@RequestMapping("/exportExcel")
	public ModelAndView exportExcel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> context=getRootMap();
		SysUser user = SessionUtils.getUser(request);
		BusFileModel busFileModel=new BusFileModel();
		if(user.getSuperAdmin()!=1){
			busFileModel.setlUserName(user.getNickName());
			busFileModel.setlUId(user.getId().toString());
		}
		busFileModel.setRows(200);
		try{
			List<BusFiles> list=busFileService.queryByList(busFileModel);
			if(list!=null&&list.size()>0){
				// 第一步，创建一个webbook，对应一个Excel文件  
		        HSSFWorkbook wb = new HSSFWorkbook();
		        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		        HSSFSheet sheet = wb.createSheet("基本信息");
		        HSSFSheet sheetLoan = wb.createSheet("贷后");
		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		        HSSFRow row = sheet.createRow((int) 0);
		        HSSFRow rowLoan = sheetLoan.createRow((int) 0);
		        // 第四步，创建单元格，并设置值表头 设置表头居中  
		        HSSFCellStyle style = wb.createCellStyle();
		        // 创建一个居中格式
		        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        
		        
		        /**sheet1 基本信息*/
		        HSSFCell cell = row.createCell((short) 0);  
		        cell.setCellValue("序号");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 1);  
		        cell.setCellValue("客户经理");//busfiles.lUserName
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 2);  
		        cell.setCellValue("客户姓名");//busLoanInfo.applicationName 
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 3);  
		        cell.setCellValue("状态"); //busfiles.lStatus
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 4);  
		        cell.setCellValue("时间");  //busfiles.createTime
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 5);  
		        cell.setCellValue("客户来源");  //busLoanInfo.channel
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 6);  
		        cell.setCellValue("授信金额");  //buslending.loanAmount
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 7);  
		        cell.setCellValue("开通额度");  //buslending.openingQuota
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 8);  
		        cell.setCellValue("授信到期日");  //busBiling.creditEndDate
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 9);  
		        cell.setCellValue("融信通帐号");  //busBiling.loanAccount
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 10);  
		        cell.setCellValue("邮寄地址");  //legal.deliveryAddress
		        cell.setCellStyle(style);
		        
		        
		        /**sheet2 贷后*/
		        HSSFCell cellLoan = rowLoan.createCell((short) 0);
		        cellLoan.setCellValue("序号");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)1);
		        cellLoan.setCellValue("客户姓名");//busLoanInfo.applicationName  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)2);
		        cellLoan.setCellValue("紧急联系人"); //busLoanInfo.urgentCont 
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)3);
		        cellLoan.setCellValue("关系");//busLoanInfo.relationship  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)4);
		        cellLoan.setCellValue("电话");//busLoanInfo.urgentContPhone  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)5);
		        cellLoan.setCellValue("地址");//busLoanInfo.urgentContAddress  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)6);
		        cellLoan.setCellValue("贷款卡号"); //biling.loanCardNumber 
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)7);
		        cellLoan.setCellValue("店铺1");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)8);
		        cellLoan.setCellValue("平台1");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)9);
		        cellLoan.setCellValue("子帐号1");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)10);
		        cellLoan.setCellValue("密码1");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)11);
		        cellLoan.setCellValue("店铺2");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)12);
		        cellLoan.setCellValue("平台2");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)13);
		        cellLoan.setCellValue("子帐号2");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)14);
		        cellLoan.setCellValue("密码2");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)15);
		        cellLoan.setCellValue("检查日期");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)16);
		        cellLoan.setCellValue("借款人征信是否正常");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)17);
		        cellLoan.setCellValue("保证人征信是否正常");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)18);
		        cellLoan.setCellValue("云贷是否有预警信息");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)19);
		        cellLoan.setCellValue("店铺经营情况");  
		        cellLoan.setCellStyle(style);
		        cellLoan=rowLoan.createCell((short)20);
		        cellLoan.setCellValue("其他需要说明的地方");  
		        cellLoan.setCellStyle(style);
		        
		        for(int i=0;i<list.size();i++){
		        	BusFiles busFiles=list.get(i);
		        	Integer lId=busFiles.getId();
		        	BusLoanInfo busLoanInfo=busLoanInfoService.queryByLId(lId);
		        	BusLending busLending=busLendingService.queryByBId(lId);
		        	BusBiling busBiling=busBilingService.queryByBId(lId);
		        	BusLoanInfoLegal legal=busLoanInfoLegalService.getBusLoanInfoLegal(lId);
		        	List<BusLoanInfoShop> shopList=busLoanInfoShopService.queryListByBId(lId);
		        	
		        	
		        	/**row对应sheet1*/
		        	row = sheet.createRow((int) 1 + i); 
		        	row.createCell((short) 0).setCellValue(i+1);
		        	row.createCell((short) 1).setCellValue(busFiles.getlUserName());
		        	row.createCell((short) 3).setCellValue(busFiles.getlStatus());
		        	row.createCell((short) 4).setCellValue(DateUtil.getFormattedDateUtil((Date)busFiles.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		        	
		        	
		        	/**rowLoan对应sheet2*/
		        	rowLoan = sheetLoan.createRow((int) 1 + i);
		        	rowLoan.createCell((short)0).setCellValue(i+1);
		        	
		        	
		        	if(busLoanInfo!=null){
		        		row.createCell((short) 2).setCellValue(busLoanInfo.getApplicationName());
		        		row.createCell((short) 5).setCellValue(busLoanInfo.getChannel());
		        		
		        		rowLoan.createCell((short)1).setCellValue(busLoanInfo.getApplicationName());
		        		rowLoan.createCell((short)2).setCellValue(busLoanInfo.getUrgentCont());
		        		rowLoan.createCell((short)3).setCellValue(busLoanInfo.getRelationship());
		        		rowLoan.createCell((short)4).setCellValue(busLoanInfo.getUrgentContPhone());
		        		rowLoan.createCell((short)5).setCellValue(busLoanInfo.getUrgentContAddress());
		        		
		        	}else{
		        		row.createCell((short) 2).setCellValue("");
		        		row.createCell((short) 5).setCellValue("");
		        		
		        		rowLoan.createCell((short)1).setCellValue("");
		        		rowLoan.createCell((short)2).setCellValue("");
		        		rowLoan.createCell((short)3).setCellValue("");
		        		rowLoan.createCell((short)4).setCellValue("");
		        		rowLoan.createCell((short)5).setCellValue("");
		        	}
		        	if(busLending!=null){
		        		row.createCell((short) 6).setCellValue(busLending.getLoanAmount());
		        		row.createCell((short) 7).setCellValue(busLending.getOpeningQuota());
		        	}else{
		        		row.createCell((short) 6).setCellValue("");
		        		row.createCell((short) 7).setCellValue("");
		        	}
		        	if(busBiling!=null){
		        		row.createCell((short) 8).setCellValue(busBiling.getCreditEndDate());
		        		row.createCell((short) 9).setCellValue(busBiling.getLoanAccount());
		        		
		        		rowLoan.createCell((short) 6).setCellValue(busBiling.getLoanCardNumber());//贷款卡号
		        		rowLoan.createCell((short) 15).setCellValue(DateUtil.getFormattedDateUtil((Date)busBiling.getCheckDate(), "yyyy-MM-dd HH:mm:ss"));//检查日期
		        		rowLoan.createCell((short)16).setCellValue(busBiling.getCreditorIfNormal());//借款人征信是否正常
		        		rowLoan.createCell((short)17).setCellValue(busBiling.getGuarantorIfNormal());//保证人征信是否正常
		        		rowLoan.createCell((short)18).setCellValue(busBiling.getCloudLoanIfWarning());//云贷是否有预警信息
		        		rowLoan.createCell((short)19).setCellValue(busBiling.getShopOperation());//店铺经营情况
		        		rowLoan.createCell((short)20).setCellValue(busBiling.getOtherNeedToExplained());//其他需要说明的地方
		        	}else{
		        		row.createCell((short) 8).setCellValue("");
		        		row.createCell((short) 9).setCellValue("");
		        		
		        		
		        		rowLoan.createCell((short) 6).setCellValue("");//贷款卡号
		        		rowLoan.createCell((short) 15).setCellValue("");//检查日期
		        		rowLoan.createCell((short)16).setCellValue("");//借款人征信是否正常
		        		rowLoan.createCell((short)17).setCellValue("");//保证人征信是否正常
		        		rowLoan.createCell((short)18).setCellValue("");//云贷是否有预警信息
		        		rowLoan.createCell((short)19).setCellValue("");//店铺经营情况
		        		rowLoan.createCell((short)20).setCellValue("");//其他需要说明的地方
		        		
		        	}
		        	if(legal!=null){
		        		row.createCell((short) 10).setCellValue(legal.getDeliveryAddress());
		        	}else{
		        		row.createCell((short) 10).setCellValue("");
		        	}
		        	
		        	
		        	if(shopList!=null&&shopList.size()>0){
		        		if(shopList.size()==1){
		        			BusLoanInfoShop shop=shopList.get(0);
		        			rowLoan.createCell((short) 7).setCellValue(shop.getShopName());//店铺
		        			rowLoan.createCell((short) 8).setCellValue(shop.getPlatformName());//平台
		        			rowLoan.createCell((short) 9).setCellValue(shop.getSubAccount());//子帐号
		        			rowLoan.createCell((short) 10).setCellValue(shop.getSbuPassword());//密码
		        			rowLoan.createCell((short) 11).setCellValue("");//店铺
		        			rowLoan.createCell((short) 12).setCellValue("");//平台
		        			rowLoan.createCell((short) 13).setCellValue("");//子帐号
		        			rowLoan.createCell((short) 14).setCellValue("");//密码
		        		}else if(shopList.size()==2){
		        			BusLoanInfoShop shop=shopList.get(0);
		        			rowLoan.createCell((short) 7).setCellValue(shop.getShopName());//店铺
		        			rowLoan.createCell((short) 8).setCellValue(shop.getPlatformName());//平台
		        			rowLoan.createCell((short) 9).setCellValue(shop.getSubAccount());//子帐号
		        			rowLoan.createCell((short) 10).setCellValue(shop.getSbuPassword());//密码
		        			BusLoanInfoShop shop1=shopList.get(1);
		        			rowLoan.createCell((short) 11).setCellValue(shop1.getShopName());//店铺
		        			rowLoan.createCell((short) 12).setCellValue(shop1.getPlatformName());//平台
		        			rowLoan.createCell((short) 13).setCellValue(shop1.getSubAccount());//子帐号
		        			rowLoan.createCell((short) 14).setCellValue(shop1.getSbuPassword());//密码
		        		}
		        		
		        	}else{
		        		rowLoan.createCell((short) 7).setCellValue("");
		        		rowLoan.createCell((short) 8).setCellValue("");
		        		rowLoan.createCell((short) 9).setCellValue("");
		        		rowLoan.createCell((short) 10).setCellValue("");
		        		rowLoan.createCell((short) 11).setCellValue("");
		        		rowLoan.createCell((short) 12).setCellValue("");
		        		rowLoan.createCell((short) 13).setCellValue("");
		        		rowLoan.createCell((short) 14).setCellValue("");
		        	}
		        	
		        }
		        String savePath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads"+File.separator+"excelfiles");//文件保存位置,项目部署绝对路径（物理路径）
		        File savePathFile=new File(savePath);
		        if(!savePathFile.exists()){
		        	savePathFile.mkdirs();
		        }
		        savePath=savePath+File.separator+UUID.randomUUID();//文件最终保存路径
		        File fileSavePath=new File(savePath);
		        /**创建要保存的文件夹*/
				if(fileSavePath.exists()){
					if(fileSavePath.isDirectory()){
						File[] files=fileSavePath.listFiles();
						for(File file:files){
							file.delete();
						}
						fileSavePath.delete();
					}else{
						fileSavePath.delete();
					}
					fileSavePath.mkdirs();
				}else{
					fileSavePath.mkdirs();
				}
				String excel="生成贷后台帐"+DateUtil.getNowLongTime()+".xls";//eccel文件名称
				BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(savePath+File.separator+excel));  //创建excel文件
				wb.write(fout); //写入excel数据
				fout.flush();
		        fout.close();
		        //设置文件MIME类型
			    response.setContentType(request.getSession().getServletContext().getMimeType(excel));
			    //设置Content-Disposition
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(excel,"UTF-8"));
				BufferedInputStream in=new BufferedInputStream(new FileInputStream(savePath+File.separator+excel));//下载时,把文件读入io流
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
				File file=new File(savePath+File.separator+excel);
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
				if(savePathFile!=null){
					savePathFile=null;
				}
				log.info("Excel导出成功。");
			}
		}catch(Exception e){
			//e.printStackTrace();
			log.error("exportExcel方法出错："+e.getMessage());
			context.put("message", "系统出错了，请联系技术人员！");
			return forword("message/message",context);
		}
		return null;
	}
	
	
	/**
	 * 
	 * 生成文档
	 * @param id
	 * @param wordType
	 * @author xuanaw
	 * */
	@RequestMapping("/createWords")
	public ModelAndView createWords(Integer id,String wordType,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> context=getRootMap();
		if(id==null){
			log.info("id为null！");
			context.put("message", "请不要非法操作！");
			return forword("message/message",context);
		}
		if(wordType==null||wordType.equals("")){
			log.info("wordType为空！");
			context.put("message", "请不要非法操作！");
			return forword("message/message",context);
		}
		boolean flag=false;
		String savePath="";
		BusFiles busFiles=null;
		try{
			busFiles=busFileService.queryById(id);
			if(busFiles==null){
				log.error("没有找到busFiles对应记录！");
				context.put("message", "没有找到对应记录！");
				return forword("message/message",context);
			}
			Integer lId=busFiles.getId();
			BusLoanInfo busLoanInfo=busLoanInfoService.queryByLId(lId);
			BusLoanInfoLegal legal=busLoanInfoLegalService.getBusLoanInfoLegal(lId);
			BusLending lending=busLendingService.queryByBId(lId);
			List<BusLoanInfoShop> shopList=busLoanInfoShopService.queryListByBId(lId);
			List<BusLoanInfoGuaranter> guaranterLsit=busLoanInfoGuaranterService.queryListByBId(lId);
			Map<String,Object> dataMap=new HashMap<String,Object>();
			String templatePath=File.separator+"com"+File.separator+"dayuan"+File.separator+"templatev1"+File.separator;//模板位置
			savePath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads");//文件保存位置,项目部署绝对路径（物理路径）
			savePath=savePath+File.separator+UUID.randomUUID();//最后保存路径
			/**创建文件夹 */
			File fileSavePath=new File(savePath);
			if(fileSavePath.exists()){
				if(fileSavePath.isDirectory()){
					File[] files=fileSavePath.listFiles();
					for(File file:files){
						file.delete();
					}
					fileSavePath.delete();
				}
				else{
					fileSavePath.delete();
				}
				fileSavePath.mkdirs();
			}else{
				fileSavePath.mkdirs();
			}
			CreateWords createWords=new CreateWords();//封装好的word生成类
			String wordName="";//保存文件名
			if(busFiles.getlName()!=null&&!busFiles.getlName().equals("")){
				wordName+=busFiles.getlName();
			}
			if(wordType.equals("1")){
				dataMap.put("lName1", StringUtil.getNotNullStr(busFiles.getlName()));
				dataMap.put("lIdCard1", StringUtil.getNotNullStr(busFiles.getlIdCard()));
				dataMap.put("lTelPhone1", StringUtil.getNotNullStr(busFiles.getlTelPhone()));
				dataMap.put("lName2", StringUtil.getNotNullStr(busFiles.getlName()));
				dataMap.put("lTelPhone2", StringUtil.getNotNullStr(busFiles.getlTelPhone()));
				dataMap.put("lName3", StringUtil.getNotNullStr(busFiles.getlName()));
				dataMap.put("lIdCard2", StringUtil.getNotNullStr(busFiles.getlIdCard()));
				dataMap.put("lTelPhone3", StringUtil.getNotNullStr(busFiles.getlTelPhone()));
				wordName+="_贷前文件四合一"+DateUtil.getNowLongTime()+".doc";
				flag=createWords.create(dataMap,templatePath,"daiqianwenjian1.ftl",savePath+File.separator,wordName);
			}else if(wordType.equals("3")){
				if(busLoanInfo!=null){
					dataMap.put("applicationName1",StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
					dataMap.put("applicationName2", StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
					dataMap.put("applicationAmount",StringUtil.getNotNullStr(busLoanInfo.getApplicationAmount()));
				}else{
					dataMap.put("applicationName1","");
					dataMap.put("applicationName2","");
					dataMap.put("applicationAmount","");
				}
				if(legal!=null){
					dataMap.put("idCard",StringUtil.getNotNullStr(legal.getIdCard()));
					dataMap.put("idCardPeriod", StringUtil.getNotNullStr(legal.getIdCardPeriod()));
					dataMap.put("idCardAddress",StringUtil.getNotNullStr(legal.getIdCardAddress()));
					dataMap.put("legalPhone",StringUtil.getNotNullStr(legal.getLegalPhone()));
				}else{
					dataMap.put("idCard","");
					dataMap.put("idCardPeriod","");
					dataMap.put("idCardAddress","");
					dataMap.put("legalPhone","");
				}
				if(busLoanInfo.getChannel().equals("自流量")){
					wordName+="_融信通开立账户信息表_自流量"+DateUtil.getNowLongTime()+".doc";
					flag=createWords.create(dataMap,templatePath,"ziliuliang3.ftl",savePath+File.separator,wordName);
				}else if(busLoanInfo.getChannel().equals("云贷推送")){
					wordName+="_融信通开立账户信息表_云贷推送"+DateUtil.getNowLongTime()+".doc";
					flag=createWords.create(dataMap,templatePath,"yundaituisong3.ftl",savePath+File.separator,wordName);
				}
			}else if(wordType.equals("4")){
				if(busLoanInfo!=null){
					dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
				}else{
					dataMap.put("applicationName","");
				}
				wordName+="_电商贷客户贷后须知"+DateUtil.getNowLongTime()+".doc";
				flag=createWords.create(dataMap,templatePath,"dianshangdaihouxuzhi4.ftl",savePath+File.separator,wordName);
			}else if(wordType.equals("5")){
				if(busLoanInfo!=null){
					dataMap.put("applicationName",StringUtil.getNotNullStr(busLoanInfo.getApplicationName()));
				}else{
					dataMap.put("applicationName","");
				}
				if(legal!=null){
					dataMap.put("houseAddress",StringUtil.getNotNullStr(legal.getHouseAddress()));
					dataMap.put("legalPhone",StringUtil.getNotNullStr(legal.getLegalPhone()));
					dataMap.put("idCard",StringUtil.getNotNullStr(legal.getIdCard()));
				}else{
					dataMap.put("houseAddress","");
					dataMap.put("legalPhone","");
					dataMap.put("idCard","");
				}
				if(lending!=null){
					dataMap.put("loanAmount",StringUtil.getNotNullStr(lending.getLoanAmount()));
					dataMap.put("creditTerm",StringUtil.getNotNullStr(lending.getCreditTerm()));
					dataMap.put("startTermYear",DateUtil.getFormattedDateUtil((Date)lending.getStartTerm(), "yyyy"));
					dataMap.put("startTermMonth",DateUtil.getFormattedDateUtil((Date)lending.getStartTerm(), "MM"));
					dataMap.put("startTermDay",DateUtil.getFormattedDateUtil((Date)lending.getStartTerm(), "dd"));
					dataMap.put("endTermYear",DateUtil.getFormattedDateUtil((Date)lending.getEndTerm(), "yyyy"));
					dataMap.put("endTermMonth",DateUtil.getFormattedDateUtil((Date)lending.getEndTerm(), "MM"));
					dataMap.put("endTermDay",DateUtil.getFormattedDateUtil((Date)lending.getEndTerm(), "dd"));
					dataMap.put("guaranteeCompany1",StringUtil.getNotNullStr(lending.getGuaranteeCompany1()));
				}else{
					dataMap.put("loanAmount","");
					dataMap.put("creditTerm","");
					dataMap.put("startTermYear","");
					dataMap.put("startTermMonth","");
					dataMap.put("startTermDay","");
					dataMap.put("endTermYear","");
					dataMap.put("endTermMonth","");
					dataMap.put("endTermDay","");
					dataMap.put("guaranteeCompany1","");
				}
				if(guaranterLsit!=null&&guaranterLsit.size()>0){
					BusLoanInfoGuaranter guaranter=guaranterLsit.get(0);
					dataMap.put("guaranterName",StringUtil.getNotNullStr(guaranter.getGuaranterName()));
				}else{
					dataMap.put("guaranterName","");
				}
				wordName+="_华夏银行小企业网络贷最高额借款合同"+DateUtil.getNowLongTime()+".doc";
				flag=createWords.create(dataMap,templatePath,"xiaoqiyejiekuanhetong5.ftl",savePath+File.separator,wordName);
			}else if(wordType.equals("61")){
				if(guaranterLsit!=null&&guaranterLsit.size()>0){
					BusLoanInfoGuaranter guaranter=guaranterLsit.get(0);
					dataMap.put("guaranterName",StringUtil.getNotNullStr(guaranter.getGuaranterName()));
					dataMap.put("guaranterCard",StringUtil.getNotNullStr(guaranter.getGuaranterCard())); ///
				}else{
					dataMap.put("guaranterName","");
				}
				
			}
		}catch(IOException e){
			log.error("文件操作出错:"+e.getMessage());
			context.put("message", "系统出错了，请联系技术人员！");
			return forword("message/message",context);
		}catch(Exception e){
			log.error("程序出错：:"+e.getMessage());
			context.put("message", "系统出错了，请联系技术人员！");
			return forword("message/message",context);
		}
		if(flag){
			try{
				String saveZipPath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads"+File.separator+"ziptemp");//zip文件保存位置,项目部署绝对路径（物理路径）
				File saveZipPathFile=new File(saveZipPath);
				if(!saveZipPathFile.exists()){
					saveZipPathFile.mkdirs();
				}
				String zipName="word";
				if(busFiles.getlName()!=null&&!busFiles.getlName().equals("")){
					zipName=busFiles.getlName();
				}
				String zipSaveName=zipName+DateUtil.getNowLongTime()+".zip";//压缩文件名
				String sourceFile=ZipUtil.fileToZip(savePath,saveZipPath,zipSaveName);//封装好的压缩方法，返回压缩后的zip绝对路径+文件名
				if(sourceFile!=null){
					File file=new File(sourceFile);
					if(!file.exists()){
						log.error("创建文件程序出错了。");
						context.put("message", "系统出错了，请联系技术人员！");
						return forword("message/message",context);
					}
					//设置文件MIME类型
					response.setContentType(request.getSession().getServletContext().getMimeType(zipSaveName));
					//设置Content-Disposition
					response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(zipSaveName,"UTF-8"));
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
					if(saveZipPathFile!=null){
						saveZipPathFile=null;
					}
					log.info("压缩文件下载完成！");
				}
			}catch(IOException e){
				log.error("文件下载IO出错了："+e.getMessage());
				context.put("message", "系统出错了，请联系技术人员！");
				return forword("message/message",context);
			}catch(Exception e){
				log.error("文件下载出错了："+e.getMessage());
				context.put("message", "系统出错了，请联系技术人员！");
				return forword("message/message",context);
			}
		}else{
			log.error("word生成失败。");
			context.put("message", "系统出错了，请联系技术人员！");
			return forword("message/message",context);
		}
		return null;
	}

}
