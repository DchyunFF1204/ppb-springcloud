package ${controllerPackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import ${entityPackage}.${className};
import ${servicePackage}.${className}Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
 
/**
 * 
 * <br>
 * <b>功能：</b>${className}Controller<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> ${createDate}<br>
 */ 
@Controller
@RequestMapping("/${lowClassName}") 
public class ${className}Controller {
	
	private final static Logger log= Logger.getLogger(${className}Controller.class);
	
	@Resource
	private ${className}Service ${lowClassName}Service; 
	
	/**
	 * 初始化页面
	 */
	@RequestMapping("/into${className}Page") 
	public String into${className}Page(){
		return "${lowClassName}Page"; 
	}
	
	/**
	 * id 查询 ${className}
	 **/
	@RequestMapping("/get${className}ById")
	@ResponseBody
	public Map<String,Object> select${className}ByPrimaryKey(Long id){
		Map<String,Object> result = new HashMap<String,Object>();
		${className} ${lowClassName} = ${lowClassName}Service.select${className}ByPrimaryKey(id);
		result.put("isSuccess",true);
		result.put("${lowClassName}",${lowClassName});
		return result;
	}
	
	/**
	 * 通过model分页查询 ${className}列表信息
     * @param model 对象参数
     * @param page 页码
     * @param rows 每页数目
	 **/
	@RequestMapping("/select${className}PgByModel")
	@ResponseBody
	public Map<String,Object> select${className}PgByModel(@ModelAttribute ${className} model,Integer page,Integer rows){
	    Map<String,Object> result = new HashMap<String,Object>();
	    page = page == null?1:page;  
    	rows = rows == null?10:rows;  
    	PageHelper.startPage(page,rows);
    	//PageHelper.orderBy("occur_time desc");  
		List<${className}> list = ${lowClassName}Service.select${className}ListByModel(model);
		PageInfo<${className}> pageInfo = new PageInfo<${className}>(list);  
		result.put("isSuccess",true);
		result.put("pageRows",rows); //每页显示数目
		result.put("total",pageInfo.getPages()); // 总页数
		result.put("records",pageInfo.getTotal()); // 总数目
		result.put("rows",list);
		return result;
	}
	
	/**
     * 保存${className}信息
     * @param model 对象参数
     */
    @RequestMapping("/save${className}")
	@ResponseBody
	public Map<String,Object> save${className}(@ModelAttribute ${className} model){
		return ${lowClassName}Service.save${className}(model);
	}
	
    /**
	 * id 物理删除  ${className}
	 **/
	@RequestMapping("/del${className}ById")
	@ResponseBody
    public Map<String,Object> del${className}ById(Long id){
    	return ${lowClassName}Service.del${className}ById(id);
    }
	
}
