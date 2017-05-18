package com.ppb.client;

import com.alipay.api.AlipayApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
	 * AlipayApiException 异常处理
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = AlipayApiException.class)
    @ResponseBody
    public Map<String,Object> jsonErrorHandler(HttpServletRequest req, AlipayApiException e) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", e.getErrCode());
		result.put("status", false);
		result.put("message", e.getErrMsg());
		result.put("url", req.getRequestURL().toString());
        return result;
    }
}
