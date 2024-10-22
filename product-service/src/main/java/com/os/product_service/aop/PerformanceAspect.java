package com.os.product_service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {


    // @Before : Metot devreye girmeden önce çalışır
    // @After : Metot devreye girdikten sonra çalışır
    // @Around : Metot devreye girmeden ve bittikten sonra çalışır
    // AfterReturning : Metot başarılı olduktan sonra çalışır
    // AfterThrowing : Metot exception döndükten sonra çalışır

    @Around("execution(* com.os.product_service.service.impl.*.*(..))")
    public Object measureGetAllExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("{} executed in {}ms to create category", joinPoint.getSignature(), duration);

        return result;
    }
}
