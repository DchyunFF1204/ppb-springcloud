package com.wx.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.wx.util.WxConfig;

/**
 * @author daizy
 * 
 * 初始化WxMpService
 * 
 * 单例模式
 *
 */
public class WxPayServiceInstance extends WxConfig {
	
	@Autowired
	private WxConfig wxConfig;

	private WxPayService wxPayService;
	private static WxPayServiceInstance instance = null;
	
	public static WxPayServiceInstance getInstance() {
		if (instance == null) {
			try {
				instance = new WxPayServiceInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private WxPayServiceInstance() throws Exception {
		wxPayService = new WxPayServiceImpl();
		WxPayConfig wconf = new WxPayConfig();
		wconf.setAppId(wxConfig.getAppId()); // appid
		wconf.setMchId(wxConfig.getAppMchId()); //mchid
		wconf.setKeyPath(wxConfig.getAppMchId());// 证书路径
		wconf.setMchKey(wxConfig.getAppMchKey()); // mchkey
		wconf.setNotifyUrl(wxConfig.getAppNotifyUrl()); // 异步通知
		wxPayService.setConfig(wconf);
	}

	public WxPayService getWxPayService() {
		return wxPayService;
	}

	public void setWxPayService(WxPayService wxPayService) {
		this.wxPayService = wxPayService;
	}

	

}
