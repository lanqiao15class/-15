package com.lanqiao.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *   注释到某一方法上, 这个方法是controller 类, 而且必须经过RequestMapping 注解
 *   指定一个方法, 具有哪些功能需要调用这个方法. 
 *   
 * @author chen baoji
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Functions {

	int[] value();
	
}
