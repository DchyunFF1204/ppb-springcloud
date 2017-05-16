package com.ppb.util.code;

import com.google.common.base.Strings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class CreateBean {
	
	private Connection connection = null;
	static String url;
	static String username;
	static String password;
	static String rt = "\r\t";
	String SQLTables = "show tables";
	private String method;
	private String argv;
	static String selectStr;
	static String from;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		selectStr = "select ";
		from = " from ";
	}
	
	public void setMysqlInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() throws SQLException {
	
		return DriverManager.getConnection(url, username, password);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> getTables() throws SQLException {
	
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(this.SQLTables);
		ResultSet rs = ps.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			String tableName = rs.getString(1);
			list.add(tableName);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
	
		String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '"
				+ tableName + "' " + "and table_schema =  '" + CodeResourceUtil.DATABASE_NAME + "'";
		
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List columnList = new ArrayList();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		while (rs.next()) {
			String name = rs.getString(1);
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = (rs.getString(6) == null) ? "" : rs.getString(6);
			String nullable = TableConvert.getNullAble(rs.getString(7));
			String importPackage = getImportType(type, precision, scale);
			type = getType(type, precision, scale);
					
			ColumnData cd = new ColumnData();
			cd.setColumnName(name);
			cd.setImportPackage(importPackage);
			cd.setMname(getMname(name));
			cd.setMethod(getMethod(name));
			cd.setDataType(type);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			formatFieldClassType(cd);
			columnList.add(cd);
		}
		this.argv = str.toString();
		this.method = getset.toString();
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getBeanFeilds(String tableName) throws SQLException {
		List dataList = getColumnDatas(tableName);
		/*StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		for (Iterator localIterator = dataList.iterator(); localIterator.hasNext();) {
			ColumnData d = (ColumnData) localIterator.next();
			String name = d.getColumnName();
			String type = d.getDataType();
			String comment = d.getColumnComment();
			
			if(Strings.isNullOrEmpty(comment)){
				str.append("\r\t").append("/** \t");
				str.append("\r\t").append(" * "+comment+" \t");
				str.append("\r\t").append(" **/
				
/*			}
			name = d.getMname();
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";\r\t");
			String method = d.getMethod();
			getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("    this." + name + "=").append(name).append(";\r\t}");
		}
		this.argv = str.toString();
		this.method = getset.toString(); */
		return dataList;
	}
	
	public List getDyBeanFeilds(String tableName) throws SQLException {
		List dataList = getColumnDatas(tableName);
		return dataList;
	}
	
	public String getMname(String name){
		String[] ff = name.split("_");
		String name1 = "";
		for(int i=0;i<ff.length;i++){
			String ss = ff[i];
			name1 += ss.substring(0, 1).toUpperCase()+ ss.substring(1, ss.length());
		}
		name1 = name1.substring(0, 1).toLowerCase()+ name1.substring(1, name1.length());
		return name1;
	}
	
	public String getMethod(String name){
		String[] ff = name.split("_");
		String name1 = "";
		for(int i=0;i<ff.length;i++){
			String ss = ff[i];
			name1 += ss.substring(0, 1).toUpperCase()+ ss.substring(1, ss.length());
		}
		name1 = name1.substring(0, 1).toLowerCase()+ name1.substring(1, name1.length());
		String maxChar = name1.substring(0, 1).toUpperCase();
		String method = maxChar + name1.substring(1, name1.length());
		return method;
	}
	
	
	public String getBeanImportPackage(String tableName) throws SQLException {
		Set<String> sl = new HashSet<String>();
		List dataList = getColumnDatas(tableName);
		StringBuffer sb = new StringBuffer();
		for (Iterator localIterator = dataList.iterator(); localIterator.hasNext();) {
			ColumnData d = (ColumnData) localIterator.next();
			if(Strings.isNullOrEmpty(d.getImportPackage())){
				if(sl.contains(d.getImportPackage())){
					continue;
				}else{
					sl.add(d.getImportPackage());
					sb.append(d.getImportPackage());
				}
			}
		}
		if(sl != null && sl.size() > 0){
			return sb.toString();
		}else{
			return "";
		}
	}
	
	private void formatFieldClassType(ColumnData columnt) {
	
		String fieldType = columnt.getColumnType();
		String scale = columnt.getScale();
		
		if ("N".equals(columnt.getNullable()))
			columnt.setOptionType("required:true");
		
		if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
			columnt.setClassType("easyui-datetimebox");
		}
		else if ("date".equals(fieldType)) {
			columnt.setClassType("easyui-datebox");
		}
		else if ("int".equals(fieldType)) {
			columnt.setClassType("easyui-numberbox");
		}
		else if ("number".equals(fieldType)) {
			if ((Strings.isNullOrEmpty(scale)) && (Integer.parseInt(scale) > 0)) {
				columnt.setClassType("easyui-numberbox");
				if (Strings.isNullOrEmpty(columnt.getOptionType()))
					columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
				else
					columnt.setOptionType("precision:2,groupSeparator:','");
			}
			else {
				columnt.setClassType("easyui-numberbox");
			}
		}
		else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
			columnt.setClassType("easyui-numberbox");
			if (Strings.isNullOrEmpty(columnt.getOptionType()))
				columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
			else
				columnt.setOptionType("precision:2,groupSeparator:','");
		}
		else {
			columnt.setClassType("easyui-validatebox");
		}
	}
	
	public String getType(String dataType, String precision, String scale) {
	
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "String";
		else if (dataType.contains("int"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number"))
			if ((Strings.isNullOrEmpty(scale)) && (Integer.parseInt(scale) > 0))
				dataType = "java.math.BigDecimal";
			else if ((Strings.isNullOrEmpty(precision)) && (Integer.parseInt(precision) > 6))
				dataType = "Long";
			else
				dataType = "Integer";
		
		else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else
			dataType = "Object";
		
		return dataType;
	}
	
	/**
	 * import package
	 * @param dataType
	 * @param precision
	 * @param scale
	 * @return
	 */
	public String getImportType(String dataType, String precision, String scale) {
		StringBuffer sb = new StringBuffer();
		dataType = dataType.toLowerCase();
		if (dataType.contains("number")){
			if ((Strings.isNullOrEmpty(scale)) && (Integer.parseInt(scale) > 0))
				sb.append("import java.math.BigDecimal;\r");
		}
		else if (dataType.contains("decimal"))
			sb.append("import java.math.BigDecimal;\r");
		else if (dataType.contains("date") || dataType.contains("datetime"))
			sb.append("import java.util.Date;\r");
		else if (dataType.contains("time"))
			sb.append("import java.sql.Timestamp;\r");
		else if (dataType.contains("clob"))
			sb.append("import java.sql.Clob;\r");
		return sb.toString();
	}
	
	public void getPackage(int type, String createPath, String content, String packageName, String className, String extendsClassName,
			String[] importName) throws Exception {
	
		if (packageName == null)
			packageName = "";
		
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; ++i)
			sb.append("import ").append(importName[i]).append(";\r");
		
		sb.append("\r");
		sb.append("/**\r *  entity. @author wolf Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null)
			sb.append(" extends ").append(extendsClassName);
		
		if (type == 1)
			sb.append(" ").append("implements java.io.Serializable {\r");
		else
			sb.append(" {\r");
		
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = temp + className.substring(1, className.length());
		if (type == 1)
			sb.append("private " + className + " " + temp + "; // entity ");
		
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		createFile(createPath, "", sb.toString());
	}
	
	/**
	 * 根据表名称得到实体类名称
	 * @param tableName
	 * @return
	 */
	public String getTablesNameToClassName(String tableName) {
	
		String[] split = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; ++i) {
				String tempTableName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
				sb.append(tempTableName);
			}
			
			return sb.toString();
		}
		String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
		return tempTables;
	}
	
	public void createFile(String path, String fileName, String str) throws IOException {
	
		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}
	
	public Map<String, Object> getAutoCreateSql(String tableName) throws Exception {
	
		Map sqlMap = new HashMap();
		List columnDatas = getColumnDatas(tableName);
		String columns = getColumnSplit(columnDatas);
		String[] columnList = getColumnList(columns);
		String columnFields = getColumnFields(columns);
		String insert = "insert into " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n values(#{"
				+ columns.replaceAll("\\|", "},#{") + "})";
		String update = getUpdateSql(tableName, columnList);
		String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
		String selectById = getSelectByIdSql(tableName, columnList);
		String delete = getDeleteSql(tableName, columnList);
		sqlMap.put("columnList", columnList);
		sqlMap.put("columnFields", columnFields);
		sqlMap.put("insert", insert.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("update", update.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("delete", delete);
		sqlMap.put("updateSelective", updateSelective);
		sqlMap.put("selectById", selectById);
		return sqlMap;
	}
	
	public String getDeleteSql(String tableName, String[] columnsList) throws SQLException {
	
		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}
	
	public String getSelectByIdSql(String tableName, String[] columnsList) throws SQLException {
	
		StringBuffer sb = new StringBuffer();
		sb.append("select <include refid=\"Base_Column_List\" /> \n");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}
	
	public String getColumnFields(String columns) throws SQLException {
	
		String fields = columns;
		if ((fields != null) && (!("".equals(fields))))
			fields = fields.replaceAll("[|]", ",");
		
		return fields;
	}
	
	public String[] getColumnList(String columns) throws SQLException {
	
		String[] columnList = columns.split("[|]");
		return columnList;
	}
	
	public String getUpdateSql(String tableName, String[] columnsList) throws SQLException {
	
		StringBuffer sb = new StringBuffer();
		
		for (int i = 1; i < columnsList.length; ++i) {
			String column = columnsList[i];
			if ("CREATETIME".equals(column.toUpperCase()))
				continue;
			
			if ("UPDATETIME".equals(column.toUpperCase()))
				sb.append(column + "=now()");
			else
				sb.append(column + "=#{" + column + "}");
			
			if (i + 1 < columnsList.length)
				sb.append(",");
		}
		
		String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{" + columnsList[0] + "}";
		return update;
	}
	
	public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
	
		StringBuffer sb = new StringBuffer();
		ColumnData cd = (ColumnData) columnList.get(0);
		sb.append("\t<trim  suffixOverrides=\",\" >\n");
		for (int i = 1; i < columnList.size(); ++i) {
			ColumnData data = (ColumnData) columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"").append(columnName).append(" != null ");
			
			if ("String" == data.getDataType())
				sb.append(" and ").append(columnName).append(" != ''");
			
			sb.append(" \">\n\t\t");
			sb.append(columnName + "=#{" + columnName + "},\n");
			sb.append("\t</if>\n");
		}
		sb.append("\t</trim>");
		String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{" + cd.getColumnName()
				+ "}";
		return update;
	}
	
	public String getColumnSplit(List<ColumnData> columnList) throws SQLException {
	
		StringBuffer commonColumns = new StringBuffer();
		for (Iterator localIterator = columnList.iterator(); localIterator.hasNext();) {
			ColumnData data = (ColumnData) localIterator.next();
			commonColumns.append(data.getColumnName() + "|");
		}
		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}
}