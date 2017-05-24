package com.ppb.client;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

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
	
	@Value("${staticDomain}")
	private String staticDomain;

	@Autowired
	protected freemarker.template.Configuration configuration;

	@PostConstruct
	public void setSharedVariable() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("staticDomain", staticDomain);
		try {
			configuration.setSharedVaribles(variables);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
	}

}