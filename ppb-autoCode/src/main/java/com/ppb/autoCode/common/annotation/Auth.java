package com.shrc.haiwaiu.webapp.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * @author daizy
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
@Documented 
@Component
public @interface  Auth {
	
	/**
	 * 是否验证 true=验证 ,false = 不验证
	 * @return
	 */
	 public boolean verifyCompetency() default true;
	 
	 
}
