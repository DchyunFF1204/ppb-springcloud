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
	
	@Value("${wxRedisHost}")
	public String WX_REDIS_HOST;
	
	@Value("${wxRedisPort}")
	public int WX_REIDS_PORT;

}
