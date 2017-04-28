package com.wx.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author daizy
 * 
 * 全局异常处理
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 
	 * WxErrorException 异常处理
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = WxErrorException.class)
    @ResponseBody
    public Map<String,Object> jsonErrorHandler(HttpServletRequest req, WxErrorException e) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", e.getError().getErrorCode());
		result.put("status", false);
		result.put("message", e.getError().getErrorMsg());
		result.put("url", req.getRequestURL().toString());
        return result;
    }
}
