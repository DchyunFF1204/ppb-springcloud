package com.ppb.mapper;


import com.ppb.model.Ofuser;
import com.ppb.model.OfuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>OfuserMapper 类<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> 2017-05-24 14:45:34 <br>
 */
public interface OfuserMapper {

    /**
     * 动态条件获取总数目
     */
	int countByExample(OfuserExample example);

    /**
     * 动态条件删除数据
     */
	int deleteByExample(OfuserExample example);
	
	/**
     * 主键删除数据
     */
    int deleteByPrimaryKey(Long id);
	
	/**
     * 新增数据
     */
    int insert(Ofuser record);

	/**
     * 动态参数新增数据
     */
    int insertSelective(Ofuser record);

	/**
     * 动态条件查询数据
     */
    List<Ofuser> selectByExample(OfuserExample example);

	/**
     * 主键查询数据
     */
    Ofuser selectByPrimaryKey(Long id);
	
	/**
     * 动态条件更新数据
     */
    int updateByExampleSelective(@Param("record") Ofuser record, @Param("example") OfuserExample example);
	
	/**
     * 按条件更新数据
     */
    int updateByExample(@Param("record") Ofuser record, @Param("example") OfuserExample example);

	/**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(Ofuser record);
	
	/**
     * 主键更新数据
     */
    int updateByPrimaryKey(Ofuser record);
	
}
