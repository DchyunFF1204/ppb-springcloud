package com.wx.controller;

import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.service.WxMpServiceInstance;

/**
 * @author daizy
 * 
 * 微信消息处理
 *
 */
@RestController
@RequestMapping("/wechat/message")
public class WxMessageController {
	
	/**
	 * 发送客服消息
	 * @param message  
	 * @return
	 * @throws WxErrorException 
	 */
	@RequestMapping("/sendKefuMessage")
	public Map<String,Object> sendKefuMessage(@ModelAttribute WxMpKefuMessage message) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpKefuService kefuService = WxMpServiceInstance.getInstance().getWxMpService().getKefuService();
		boolean b = kefuService.sendKefuMessage(message);
		result.put("status", b);
		result.put("message", b==true?"消息发送成功":"消息发送失败");
		return result;
	}
	
	/**
	 * 发送模版消息
	 * @param templateMessage
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/sendTemplateMsg")
	public Map<String,Object> sendTemplateMsg(@ModelAttribute WxMpTemplateMessage templateMessage) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpTemplateMsgService wxMpTemplateMsgService = WxMpServiceInstance.getInstance().getWxMpService().getTemplateMsgService();
		String b = wxMpTemplateMsgService.sendTemplateMsg(templateMessage);
		result.put("status", true);
		result.put("data", b);
		return result;
	}
	
	
	

}
