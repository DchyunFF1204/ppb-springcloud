package com.ppb.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import freemarker.template.TemplateModelException;

/**
 * @author daizy
 * 
 * 重构freemarkerconfig    
 * 
 * 动态赋值sharedVaribles  传值到ftl
 *
 */
@Configuration
public class FreeMarkerConfig {
	
	@Value("${mainDomain}")
	private String mainDomain;

	@Autowired
	protected freemarker.template.Configuration configuration;

	/**
	 * 动态赋值传递
	 **/
	@PostConstruct
	public void setSharedVariable() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("mainDomain", mainDomain);
		try {
			configuration.setSharedVaribles(variables);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
	}

}