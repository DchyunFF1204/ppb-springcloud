package com.ppb.util.code;

import freemarker.template.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Map;

public class CommonPageParser {
	
	private static final Log log = LogFactory.getLog(CommonPageParser.class);
	private static boolean isReplace = true;
	
	private static String pageEncoding = "UTF-8";  
    private static Configuration configuration;    //FreeMarker配置  
	
	static {
		try {
			configuration = new Configuration();  
			configuration.setClassForTemplateLoading(CommonPageParser.class,"template");  
			configuration.setEncoding(Locale.getDefault(), pageEncoding);  
		}
		catch (Exception e) {
			log.error(e);
		}
	}
	
	public static void WriterCreatePage(Map<String,Object> context, String templateName, String fileDirPath, String targetFile) {
		
		File file;
		try {
			file = new File(fileDirPath + targetFile);
			if (!(file.exists())) {
				new File(file.getParent()).mkdirs();
			}
			else if (isReplace) {
				log.info("替换文件:" + file.getAbsolutePath());
				file.delete();
			}
			
			freemarker.template.Template temp = configuration.getTemplate(templateName, pageEncoding);  
	        temp.setEncoding(pageEncoding);  
			//Template template = ve.getTemplate(templateName, "UTF-8");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			//template.merge(context, writer);
			temp.process(context, writer); 
			writer.flush();
			writer.close();
			fos.close();
			log.info("生成文件：" + file.getAbsolutePath());
		}
		catch (Exception e) {
			log.error(e);
		}
	}
	
	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}
}