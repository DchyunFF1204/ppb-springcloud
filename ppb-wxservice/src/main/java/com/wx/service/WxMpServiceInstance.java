package com.wx.service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import redis.clients.jedis.Jedis;

import com.wx.util.ClickHandler;
import com.wx.util.SubscribeHandler;
import com.wx.util.TextHandler;
import com.wx.util.UnsubscribeHandler;

/**
 * @author daizy
 * 
 * 初始化WxMpService
 * 
 * 单例模式
 *
 */
public class WxMpServiceInstance {
	

	private WxMpService wxMpService;
	private WxMpInRedisConfigStorage wxMpConfigStorage;
	private WxMpMessageRouter wxMpMessageRouter;
	private static WxMpServiceInstance instance = null;
	
	@Autowired
	private SubscribeHandler subscribeHandler;
	
	@Autowired
	private UnsubscribeHandler unsubscribeHandler;
	
	@Autowired
	private ClickHandler clickHandler;
	
	@Autowired
	private TextHandler textHandler;
	
	public static WxMpServiceInstance getInstance(Environment env) {
		if (instance == null) {
			try {
				instance = new WxMpServiceInstance(env);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private WxMpServiceInstance(Environment env) throws Exception {
		wxMpService = new WxMpServiceImpl();
		// 读取配置文件
		/*InputStream inputStream = WxMpServiceInstance.class
				.getResourceAsStream("/config/weixin.xml");
		wxMpConfigStorage = WxMpXMLInMemoryConfigStorage.fromXml(inputStream);*/
		wxMpConfigStorage = new WxMpInRedisConfigStorage();
		Jedis jedis = new Jedis(env.getProperty("wx.redisHost"), Integer.valueOf(env.getProperty("wx.redisPort")));
		wxMpConfigStorage.setJedis(jedis);
		wxMpConfigStorage.setAppId(env.getProperty("wx.appId")); // 设置微信公众号的appid
		wxMpConfigStorage.setSecret(env.getProperty("wx.appSecret")); // 设置微信公众号的app corpSecret
		wxMpConfigStorage.setToken(env.getProperty("wx.appToken")); // 设置微信公众号的token
		wxMpConfigStorage.setAesKey(env.getProperty("wx.appAse")); // 设置微信公众号的EncodingAESKey
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
		wxMpMessageRouter
			.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE) // 关注事件
				.handler(subscribeHandler)
				.end()
			.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE) // 取消关注事件
				.handler(unsubscribeHandler)
				.end()
			.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_EVENT)
				.event(WxConsts.EVT_SCAN) // 重复关注事件
				.handler(subscribeHandler)
				.end()
			.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_EVENT)
				.event(WxConsts.EVT_CLICK) // CLICK事件
				.handler(clickHandler)
				.end()
			.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_TEXT) // TEXT 消息
				.handler(textHandler)
				.end();
	}

	public WxMpService getWxMpService() {
		return wxMpService;
	}

	public WxMpConfigStorage getWxMpConfigStorage() {
		return wxMpConfigStorage;
	}

	public WxMpMessageRouter getWxMpMessageRouter() {
		return wxMpMessageRouter;
	}

}
