package org.task.maid.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @AfterThrowing(pointcut = "execution(* org.task.maid.service.*.*(..)) || " +
            "execution(* org.task.maid.controller.*.*(..))  || " +
            "execution(* org.task.maid.mapper.*.*(..))  || " +
            "execution(* org.task.maid.repository.*.*(..))"
            , throwing = "exception")
    public void logAfterThrowing(JoinPoint jp, Exception exception) {
        logger.error("Method with Signature: {},  threw an exception: {}", jp.getSignature(), exception.getMessage());
    }

    @Around("execution(* org.task.maid.service.*.*(..)) || " +
            "execution(* org.task.maid.controller.*.*(..))  || " +
            "execution(* org.task.maid.mapper.*.*(..))  || " +
            "execution(* org.task.maid.repository.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        logger.info("{} executed in {}ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }
}
