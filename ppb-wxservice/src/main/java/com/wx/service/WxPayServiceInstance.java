package com.wx.service;

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
		wconf.setAppId(WX_APP_ID); // appid
		wconf.setMchId(WX_APP_MCH_ID); //mchid
		wconf.setKeyPath(WX_APP_KEY_PATH);// 证书路径
		wconf.setMchKey(WX_APP_MCH_KEY); // mchkey
		wconf.setNotifyUrl(WX_APP_NOTIFY_URL); // 异步通知
		wxPayService.setConfig(wconf);
	}

	public WxPayService getWxPayService() {
		return wxPayService;
	}

	public void setWxPayService(WxPayService wxPayService) {
		this.wxPayService = wxPayService;
	}

	

}
