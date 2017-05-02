package com.wx.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="wx")
public class WxConfig {
	
	public String appId;
	
	public String appSecret;
	
	public String appToken;
	
	public String appAse;
	
	public String appMchId;
	
	public String appNotifyUrl;
	
	public String appKeyPath;
	
	public String appMchKey;
	
	public String redisHost;
	
	public int redisPort;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public String getAppAse() {
		return appAse;
	}

	public void setAppAse(String appAse) {
		this.appAse = appAse;
	}

	public String getAppMchId() {
		return appMchId;
	}

	public void setAppMchId(String appMchId) {
		this.appMchId = appMchId;
	}

	public String getAppNotifyUrl() {
		return appNotifyUrl;
	}

	public void setAppNotifyUrl(String appNotifyUrl) {
		this.appNotifyUrl = appNotifyUrl;
	}

	public String getAppKeyPath() {
		return appKeyPath;
	}

	public void setAppKeyPath(String appKeyPath) {
		this.appKeyPath = appKeyPath;
	}

	public String getAppMchKey() {
		return appMchKey;
	}

	public void setAppMchKey(String appMchKey) {
		this.appMchKey = appMchKey;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

}
