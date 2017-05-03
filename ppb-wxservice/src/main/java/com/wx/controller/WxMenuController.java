package com.wx.controller;

import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.service.WxMpServiceInstance;

/**
 * @author daizy
 * 
 * 微信菜单
 *
 */
@RestController
@RequestMapping("/wechat/menu")
public class WxMenuController {
	
	@Autowired
    private Environment env;
	
	/**
	 * 获取自定义菜单
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/menuGet")
	public Map<String,Object> menuGet() throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance(env).getWxMpService();
		WxMpMenu wxMenu = wxMpService.getMenuService().menuGet();
		result.put("status", true);
		result.put("data", wxMenu);
		return result;
	}
	
	/**
	 * 自定义菜单删除
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/menuDelete")
	public Map<String,Object> menuDelete() throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance(env).getWxMpService();
		wxMpService.getMenuService().menuDelete();
		result.put("status", true);
		return result;
	}
	
	/**
	 * 自定义菜单创建
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/menuCreate")
	public Map<String,Object> menuCreate(@ModelAttribute WxMenu menu) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance(env).getWxMpService();
		String menuId = wxMpService.getMenuService().menuCreate(menu);
		result.put("status", true);
		result.put("data", menuId);
		return result;
	}

}
