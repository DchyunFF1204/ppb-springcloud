package com.wx.util;

import org.springframework.beans.factory.annotation.Value;

public class WxConfig {
	
	@Value("${wxAppId}")
	public String WX_APP_ID;
	
	@Value("${wxAppSecret}")
	public String WX_APP_SECRET;
	
	@Value("${wxAppToken}")
	public String WX_APP_TOKEN;
	
	@Value("${wxAppAse}")
	public String WX_APP_ASE;
	
	@Value("${wxAppMchId}")
	public String WX_APP_MCH_ID;
	
	@Value("${wxAppNotifyUrl}")
	public String WX_APP_NOTIFY_URL;
	
	@Value("${wxAppKeyPath}")
	public String WX_APP_KEY_PATH;
	
	@Value("${wxAppMchKey}")
	public String WX_APP_MCH_KEY;
	
	@Value("${wxRedisHost}")
	public String WX_REDIS_HOST;
	
	@Value("${wxRedisPort}")
	public int WX_REIDS_PORT;

}
