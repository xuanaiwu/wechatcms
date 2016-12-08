package com.dayuan.action;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.dayuan.utils.DateUtil;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;

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

}
