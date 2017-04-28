package com.wx.controller;

import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.service.WxMpServiceInstance;

@RestController
@RequestMapping("/wechat/jssdk")
public class WxJsController {
	
	/**
	 * jssdk 鉴权
	 * @param url
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/createJsapiSignature")
	public Map<String,Object> createJsapiSignature(String url) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);
		result.put("status", true);
		result.put("data", wxJsapiSignature);
		return result;
	}

}
