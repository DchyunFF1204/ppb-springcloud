package com.wx.controller;

import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.service.WxMpServiceInstance;


/**
 * @author daizy
 * 
 * 微信网页授权
 *
 */
@RestController
@RequestMapping("/wechat/oauth")
public class WxOauthController {
	
	@Autowired
    private Environment env;
	
	/**
	 * 获取网页授权链接
	 * @param redirectURI    用户授权完成后的重定向链接，无需urlencode
	 * @param scope  snsapi_base 静默授权  snsapi_userinfo手动授权
	 * @param state  授权标记
	 * @return
	 */
	@RequestMapping("/oauth2buildAuthorizationUrl")
	public Map<String,Object> oauth2buildAuthorizationUrl(String redirectURI, String scope, String state){
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance(env).getWxMpService();
		String url = wxMpService.oauth2buildAuthorizationUrl(redirectURI, scope, state);
		result.put("status", true);
		result.put("data", url);
		return result;
	}
	
	/**
	 * 获取授权用户信息
	 * @param code
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/oauth2getUserInfo")
	public Map<String,Object> oauth2getUserInfo(String code) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance(env).getWxMpService();
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		boolean valid = wxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken);
		// 验证token 是否有效
		if(!valid){
			wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
		}
		WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
		result.put("status", true);
		result.put("data", wxMpUser);
		return result;
	}

}
