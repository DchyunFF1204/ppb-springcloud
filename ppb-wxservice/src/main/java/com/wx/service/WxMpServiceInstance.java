package com.wx.service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.wx.util.ClickHandler;
import com.wx.util.SubscribeHandler;
import com.wx.util.TextHandler;
import com.wx.util.UnsubscribeHandler;
import com.wx.util.WxConfig;

/**
 * @author daizy
 * 
 * 初始化WxMpService
 * 
 * 单例模式
 *
 */
public class WxMpServiceInstance extends WxConfig {

	private WxMpService wxMpService;
	private WxMpConfigStorage wxMpConfigStorage;
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
	
	public static WxMpServiceInstance getInstance() {
		if (instance == null) {
			try {
				instance = new WxMpServiceInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private WxMpServiceInstance() throws Exception {
		wxMpService = new WxMpServiceImpl();
		// 读取配置文件
		/*InputStream inputStream = WxMpServiceInstance.class
				.getResourceAsStream("/config/weixin.xml");
		wxMpConfigStorage = WxMpXMLInMemoryConfigStorage.fromXml(inputStream);*/
		WxMpInRedisConfigStorage config = new WxMpInRedisConfigStorage();
		Jedis jedis = new Jedis(WX_REDIS_HOST, WX_REIDS_PORT);
		config.setJedis(jedis);
		config.setAppId(WX_APP_ID); // 设置微信公众号的appid
		config.setSecret(WX_APP_SECRET); // 设置微信公众号的app corpSecret
		config.setToken(WX_APP_TOKEN); // 设置微信公众号的token
		config.setAesKey(WX_APP_ASE); // 设置微信公众号的EncodingAESKey
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
