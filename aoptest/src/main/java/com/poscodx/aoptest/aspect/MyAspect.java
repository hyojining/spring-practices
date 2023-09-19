package com.poscodx.aoptest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
	
	// find 메서드 실행 전에 실행되는 advice
	@Before("execution(public com.poscodx.aoptest.vo.ProductVo com.poscodx.aoptest.service.ProductService.find(String))")
	public void adviceBefore() {
		System.out.println("---- Before Advice ----");
	}
	
	// find 메서드 실행 후에 실행되는 advice
	// 접근지시자 생략 가능 (ex>public)
	@After("execution(com.poscodx.aoptest.vo.ProductVo com.poscodx.aoptest.service.ProductService.find(String))")
	public void adviceAfter() {
		System.out.println("---- After Advice ----");
	}
	
	// find 메서드가 정상적으로 실행된 후에 실행되는 advice
	@AfterReturning("execution(* com.poscodx.aoptest.service.ProductService.find(String))")
	public void adviceAfterReturning() {
		System.out.println("---- AfterReturning Advice ----");
	}
	
	// find 메서드에서 예외가 발생한 경우 실행되는 advice
	@AfterThrowing(value="execution(* *..*.*.*(..))", throwing="ex")
	public void adviceAfterThrowing(Throwable ex) {
		System.out.println("---- AfterThrowing Advice ----" + ex + " ----");
	}
	
	// find 메서드 실행 전후에 실행되는 advice (메서드 실행을 직접 제어 가능)
	@Around("execution(* *..*.ProductService.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable{
		/* before */
		System.out.println("---- Around(before) Advice ----");
		
		/* Point Cut Method 실행 */
		
//		Object[] params = {"Camera"};
//		Object result = pjp.proceed(params);
		
		Object result = pjp.proceed(); // 원본 메서드를 실행하고, 원본 메서드의 반환값을 받아옴
		
		/* after */
		System.out.println("---- Around(after) Advice ----");
		
		return result;
	}
}
