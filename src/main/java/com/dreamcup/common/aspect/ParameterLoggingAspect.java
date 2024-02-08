package com.dreamcup.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ParameterLoggingAspect {

    @Around("@annotation(com.dreamcup.common.annotation.ParamLoggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logRequest(joinPoint);

        Object result = joinPoint.proceed(); // 메서드 실행

        logResponse(joinPoint, result);

        return result;
    }

    private void logRequest(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] != null) {
            log.info("[REQUEST] {}.{}: {}", className, methodName, args[0]);
        }
    }

    private void logResponse(ProceedingJoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if (result != null) {
            log.info("[RESPONSE] {}.{}: {}", className, methodName, result);
        }
    }
}
