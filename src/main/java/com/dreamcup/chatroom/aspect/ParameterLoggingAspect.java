package com.dreamcup.chatroom.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ParameterLoggingAspect {

	@Before("execution(* com.dreamcup.chatroom.controller.*.*(..))")
	public void logParameters(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String[] parameterNames = methodSignature.getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();

		log.info("Method: {}.{}", joinPoint.getTarget().getClass().getSimpleName(), methodSignature.getName());

		for (int i = 0; i < parameterNames.length; i++) {
			log.info("  - {}: {}", parameterNames[i], parameterValues[i]);
		}
	}
}