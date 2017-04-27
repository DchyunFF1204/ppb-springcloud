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

}
