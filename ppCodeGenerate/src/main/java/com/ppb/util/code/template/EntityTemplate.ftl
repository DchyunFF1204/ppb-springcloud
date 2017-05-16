package ${entityPackage};

import java.io.Serializable;
${importPackage}
/**
 * 
 * <br>
 * <b>功能：</b>${className} 实体类<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> ${createDate} <br>
 */
@SuppressWarnings("serial")
public class ${className} implements Serializable {
	
	<#list feilds as fl>
	/**
	 * ${fl.columnComment!''}
	 */
	private ${fl.dataType} ${fl.mname};
	
	</#list>
	<#list feilds as fl>
	public ${fl.dataType} get${fl.method}(){
	   return ${fl.mname};
	}
	 
	public void set${fl.method}(${fl.dataType} ${fl.mname}){
	    this.${fl.mname} = ${fl.mname};
	}
	 
	</#list>
}

