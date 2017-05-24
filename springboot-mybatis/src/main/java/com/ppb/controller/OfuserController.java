package com.ppb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.ppb.model.Ofuser;
import com.ppb.service.OfuserService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
 
/**
 * 
 * <br>
 * <b>功能：</b>OfuserController<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> 2017-05-24 14:45:34<br>
 */ 
@Controller
@RequestMapping("/ofuser") 
public class OfuserController {
	
	private final static Logger log= Logger.getLogger(OfuserController.class);
	
	@Resource
	private OfuserService ofuserService; 
	
	/**
	 * 初始化页面
	 */
	@RequestMapping("/intoOfuserPage") 
	public String intoOfuserPage(){
		return "ofuserPage"; 
	}
	
	/**
	 * id 查询 Ofuser
	 **/
	@RequestMapping("/getOfuserById")
	@ResponseBody
	public Map<String,Object> selectOfuserByPrimaryKey(Long id){
		Map<String,Object> result = new HashMap<String,Object>();
		Ofuser ofuser = ofuserService.selectOfuserByPrimaryKey(id);
		result.put("isSuccess",true);
		result.put("ofuser",ofuser);
		return result;
	}
	
	/**
	 * 通过model分页查询 Ofuser列表信息
     * @param model 对象参数
     * @param page 页码
     * @param rows 每页数目
	 **/
	@RequestMapping("/selectOfuserPgByModel")
	@ResponseBody
	public Map<String,Object> selectOfuserPgByModel(@ModelAttribute Ofuser model,Integer page,Integer rows){
	    Map<String,Object> result = new HashMap<String,Object>();
	    page = page == null?1:page;  
    	rows = rows == null?10:rows;  
    	PageHelper.startPage(page,rows);
    	//PageHelper.orderBy("occur_time desc");  
		List<Ofuser> list = ofuserService.selectOfuserListByModel(model);
		PageInfo<Ofuser> pageInfo = new PageInfo<Ofuser>(list);  
		result.put("isSuccess",true);
		result.put("pageRows",rows); //每页显示数目
		result.put("total",pageInfo.getPages()); // 总页数
		result.put("records",pageInfo.getTotal()); // 总数目
		result.put("rows",list);
		return result;
	}
	
	/**
     * 保存Ofuser信息
     * @param model 对象参数
     */
    @RequestMapping("/saveOfuser")
	@ResponseBody
	public Map<String,Object> saveOfuser(@ModelAttribute Ofuser model){
		return ofuserService.saveOfuser(model);
	}
	
    /**
	 * id 物理删除  Ofuser
	 **/
	@RequestMapping("/delOfuserById")
	@ResponseBody
    public Map<String,Object> delOfuserById(Long id){
    	return ofuserService.delOfuserById(id);
    }
	
}
