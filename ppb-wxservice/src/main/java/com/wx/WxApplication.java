package com.wx;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.wx.util.WxConfig;

/**
 * @author daizy
 * 
 * 微信微服务入口
 *
 */
@SpringBootApplication
@EnableConfigurationProperties({WxConfig.class})
public class WxApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(WxApplication.class).web(true).run(args);
	}
}
	