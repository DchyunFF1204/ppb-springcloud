package com.wx.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.service.WxMpServiceInstance;

/**
 * @author daizy
 * 
 * 微信二维码
 *
 */
@RestController
@RequestMapping("/wechat/qrcode")
public class WxQrcodeController {
	
	/**
	 * 
	 * 生成微信二维码
	 * 
	 * @param scene_str 场景值
	 * @param type  tmp 临时    last 永久
	 * @throws WxErrorException 
	 */
	@RequestMapping("/qrCodePicture")
	public Map<String,Object> qrCodePicture(String scene_str,String type) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		WxMpQrCodeTicket ticket = null;
		switch (type) {
		case "tmp":
			ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(Integer.valueOf(scene_str), 604000);
			break;
		case "last":
			ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(scene_str);
			break;
		default:
			break;
		}
		File file = wxMpService.getQrcodeService().qrCodePicture(ticket);
		// TODO 二维码图片处理
		result.put("status", true);
		result.put("data", "");
		return result;
	}
	

}
