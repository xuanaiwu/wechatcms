package com.dayuan.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.dayuan.action.BaseAction;

public class ZipUtil {
	
	public final static Logger log= Logger.getLogger(BaseAction.class.getName());
	/**
	 * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
	 * @param sourceFilePath :待压缩的文件路径
	 * @param zipFilePath :压缩后存放路径
	 * @param fileName :压缩后文件的名称
	 * @return 返回文件存放路径+文件名
	 */
	public static String fileToZip(String sourceFilePath,String zipFilePath,String fileName){
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);//待压缩的文件路径
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		if(sourceFile.exists() == false){
			log.error("待压缩的文件目录："+sourceFilePath+"不存在.");
		}else{
			try {
				File zipFile = new File(zipFilePath + File.separator + fileName);//压缩后存放路径+压缩后文件的名称
				if(zipFile.exists()){
					System.out.println(zipFilePath + "目录下存在名字为:" + fileName +"打包文件.");
					log.error(zipFilePath + "目录下已经存在名字为:" + fileName +"打包文件.");
				}else{
					File[] sourceFiles = sourceFile.listFiles();
					if(null == sourceFiles || sourceFiles.length<1){
						log.error("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
					}else{
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024*10];
						for(int i=0;i<sourceFiles.length;i++){
							//创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							//读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024*10);
							int read = 0;
							while((read=bis.read(bufs, 0, 1024*10)) != -1){
								zos.write(bufs,0,read);
							}
						}
						flag = true;
						log.error("文件："+zipFile+"创建成功！");
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally{
				//关闭流
				try {
					if(null != bis) bis.close();
					if(null != zos) zos.close();
					if(null != fis) fis.close();
					if(null != fos) fos.close();
					if(null != sourceFile) sourceFile=null;
					
					/**删除文件下所有文件，然后删除文件夹  xuanaw 20161114*/
					File filePath=new File(sourceFilePath);
					if(filePath.isDirectory()){
						File[] files=filePath.listFiles();
						for(File file:files){
							file.delete();
						}
						filePath.delete();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		if(flag){
			return zipFilePath + File.separator + fileName;
		}else{
			return null;
		}
		
	}

}
