package ${servicePackage};

import org.apache.log4j.Logger;

import ${entityPackage}.${className};
import ${entityPackage}.${className}Example;
import ${daoPackage}.${className}Mapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;


/**
 * 
 * <br>
 * <b>功能：</b>${className}Service 服务<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> ${createDate}<br>
 */
@Service("${lowClassName}Service")
public class ${className}Service {

	private final static Logger log= Logger.getLogger(${className}Service.class);
	

	@Resource
    private ${className}Mapper ${lowClassName}Mapper;
    
    /**
     * 通过id 查询 ${className}
     */
    public ${className} select${className}ByPrimaryKey(Long id){
        return ${lowClassName}Mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 通过model查询 ${className}列表信息
     */
    public List<${className}> select${className}ListByModel(${className} model){
        ${className}Example example = new ${className}Example();
        ${className}Example.Criteria criteria = example.createCriteria();
        if(model != null){
            <#list feilds as fl>
            <#if fl.dataType == 'String'>
            if(StringUtils.isNotBlank(model.get${fl.method}())){
                criteria.and${fl.method}EqualTo(model.get${fl.method}());
            }
            <#else>
            if(model.get${fl.method}() != null){
                criteria.and${fl.method}EqualTo(model.get${fl.method}());
            }
            </#if>
            </#list>
        }
        List<${className}> list = ${lowClassName}Mapper.selectByExample(example);
        return list;
    }
    
    /**
     * 保存${className}信息
     * @param model 对象参数
     */
    public Map<String,Object> save${className}(${className} model){
    	Map<String,Object> result = new HashMap<String,Object>();
    	if(model == null){
    		result.put("isSuccess",false);
    		result.put("message","参数为空");
    		return result;
    	}
    	if(model.getId() == null){
    	   ${lowClassName}Mapper.insert(model);
    	}else{
    	   ${lowClassName}Mapper.updateByPrimaryKeySelective(model);
    	}
    	result.put("isSuccess",true);
    	result.put("message","save success!");
    	return result;
    }
    
    /**
     * 通过id 物理删除 ${className}
     */
    public Map<String,Object> del${className}ById(Long id){
    	Map<String,Object> result = new HashMap<String,Object>();
    	int i = ${lowClassName}Mapper.deleteByPrimaryKey(id);
    	if(i>0){
    		result.put("status","success");
        	return result;
    	}else{
    		result.put("status","error");
    		result.put("message","删除数据异常");
    		return result;
    	}
    }

}
