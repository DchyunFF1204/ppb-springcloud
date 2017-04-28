package com.wx.util;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import redis.clients.jedis.Jedis;

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
