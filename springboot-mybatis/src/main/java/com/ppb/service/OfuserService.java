package com.ppb.service;

import org.apache.log4j.Logger;

import com.ppb.model.Ofuser;
import com.ppb.model.OfuserExample;
import com.ppb.mapper.OfuserMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Strings;


/**
 * 
 * <br>
 * <b>功能：</b>OfuserService 服务<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> 2017-05-24 14:45:34<br>
 */
@Service("ofuserService")
public class OfuserService {

	private final static Logger log= Logger.getLogger(OfuserService.class);
	

	@Resource
    private OfuserMapper ofuserMapper;
    
    /**
     * 通过id 查询 Ofuser
     */
    public Ofuser selectOfuserByPrimaryKey(Long id){
        return ofuserMapper.selectByPrimaryKey(id);
    }
    
    /**
     * 通过model查询 Ofuser列表信息
     */
    public List<Ofuser> selectOfuserListByModel(Ofuser model){
        OfuserExample example = new OfuserExample();
        OfuserExample.Criteria criteria = example.createCriteria();
        if(model != null){
            if(model.getId() != null){
                criteria.andIdEqualTo(model.getId());
            }
            if(!Strings.isNullOrEmpty(model.getUsername())){
                criteria.andUsernameEqualTo(model.getUsername());
            }
            if(!Strings.isNullOrEmpty(model.getPlainpassword())){
                criteria.andPlainpasswordEqualTo(model.getPlainpassword());
            }
            if(!Strings.isNullOrEmpty(model.getEncryptedpassword())){
                criteria.andEncryptedpasswordEqualTo(model.getEncryptedpassword());
            }
            if(!Strings.isNullOrEmpty(model.getName())){
                criteria.andNameEqualTo(model.getName());
            }
            if(!Strings.isNullOrEmpty(model.getEmail())){
                criteria.andEmailEqualTo(model.getEmail());
            }
            if(!Strings.isNullOrEmpty(model.getCreationdate())){
                criteria.andCreationdateEqualTo(model.getCreationdate());
            }
            if(!Strings.isNullOrEmpty(model.getModificationdate())){
                criteria.andModificationdateEqualTo(model.getModificationdate());
            }
            if(model.getOpTime() != null){
                criteria.andOpTimeEqualTo(model.getOpTime());
            }
        }
        List<Ofuser> list = ofuserMapper.selectByExample(example);
        return list;
    }
    
    /**
     * 保存Ofuser信息
     * @param model 对象参数
     */
    public Map<String,Object> saveOfuser(Ofuser model){
    	Map<String,Object> result = new HashMap<String,Object>();
    	if(model == null){
    		result.put("isSuccess",false);
    		result.put("message","参数为空");
    		return result;
    	}
    	if(model.getId() == null){
    	   ofuserMapper.insert(model);
    	}else{
    	   ofuserMapper.updateByPrimaryKeySelective(model);
    	}
    	result.put("isSuccess",true);
    	result.put("message","save success!");
    	return result;
    }
    
    /**
     * 通过id 物理删除 Ofuser
     */
    public Map<String,Object> delOfuserById(Long id){
    	Map<String,Object> result = new HashMap<String,Object>();
    	int i = ofuserMapper.deleteByPrimaryKey(id);
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
