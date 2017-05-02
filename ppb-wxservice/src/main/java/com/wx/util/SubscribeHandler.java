package com.wx.util;

import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/**
 * @author daizy
 * 
 * 关注处理器
 *
 */
@Service
public class SubscribeHandler implements WxMpMessageHandler {
	
	private String wxDomain;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String eventKey = wxMessage.getEventKey();
		if (StringUtils.isNotBlank(eventKey)) {
			// 特殊二维码关注
			String[] scs = eventKey.split("qrscene_");
			if (scs.length > 1) {
				String scene = scs[1];
				String distUserId = scene.split("-")[1];
				WxMpUser wx = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
				if (wx != null) {
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("headpic", wx.getHeadImgUrl());
					param.put("nickname", wx.getNickname());
					param.put("openid", wx.getUnionId());
					param.put("payOpenId", wxMessage.getFromUser());
					param.put("regType", 1);
					param.put("sex", wx.getSex());
					param.put("userName", wx.getNickname());
					param.put("distUserId", distUserId);
					// 用户绑定
					Gson gson = new Gson();
					gson.toJson(param);
					
				} 
			}
		}
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription("<![CDATA[先领券，再买单！]]>");
		item.setPicUrl("<![CDATA["+wxDomain+"/statics/wap/images/generalize/20170408105303.jpg]]>");
		item.setTitle("<![CDATA[点击领取 | 专属您的80元新人优惠券！]]>");
		item.setUrl("<![CDATA["+wxDomain+"/coupon/intoGetRed]]>");
		WxMpXmlOutMessage msg = WxMpXmlOutMessage.NEWS()
				.fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser())
				.addArticle(item)
				.build();
		return msg;
	}

}
