package com.dayuan.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.dayuan.bean.BusInsuranceBaseMaterial;
import com.dayuan.bean.BusInsuranceGuarantee;
import com.dayuan.bean.BusInsuranceIntegra;
import com.dayuan.bean.BusInsuranceVerification;
import com.dayuan.bean.SysUser;
import com.dayuan.form.InsuranceGuaranteeForm;
import com.dayuan.model.BusInsuranceBaseMaterialModel;
import com.dayuan.model.BusInsuranceIntegraModel;
import com.dayuan.service.BusInsuranceBaseMaterialService;
import com.dayuan.service.BusInsuranceGuaranteeService;
import com.dayuan.service.BusInsuranceIntegraService;
import com.dayuan.service.BusInsuranceVerificationService;
import com.dayuan.utils.CreateWords;
import com.dayuan.utils.DateUtil;
import com.dayuan.utils.HtmlUtil;
import com.dayuan.utils.SessionUtils;
import com.dayuan.utils.StringUtil;
import com.dayuan.utils.ZipUtil;
import com.dayuan.utils.Constant.SuperAdmin;

@Controller
@RequestMapping("/Insurance") 
public class InsuranceAction extends BaseAction{
	
	//Service start 
	@Autowired(required=false)
	private BusInsuranceBaseMaterialService<BusInsuranceBaseMaterial> busInsuranceBaseMaterialService;
	
	
	
	//Service start 
	@Autowired(required=false)
	private BusInsuranceVerificationService<BusInsuranceVerification> busInsuranceVerificationService;
	
	
	//Service start 
	@Autowired(required=false)
	private  BusInsuranceGuaranteeService<BusInsuranceGuarantee>  busInsuranceGuaranteeService;
	
	@Autowired(required=false)
	private BusInsuranceIntegraService<BusInsuranceIntegra> busInsuranceIntegraService;
	
	
	
	//DATE属性编辑器
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 跳转到添加页面
	 * */
	@RequestMapping("/insuranceAdd")
	public ModelAndView insuranceAdd(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		return forword("insurance/insuranceAdd",context);
	}
	
	
	/**
	 * 跳转到信息维护页面
	 * */
	@RequestMapping("/insuranceManage")
	public ModelAndView insuranceManage(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		return forword("insurance/insuranceManage",context);
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
		String originalFilename=file.getOriginalFilename();
        String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);//得到上传文件的扩展名
        //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
        if(!fileExtName.equals("xlsx")){
        	log.info("文件格式为:"+fileExtName);
			context.put("message", "文件格式错误,应为excel2007以上文档");
			return forword("message/message",context);
        }
        try {
        	//2007
        	Workbook wbx= WorkbookFactory.create(file.getInputStream()); 
            Sheet sheet = wbx.getSheetAt(2);
            wbx.close();
             //2003
			//POIFSFileSystem fs=new POIFSFileSystem(file.getInputStream());//此时的Workbook应该是从 客户端浏览器上传过来的 uploadFile了,其实跟读取本地磁盘的一个样
			//HSSFWorkbook wb=new HSSFWorkbook(fs); 
			//HSSFSheet hssfSheet=wb.getSheetAt(2);
			if(sheet!=null){
					//遍历excel,从第二行开始 即 rowNum=1,逐个获取单元格的内容,然后进行格式处理,最后插入数据库 
					Row row=sheet.getRow(1);
					if(row==null){  
						log.info("文件内容为空");
						context.put("message", "文件内容不能为空");
						return forword("message/message",context);
	                }
					BusInsuranceBaseMaterial material=new BusInsuranceBaseMaterial();
					BusInsuranceVerification verification=new BusInsuranceVerification();
					BusInsuranceGuarantee guarantee1=new BusInsuranceGuarantee();
					BusInsuranceGuarantee guarantee2=new BusInsuranceGuarantee();
					BusInsuranceGuarantee guarantee3=new BusInsuranceGuarantee();
					 /** 
	                 * 为了处理：Excel异常Cannot get a text value from a numeric cell 
	                 * 将所有列中的内容都设置成String类型格式 
	                 */
					Cell cell0=row.getCell(0);
	                if(cell0!=null){  
	                	cell0.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setCompanyName(cell0.getStringCellValue());
					Cell cell1=row.getCell(1);
	                if(cell1!=null){  
	                	cell1.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setSetupTime(cell1.getStringCellValue());
					Cell cell2=row.getCell(2);
	                if(cell2!=null){  
	                	cell2.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setCompanyAddress(cell2.getStringCellValue());
					Cell cell3=row.getCell(3);
	                if(cell3!=null){  
	                	cell3.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setEmployeeNumber(cell3.getStringCellValue());
					Cell cell4=row.getCell(4);
	                if(cell4!=null){  
	                	cell4.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setCompanyLegal(cell4.getStringCellValue());
					Cell cell5=row.getCell(5);
	                if(cell5!=null){  
	                	cell5.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setAssociationCompany(cell5.getStringCellValue());
					Cell cell6=row.getCell(6);
	                if(cell6!=null){  
	                	cell6.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setOtherHappening(cell6.getStringCellValue());
					Cell cell7=row.getCell(7);
	                if(cell7!=null){  
	                	cell7.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setControllerName(cell7.getStringCellValue());
					Cell cell8=row.getCell(8);
	                if(cell8!=null){  
	                	cell8.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setControllerGender(cell8.getStringCellValue());
					Cell cell9=row.getCell(9);
	                if(cell9!=null){  
	                	cell9.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setControllerIdCard(cell9.getStringCellValue());
					Cell cell10=row.getCell(10);
	                if(cell10!=null){  
	                	cell10.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setControllerPhone(cell10.getStringCellValue());
					
					Cell cell11=row.getCell(11);
	                if(cell11!=null){  
	                	cell11.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setControllerAddress(cell11.getStringCellValue());
					
					Cell cell12=row.getCell(12);
	                if(cell12!=null){  
	                	cell12.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setIfLegal(cell12.getStringCellValue());
					
					Cell cell13=row.getCell(13);
	                if(cell13!=null){  
	                	cell13.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setSpouseName(cell13.getStringCellValue());
					Cell cell14=row.getCell(14);
	                if(cell14!=null){  
	                	cell14.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setSpousePhone(cell14.getStringCellValue());
					Cell cell15=row.getCell(15);
	                if(cell15!=null){  
	                	cell15.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setSpouseIdCard(cell15.getStringCellValue());
					
					
					Cell cell16=row.getCell(16);
	                if(cell16!=null){  
	                	cell16.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setFamilyAssets(cell16.getStringCellValue());
					
					Cell cell17=row.getCell(17);
	                if(cell17!=null){  
	                	cell17.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setMainAssets(cell17.getStringCellValue());
					
					Cell cell18=row.getCell(18);
	                if(cell18!=null){  
	                	cell18.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setLiabilities(cell18.getStringCellValue());
					
					
					Cell cell19=row.getCell(19);
	                if(cell19!=null){  
	                	cell19.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setMortgageOwner(cell19.getStringCellValue());
					
					Cell cell20=row.getCell(20);
	                if(cell20!=null){  
	                	cell20.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setMortgageAddress(cell20.getStringCellValue());
					
					Cell cell21=row.getCell(21);
	                if(cell21!=null){  
	                	cell21.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setEvaluationPrice(cell21.getStringCellValue());
					
					Cell cell22=row.getCell(22);
	                if(cell22!=null){  
	                	cell22.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setEvaluationTotalPrice(cell22.getStringCellValue());
					
					Cell cell23=row.getCell(23);
	                if(cell23!=null){  
	                	cell23.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setMortgageArea(cell23.getStringCellValue());
					
					
					Cell cell24=row.getCell(24);
	                if(cell24!=null){  
	                	cell24.setCellType(Cell.CELL_TYPE_STRING);  
	                 }  
					material.setPropertyNumber(cell24.getStringCellValue());
					material.setuId(user.getId().toString());
					material.setuName(user.getNickName());
					busInsuranceBaseMaterialService.addT(material);
					
					if(material.getId()!=null){
						
						
						Cell cell25=row.getCell(25);
		                if(cell25!=null){  
		                	cell25.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                verification.setLoanPerson(cell25.getStringCellValue());
						
		                
		                Cell cell26=row.getCell(26);
		                if(cell26!=null){  
		                	cell26.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                verification.setLoanIdCard(cell26.getStringCellValue());
						
		                
		                Cell cell27=row.getCell(27);
		                if(cell27!=null){  
		                	cell27.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                verification.setLoanPhone(cell27.getStringCellValue());
						
						
		                Cell cell28=row.getCell(28);
		                if(cell28!=null){  
		                	cell28.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                verification.setMortgagePerson(cell28.getStringCellValue());
						
		                Cell cell29=row.getCell(29);
		                if(cell29!=null){  
		                	cell29.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                verification.setMortgageIdCard(cell29.getStringCellValue());
		                
						
		                Cell cell30=row.getCell(30);
		                if(cell30!=null){  
		                	cell30.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                verification.setMortgagePhone(cell30.getStringCellValue());
						verification.setBid(material.getId());
						busInsuranceVerificationService.addT(verification);
						
						
						
						Cell cell31=row.getCell(31);
		                if(cell31!=null){  
		                	cell31.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee1.setGuaranteePersonName(cell31.getStringCellValue());
						
		                Cell cell32=row.getCell(32);
		                if(cell32!=null){  
		                	cell32.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee1.setGuaranteePersonIdCard(cell32.getStringCellValue());
		                
		                Cell cell33=row.getCell(33);
		                if(cell33!=null){  
		                	cell33.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee1.setLguaranteePersonPhone(cell33.getStringCellValue());
						guarantee1.setBid(material.getId());
						busInsuranceGuaranteeService.addT(guarantee1);
						
						
						Cell cell34=row.getCell(34);
		                if(cell34!=null){  
		                	cell34.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee2.setGuaranteePersonName(cell34.getStringCellValue());
						
		                Cell cell35=row.getCell(35);
		                if(cell35!=null){  
		                	cell35.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee2.setGuaranteePersonIdCard(cell35.getStringCellValue());
		                
		                Cell cell36=row.getCell(36);
		                if(cell36!=null){  
		                	cell36.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee2.setLguaranteePersonPhone(cell36.getStringCellValue());
		                guarantee2.setBid(material.getId());
						busInsuranceGuaranteeService.addT(guarantee2);
						
					
						Cell cell37=row.getCell(37);
		                if(cell37!=null){  
		                	cell37.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee3.setGuaranteePersonName(cell37.getStringCellValue());
		                Cell cell38=row.getCell(38);
		                if(cell38!=null){  
		                	cell38.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee3.setGuaranteePersonIdCard(cell38.getStringCellValue());
		                Cell cell39=row.getCell(39);
		                if(cell39!=null){  
		                	cell39.setCellType(Cell.CELL_TYPE_STRING);  
		                 }  
		                guarantee3.setLguaranteePersonPhone(cell39.getStringCellValue());
						guarantee3.setBid(material.getId());
						busInsuranceGuaranteeService.addT(guarantee3);
					}
				
			}
			log.info("数据导入成功");
			message="数据导入成功";
		}catch (IOException e) {
			message="文件操作出错";
			log.info("文件操作出错");
			e.printStackTrace();
		}finally{
			if(file!=null){
				file=null;
			}
		}
		context.put("message",message);
		return forword("message/message",context);
	}
	
	
	/**
	 * 跳转图片上传页面
	 * */
	@RequestMapping("/toUpload")
	public ModelAndView toUpload(HttpServletRequest request){
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		return forword("insurance/uploadFile",context);
	}
	
	
	
	@RequestMapping("/upload")
    public void upload(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException, FileUploadException {
		
		String uploadPath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"uploads");//文件保存位置,项目部署绝对路径（物理路径）
		String uploadPathTemp=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"uploadstemp");//文件保存位置,项目部署绝对路径（物理路径）
	
		File file=new File(uploadPath);
		if(!file.exists()&&!file.isDirectory()){
			file.mkdir();
		}
		File fileTemp=new File(uploadPathTemp);
		if(!file.exists()&&!file.isDirectory()){
			fileTemp.mkdir();
		}
		DiskFileItemFactory factory=new DiskFileItemFactory();
		factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
		factory.setRepository(fileTemp); //设置上传时生成的临时文件的保存目录
		ServletFileUpload upload = new ServletFileUpload(factory);
		//监听文件上传进度
        upload.setProgressListener(new ProgressListener(){
            public void update(long pBytesRead, long pContentLength, int arg2) {
                System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                /**
                 *文件大小为：14608,当前已处理：4096
		                    文件大小为：14608,当前已处理：7367
		                    文件大小为：14608,当前已处理：11419
		                    文件大小为：14608,当前已处理：14608
                */
            }
        });
        //解决上传文件名的中文乱码
        upload.setHeaderEncoding("UTF-8"); 
        //3、判断提交上来的数据是否是上传表单的数据
        if(!ServletFileUpload.isMultipartContent(request)){
            //按照传统方式获取数据
            return;
        }
        //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
        upload.setFileSizeMax(1024*1024);
        //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
        upload.setSizeMax(1024*1024*10);
        //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
        List<FileItem> list = upload.parseRequest(request);
        for(FileItem item : list){
            //如果fileitem中封装的是普通输入项的数据
            if(item.isFormField()){
                String name = item.getFieldName();
                //解决普通输入项的数据的中文乱码问题
                String value = item.getString("UTF-8");
                //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                System.out.println(name + "=" + value);
            }else{//如果fileitem中封装的是上传文件
                //得到上传的文件名称，
                String filename = item.getName();
                System.out.println(filename);
                if(filename==null || filename.trim().equals("")){
                    continue;
                }
                //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                filename = filename.substring(filename.lastIndexOf("\\")+1);
                //得到上传文件的扩展名
                String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
                //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                System.out.println("上传的文件的扩展名是："+fileExtName);
                //获取item中的上传文件的输入流
                InputStream in = item.getInputStream();
                //得到文件保存的名称
                String saveFilename = makeFileName(filename);
                //得到文件的保存目录
                String realSavePath = makePath(saveFilename, uploadPath);
                //创建一个文件输出流
                FileOutputStream out = new FileOutputStream(realSavePath + File.separator + saveFilename);
                //创建一个缓冲区
                byte buffer[] = new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len = 0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while((len=in.read(buffer))>0){
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    out.write(buffer, 0, len);
                }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();
                //删除处理文件上传时生成的临时文件
                //item.delete();
               
            }
        }
        
	} 
	
	 /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
    * @Method: makePath
    * @Description: 
    * @Anthor:孤傲苍狼
    *
    * @param filename 文件名，要根据文件名生成存储目录
    * @param savePath 文件存储路径
    * @return 新的存储目录
    */ 
    private String makePath(String filename,String savePath){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        //构造新的保存目录
        String dir = savePath + File.separator + dir1 + File.separator+ dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }

	
	
	 /**
	    * @Method: makeFileName
	    * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * @Anthor:孤傲苍狼
	* @param filename 文件的原始名称
	* @return uuid+"_"+文件的原始名称
	*/ 
	private String makeFileName(String filename){  //2.jpg
	     //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
	     return UUID.randomUUID().toString() + "_" + filename;
	}
	
	@RequestMapping("/save")
	public void save(BusInsuranceBaseMaterial material,BusInsuranceVerification verification,InsuranceGuaranteeForm guaranteeForm,HttpServletRequest request,HttpServletResponse response) throws Exception{
		boolean flag=false;
		int num=0;
		String message="";
		if(material!=null){
			/**保存基础材料信息*/
			if(material.getId()==null){
				material.setCreateTime(new Date());
				num=busInsuranceBaseMaterialService.addT(material);
				if(num==1){
					flag=true;
				}
			}else{
				material.setUpdateTime(new Date());
				num=busInsuranceBaseMaterialService.updateT(material);
				if(num==1){
					flag=true;
				}
			}
			Integer bid=material.getId();
			//System.out.println("bid="+bid);
			/**保存核保信息*/
			if(bid!=null&&verification!=null){
				verification.setBid(bid);
				verification.setId(verification.getVerificationTempId());
				if(verification.getId()==null){
					num=busInsuranceVerificationService.addT(verification);
					if(num!=1){
						flag=false;
					}
				}else{
					num=busInsuranceVerificationService.updateT(verification);
					if(num!=1){
						flag=false;
					}
				}
			}
			/**保存保证人信息*/
			if(bid!=null&&guaranteeForm!=null){
				for(BusInsuranceGuarantee gurantee:guaranteeForm.getGuarantee()){
					gurantee.setBid(bid);
					gurantee.setId(gurantee.getGuaranteeTempId());
					if(gurantee.getId()==null){
						num=busInsuranceGuaranteeService.addT(gurantee);
						if(num!=1){
							flag=false;
						}
					}else{
						num=busInsuranceGuaranteeService.updateT(gurantee);
						if(num!=1){
							flag=false;
						}
					}
				}
			}
			if(flag){
				message="保存成功！";
				sendSuccessMessage(response,message);
			}else{
				message="保存出错";
				sendFailureMessage(response,message);
			}
		}else{
			message="请输入数据";
			sendFailureMessage(response,message);
		}
		log.info("InsuranceAction.save："+message);
	}
	
	/**
	@RequestMapping("/dataList")
	public void dataList(BusInsuranceBaseMaterialModel materilaModel,HttpServletRequest request,HttpServletResponse response) throws SQLException,Exception{
		if(materilaModel!=null){
			SysUser user = SessionUtils.getUser(request);
			if(SuperAdmin.YES.key!=user.getSuperAdmin()){
				materilaModel.setuId(user.getId().toString());
				materilaModel.setuName(user.getNickName());
			}
			List<BusInsuranceBaseMaterial>  materialLsit=busInsuranceBaseMaterialService.queryByList(materilaModel);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("total",materilaModel.getPager().getRowCount());
			map.put("rows",materialLsit);
			HtmlUtil.writerJson(response,map);
		}
	}
	*/
	
	@RequestMapping("/dataList")
	public void dataList(BusInsuranceIntegraModel integraModel,HttpServletRequest request,HttpServletResponse response)throws SQLException,Exception{
		if(integraModel!=null){
			SysUser user = SessionUtils.getUser(request);
			if(SuperAdmin.YES.key!=user.getSuperAdmin()&&user.getExcelAuth()==0){
				integraModel.setuId(user.getId().toString());
			}
			List<BusInsuranceIntegra> integraList=busInsuranceIntegraService.queryByList(integraModel);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("total",integraModel.getPager().getRowCount());
			map.put("rows",integraList);
			HtmlUtil.writerJson(response,map);
		}
	}
	
	
	
	
	/**
	 * 跳转到编辑页面
	 * @throws Exception 
	 * */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(HttpServletRequest request) throws SQLException, Exception{
		Map<String,Object> context=getRootMap();
		SysUser user=SessionUtils.getUser(request);
		context.put("user", user);
		Integer id=Integer.parseInt(request.getParameter("id").trim());
		if(id==null||id.equals("")){
			return forword("error/error",context);
		}
		BusInsuranceBaseMaterial material=busInsuranceBaseMaterialService.queryById(id);
		if(material!=null){
			context.put("material", material);
		}
		BusInsuranceVerification  verification=busInsuranceVerificationService.queryBybid(id);
		if(verification!=null){
			context.put("verification", verification);
		}
		List<BusInsuranceGuarantee> guaranteeList=busInsuranceGuaranteeService.queryBybid(id);
		if(guaranteeList!=null&&guaranteeList.size()>0){
			context.put("guaranteeList",guaranteeList);
		}
		return forword("insurance/insuranceAdd",context);
		
	}
	
	@RequestMapping("/delete")
	public void delete(Integer[] id,HttpServletResponse response)throws SQLException ,Exception{
		boolean flag=false;
		if(id!=null&&id.length>0){
			busInsuranceBaseMaterialService.delete(id);
			for(Integer bid:id){
				busInsuranceVerificationService.deleteBybid(bid);
				busInsuranceGuaranteeService.deleteBybid(bid);
			}
			flag=true;
		}
		if(flag){
			log.info("删除成功！");
			sendSuccessMessage(response,"删除成功！");
		}else{
			log.info("删除失败！");
			sendFailureMessage(response,"删除失败！");
		}
	}
	
	@RequestMapping("/templateDownload")
	public ModelAndView templateDownload(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> context=getRootMap();
		 String savePath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads"+File.separator+"template");//文件保存位置,项目部署绝对路径（物理路径）
		 String excel="信息采集表_导入数据.xlsx";//eccel文件名称
		 try{
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
		}catch(Exception e){
			log.error("templateDownload方法出错："+e.getMessage());
			context.put("message", "系统出错了，请联系技术人员！");
			return forword("message/message",context);
		}
		return null;
	}
	
	
	/**贷后台帐，导出Excel*/
	@RequestMapping("/exportExcel")
	public ModelAndView exportExcel(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> context=getRootMap();
		SysUser user = SessionUtils.getUser(request);
		BusInsuranceBaseMaterialModel materialModel=new BusInsuranceBaseMaterialModel();
		if(SuperAdmin.YES.key!=user.getSuperAdmin()&&user.getExcelAuth()==0){
			materialModel.setuId(user.getId().toString());
			//materialModel.setuName(user.getNickName());
		}
		materialModel.setRows(500);
		try{
			List<BusInsuranceBaseMaterial> list=busInsuranceBaseMaterialService.queryByList(materialModel);
			if(list!=null&&list.size()>0){
				// 第一步，创建一个webbook，对应一个Excel文件  
		        HSSFWorkbook wb = new HSSFWorkbook();
		        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		        HSSFSheet sheet = wb.createSheet("保险抵押贷 台帐");
		  
		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		        HSSFRow row = sheet.createRow((int) 0);
		  
		        // 第四步，创建单元格，并设置值表头 设置表头居中  
		        HSSFCellStyle style = wb.createCellStyle();
		        
		        // 创建一个居中格式
		        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        
		        
		        /**sheet1保险抵押贷 台帐*/
		        HSSFCell cell = row.createCell((short) 0);  
		        cell.setCellValue("序号");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 1);  
		        cell.setCellValue("客户经理");//material.lUserName
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 2);  
		        cell.setCellValue("客户姓名");
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 3);  
		        cell.setCellValue("身份证"); 
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 4);  
		        cell.setCellValue("电话");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 5);  
		        cell.setCellValue("抵押贷款金额");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 6);  
		        cell.setCellValue("利率");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 7);  
		        cell.setCellValue("抵押物地址");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 8);  
		        cell.setCellValue("共有人");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 9);  
		        cell.setCellValue("评估总价");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 10);  
		        cell.setCellValue("房产证号");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 11);  
		        cell.setCellValue("保证人1");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 12);  
		        cell.setCellValue("保证人2");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 13);  
		        cell.setCellValue("保证人3");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 14);  
		        cell.setCellValue("对应的特别授信姓名");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 15);  
		        cell.setCellValue("身份证");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 16);  
		        cell.setCellValue("电话");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 17);  
		        cell.setCellValue("金额");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 18);  
		        cell.setCellValue("利率");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 19);  
		        cell.setCellValue("质押人");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 20);  
		        cell.setCellValue("电话");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 21);  
		        cell.setCellValue("质押金额");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 22);  
		        cell.setCellValue("存款利率");  
		        cell.setCellStyle(style);
		        cell = row.createCell((short) 23);  
		        cell.setCellValue("综合成本");  
		        cell.setCellStyle(style);

		        for(int i=0;i<list.size();i++){
		        	BusInsuranceBaseMaterial material=list.get(i);
		        	Integer bId=material.getId();
		        	
		        	BusInsuranceVerification verification= busInsuranceVerificationService.queryBybid(bId);
		        	List<BusInsuranceGuarantee> guaranteeList=busInsuranceGuaranteeService.queryBybid(bId);
		        	
		        	/**row对应sheet1*/
		        	row = sheet.createRow((int) 1 + i); 
		        	row.createCell((short) 0).setCellValue(i+1);
		        	row.createCell((short) 6).setCellValue("");
		        	row.createCell((short) 18).setCellValue("");
		        	row.createCell((short) 21).setCellValue("");
		        	row.createCell((short) 22).setCellValue("");
		        	row.createCell((short) 23).setCellValue("");
		        	
		        	if(material!=null){
		        		row.createCell((short) 1).setCellValue(material.getuName());
			        	row.createCell((short) 7).setCellValue(material.getMortgageAddress());
			        	row.createCell((short) 8).setCellValue(material.getMortgageOwner());
			        	row.createCell((short) 9).setCellValue(material.getEvaluationTotalPrice());
			        	row.createCell((short) 10).setCellValue(material.getPropertyNumber());
		        	}else{
		        		row.createCell((short) 1).setCellValue("");
			        	row.createCell((short) 7).setCellValue("");
			        	row.createCell((short) 8).setCellValue("");
			        	row.createCell((short) 9).setCellValue("");
			        	row.createCell((short) 10).setCellValue("");
		        	}
		        	
		        	if(verification!=null){
		        		row.createCell((short) 2).setCellValue(verification.getLoanPerson());
			        	row.createCell((short) 3).setCellValue(verification.getLoanIdCard());
			        	row.createCell((short) 4).setCellValue(verification.getLoanPhone());
			        	row.createCell((short) 5).setCellValue(verification.getLoanAmount());
			        	row.createCell((short) 14).setCellValue(verification.getCreditPerson());
			        	row.createCell((short) 15).setCellValue(verification.getCreditPersonIdCard());
			        	row.createCell((short) 16).setCellValue(verification.getCreditPersonPhone());
			        	row.createCell((short) 17).setCellValue(verification.getCreditAmount());
			        	row.createCell((short) 19).setCellValue(verification.getPledgePerson());
			        	row.createCell((short) 20).setCellValue(verification.getPledgePersonPhone());
		        	}else{
		        		row.createCell((short) 2).setCellValue("");
			        	row.createCell((short) 3).setCellValue("");
			        	row.createCell((short) 4).setCellValue("");
			        	row.createCell((short) 5).setCellValue("");
			        	row.createCell((short) 14).setCellValue("");
			        	row.createCell((short) 15).setCellValue("");
			        	row.createCell((short) 16).setCellValue("");
			        	row.createCell((short) 17).setCellValue("");
			        	row.createCell((short) 19).setCellValue("");
			        	row.createCell((short) 20).setCellValue("");
		        	}
		        	if(guaranteeList!=null&&guaranteeList.size()>0){
		        		if(guaranteeList.size()==1){
		        			BusInsuranceGuarantee guarantee=guaranteeList.get(0);
		        			row.createCell((short) 11).setCellValue(guarantee.getGuaranteePersonName());
				        	row.createCell((short) 12).setCellValue("");
				        	row.createCell((short) 13).setCellValue("");
		        		}else if(guaranteeList.size()==2){
		        			BusInsuranceGuarantee guarantee=guaranteeList.get(0);
				        	BusInsuranceGuarantee guarantee1=guaranteeList.get(1);
				        	row.createCell((short) 11).setCellValue(guarantee.getGuaranteePersonName());
				        	row.createCell((short) 12).setCellValue(guarantee1.getGuaranteePersonName());
				        	row.createCell((short) 13).setCellValue("");
		        		}else if(guaranteeList.size()==3){
		        			BusInsuranceGuarantee guarantee=guaranteeList.get(0);
				        	BusInsuranceGuarantee guarantee1=guaranteeList.get(1);
				        	BusInsuranceGuarantee guarantee2=guaranteeList.get(2);
				        	row.createCell((short) 11).setCellValue(guarantee.getGuaranteePersonName());
				        	row.createCell((short) 12).setCellValue(guarantee1.getGuaranteePersonName());
				        	row.createCell((short) 13).setCellValue(guarantee2.getGuaranteePersonName());
		        		}
		        	}else{
		        		row.createCell((short) 11).setCellValue("");
		        		row.createCell((short) 12).setCellValue("");
			        	row.createCell((short) 13).setCellValue("");
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
				String excel="保险抵押贷台帐"+DateUtil.getNowLongTime()+".xls";//eccel文件名称
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
	 * 生成文件
	 * @param id
	 * @param wordType
	 * @author xuanaw
	 * */
	@RequestMapping("/createWords")
	public ModelAndView createWords(Integer id,String wordType,HttpServletRequest request,HttpServletResponse response)throws SQLException,IOException,Exception{
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
		BusInsuranceBaseMaterial material=busInsuranceBaseMaterialService.queryById(id);
		if(material==null){
			log.info("busInsuranceBaseMaterial为空");
			context.put("message", "没有找到对应记录");
			return forword("message/message",context);
		}
		Integer bid=material.getId();
		BusInsuranceVerification verification=busInsuranceVerificationService.queryBybid(bid);
		if(verification==null){
			log.info("verification为空");
			context.put("message", "数据有缺陷");
			return forword("message/message",context);
		}
		List<BusInsuranceGuarantee> guaranteeList=busInsuranceGuaranteeService.queryBybid(bid);
		if(guaranteeList==null){
			log.info("guaranteeList为空");
			context.put("message", "数据有缺陷");
			return forword("message/message",context);
		}
		Map<String,Object> dataMap=new HashMap<String,Object>();
		String templatePath=File.separator+"com"+File.separator+"dayuan"+File.separator+"template"+File.separator+"insurance"+File.separator;//模板位置
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
		if(wordType.equals("1")){
			//1-1
			dataMap.put("companyName", StringUtil.getNotNullStr(material.getCompanyName()));
			dataMap.put("companyAddress", StringUtil.getNotNullStr(material.getCompanyAddress()));
			dataMap.put("setupTime", StringUtil.getNotNullStr(material.getSetupTime()));
			dataMap.put("employeeNumber", StringUtil.getNotNullStr(material.getEmployeeNumber()));
			dataMap.put("companyLegal", StringUtil.getNotNullStr(material.getCompanyLegal()));
			dataMap.put("associationCompany", StringUtil.getNotNullStr(material.getAssociationCompany()));
			dataMap.put("otherHappening", StringUtil.getNotNullStr(material.getOtherHappening()));
			dataMap.put("controllerName", StringUtil.getNotNullStr(material.getControllerName()));
			dataMap.put("controllergender", StringUtil.getNotNullStr(material.getControllerGender()));
			dataMap.put("controllerIdCard", StringUtil.getNotNullStr(material.getControllerIdCard()));
			dataMap.put("controllerPhone", StringUtil.getNotNullStr(material.getControllerPhone()));
			dataMap.put("controllerAddress", StringUtil.getNotNullStr(material.getControllerAddress()));
			dataMap.put("ifLegal", StringUtil.getNotNullStr(material.getIfLegal()));
			dataMap.put("spouseName", StringUtil.getNotNullStr(material.getSpouseName()));
			dataMap.put("spousePhone", StringUtil.getNotNullStr(material.getSpousePhone()));
			dataMap.put("spouseIdCard", StringUtil.getNotNullStr(material.getSpouseIdCard()));
			dataMap.put("familyAssets", StringUtil.getNotNullStr(material.getFamilyAssets()));
			dataMap.put("mainAssets", StringUtil.getNotNullStr(material.getMainAssets()));
			dataMap.put("liabilities", StringUtil.getNotNullStr(material.getLiabilities()));
			wordName=verification.getLoanPerson();
			wordName+="_授信_1-1保险抵押贷 授信调查报告_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"shouxindiaochabaogao1_1.ftl",savePath+File.separator,wordName);
		}else if(wordType.equals("2")){
			//2-1
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-01华夏银行支付融资系统业务协议书_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"xitongyewuxieyishu2_1.ftl",savePath+File.separator,wordName);
			//2-2
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-02华夏银行支付融资系统业务办理申请书_"+DateUtil.getNowLongTime()+".xls";
			flag=createWords.create(dataMap,templatePath,"yewubanlishenqingshu2_2.ftl",savePath+File.separator,wordName);
			//2-3
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-03授信基础材料核实证明_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"jichucailiaoheshi2_3.ftl",savePath+File.separator,wordName);
			//2-4
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-04送达确认书1  借款人_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"jiekuanren2_4.ftl",savePath+File.separator,wordName);
			
			//2-4-2
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			if(guaranteeList.size()>0){
				dataMap.put("name1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(0).getLguaranteePersonPhone()));
			}else{
				dataMap.put("name1", "");
				dataMap.put("lguaranteePersonPhone","");
			}
			if(guaranteeList.size()>1){
				dataMap.put("name2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
			}else{
				dataMap.put("name2", "");
			}
			if(guaranteeList.size()>2){
				dataMap.put("name3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
			}else{
				dataMap.put("name3","");
			}
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-04送达确认书2  保证人_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhengren2_4_2.ftl",savePath+File.separator,wordName);
			
			
			List<Map<String, Object>> holdersList=new ArrayList<Map<String,Object>>();
			if(verification!=null){
				dataMap.put("numberOfShareHolders1",StringUtil.getNotNullStr(verification.getShareholders()));
				dataMap.put("guaranteeCompany1",StringUtil.getNotNullStr(verification.getUseCompanyName()));
				String holders=verification.getShareholdersName();
				if(holders!=null&&!holders.equals("")){
					holders=holders.replaceAll("、", "、");
					String[] holdersArr=holders.split("、");
					for(String value:holdersArr){
						 Map<String, Object> map=new HashMap<String, Object>();
				         map.put("holdersName",value);
				         holdersList.add(map);
					}
				}
			}else{
				dataMap.put("numberOfShareHolders1","");
				dataMap.put("guaranteeCompany1","");
			}
			dataMap.put("holdersList", holdersList);
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-05董事会（股东会）成员名单及签字样本_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"gudongqianziyangben2_5.ftl",savePath+File.separator,wordName);
			
			//2-6
			dataMap.put("useCompanyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-06法定代表人证明书及签字样本_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"qianziyangben2_6.ftl",savePath+File.separator,wordName);
			
			
			//2-7
			dataMap.put("useCompanyName1", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			dataMap.put("useCompanyName2", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-07股东会决议_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"gudonghuiyi2_7.ftl",savePath+File.separator,wordName);
			
			
			//2-8
			dataMap.put("useCompanyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-08预留公章样本_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"yuliugongzhang2_8.ftl",savePath+File.separator,wordName);
			
			
			//2-9
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			dataMap.put("loanTerm", StringUtil.getNotNullStr(verification.getLoanTerm()));
			dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			
			if(guaranteeList.size()>0){
				dataMap.put("guaranteePersonName1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
				dataMap.put("guaranteeNumber1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteeNumber()));
			}else{
				dataMap.put("guaranteePersonName1", "");
				dataMap.put("guaranteeNumber1","");
			}
			if(guaranteeList.size()>1){
				dataMap.put("guaranteePersonName2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
				dataMap.put("guaranteeNumber2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteeNumber()));
			}else{
				dataMap.put("guaranteePersonName2", "");
				dataMap.put("guaranteeNumber2","");
			}
			if(guaranteeList.size()>2){
				dataMap.put("guaranteePersonName3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
				dataMap.put("guaranteeNumber3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteeNumber()));
			}else{
				dataMap.put("guaranteePersonName3","");
				dataMap.put("guaranteeNumber3","");
			}
			
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-09华夏银行小企业网络贷最高额借款合同1_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"jiekuanhetong2_9_1.ftl",savePath+File.separator,wordName);
			
			
			//2-10
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
			dataMap.put("mortgageIdCard", StringUtil.getNotNullStr(verification.getMortgageIdCard()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("mortgagePhone", StringUtil.getNotNullStr(verification.getMortgagePhone()));
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			dataMap.put("mortgageValue", StringUtil.getNotNullStr(verification.getMortgageValue()));
			dataMap.put("highMortgageNumber2", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			dataMap.put("mortgageAddress2", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("mortgageOwner2", StringUtil.getNotNullStr(material.getMortgageOwner()));
			dataMap.put("Area", StringUtil.getNotNullStr(material.getMortgageArea()));
			dataMap.put("propertyNumber", StringUtil.getNotNullStr(material.getPropertyNumber()));
			dataMap.put("price", StringUtil.getNotNullStr(material.getEvaluationPrice()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-10个人最高额抵押合同_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"diyahetong2_10.ftl",savePath+File.separator,wordName);
			
			
			//2-12
			
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			if(guaranteeList.size()>0){
				dataMap.put("guaranteeNumber", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
				dataMap.put("uaranteePersonIdCard", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonIdCard()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(0).getLguaranteePersonPhone()));
			}else{
				dataMap.put("guaranteeNumber","");
				dataMap.put("guaranteePersonName","");
				dataMap.put("uaranteePersonIdCard","");
				dataMap.put("lguaranteePersonPhone","");
			}
			
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-12个人最高额保证合同1_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhenghetong2_12.ftl",savePath+File.separator,wordName);
			
			
			if(guaranteeList.size()>1){
				dataMap.put("guaranteeNumber", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
				dataMap.put("uaranteePersonIdCard", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonIdCard()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(1).getLguaranteePersonPhone()));
			}else{
				dataMap.put("guaranteeNumber","");
				dataMap.put("guaranteePersonName","");
				dataMap.put("uaranteePersonIdCard","");
				dataMap.put("lguaranteePersonPhone","");
			}
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-12个人最高额保证合同2_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhenghetong2_12.ftl",savePath+File.separator,wordName);
			
			
			if(guaranteeList.size()>2){
				dataMap.put("guaranteeNumber", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
				dataMap.put("uaranteePersonIdCard", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonIdCard()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(2).getLguaranteePersonPhone()));
			}else{
				dataMap.put("guaranteeNumber","");
				dataMap.put("guaranteePersonName","");
				dataMap.put("uaranteePersonIdCard","");
				dataMap.put("lguaranteePersonPhone","");
			}
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-12个人最高额保证合同3_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhenghetong2_12.ftl",savePath+File.separator,wordName);
			
			
			//2-13
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-13个人经营性面谈记录_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"miantanjilv2_13.ftl",savePath+File.separator,wordName);
			
			
			//2-14
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-14续保承诺书_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"chengnuoshu2_14.ftl",savePath+File.separator,wordName);
			
			
			
			
		}else if(wordType.equals("3")){
			//3-1
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-01法定代表人证明书（抵押、领证）_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"zhengmingshu3_1.ftl",savePath+File.separator,wordName);
			//3-4
			dataMap.put("proxyPerson", StringUtil.getNotNullStr(verification.getProxyPerson()));
			dataMap.put("proxyPersonIdCard", StringUtil.getNotNullStr(verification.getProxyPersonIdCard()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-04取证委托书  房管局_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"quzhengweituoshu3_4.ftl",savePath+File.separator,wordName);
		    
		    
		    //3-7
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			
			dataMap.put("loanPerson1", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
			dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
			
			dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			
			if(guaranteeList.size()>0){
				dataMap.put("guaranteeNumber1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
			}else{
				dataMap.put("guaranteeNumber1", "");
				dataMap.put("guaranteePersonName1","");
			}
			if(guaranteeList.size()>1){
				dataMap.put("guaranteeNumber2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
			}else{
				dataMap.put("guaranteeNumber2", "");
				dataMap.put("guaranteePersonName2","");
			}
			if(guaranteeList.size()>2){
				dataMap.put("guaranteeNumber3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
			}else{
				dataMap.put("guaranteeNumber3", "");
				dataMap.put("guaranteePersonName3","");
			}
			dataMap.put("pledgeContrateNumber", StringUtil.getNotNullStr(verification.getPledgeContrateNumber()));
			dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-07 保险抵押贷 合同用印_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"hetongyongyin3_7.ftl",savePath+File.separator,wordName);
		    
		    //3-8
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("Area", StringUtil.getNotNullStr(material.getMortgageArea()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-08 新《广州市不动产登记申请表》_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"budongchanshenqingbiao3_8.ftl",savePath+File.separator,wordName);
		    
		    
		    //从指定目录复制文件到生成文件目录
		    String picPath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads"+File.separator+"insurancepic3");//文件保存位置,项目部署绝对路径（物理路径）
		    File from=new File(picPath);
		    if(from.isDirectory()){
		    	FileUtils.copyDirectory(from, fileSavePath);
		    }
		    
			
			
		}else if(wordType.equals("4")){
			 //4-1
			dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
			dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
			dataMap.put("creditPerson1", StringUtil.getNotNullStr(verification.getCreditPerson()));
			dataMap.put("creditPersonIdCard", StringUtil.getNotNullStr(verification.getCreditPersonIdCard()));
			dataMap.put("creditPersonPhone", StringUtil.getNotNullStr(verification.getCreditPersonPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-01保险抵押贷 开立账户信息表  特别授信_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"tebieshouxin4_1.ftl",savePath+File.separator,wordName);
		    
		    //4-1-1
		    dataMap.put("loanPerson1", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanPerson2", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-01保险抵押贷 开立账户信息表_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"kailizhangtuxinxibiao4_1_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-2
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-02华夏银行授信业务办理申请书  特别授信_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shouxinyewushenqingshu4_2.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-2-1
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-02华夏银行授信业务办理申请书_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shouxinyewushenqingshu4_2_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-3-1
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("witness", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("user", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("loan", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("Amount", StringUtil.getNotNullStr(verification.getLoanAmount()));
		    dataMap.put("Person", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("Manager", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("mortgage", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("Peraaer", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("anAm", StringUtil.getNotNullStr(verification.getLoanAmount()));
		    dataMap.put("nessPer", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("erMan", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("agePer", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("son", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("unt", StringUtil.getNotNullStr(verification.getLoanAmount()));
		    dataMap.put("Price", StringUtil.getNotNullStr(material.getEvaluationTotalPrice()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书1_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-3-2
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("witnessPerson1", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager1", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("witnessPerson2", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager2", StringUtil.getNotNullStr(verification.getUserManager()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书2_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_2.ftl",savePath+File.separator,wordName);
		    
		    
		     //4-3-3
		    dataMap.put("creditPerson1", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("pledgePerson1", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("witnessPerson1", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager1", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("creditPerson2", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("witnessPerson2", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager2", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("pledgePerson2", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("creditPerson3", StringUtil.getNotNullStr(verification.getCreditPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书3 特别授信_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_3.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-3-4
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("witnessPerson", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager", StringUtil.getNotNullStr(verification.getUserManager()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书4 取证_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_4.ftl",savePath+File.separator,wordName);
		    
		    
		}else if(wordType.equals("5")){
			//5-1
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditPersonPhone", StringUtil.getNotNullStr(verification.getCreditPersonPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-21送达确认书1  借款人_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"tebieshouxin5_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-2
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("pledgePersonPhone", StringUtil.getNotNullStr(verification.getPledgePersonPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-22送达确认书2  质押人_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zhiyarenquerenshu5_2.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-3
		    dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditPersonPhone", StringUtil.getNotNullStr(verification.getCreditPersonPhone()));
		    dataMap.put("creditPersonIdCard", StringUtil.getNotNullStr(verification.getCreditPersonIdCard()));
		    dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("pledgeContrateNumber", StringUtil.getNotNullStr(verification.getPledgeContrateNumber()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-23华夏银行小企业网络贷最高额借款合同_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zuigaoejiekuanhetong5_3.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-4
		    dataMap.put("pledge", StringUtil.getNotNullStr(verification.getPledgeContrateNumber()));
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("pledgePersonIdCard", StringUtil.getNotNullStr(verification.getPledgePersonIdCard()));
		    dataMap.put("pledgePersonPhone", StringUtil.getNotNullStr(verification.getPledgePersonPhone()));
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
		    dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-24个人最高额质押合同_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zuigaoezhiyahetong5_4.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-5
		    dataMap.put("companyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
		    dataMap.put("useCompanyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-25股东会决议_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"gudonghuiyijueyi5_5.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-6
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-26关于XX支行XX低风险授信业务质押资金来源的说明_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zhiyazijinlaiyuanshuoming5_6.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-7
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-27华夏银行客户存款差异化定价确认书_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"dingjiaquerenshu5_7.ftl",savePath+File.separator,wordName);
		}else if(wordType.equals("0")){
			
			//1-1
			dataMap.put("companyName", StringUtil.getNotNullStr(material.getCompanyName()));
			dataMap.put("companyAddress", StringUtil.getNotNullStr(material.getCompanyAddress()));
			dataMap.put("setupTime", StringUtil.getNotNullStr(material.getSetupTime()));
			dataMap.put("employeeNumber", StringUtil.getNotNullStr(material.getEmployeeNumber()));
			dataMap.put("companyLegal", StringUtil.getNotNullStr(material.getCompanyLegal()));
			dataMap.put("associationCompany", StringUtil.getNotNullStr(material.getAssociationCompany()));
			dataMap.put("otherHappening", StringUtil.getNotNullStr(material.getOtherHappening()));
			dataMap.put("controllerName", StringUtil.getNotNullStr(material.getControllerName()));
			dataMap.put("controllergender", StringUtil.getNotNullStr(material.getControllerGender()));
			dataMap.put("controllerIdCard", StringUtil.getNotNullStr(material.getControllerIdCard()));
			dataMap.put("controllerPhone", StringUtil.getNotNullStr(material.getControllerPhone()));
			dataMap.put("controllerAddress", StringUtil.getNotNullStr(material.getControllerAddress()));
			dataMap.put("ifLegal", StringUtil.getNotNullStr(material.getIfLegal()));
			dataMap.put("spouseName", StringUtil.getNotNullStr(material.getSpouseName()));
			dataMap.put("spousePhone", StringUtil.getNotNullStr(material.getSpousePhone()));
			dataMap.put("spouseIdCard", StringUtil.getNotNullStr(material.getSpouseIdCard()));
			dataMap.put("familyAssets", StringUtil.getNotNullStr(material.getFamilyAssets()));
			dataMap.put("mainAssets", StringUtil.getNotNullStr(material.getMainAssets()));
			dataMap.put("liabilities", StringUtil.getNotNullStr(material.getLiabilities()));
			wordName=verification.getLoanPerson();
			wordName+="_授信_1-1保险抵押贷 授信调查报告_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"shouxindiaochabaogao1_1.ftl",savePath+File.separator,wordName);
			
			//5-1
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditPersonPhone", StringUtil.getNotNullStr(verification.getCreditPersonPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-21送达确认书1  借款人_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"tebieshouxin5_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-2
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("pledgePersonPhone", StringUtil.getNotNullStr(verification.getPledgePersonPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-22送达确认书2  质押人_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zhiyarenquerenshu5_2.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-3
		    dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditPersonPhone", StringUtil.getNotNullStr(verification.getCreditPersonPhone()));
		    dataMap.put("creditPersonIdCard", StringUtil.getNotNullStr(verification.getCreditPersonIdCard()));
		    dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("pledgeContrateNumber", StringUtil.getNotNullStr(verification.getPledgeContrateNumber()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-23华夏银行小企业网络贷最高额借款合同_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zuigaoejiekuanhetong5_3.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-4
		    dataMap.put("pledge", StringUtil.getNotNullStr(verification.getPledgeContrateNumber()));
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("pledgePersonIdCard", StringUtil.getNotNullStr(verification.getPledgePersonIdCard()));
		    dataMap.put("pledgePersonPhone", StringUtil.getNotNullStr(verification.getPledgePersonPhone()));
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
		    dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-24个人最高额质押合同_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zuigaoezhiyahetong5_4.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-5
		    dataMap.put("companyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
		    dataMap.put("useCompanyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-25股东会决议_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"gudonghuiyijueyi5_5.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-6
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-26关于XX支行XX低风险授信业务质押资金来源的说明_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"zhiyazijinlaiyuanshuoming5_6.ftl",savePath+File.separator,wordName);
		    
		    
		    //5-7
		    dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
			wordName=verification.getLoanPerson();
			wordName+="_特别授信_2-27华夏银行客户存款差异化定价确认书_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"dingjiaquerenshu5_7.ftl",savePath+File.separator,wordName);
		    //2-1
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-01华夏银行支付融资系统业务协议书_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"xitongyewuxieyishu2_1.ftl",savePath+File.separator,wordName);
			//2-2
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-02华夏银行支付融资系统业务办理申请书_"+DateUtil.getNowLongTime()+".xls";
			flag=createWords.create(dataMap,templatePath,"yewubanlishenqingshu2_2.ftl",savePath+File.separator,wordName);
			//2-3
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-03授信基础材料核实证明_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"jichucailiaoheshi2_3.ftl",savePath+File.separator,wordName);
			//2-4-1
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-04送达确认书1  借款人_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"jiekuanren2_4.ftl",savePath+File.separator,wordName);
			
			//2-4-2
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			if(guaranteeList.size()>0){
				dataMap.put("name1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(0).getLguaranteePersonPhone()));
			}else{
				dataMap.put("name1", "");
				dataMap.put("lguaranteePersonPhone","");
			}
			if(guaranteeList.size()>1){
				dataMap.put("name2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
			}else{
				dataMap.put("name2", "");
			}
			if(guaranteeList.size()>2){
				dataMap.put("name3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
			}else{
				dataMap.put("name3","");
			}
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-04送达确认书2  保证人_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhengren2_4_2.ftl",savePath+File.separator,wordName);
			
			//2-5
			List<Map<String, Object>> holdersList=new ArrayList<Map<String,Object>>();
			if(verification!=null){
				dataMap.put("numberOfShareHolders1",StringUtil.getNotNullStr(verification.getShareholders()));
				dataMap.put("guaranteeCompany1",StringUtil.getNotNullStr(verification.getUseCompanyName()));
				String holders=verification.getShareholdersName();
				if(holders!=null&&!holders.equals("")){
					holders=holders.replaceAll("、", "、");
					String[] holdersArr=holders.split("、");
					for(String value:holdersArr){
						 Map<String, Object> map=new HashMap<String, Object>();
				         map.put("holdersName",value);
				         holdersList.add(map);
					}
				}
			}else{
				dataMap.put("numberOfShareHolders1","");
				dataMap.put("guaranteeCompany1","");
			}
			dataMap.put("holdersList", holdersList);
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-05董事会（股东会）成员名单及签字样本_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"gudongqianziyangben2_5.ftl",savePath+File.separator,wordName);
			
			//2-6
			dataMap.put("useCompanyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-06法定代表人证明书及签字样本_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"qianziyangben2_6.ftl",savePath+File.separator,wordName);
			
			
			//2-7
			dataMap.put("useCompanyName1", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			dataMap.put("useCompanyName2", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-07股东会决议_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"gudonghuiyi2_7.ftl",savePath+File.separator,wordName);
			
			
			//2-8
			dataMap.put("useCompanyName", StringUtil.getNotNullStr(verification.getUseCompanyName()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-08预留公章样本_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"yuliugongzhang2_8.ftl",savePath+File.separator,wordName);
			
			
			//2-9
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			dataMap.put("loanTerm", StringUtil.getNotNullStr(verification.getLoanTerm()));
			dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			
			if(guaranteeList.size()>0){
				dataMap.put("guaranteePersonName1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
				dataMap.put("guaranteeNumber1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteeNumber()));
			}else{
				dataMap.put("guaranteePersonName1", "");
				dataMap.put("guaranteeNumber1","");
			}
			if(guaranteeList.size()>1){
				dataMap.put("guaranteePersonName2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
				dataMap.put("guaranteeNumber2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteeNumber()));
			}else{
				dataMap.put("guaranteePersonName2", "");
				dataMap.put("guaranteeNumber2","");
			}
			if(guaranteeList.size()>2){
				dataMap.put("guaranteePersonName3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
				dataMap.put("guaranteeNumber3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteeNumber()));
			}else{
				dataMap.put("guaranteePersonName3","");
				dataMap.put("guaranteeNumber3","");
			}
			
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-09华夏银行小企业网络贷最高额借款合同1_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"jiekuanhetong2_9_1.ftl",savePath+File.separator,wordName);
			
			
			//2-10
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
			dataMap.put("mortgageIdCard", StringUtil.getNotNullStr(verification.getMortgageIdCard()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("mortgagePhone", StringUtil.getNotNullStr(verification.getMortgagePhone()));
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			dataMap.put("mortgageValue", StringUtil.getNotNullStr(verification.getMortgageValue()));
			dataMap.put("highMortgageNumber2", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			dataMap.put("mortgageAddress2", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("mortgageOwner2", StringUtil.getNotNullStr(material.getMortgageOwner()));
			dataMap.put("Area", StringUtil.getNotNullStr(material.getMortgageArea()));
			dataMap.put("propertyNumber", StringUtil.getNotNullStr(material.getPropertyNumber()));
			dataMap.put("price", StringUtil.getNotNullStr(material.getEvaluationPrice()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-10个人最高额抵押合同_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"diyahetong2_10.ftl",savePath+File.separator,wordName);
			
			
			//2-12
			
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			if(guaranteeList.size()>0){
				dataMap.put("guaranteeNumber", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
				dataMap.put("uaranteePersonIdCard", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonIdCard()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(0).getLguaranteePersonPhone()));
			}else{
				dataMap.put("guaranteeNumber","");
				dataMap.put("guaranteePersonName","");
				dataMap.put("uaranteePersonIdCard","");
				dataMap.put("lguaranteePersonPhone","");
			}
			
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-12个人最高额保证合同1_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhenghetong2_12.ftl",savePath+File.separator,wordName);
			
			
			if(guaranteeList.size()>1){
				dataMap.put("guaranteeNumber", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
				dataMap.put("uaranteePersonIdCard", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonIdCard()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(1).getLguaranteePersonPhone()));
			}else{
				dataMap.put("guaranteeNumber","");
				dataMap.put("guaranteePersonName","");
				dataMap.put("uaranteePersonIdCard","");
				dataMap.put("lguaranteePersonPhone","");
			}
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-12个人最高额保证合同2_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhenghetong2_12.ftl",savePath+File.separator,wordName);
			
			
			if(guaranteeList.size()>2){
				dataMap.put("guaranteeNumber", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
				dataMap.put("uaranteePersonIdCard", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonIdCard()));
				dataMap.put("lguaranteePersonPhone", StringUtil.getNotNullStr(guaranteeList.get(2).getLguaranteePersonPhone()));
			}else{
				dataMap.put("guaranteeNumber","");
				dataMap.put("guaranteePersonName","");
				dataMap.put("uaranteePersonIdCard","");
				dataMap.put("lguaranteePersonPhone","");
			}
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-12个人最高额保证合同3_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"baozhenghetong2_12.ftl",savePath+File.separator,wordName);
			
			
			//2-13
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-13个人经营性面谈记录_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"miantanjilv2_13.ftl",savePath+File.separator,wordName);
			
			
			//2-14
			wordName=verification.getLoanPerson();
			wordName+="_核保_2-14续保承诺书_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"chengnuoshu2_14.ftl",savePath+File.separator,wordName);
			
			
			
			//3-1
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-01法定代表人证明书（抵押、领证）_"+DateUtil.getNowLongTime()+".doc";
			flag=createWords.create(dataMap,templatePath,"zhengmingshu3_1.ftl",savePath+File.separator,wordName);
			//3-4
			dataMap.put("proxyPerson", StringUtil.getNotNullStr(verification.getProxyPerson()));
			dataMap.put("proxyPersonIdCard", StringUtil.getNotNullStr(verification.getProxyPersonIdCard()));
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-04取证委托书  房管局_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"quzhengweituoshu3_4.ftl",savePath+File.separator,wordName);
		    
		    
		    //3-7
			dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanAmount", StringUtil.getNotNullStr(verification.getLoanAmount()));
			
			dataMap.put("loanPerson1", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
			dataMap.put("creditAmount", StringUtil.getNotNullStr(verification.getCreditAmount()));
			
			dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
			dataMap.put("highMortgageNumber", StringUtil.getNotNullStr(verification.getHighMortgageNumber()));
			
			if(guaranteeList.size()>0){
				dataMap.put("guaranteeNumber1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName1", StringUtil.getNotNullStr(guaranteeList.get(0).getGuaranteePersonName()));
			}else{
				dataMap.put("guaranteeNumber1", "");
				dataMap.put("guaranteePersonName1","");
			}
			if(guaranteeList.size()>1){
				dataMap.put("guaranteeNumber2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName2", StringUtil.getNotNullStr(guaranteeList.get(1).getGuaranteePersonName()));
			}else{
				dataMap.put("guaranteeNumber2", "");
				dataMap.put("guaranteePersonName2","");
			}
			if(guaranteeList.size()>2){
				dataMap.put("guaranteeNumber3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteeNumber()));
				dataMap.put("guaranteePersonName3", StringUtil.getNotNullStr(guaranteeList.get(2).getGuaranteePersonName()));
			}else{
				dataMap.put("guaranteeNumber3", "");
				dataMap.put("guaranteePersonName3","");
			}
			dataMap.put("pledgeContrateNumber", StringUtil.getNotNullStr(verification.getPledgeContrateNumber()));
			dataMap.put("pledgePerson", StringUtil.getNotNullStr(verification.getPledgePerson()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-07 保险抵押贷 合同用印_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"hetongyongyin3_7.ftl",savePath+File.separator,wordName);
		    
		    //3-8
			dataMap.put("mortgageAddress", StringUtil.getNotNullStr(material.getMortgageAddress()));
			dataMap.put("Area", StringUtil.getNotNullStr(material.getMortgageArea()));
			wordName=verification.getLoanPerson();
			wordName+="_入押_3-08 新《广州市不动产登记申请表》_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"budongchanshenqingbiao3_8.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-1
			dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
			dataMap.put("creditLoanContract", StringUtil.getNotNullStr(verification.getCreditLoanContract()));
			dataMap.put("creditPerson1", StringUtil.getNotNullStr(verification.getCreditPerson()));
			dataMap.put("creditPersonIdCard", StringUtil.getNotNullStr(verification.getCreditPersonIdCard()));
			dataMap.put("creditPersonPhone", StringUtil.getNotNullStr(verification.getCreditPersonPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-01保险抵押贷 开立账户信息表  特别授信_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"tebieshouxin4_1.ftl",savePath+File.separator,wordName);
		    
		    //4-1-1
		    dataMap.put("loanPerson1", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("contrateNumber", StringUtil.getNotNullStr(verification.getContrateNumber()));
			dataMap.put("loanPerson2", StringUtil.getNotNullStr(verification.getLoanPerson()));
			dataMap.put("loanIdCard", StringUtil.getNotNullStr(verification.getLoanIdCard()));
			dataMap.put("loanPhone", StringUtil.getNotNullStr(verification.getLoanPhone()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-01保险抵押贷 开立账户信息表_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"kailizhangtuxinxibiao4_1_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-2
		    dataMap.put("creditPerson", StringUtil.getNotNullStr(verification.getCreditPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-02华夏银行授信业务办理申请书  特别授信_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shouxinyewushenqingshu4_2.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-2-1
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-02华夏银行授信业务办理申请书_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shouxinyewushenqingshu4_2_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-3-1
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("witness", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("user", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("loan", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("Amount", StringUtil.getNotNullStr(verification.getLoanAmount()));
		    dataMap.put("Person", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("Manager", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("mortgage", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("Peraaer", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("anAm", StringUtil.getNotNullStr(verification.getLoanAmount()));
		    dataMap.put("nessPer", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("erMan", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("agePer", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("son", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("unt", StringUtil.getNotNullStr(verification.getLoanAmount()));
		    dataMap.put("Price", StringUtil.getNotNullStr(material.getEvaluationTotalPrice()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书1_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_1.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-3-2
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("witnessPerson1", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager1", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("witnessPerson2", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager2", StringUtil.getNotNullStr(verification.getUserManager()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书2_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_2.ftl",savePath+File.separator,wordName);
		    
		    
		     //4-3-3
		    dataMap.put("creditPerson1", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("pledgePerson1", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("witnessPerson1", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager1", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("creditPerson2", StringUtil.getNotNullStr(verification.getCreditPerson()));
		    dataMap.put("witnessPerson2", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager2", StringUtil.getNotNullStr(verification.getUserManager()));
		    dataMap.put("pledgePerson2", StringUtil.getNotNullStr(verification.getPledgePerson()));
		    dataMap.put("creditPerson3", StringUtil.getNotNullStr(verification.getCreditPerson()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书3 特别授信_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_3.ftl",savePath+File.separator,wordName);
		    
		    
		    //4-3-4
		    dataMap.put("loanPerson", StringUtil.getNotNullStr(verification.getLoanPerson()));
		    dataMap.put("mortgagePerson", StringUtil.getNotNullStr(verification.getMortgagePerson()));
		    dataMap.put("witnessPerson", StringUtil.getNotNullStr(verification.getWitnessPerson()));
		    dataMap.put("userManager", StringUtil.getNotNullStr(verification.getUserManager()));
			wordName=verification.getLoanPerson();
			wordName+="_放款_4-03华夏银行小企业授信业务实地见证确认书4 取证_"+DateUtil.getNowLongTime()+".doc";
		    flag=createWords.create(dataMap,templatePath,"shidijianzhengquerenshu4_3_4.ftl",savePath+File.separator,wordName);
		    
		    
		    //从指定目录复制文件到生成文件目录
		    String picPath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads"+File.separator+"insurancepic3");//文件保存位置,项目部署绝对路径（物理路径）
		    File from=new File(picPath);
		    if(from.isDirectory()){
		    	FileUtils.copyDirectory(from, fileSavePath);
		    }
		}
		if(flag){
			String saveZipPath=request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"downloads"+File.separator+"ziptemp");//zip文件保存位置,项目部署绝对路径（物理路径）
			File saveZipPathFile=new File(saveZipPath);
			if(!saveZipPathFile.exists()){
				saveZipPathFile.mkdirs();
			}
			String zipName="word";
			if(verification!=null&&verification.getLoanPerson()!=null&&!verification.getLoanPerson().equals("")){
				zipName=verification.getLoanPerson();
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
		}else{
			log.error("word生成失败。");
			context.put("message", "系统出错了，请联系技术人员！");
			return forword("message/message",context);
		}
		return null;
	}
}
