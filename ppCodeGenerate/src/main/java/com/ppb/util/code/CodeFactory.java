package com.ppb.util.code;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ppb.model.CodeBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author daizy
 *
 * 代码生成工程
 */
public class CodeFactory {
	
	private static final Log log = LogFactory.getLog(CodeFactory.class);

	/**
	 * 代码生成
	 * @param codeBean
	 */
	public static void buildCode(CodeBean codeBean){
		List<String> tables = Lists.newArrayList(Splitter.on(",").split(codeBean.getTableNames()));
		tables.forEach(entry-> {
			MysqlFactory mysqlFactory = MysqlFactory.getInstance(codeBean.getDatasourceDriver());
			Connection con  = mysqlFactory.getConnection(codeBean.getDatasourceUrl(),
					codeBean.getDatasourceUserName(),
					codeBean.getDatasourceUserPwd());
			String className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,entry);
			// 实体类工程路径
			String modelPath = codeBean.getCodeModelPackage().replace(".", "/")+ "\\" + className + ".java";
			String modelExamplePath = codeBean.getCodeModelPackage().replace(".", "/")+ "\\" + className + "Example.java";
			String modelDtoPath = codeBean.getCodeModelPackage().replace(".", "/")+ "\\" + className + "Dto.java";
			// Mapper 工程路径
			String mapperPath = codeBean.getCodeMapperPackage().replace(".", "/")+ "\\" + className + "Mapper.java";
			// service 工程路径
			String servicePath = codeBean.getCodeServicePackage().replace(".", "/")+ "\\" + className + "Service.java";
			// controller 工程路径
			String controllerPath = codeBean.getCodeControllerPackage().replace(".", "/")+ "\\" + className + "Controller.java";
			// sqlxml 工程路径resources
			String sqlXmlPath = codeBean.getCodeTargetProject()+"resources"+"\\" + className + "Mapper.xml";
			// 工程路径java
			String pckPath = codeBean.getCodeTargetProject()+"java"+"\\";
			// ftl 工程路径
			String ftlPath = codeBean.getCodeFtlPackage().replace(".", "/")+"\\" + className + "Page.ftl";
			String jsPath = codeBean.getCodeFtlPackage().replace(".","/")+"\\"+className+".js";
			Map<String,Object> context = Maps.newConcurrentMap();
			context.put("className", className);
			context.put("lowClassName", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry));
			context.put("entityPackage", codeBean.getCodeModelPackage());
			context.put("servicePackage", codeBean.getCodeServicePackage());
			context.put("controllerPackage",codeBean.getCodeControllerPackage());
			context.put("daoPackage", codeBean.getCodeMapperPackage());
			context.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			context.put("tableName", entry);
			List<ColumnData> list = mysqlFactory.getColumnDatas(entry,con);
			context.put("feilds", list);
			context.put("importPackage", mysqlFactory.getBeanImportPackage(list));
			CommonPageParser.WriterCreatePage(context, "EntityTemplate.ftl", pckPath, modelPath);
			CommonPageParser.WriterCreatePage(context, "EntityExampleTemplate.ftl", pckPath, modelExamplePath);
			CommonPageParser.WriterCreatePage(context, "EntityDtoTemplate.ftl", pckPath, modelDtoPath);
			CommonPageParser.WriterCreatePage(context, "DaoTemplate.ftl", pckPath, mapperPath);
			CommonPageParser.WriterCreatePage(context, "ServiceTemplate.ftl", pckPath, servicePath);
			CommonPageParser.WriterCreatePage(context, "ControllerTemplate.ftl", pckPath, controllerPath);
			CommonPageParser.WriterCreatePage(context, "MapperTemplate.ftl", "", sqlXmlPath);
			CommonPageParser.WriterCreatePage(context, "ftlTemplate.ftl", "", ftlPath);
			CommonPageParser.WriterCreatePage(context, "jsTemplate.ftl", "", jsPath);
		});
	}
}
