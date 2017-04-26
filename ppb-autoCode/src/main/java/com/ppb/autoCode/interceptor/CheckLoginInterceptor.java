package com.shrc.haiwaiu.webapp.interceptor;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shrc.common.model.Result;
import com.shrc.common.utils.cache.redis.shared.RedisUtils;
import com.shrc.common.utils.codec.AESUtils;
import com.shrc.common.utils.lang.json.JsonUtils;
import com.shrc.common.utils.web.servlet.CookieUtils;
import com.shrc.haiwaiu.webapp.common.annotation.Auth;
import com.shrc.haiwaiu.webapp.common.annotation.PayAuth;
import com.shrc.haiwaiu.webapp.common.annotation.WxShare;
import com.shrc.haiwaiu.webapp.model.userCenter.User;
import com.shrc.haiwaiu.webapp.model.weixinCenter.WXOauthTokenModel;
import com.shrc.haiwaiu.webapp.service.userCenter.CookiesService;
import com.shrc.haiwaiu.webapp.service.weixinCenter.CommonService;
import com.shrc.haiwaiu.webapp.sso.constant.ConstantParam;

/**
 * 登录拦截器<br>
 * 
 * daizy
 * 
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(CheckLoginInterceptor.class);
	
	@Resource
	private CookiesService cookiesService;
	@Resource
	private CommonService commonService;
	@Resource
	public RedisUtils redisUtils;
	
	@Value("${mainDomain}")
	private String mainDomain;
	
	@Value("${cookiesDomain}")
	private String cookiesDomain;
	
	@Value("${wxAppId}")
	private String WX_APP_ID;
	@Value("${wxAppSecret}")
	public String WX_APP_SECRET;

	/**
	 * 拦截器
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("deprecation")
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// 去request请求地址
		StringBuffer  url= request.getRequestURL();
		if (log.isDebugEnabled()) {
			log.debug("CheckLoginInterceptor preHandle url is : " + url);
		}
		String returnUrl = "";
		String q = request.getQueryString();
		if(StringUtils.isNotBlank(q)){
			q = "?"+q;
			returnUrl = URLEncoder.encode(url+q);
		}else{
			returnUrl = URLEncoder.encode(url.toString());
		}
		// 登录信息cookies
		String cookieValue = decodeCookie(request);
		// 用户登录状态
		if(StringUtils.isNotBlank(cookieValue)){
			User user = JsonUtils.fromJson(cookieValue, User.class);
			// 延长cookies有效时间  保证用户是一直登录状态
			cookiesService.doCookie(user, response, request);
			
			// 分销绑定用户信息
			String u = request.getParameter("u");
			if(StringUtils.isNotBlank(u)){
				commonService.bindUser(Long.valueOf(u),user.getUserId(),request, response);
			}
			request.setAttribute(ConstantParam.cookieLoginUser, user);
			String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
			if (ua.indexOf("micromessenger") > 0) { 
				HandlerMethod method = (HandlerMethod)handler;
				PayAuth  payAuth = method.getMethod().getAnnotation(PayAuth.class);
				if(payAuth != null && payAuth.isCheck()){
					String code = request.getParameter("code");
					if(StringUtils.isNotBlank(code)){
						WXOauthTokenModel wx = commonService.getOauthOpenId(code);
						cookiesService.doCookieForPayOpenId(wx, response, request);
						request.setAttribute(ConstantParam.cookiesLoginPayKey, wx.getOpenid());
					}else{
						//判断是否为微信支付请求
						String payOpenId = decodeCookieForPayOpenId(request);
						if(!StringUtils.isNotBlank(payOpenId)){
								String u1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APP_ID+"&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_base&state=A26#wechat_redirect";
								response.sendRedirect(u1);
								return false;
							}
						}
				}
				request.setAttribute("haiwaiuUa", "weixin");
				// 拦截 微信 分享入口  处理微信分享业务
				wxShare(request, handler, user, response);
			}else{
				request.setAttribute("haiwaiuUa", "wap");
			}
			return true;
		}else{
			// 是微信浏览器 
			String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
			if (ua.indexOf("micromessenger") > 0) { 
				request.setAttribute("haiwaiuUa", "weixin");
				String code = request.getParameter("code");
				if(StringUtils.isNotBlank(code)){
					commonService.getWXOauthUserInfo(request, response);
					return true;
				}else{
					String u = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APP_ID+"&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_userinfo&state=A25#wechat_redirect";
					response.sendRedirect(u);
					return false;
				}
			}else{
				// 存储分销的cookies
				String u = request.getParameter("u");
				if(StringUtils.isNotBlank(u)){
					cookiesService.doShareCookies(Long.valueOf(u), request, response);
				}
				request.setAttribute("haiwaiuUa", "wap");
				HandlerMethod method = (HandlerMethod)handler;
				Auth  auth = method.getMethod().getAnnotation(Auth.class);
				////验证超时问题  auth = null，默认验证 
				if( auth != null && auth.verifyCompetency()){
					// 非微信浏览器
					String requestType = request.getHeader("X-Requested-With");
					if(StringUtils.isNotBlank(requestType) && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
						PrintWriter writer = response.getWriter();
						try {
							Result result = new Result(false,-100,"请登录");
							response.setCharacterEncoding("UTF-8");
							writer.print(result.toString());
						} catch (Exception e) {
							log.error("ajax print page error:", e);
						}finally {
							if (writer != null) {
								writer.flush();
								writer.close();
							}
						}
						return false;
					}else{
						response.sendRedirect(mainDomain+"/login/toLogin?returnURL="+returnUrl);
						return false;
					}
				}else{
					return true;
				}
			}
		}
    }
    
    
    /**
     * 分享进入微信商城
     * @param request
     * @param handler
     * @param user
     */
	public void wxShare(HttpServletRequest request,Object handler,User user,HttpServletResponse response){
    	// 判断是否为ajax请求
    	String requestType = request.getHeader("X-Requested-With");
		if(StringUtils.isNotBlank(requestType) && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			
		}else{
			String u = request.getParameter("u");
			if(StringUtils.isNotBlank(u)){
				 cookiesService.doShareCookies(Long.valueOf(u),request,response);
			}
			//特殊分享  注解解析
			HandlerMethod method = (HandlerMethod)handler;
			WxShare ws = method.getMethod().getAnnotation(WxShare.class);
			if( ws != null && ws.isDefaultShare()){
				// 特殊分享
				String shareUrl = "";
				StringBuffer url= request.getRequestURL();
				String returnUrl = "";
				String q = request.getQueryString();
				if(StringUtils.isNotBlank(q)){
					q = "?"+q;
					shareUrl = url+q;
					returnUrl = url+q+"&u="+user.getUserId();
				}else{
					shareUrl = url.toString();
					returnUrl = url.toString()+"?u="+user.getUserId();
				}
		        Result jssdk = commonService.findJsSdk(shareUrl, user.getUserId());
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", ws.title());
				m.put("desc", ws.desc());
				m.put("link", returnUrl);
				m.put("imgUrl", ws.imgUrl());
				m.put("jssdk", jssdk.getAttribute("jssdk"));
				request.setAttribute("wxShareInfo", m);
			}else{
				String ss = mainDomain+"/floor/toIndex?u="+user.getUserId();
				String shareUrl = "";
				StringBuffer url= request.getRequestURL();
				String q = request.getQueryString();
				if(StringUtils.isNotBlank(q)){
					q = "?"+q;
					shareUrl = url+q;
				}else{
					shareUrl = url.toString();
				}
				Result jssdk = commonService.findJsSdk(shareUrl, user.getUserId());
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", "海外u，全球的好吃的");
				m.put("desc", "明星站台，网红力荐的社交型海淘新体验，让您“随时购 放心购”");
				m.put("link", ss);
				m.put("imgUrl", mainDomain+"/statics/wap/img/wxShare/logoShare.jpg");
				m.put("jssdk", jssdk.getAttribute("jssdk"));
				request.setAttribute("wxShareInfo", m);
			}
		}
    }

    /**
     * Description ： cookie解密
     * 
     * @param request
     * @return
     *
     */
    public String decodeCookie(HttpServletRequest request){
        // 判断cookie中的值
        Cookie[] cookies = CookieUtils.getCookies(request);
        if(cookies == null){
        	return null;
        }
        String loginuserInfoStr = null;
        String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieLoginUser, request);
        if(StringUtils.isNotEmpty(cookieValue)){
        	loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
		}
        return loginuserInfoStr;
    }
    
    /**
     * Description ： cookie解密
     * 
     * @param request
     * @return
     *
     */
    public String decodeCookieForPayOpenId(HttpServletRequest request){
        // 判断cookie中的值
        Cookie[] cookies = CookieUtils.getCookies(request);
        if(cookies == null){
        	return null;
        }
        String loginuserInfoStr = null;
        String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookiesLoginPayKey, request);
        if(StringUtils.isNotEmpty(cookieValue)){
        	loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
		}
        return loginuserInfoStr;
    }

	/**
	 * 获取分享 分销userId
	 * @param request
	 * @return
	 */
	public String decodeCookieForShare(HttpServletRequest request){
		// 判断cookie中的值
		Cookie[] cookies = CookieUtils.getCookies(request);
		if(cookies == null){
			return null;
		}
		String loginuserInfoStr = null;
		String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieShareU, request);
		if(StringUtils.isNotEmpty(cookieValue)){
			loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
		}
		return loginuserInfoStr;
	}
}
