package com.wx.service;

import org.springframework.core.env.Environment;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

/**
 * @author daizy
 * 
 * 初始化WxMpService
 * 
 * 单例模式
 *
 */
public class WxPayServiceInstance {
	
	private WxPayService wxPayService;
	private static WxPayServiceInstance instance = null;
	
	public static WxPayServiceInstance getInstance(Environment env) {
		if (instance == null) {
			try {
				instance = new WxPayServiceInstance(env);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private WxPayServiceInstance(Environment env) throws Exception {
		wxPayService = new WxPayServiceImpl();
		WxPayConfig wconf = new WxPayConfig();
		wconf.setAppId(env.getProperty("wx.appId")); // appid
		wconf.setMchId(env.getProperty("wx.appMchId")); //mchid
		wconf.setKeyPath(env.getProperty("wx.appKeyPath"));// 证书路径
		wconf.setMchKey(env.getProperty("wx.appMchKey")); // mchkey
		wconf.setNotifyUrl(env.getProperty("wx.appNotifyUrl")); // 异步通知
		wxPayService.setConfig(wconf);
	}

	public WxPayService getWxPayService() {
		return wxPayService;
	}

	public void setWxPayService(WxPayService wxPayService) {
		this.wxPayService = wxPayService;
	}

	

}
