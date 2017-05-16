package ${daoPackage};


import ${entityPackage}.${className};
import ${entityPackage}.${className}Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Mapper 类<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> ${createDate} <br>
 */
public interface ${className}Mapper {

    /**
     * 动态条件获取总数目
     */
	int countByExample(${className}Example example);

    /**
     * 动态条件删除数据
     */
	int deleteByExample(${className}Example example);
	
	/**
     * 主键删除数据
     */
    int deleteByPrimaryKey(Long id);
	
	/**
     * 新增数据
     */
    int insert(${className} record);

	/**
     * 动态参数新增数据
     */
    int insertSelective(${className} record);

	/**
     * 动态条件查询数据
     */
    List<${className}> selectByExample(${className}Example example);

	/**
     * 主键查询数据
     */
    ${className} selectByPrimaryKey(Long id);
	
	/**
     * 动态条件更新数据
     */
    int updateByExampleSelective(@Param("record") ${className} record, @Param("example") ${className}Example example);
	
	/**
     * 按条件更新数据
     */
    int updateByExample(@Param("record") ${className} record, @Param("example") ${className}Example example);

	/**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(${className} record);
	
	/**
     * 主键更新数据
     */
    int updateByPrimaryKey(${className} record);
	
}
