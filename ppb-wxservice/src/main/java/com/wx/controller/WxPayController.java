package com.wx.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.wx.service.WxPayServiceInstance;

/**
 * @author daizy
 * 
 * 微信支付
 *
 */
@RestController
@RequestMapping("/wechat/pay")
public class WxPayController {
	
	/**
	 * 微信统一下单
	 * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	 * @param orderRequest
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/unifiedOrder")
	public Map<String,Object> unifiedOrder(@ModelAttribute WxPayUnifiedOrderRequest orderRequest) throws WxErrorException{
		Map<String,Object> result = new HashMap<String, Object>();
		//orderRequest.setTotalFee(WxPayBaseRequest.yuanToFee(order.getTotalFee()));//元转成分
		WxPayService wxPayService = WxPayServiceInstance.getInstance().getWxPayService();
		wxPayService.getConfig().setTradeType(orderRequest.getTradeType());
		Map<String, String> udata = wxPayService.getPayInfo(orderRequest);
		result.put("status", true);
		result.put("data", udata);
		return result;
	}
	
	/**
	 * 微信支付回写
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws WxErrorException
	 */
	@RequestMapping("/payNotify")
	public String payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, WxErrorException{
		WxPayService wxPayService = WxPayServiceInstance.getInstance().getWxPayService();
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		WxPayOrderNotifyResult result = wxPayService.getOrderNotifyResult(xmlResult);
		// 校验回写数据
		return WxPayOrderNotifyResponse.success("处理成功!");
	}

}
