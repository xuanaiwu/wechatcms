package com.dayuan.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.DataSwIndex;
import com.dayuan.bean.SysUser;
import com.dayuan.model.DataSwIndexModel;
import com.dayuan.service.DataSwIndexService;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;


@Controller
@RequestMapping("/SwIndex") 
public class SwIndexAction extends BaseAction{
	
	
	@Autowired
	private DataSwIndexService<DataSwIndex> dataSwIndexService;
	
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
	public void dataList(DataSwIndexModel model,HttpServletRequest request,HttpServletResponse response)throws SQLException,Exception{
		if(model!=null){
			List<DataSwIndex> list=dataSwIndexService.queryByList(model);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("total",model.getPager().getRowCount());
			map.put("rows",list);
			HtmlUtil.writerJson(response,map);
		}
	}
	
	
	@RequestMapping("/uploadExcel")
	public ModelAndView uploadExcel(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws EncryptedDocumentException, InvalidFormatException{
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		String message="";
		if(file.isEmpty()){
			log.info("文件为空");
			context.put("message", "文件不能为空");
			return forword("message/message",context);
		}
		try{

			//xls xlsx
			Workbook wbx= WorkbookFactory.create(file.getInputStream()); 
            Sheet sheet = wbx.getSheetAt(0);
            wbx.close();

			if(sheet!=null){
				boolean flag=true;
				int i=1;
				DataSwIndex dataSwIndex=null;
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String releaseDate="";
				int num=0;
				DataSwIndexModel mode=new DataSwIndexModel();
				while(flag){
					//遍历excel,从第二行开始 即 rowNum=1,逐个获取单元格的内容,然后进行格式处理,最后插入数据库 
					Row row=sheet.getRow(i);
					dataSwIndex=new DataSwIndex();				
					if(row==null){
						flag=false;
						continue;
					}
					i++;
					Cell cell0=row.getCell(0);
					if(cell0!=null){  
	                	cell0.setCellType(Cell.CELL_TYPE_STRING);
	                	dataSwIndex.setIndexCode(cell0.getStringCellValue());
	                }
					
					
					Cell cell1=row.getCell(1);
					if(cell1!=null){  
	                	cell1.setCellType(Cell.CELL_TYPE_STRING);
						dataSwIndex.setIndexName(cell1.getStringCellValue());
	                }
					
					
					Cell cell2=row.getCell(2);
					if(cell2!=null){  
	                	if(cell2.getCellType()==0){
	                		dataSwIndex.setReleaseDate(cell2.getDateCellValue());
	                		releaseDate=sdf.format(cell2.getDateCellValue());
	                		dataSwIndex.setReleaseDateString(releaseDate);
	                		mode.setReleaseDateString(releaseDate);
	                		mode.setIndexCode(dataSwIndex.getIndexCode());
	                		num=dataSwIndexService.queryByCount(mode);
	                		if(num>0)continue;
	                	}
	                }
					//log.info("##############2:"+cell2.getDateCellValue());
					//log.info("##############2:"+dataSwIndex.getReleaseDate());
					//log.info("##############2:"+dataSwIndex.getReleaseDateString());
					
					
					Cell cell3=row.getCell(3);
					if(cell3!=null){  
	                	cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell3.getNumericCellValue());
	                	dataSwIndex.setIndexStart(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					log.info("##############2:"+dataSwIndex.getIndexStart());
					
					Cell cell4=row.getCell(4);
					if(cell4!=null){  
	                	cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell4.getNumericCellValue());
	                	dataSwIndex.setIndexHigh(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					log.info("##############2:"+dataSwIndex.getIndexHigh());
					
					Cell cell5=row.getCell(5);
					if(cell5!=null){  
	                	cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell5.getNumericCellValue());
	                	dataSwIndex.setIndexLow(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell6=row.getCell(6);
					if(cell6!=null){  
	                	cell6.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell6.getNumericCellValue());
	                	dataSwIndex.setIndexEnd(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					
					Cell cell7=row.getCell(7);
					if(cell7!=null){  
	                	cell7.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell7.getNumericCellValue());
	                	dataSwIndex.setTurnover(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell8=row.getCell(8);
					if(cell8!=null){  
	                	cell8.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell8.getNumericCellValue());
	                	dataSwIndex.setVolume(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					
					Cell cell9=row.getCell(9);
					if(cell9!=null){  
	                	cell9.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell9.getNumericCellValue());
	                	dataSwIndex.setIndexRange(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell10=row.getCell(10);
					if(cell10!=null){  
	                	cell10.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell10.getNumericCellValue());
	                	dataSwIndex.setTrade(decimal.setScale(4, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell11=row.getCell(11);
					if(cell11!=null){  
	                	cell11.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell11.getNumericCellValue());
	                	dataSwIndex.setPe(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell12=row.getCell(12);
					if(cell12!=null){  
	                	cell12.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell12.getNumericCellValue());
	                	dataSwIndex.setPb(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell13=row.getCell(13);
					if(cell13!=null){  
	                	cell13.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell13.getNumericCellValue());
	                	dataSwIndex.setAverage(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell14=row.getCell(14);
					if(cell14!=null){  
	                	cell14.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell14.getNumericCellValue());
	                	dataSwIndex.setVolumeRatio(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell15=row.getCell(15);
					if(cell15!=null){  
	                	cell15.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell15.getNumericCellValue());
	                	dataSwIndex.setCirculateValue(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell16=row.getCell(16);
					if(cell16!=null){  
	                	cell16.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell16.getNumericCellValue());
	                	dataSwIndex.setAverageCirculateValue(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					Cell cell17=row.getCell(17);
					if(cell17!=null){  
	                	cell17.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	BigDecimal decimal=new BigDecimal(cell17.getNumericCellValue());
	                	dataSwIndex.setDividendRatio(decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	                }
					
					dataSwIndex.setUuid(java.util.UUID.randomUUID().toString());
					if(!"".equals(dataSwIndex.getIndexCode())&&!"".equals(dataSwIndex.getIndexName())){
						dataSwIndexService.add(dataSwIndex);
						log.info("################添加成功数据的日期为："+dataSwIndex.getReleaseDateString());
					}
					
					
				}
				log.info("数据导入成功");
				message="数据导入成功";
			} 
		}catch(Exception e){
			message="文件操作出错";
			log.info("文件操作出错");
			e.printStackTrace();
		}
		context.put("message",message);
		return forword("message/message",context);
		
	}

}
