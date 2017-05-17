package com.ppb.util.code;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.*;

/**
 * mysql操作
 *
 * @author daizy
 * @create 2017-05-17 9:17
 */
public class MysqlFactory {

    /**
     * 查询所有表信息
     */
    private static final String SQLTables="show tables";

    private static MysqlFactory instance = null;

    public static MysqlFactory getInstance(String datasourceDriver) {
        if (instance == null) {
            try {
                instance = new MysqlFactory(datasourceDriver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private MysqlFactory(String datasourceDriver) throws ClassNotFoundException {
        Class.forName(datasourceDriver);
    }


    /**
     * 获取数据库链接
     * @param datasourceUrl
     * @param datasourceUserName
     * @param datasourceUserPwd
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String datasourceUrl, String datasourceUserName, String datasourceUserPwd) {
        try {
            return DriverManager.getConnection(datasourceUrl,datasourceUserName,datasourceUserPwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表信息
     * @param con
     * @return
     */
    public List<String> getTables(Connection con)  {
        List list = new ArrayList();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(this.SQLTables);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString(1);
                list.add(tableName);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析表字段
     * @param tableName
     * @param con
     * @return
     * @throws SQLException
     */
    public List<ColumnData> getColumnDatas(String tableName,Connection con) {
        List columnList = Lists.newArrayList();
        String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '"
                + tableName + "' " + "and table_schema =  'sys'";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQLColumns);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String type = rs.getString(2);
                String comment = rs.getString(3);
                String precision = rs.getString(4);
                String scale = rs.getString(5);
                String charmaxLength = (rs.getString(6) == null) ? "" : rs.getString(6);
                ColumnData cd = new ColumnData();
                cd.setColumnName(name);
                Map<String,String> re = getFiledInfo(type, precision, scale);
                cd.setImportPackage(re.get("importType"));
                cd.setDataType(re.get("dataTy"));
                cd.setMname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name)); // bean filedName
                cd.setMethod(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)); // 获取方法名
                cd.setColumnType(rs.getString(2));
                cd.setColumnComment(comment);
                cd.setPrecision(precision);
                cd.setScale(scale);
                cd.setCharmaxLength(charmaxLength);
                columnList.add(cd);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

    /**
     * 解析字段
     * @param dataType
     * @param precision
     * @param scale
     * @return
     */
    public Map<String,String> getFiledInfo(String dataType, String precision, String scale) {
        Map<String,String> re = new HashMap<String,String>();
        StringBuffer sb = new StringBuffer();
        dataType = dataType.toLowerCase();
        String dataTy = "";
        if (dataType.contains("char"))
            dataTy = "String";
        else if (dataType.contains("int"))
            dataTy = "Integer";
        else if (dataType.contains("float"))
            dataTy = "Float";
        else if (dataType.contains("double"))
            dataTy = "Double";
        else if (dataType.contains("number")){
            if ((Strings.isNullOrEmpty(scale)) && (Integer.parseInt(scale) > 0)){
                sb.append("import java.math.BigDecimal;\r");
                dataTy = "BigDecimal";
            }
            else if ((Strings.isNullOrEmpty(precision)) && (Integer.parseInt(precision) > 6))
                dataType = "Long";
            else
                dataType = "Integer";
        }
        else if (dataType.contains("decimal")){
            sb.append("import java.math.BigDecimal;\r");
            dataTy = "BigDecimal";
        }
        else if (dataType.contains("date") || dataType.contains("datetime")){
            sb.append("import java.util.Date;\r");
            dataTy = "Date";
        }
        else if (dataType.contains("time")){
            sb.append("import java.sql.Timestamp;\r");
            dataTy = "Timestamp";
        }
        else if (dataType.contains("clob"))
            sb.append("import java.sql.Clob;\r");
            dataTy = "Clob";
        re.put("importType",sb.toString());
        re.put("dataTy",dataTy);
        return re;
    }

    /**
     *  获取bean importPage
     * @param list
     * @return
     * @throws SQLException
     */
    public String getBeanImportPackage(List<ColumnData> list) {
        Set<String> transformPersonList = FluentIterable
                .from(list)
                .transform(new Function<ColumnData, String>() {
                    @Override
                    public String apply(ColumnData input) {
                        return input.getImportPackage();
                    }
                }).toSet();
        StringBuffer sb = new StringBuffer();
        transformPersonList.forEach(entry-> sb.append(entry));
        return sb.toString();
    }

}
