package com.lanqiao.aop;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;

/*@Aspect
@Component*/
public class ServiceInterceptor {
	protected static final Log log = org.apache.commons.logging.LogFactory.getLog(ServiceInterceptor.class);

	public ServiceInterceptor() {
		System.err.println("create ServiceInterceptor...");

	}

	/**
	 * 任何service 方法执行之前触发
	 * @param point
	 */
	@Before("execution(* com.lanqiao.service.*.*(..))")
	public void doBefore(JoinPoint point) {
		//String fname = point.getSourceLocation().getFileName();

		System.out.println("=========AOP doBefore ===========");
		// System.out.println ("Filename:" + fname);
		System.out.println("@Before：目标方法为：" + point.getSignature().getDeclaringTypeName() + "."
				+ point.getSignature().getName());
		System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
		System.out.println("@Before：目标对象为：" + point.getTarget());
		System.out.println("=========AOP doBefore ===========");

	}

	/**
	 * service 方法异常时触发
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "execution(* com.lanqiao.service.*.*(..))", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		System.out.println("=========doAfterThrowing ===========");
		System.out.println("异常代码:" + e.getClass().getName());
		System.out.println("异常信息:" + e.getMessage());
		System.out.println("异常方法:"
				+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
		System.out.println("=========doAfterThrowing ===========");

	}

	/**
	 * service 方法执行后，返回结果时触发
	 * @param joinPoint
	 * @param retVal
	 * 
	 */
	@AfterReturning(value = "execution(* com.lanqiao.service.*.*(..))", argNames = "retVal", returning = "retVal")
	public void AfterReturning(JoinPoint joinPoint, Object retVal) {
		System.out.println("=========@AfterReturning ===========");

		String action = joinPoint.getSignature().getName();
		String service = joinPoint.getSignature().getDeclaringTypeName();
		Object[] object = joinPoint.getArgs();
		String name = joinPoint.getSignature().getName();
		StringBuffer sbuff = new StringBuffer();
		for (int i = 0; object != null && i < object.length; i++) {
			Object o = object[i];
			/*sbuff.append("arg["+i+"]=" +o.toString() );*/
		}
		log.info("action:" + action);
		log.info("service:" + service);
		log.info("name:" + name);
		log.info("args:" + sbuff);
		System.out.println("=========@AfterReturning ===========");

	}
}
