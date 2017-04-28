package com.wx.controller;

import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.service.WxMpServiceInstance;

/**
 * @author daizy
 * 
 * 微信用户
 *
 */
@RestController
@RequestMapping("/wechat/user")
public class WxUserController {
	
	/**
	 * 通过openid 获取用户信息   默认zh_CN
	 * @param openid
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/userInfo")
	public Map<String,Object> userInfo(String openid) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		WxMpUser user = wxMpService.getUserService().userInfo(openid);
		result.put("status", true);
		result.put("data", user);
		return result;
	}
	
	
	/**
	 * 分页查询用户列表
	 * @param next_openid
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/userList")
	public Map<String,Object> userList(String next_openid) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		WxMpUserList userList = wxMpService.getUserService().userList(next_openid);
		result.put("status", true);
		result.put("data", userList);
		return result;
	}

}
